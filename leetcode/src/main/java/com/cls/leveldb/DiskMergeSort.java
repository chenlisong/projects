package com.cls.leveldb;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiskMergeSort implements Closeable {

    public static List<String> generateFiles(int n, int minEntries, int maxEntries) {
        List<String> files = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String filename = "input-" + i + ".txt";
            PrintWriter writer;
            try {
                writer = new PrintWriter(new FileOutputStream(filename));
                int entries = ThreadLocalRandom.current().nextInt(minEntries, maxEntries);
                List<Integer> nums = new ArrayList<>();
                for (int k = 0; k < entries; k++) {
                    int num = ThreadLocalRandom.current().nextInt(10000000);
                    nums.add(num);
                }
                Collections.sort(nums);
                for (int num : nums) {
                    writer.println(num);
                }
                writer.close();
            } catch (FileNotFoundException e) {
            }
            files.add(filename);
        }
        return files;
    }

    private List<MergeSource> sources;
    private MergeOut out;

    public DiskMergeSort(List<String> files, String outFilename) {
        this.sources = new ArrayList<>();
        for (String filename : files) {
            this.sources.add(new MergeSource(filename));
        }
        this.out = new MergeOut(outFilename);
    }

    static class MergeOut implements Closeable {
        private PrintWriter writer;

        public MergeOut(String filename) {
            try {
                this.writer = new PrintWriter(new FileOutputStream(filename));
            } catch (FileNotFoundException e) {
            }
        }

        public void write(Bin bin) {
            writer.println(bin.num);
        }

        @Override
        public void close() throws IOException {
            writer.flush();
            writer.close();
        }
    }

    static class MergeSource implements Closeable {
        private BufferedReader reader;
        private String cachedLine;

        public MergeSource(String filename) {
            try {
                FileReader fr = new FileReader(filename);
                this.reader = new BufferedReader(fr);
            } catch (FileNotFoundException e) {
            }
        }

        public boolean hasNext() {
            String line;
            try {
                line = this.reader.readLine();
                if (line == null || line.isEmpty()) {
                    return false;
                }
                this.cachedLine = line.trim();
                return true;
            } catch (IOException e) {
            }
            return false;
        }

        public int next() {
            if (this.cachedLine == null) {
                if (!hasNext()) {
                    throw new IllegalStateException("no content");
                }
            }
            int num = Integer.parseInt(this.cachedLine);
            this.cachedLine = null;
            return num;
        }

        @Override
        public void close() throws IOException {
            this.reader.close();
        }
    }

    static class Bin implements Comparable<Bin> {
        int num;
        MergeSource source;

        Bin(MergeSource source, int num) {
            this.source = source;
            this.num = num;
        }

        @Override
        public int compareTo(Bin o) {
            return this.num - o.num;
        }
    }

    public List<Bin> prepare() {
        List<Bin> bins = new ArrayList<>();
        for (MergeSource source : sources) {
            Bin newBin = new Bin(source, source.next());
            bins.add(newBin);
        }
        Collections.sort(bins);
        return bins;
    }

    public void sort() {
        List<Bin> bins = prepare();
        while (true) {
            MergeSource current = bins.get(0).source;
            if (current.hasNext()) {
                Bin newBin = new Bin(current, current.next());
                int index = Collections.binarySearch(bins, newBin);
                if (index == 0 || index == -1) {
                    this.out.write(newBin);
                    if (index == -1) {
                        throw new IllegalStateException("impossible");
                    }
                } else {
                    if (index < 0) {
                        index = -index - 1;
                    }
                    bins.add(index, newBin);
                    Bin minBin = bins.remove(0);
                    this.out.write(minBin);
                }
            } else {
                Bin minBin = bins.remove(0);
                this.out.write(minBin);
                if (bins.isEmpty()) {
                    break;
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        for (MergeSource source : sources) {
            source.close();
        }
        this.out.close();
    }

    public static void main(String[] args) throws IOException {
        List<String> inputs = DiskMergeSort.generateFiles(100, 10000, 20000);
        // 运行多次看算法耗时
        for (int i = 0; i < 20; i++) {
            DiskMergeSort sorter = new DiskMergeSort(inputs, "output.txt");
            long start = System.currentTimeMillis();
            sorter.sort();
            long duration = System.currentTimeMillis() - start;
            System.out.printf("%dms\n", duration);
            sorter.close();
        }
    }
}
