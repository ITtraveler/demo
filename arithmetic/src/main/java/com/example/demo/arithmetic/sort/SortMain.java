/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.arithmetic.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author guosheng.huang
 * @version BubbingSort.java, v 0.1 2019年06月03日 21:34 guosheng.huang Exp $
 */
public class SortMain {
    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new Random().nextInt(100));
        }

        System.out.println(data);
        Sort bubbleSort = new BubbleSort();
        bubbleSort.sort(data);
        System.out.println(data);

    }
}
