package com.example.demo.piece.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guosheng.huang
 * @version 1: BloomfilterTest, v1.0 2019年07月25日 14:23 guosheng.huang Exp $
 * BloomFiler又叫布隆过滤器，下面举例说明BlooFilter的实现原理：
 * 比如你有10个Url，你完全可以创建一长度是100bit的数组，然后对url分别用5个不同的hash函数进行hash，得到5个hash后的值，
 * 这5个值尽可能的保证均匀分布在100个bit的范围内。然后把* 5个hash
 * 值对应的bit位都置为1，判断一个url是否已经存在时，一次看5个bit位是否为1就可以了，如果有任何一个不为1，那么说明这个url不存在。
 * 这里需要注意的是，如果对应的bit位值都为1，* 那么也不能肯定这个url一定存在
 * 尽管BloomFilter已经尽可能的减小hash碰撞的概率了，但是，并不能彻底消除，
 *
 * 应用：
 * 黑名单
 * 比如邮件黑名单过滤器，判断邮件地址是否在黑名单中
 * 排序(仅限于BitSet)
 * 仔细想想，其实BitSet在set(int value)的时候，“顺便”把value也给排序了。
 * 网络爬虫
 * 判断某个URL是否已经被爬取过
 * K-V系统快速判断某个key是否存在
 * 典型的例子有Hbase，Hbase的每个Region中都包含一个BloomFilter，用于在查询时快速判断某个key在该region中是否存在，如果不存在，直接返回，节省掉后续的查询。
 */
public class BloomfilterTest {
    public static void main(String[] args) {
        try {
            int size = 1000000;
            BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
            Instant now0 = Instant.now();
            for (int i = 0; i < size; i++) {
                bloomFilter.put(i);
            }
            Instant now1 = Instant.now();
            System.out.println("录入" + size + "条数据共耗时:" + (now1.toEpochMilli() - now0.toEpochMilli()));

            Instant now2 = Instant.now();
            for (int i = 0; i < size; i++) {
                if (!bloomFilter.mightContain(i)) {
                    System.out.println("有漏掉的");
                }
            }
            Instant now3 = Instant.now();
            System.out.println("判断" + size + "条在过滤器中的数据共耗时:" + (now3.toEpochMilli() - now2.toEpochMilli()));

            Instant now4 = Instant.now();
            List<Integer> list = new ArrayList<Integer>(1000);
            for (int i = size + size / 10; i < size + (size * 2) / 10; i++) {
                if (bloomFilter.mightContain(i)) {
                    list.add(i);
                }
            }
            Instant now5 = Instant.now();
            System.out.println("判断" + size / 10 + "条不在过滤器中的数据共耗时:" + (now5.toEpochMilli() - now4.toEpochMilli()));

            System.out.println("有误判的数量：" + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
