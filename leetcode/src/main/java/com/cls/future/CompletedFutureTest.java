package com.cls.future;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CompletedFutureTest {

    private static Random random = new Random();

    private static long t = System.currentTimeMillis();

    static int getMoreData() {
        System.out.println("begin to start complete");
        try{
            Thread.sleep(10000);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("cost: " + (System.currentTimeMillis()-t));
        return random.nextInt(1000);
    }

    public static void main(String[] args) throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletedFutureTest::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(f.get());
        System.out.println("123");
        System.in.read();
    }

}
