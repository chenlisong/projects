import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Abc {

    public static void main(String[] args) {
        List<String> sl = new ArrayList<>();
        sl.add("1,28,300.6,San Francisco");
        sl.add("4,5,209.1,San Francisco");
        sl.add("20,7,203.4,Oakland");
        sl.add("6,8,202.9,San Francisco");
        sl.add("6,10,199.8,San Francisco");

        sl.add("1,16,190.5,San Francisco");
        sl.add("6,29,185.3,San Francisco");
        sl.add("7,20,180.0,Oakland");
        sl.add("6,21,162.2,San Francisco");
        sl.add("2,18,161.7,Jose");

        sl.add("2,30,149.8,Jose");
        sl.add("3,76,146.7,San Francisco");
        sl.add("2,14,141.8,Jose");

        List<String> result = paginate(5, sl);
        //for(String unit: result) {
        //    System.out.println(unit);
        //}
    }

    public static List<String> paginate(int resultsPerPage, List<String> data) {
        List<String> result = new ArrayList<>();
        List<String> skipList = new ArrayList<>();

        Set<Integer> hostids = new HashSet<>();
        dataLoop: for(String unit: data) {
            String[] unitArray = unit.split(",");
            int hostId = Integer.parseInt(unitArray[0]);
            if(hostids.contains(hostId)) {
                skipList.add(unit);
                continue dataLoop;
            }else {
                hostids.add(hostId);
                result.add(unit);
            }
            if(result.size() >= resultsPerPage) {
                break dataLoop;
            }
        }

        if(result.size() < resultsPerPage && skipList.size() > 0) {
            for(String unit: skipList) {
                if(result.size() >= resultsPerPage) {
                    result.add(unit);
                }
            }
        }

        if(result.size() > 0) {
            for(String resultUnit: result) {
                data.remove(resultUnit);
            }
        }
        for(String printUnit: data) {
            System.out.println(printUnit);

        }
        System.out.println("-------");

        if(data.size() > 0) {
            paginate(resultsPerPage, data);
            //result.addAll(paginate(resultsPerPage, data));
        }

        return result;
    }
}
