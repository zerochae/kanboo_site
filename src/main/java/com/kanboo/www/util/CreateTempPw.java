package com.kanboo.www.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CreateTempPw {

    public static String create() {

        StringBuilder result = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        Random random = new Random();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }

        for (char i = 'a'; i < 'z'; i++) {
            list.add(String.valueOf(i));
        }

        for (char i = 'A'; i < 'Z'; i++) {
            list.add(String.valueOf(i));
        }

        while (set.size() < 6) {
            int index = random.nextInt(list.size());
            set.add(list.get(index));
        }

        for (String s : set) {
            result.append(s);
        }

        return result.toString();
    }
}
