package com.example.demo.piece.seckill;

import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guosheng.huang
 * @version 1: SeckillTest, v1.0 2019年07月30日 17:25 guosheng.huang Exp $
 */
public class SeckillTest {
    static AtomicInteger count = new AtomicInteger(0);
    static Stack<String> goodsStack = new Stack<>();
    static {
        for (int i = 0; i < 100; i++) {
            goodsStack.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int people = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(people);
        for (int i = 0; i < people; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    buy();
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }

    private static boolean buy() {
        try {
            int last = goodsStack.size();
            if (last > 0) {
                String goodsId = goodsStack.pop();
                System.out.println("抢购成功,goodsId:" + goodsId + " count:" + count.getAndIncrement());
            } else {
                System.out.println("抢购失败");
            }
        } catch (Exception e) {
            System.out.println("抢购失败");
            return false;
        }
        return true;
    }
}
