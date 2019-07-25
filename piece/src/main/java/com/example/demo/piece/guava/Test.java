/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2019 All Rights Reserved.
 */
package com.example.demo.piece.guava;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guosheng.huang
 * @version 1: Test, v1.0 2019年07月25日 15:59 guosheng.huang Exp $
 */
public class Test {
    public static void main(String[] args) {
        // testOptional();
        // testPreconditions();
        // testOrdering();
        // testObjects();
        // testRange();
        // testSplitter();
        // testJoiner();
        // testCharMatcher();
        // testCaseFormat();
        testCaches();
    }

    /**
     * Optional用于包含非空对象的不可变对象。 Optional对象，用于不存在值表示null。这个类有各种实用的方法，
     * 以方便代码来处理为可用或不可用，而不是检查null值。
     */
    private static void testOptional() {
        Integer value1 = null;
        Integer value2 = new Integer(10);
        //Optional.fromNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.fromNullable(value1);
        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println(a.or(1) + b.get());
    }

    /**
     * Preconditions提供静态方法来检查方法或构造函数，被调用是否给定适当的参数。
     * 它检查的先决条件。其方法失败抛出IllegalArgumentException。
     */
    private static void testPreconditions() {
        Preconditions.checkArgument(true, "error,please check code");
        Preconditions.checkArgument(false, "error,please check code2", 40, 1);
    }

    /**
     * Ordering(排序)可以被看作是一个丰富的比较具有增强功能的链接，多个实用方法，多类型排序功能等。
     */
    private static void testOrdering() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(2);
        numbers.add(15);
        numbers.add(51);
        numbers.add(53);
        numbers.add(35);
        numbers.add(45);
        numbers.add(32);

        // 正序 从小-大
        Ordering ordering = Ordering.natural();
        System.out.println("Input List: ");
        System.out.println(numbers);

        Collections.sort(numbers, ordering);
        System.out.println("Sorted List: ");
        System.out.println(numbers);

        System.out.println("======================");
        System.out.println("List is sorted: " + ordering.isOrdered(numbers));
        System.out.println("Minimum: " + ordering.min(numbers));
        System.out.println("Maximum: " + ordering.max(numbers));
        // 正序 从大-小
        Collections.sort(numbers, ordering.reverse());
        System.out.println("Reverse: " + numbers);

        numbers.add(null);
        System.out.println("Null added to Sorted List: ");
        System.out.println(numbers);

        Collections.sort(numbers, ordering.nullsFirst());
        System.out.println("Null first Sorted List: ");
        System.out.println(numbers);
        System.out.println("======================");

        List<String> names = new ArrayList<String>();
        names.add("Ram");
        names.add("Shyam");
        names.add("Mohan");
        names.add("Sohan");
        names.add("Ramesh");
        names.add("Suresh");
        names.add("Naresh");
        names.add("Mahesh");
        names.add(null);
        names.add("Vikas");
        names.add("Deepak");

        System.out.println("Another List: ");
        System.out.println(names);

        Collections.sort(names, ordering.nullsFirst().reverse());
        System.out.println("Null first then reverse sorted list: ");
        System.out.println(names);
    }

    /**
     * Objects类提供适用于所有对象，如equals, hashCode等辅助函数
     */
    private static void testObjects() {
        Integer a = 2;
        Integer b = 2;
        int c = 2;
        System.out.println(Objects.equal(a, b));
        System.out.println(Objects.equal(a, c));
        System.out.println(Objects.hashCode(a, b));
    }

    /**
     * Range 表示一个间隔或一个序列。它被用于获取一组数字/串在一个特定范围之内。
     */
    private static void testRange() {
        //create a range [a,b] = { x | a <= x <= b}
        Range<Integer> range1 = Range.closed(0, 9);
        System.out.print("[0,9] : ");
        printRange(range1);
        System.out.println("5 is present: " + range1.contains(5));
        System.out.println("(1,2,3) is present: " + range1.containsAll(Ints.asList(1, 2, 3)));
        System.out.println("Lower Bound: " + range1.lowerEndpoint());
        System.out.println("Upper Bound: " + range1.upperEndpoint());

        //create a range (a,b) = { x | a < x < b}
        Range<Integer> range2 = Range.open(0, 9);
        System.out.print("(0,9) : ");
        printRange(range2);

        //create a range (a,b] = { x | a < x <= b}
        Range<Integer> range3 = Range.openClosed(0, 9);
        System.out.print("(0,9] : ");
        printRange(range3);

        //create a range [a,b) = { x | a <= x < b}
        Range<Integer> range4 = Range.closedOpen(0, 9);
        System.out.print("[0,9) : ");
        printRange(range4);

        //create an open ended range (9, infinity
        Range<Integer> range5 = Range.greaterThan(9);
        System.out.println("(9,infinity) : ");
        System.out.println("Lower Bound: " + range5.lowerEndpoint());
        System.out.println("Upper Bound present: " + range5.hasUpperBound());

        Range<Integer> range6 = Range.closed(3, 5);
        printRange(range6);

        //check a subrange [3,5] in [0,9]
        System.out.println("[0,9] encloses [3,5]:" + range1.encloses(range6));

        Range<Integer> range7 = Range.closed(9, 20);
        printRange(range7);
        //check ranges to be connected
        System.out.println("[0,9] is connected [9,20]:" + range1.isConnected(range7));

        Range<Integer> range8 = Range.closed(5, 15);

        //intersection
        printRange(range1.intersection(range8));

        //span 并集
        printRange(range1.span(range8));
    }

    private static void printRange(Range<Integer> range) {
        System.out.print("[ ");
        for (int grade : ContiguousSet.create(range, DiscreteDomain.integers())) {
            System.out.print(grade + " ");
        }
        System.out.println("]");
    }

    /**
     * Throwable类提供了相关的Throwable接口的实用方法。
     */
    private static void testThrowable() {

    }

    /**
     * Guava介绍了基于开发者的应用开发经验，许多先进的集合。以下是有用的集合列表：
     */
    private static void testCollections() {

    }

    /**
     * Multiset接口扩展设置有重复的元素，并提供了各种实用的方法来处理这样的元素在集合中出现。
     */
    private static void testMultiset() {

    }

    /**
     * BiMap是一种特殊的映射其保持映射，同时确保没有重复的值是存在于该映射和一个值可以安全地用于获取键背面的倒数映射。
     */
    private static void testBitMap() {

    }

    /**
     * Table代表一个特殊的映射，其中两个键可以在组合的方式被指定为单个值。它类似于创建映射的映射。
     */
    private static void testTable() {

    }

    /**
     * Guava通过接口LoadingCache提供了一个非常强大的基于内存的LoadingCache<K，V>。
     * 在缓存中自动加载值，它提供了许多实用的方法，在有缓存需求时非常有用。
     */
    private static void testCaches() {
        //create a cache for employees based on their employee id
        LoadingCache employeeCache = CacheBuilder.newBuilder().maximumSize(100) // maximum 100 records can be cached
            .expireAfterAccess(30, TimeUnit.SECONDS) // cache will expire after 30 minutes of access
            .build(new CacheLoader<String, Employee>() { // build the cacheloader
                @Override
                public Employee load(String empId) throws Exception {
                    //make the expensive call
                    return getFromDatabase(empId);
                }
            });

        try {
            //on first invocation, cache will be populated with corresponding
            //employee record
            System.out.println("Invocation #1");
            System.out.println(employeeCache.get("100"));
            System.out.println(employeeCache.get("103"));
            System.out.println(employeeCache.get("110"));
            //second invocation, data will be returned from cache
            System.out.println("Invocation #2");
            System.out.println(employeeCache.get("100"));
            System.out.println(employeeCache.get("103"));
            System.out.println(employeeCache.get("110"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static Employee getFromDatabase(String empId) {
        Employee e1 = new Employee("Mahesh", "Finance", "100");
        Employee e2 = new Employee("Rohan", "IT", "103");
        Employee e3 = new Employee("Sohan", "Admin", "110");

        Map<String, Employee> database = new HashMap();
        database.put("100", e1);
        database.put("103", e2);
        database.put("110", e3);
        System.out.println("Database hit for" + empId);
        return database.get(empId);
    }

    /**
    * Guava介绍基于开发者的应用开发经验，许多先进的字符串工具
    * Joiner 实用加入对象，字符串等。
    * Spilter 实用程序用来分割字符串。
    * CharMatcher 实用的字符操作。
    * CaseFormat 实用程序，用于改变字符串格式。
    */
    private static void testString() {

    }

    /**
     * Joiner 提供了各种方法来处理字符串加入操作，对象等。
     */
    private static void testJoiner() {
        System.out.println(Joiner.on("-").skipNulls().join(Arrays.asList(1, 2, 3, 4, 5, null, 6)));
    }

    /**
     * Splitter 提供了各种方法来处理分割操作字符串，对象等
     */
    private static void testSplitter() {
        System.out.println(Splitter.on(',').trimResults().omitEmptyStrings().split(
            "the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog."));
    }

    /**
     * CharMatcher提供了各种方法来处理各种JAVA char类型值
     */
    private static void testCharMatcher() {
        System.out.println(CharMatcher.digit().retainFrom("mahesh123")); // only the digits
        System.out.println(
            CharMatcher.whitespace().trimAndCollapseFrom("     Mahesh     Parashar ", ' '));
        // trim whitespace at ends, and replace/collapse whitespace into single spaces
        System.out.println(CharMatcher.javaDigit().replaceFrom("mahesh123", "*")); // star out all digits
        System.out.println(
            CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom("mAHesh123"));
        // eliminate all characters that aren't digits or lowercase
    }

    /**
     * CaseFormat是一种实用工具类，以提供不同的ASCII字符格式之间的转换。
     */
    private static void testCaseFormat() {
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));
    }

    /**
     * 作为Java的原语类型，不能用来传递在泛型或于类别作为输入。Guava提供大量包装工具类来处理原始类型的对象。
     * Bytes 实用程序的原始字节。
     * Shorts 实用的原始short。
     * Ints 实用为基本整型。
     * Longs 实用的原始长整型。
     * Floats 实用为基本float。
     * Doubles 实用为基本的double。
     * Chars 实用的原始字符。
     * Booleans 实用为基本布尔。
     */
    private static void testType() {

    }

    /**
     * 提供Guava数学相关的实用工具类来处理int，long及BigInteger
     * IntMath 数学工具为int类型。
     * LongMath 数学工具为long类型。
     * BigIntegerMath 数学实用程序处理BigInteger。。
     */
    private static void testMath() {

    }

    /**
     * 多重映射接口扩展映射，使得其键一次可被映射到多个值。Multimap
     */
    private static void testMultimap() {

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Employee {
        String name;
        String dept;
        String emplD;
    }
}
