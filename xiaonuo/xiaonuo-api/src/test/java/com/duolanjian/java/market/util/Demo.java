package com.duolanjian.java.market.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Demo {

    public static String conver2Match(String text, Object... params) {

        if(params.length == 0) {
            return text;
        }

        String result = "";
        int index = 0;

        boolean nextMatchFlag = false;
        for(char unit: text.toCharArray()) {

            if(nextMatchFlag) {
                switch (unit) {
                    case 'd' : result += (int) params[index++]; break;
                    case 'c' : result += (char) params[index++]; break;
                    case 's' : result += params[index++].toString(); break;
                    default: result += "%" + unit;
                }
                nextMatchFlag = false;
            }else if(unit == '%') {
                nextMatchFlag = true;
            }else {
                nextMatchFlag = false;
                result += unit;
            }

        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(String.format("", ""));
        System.out.println(conver2Match("hello world, %s, time: %s", "cls", new Date().getTime()));

        Logger logger = LoggerFactory.getLogger(Demo.class);
        logger.info("", "");
    }

}
