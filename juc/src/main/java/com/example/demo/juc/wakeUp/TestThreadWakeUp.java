/**
 * beedo.com Inc.
 * Copyright (c) 2018- All Rights Reserved.
 */
package com.example.demo.juc.wakeUp;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * wait / notifyAll 实现线程唤醒
 *
 * @author guosheng.huang
 * @version TestThreadWakeUp.java, v 0.1 2019年05月29日 22:36 guosheng.huang Exp $
 */
@Slf4j
public class TestThreadWakeUp {
    public static void main(String[] args) {
        Shop shop = new Shop();

        Productor productor = new Productor(shop);
        Consumer consumer = new Consumer(shop);
        new Thread(productor).start();
        new Thread(consumer).start();
        new Thread(productor).start();
        new Thread(consumer).start();
        new Thread(productor).start();
        new Thread(consumer).start();

    }


    static class Shop {
        private int goodsNum = 0;

        private int maxNum = 10;

        public synchronized void in(int goodsNum) {
            log.info("in,当前库存：{}", this.goodsNum);
            while (this.goodsNum >= maxNum) {// 多个线程调用时防止虚假唤醒
                log.info("库存已满");
                try {

                    this.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.goodsNum += goodsNum;

            this.notifyAll();
        }

        public synchronized void out(int goodsNum) {
            log.info("out,当前库存：{}", this.goodsNum);
            while (this.goodsNum <= 0) {// 多个线程调用时防止虚假唤醒
                log.info("库存已空");
                try {

                    this.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            this.goodsNum -= goodsNum;

            this.notifyAll();
        }

    }

    static class Productor implements Runnable {
        private Shop shop;

        public Productor(Shop shop) {
            this.shop = shop;
        }

        private void product() {
            this.shop.in(1);
        }

        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                product();
            }
        }
    }

    static class Consumer implements Runnable {
        private Shop shop;

        public Consumer(Shop shop) {
            this.shop = shop;
        }

        private void consumer() {
            this.shop.out(1);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                consumer();
            }
        }
    }
}
