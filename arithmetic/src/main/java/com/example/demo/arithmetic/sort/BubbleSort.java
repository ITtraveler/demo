/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.arithmetic.sort;

import java.util.List;

/**
 * @author guosheng.huang
 * @version BubbleSort.java, v 0.1 2019年06月03日 21:40 guosheng.huang Exp $
 */
public class BubbleSort implements Sort {
    @Override
    public void sort(List<Integer> datas) {

        if (datas == null || datas.isEmpty()) {
            return;
        }

        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < datas.size() - i - 1; j++) {
                if (datas.get(j) > datas.get(j + 1)) {
                    int temp = datas.get(j);
                    datas.set(j, datas.get(j + 1));
                    datas.set(j + 1, temp);
                }
            }
        }

    }
}
