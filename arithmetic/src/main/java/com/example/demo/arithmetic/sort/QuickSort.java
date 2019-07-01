/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.arithmetic.sort;

import java.util.List;

/**
 * @author guosheng.huang
 * @version QuickSort.java, v 0.1 2019年06月03日 22:00 guosheng.huang Exp $
 */
public class QuickSort implements Sort {
    @Override
    public void sort(List<Integer> data) {
        quickSort(data, 0, data.size());
    }

    private void quickSort(List<Integer> data, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(data, left, right);
            quickSort(data, left, partitionIndex - 1);
            quickSort(data, partitionIndex + 1, right);
        }
    }

    private int partition(List<Integer> data, int left, int right) {
        int pivot = left;                   // 设定基准值（pivot）
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (data.get(i) < data.get(pivot)) {
                swap(data, i, index);
                index++;
            }
        }
        swap(data, pivot, index - 1);
        return index - 1;
    }

    private void swap(List<Integer> data, int i, int j) {
        if (data.get(i) > data.get(j)) {
            int temp = data.get(j);
            data.set(j, data.get(i));
            data.set(i, temp);
        }
    }
}
