package com.cls.java8;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Test {

    /**
     *   Consumer<T>：接受一个输入，没有输出。抽象方法为 void accept(T t)。
         Supplier<T>：没有输入，一个输出。抽象方法为 T get()。
         Predicate<T>：接受一个输入，输出为 boolean 类型。抽象方法为 boolean test(T t)。
         UnaryOperator<T>：接受一个输入，输出的类型与输入相同，相当于 Function<T, T>。
         BinaryOperator<T>：接受两个类型相同的输入，输出的类型与输入相同，相当于 BiFunction<T,T,T>。
         BiPredicate<T, U>：接受两个输入，输出为 boolean 类型。抽象方法为 boolean test(T t, U u)。
     * @param args
     */
    public static void main(String[] args) {

//        testStream3();
        functionTest1();
    }

    private static void functionTest1() {
        BiFunction<Integer, Integer, Integer> biFunction = (v1, v2) -> v1 - v2;
        Function<Integer, Integer> subtractFrom10 = partialLeft(biFunction, 10);
        Function<Integer, Integer> subtractBy10 = partialRight(biFunction, 10);
        System.out.println(subtractFrom10.apply(5));
        System.out.println(subtractBy10.apply(5));
    }

    private static  <T, U, R> Function<U, R> partialLeft(BiFunction<T, U, R> biFunction, T t) {
        return (u) -> biFunction.apply(t, u);
    }
    private static  <T, U, R> Function<T, R> partialRight(BiFunction<T, U, R> biFunction, U u) {
        return (t) -> biFunction.apply(t, u);
    }

    private static void testStream3() {
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);

        IntStream.generate(() -> (int)System.nanoTime()%100).limit(10).forEach(System.out::println);

        Stream.generate(new PersonSupplier()).limit(10).forEach(p -> System.out.println(p.getName() + "," + p.getAge()));

        Stream.iterate(0, n -> n + 2).limit(10).forEach(x -> System.out.print(x + " "));

        Map<Integer, List<Person>> persons = Stream.generate(new PersonSupplier()).limit(10)
                .collect(Collectors.groupingBy(Person::getAge));
        System.out.println(JSON.toJSONString(persons));

        Map<Boolean, List<Person>> personMap = Stream.generate(new PersonSupplier()).limit(10)
                .collect(Collectors.partitioningBy(p -> p.getAge() > 18));
        System.out.println(JSON.toJSONString(personMap));

    }

    private static void testStream2() {

        System.out.println(Stream.of("A", "b", "c", "D").reduce("", String::concat));
        System.out.println(Stream.of(7.1,2.3,3.1,4.1).reduce(Double.MAX_VALUE, Double::min));
        System.out.println(Stream.of(7.1,2.3,3.1,4.1).reduce(0.0, Double::sum));

        System.out.println(Stream.of(1,2,3,4).reduce(Integer::sum).get());

        System.out.println(Stream.of("a", "B", "c", "D", "e", "F")
                .filter(word -> word.compareTo("Z") > 0)
                .reduce("", String::concat)
        );

        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i, 0);
            persons.add(person);
        }

        List<String> names= persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(names);

        //System.out.println(persons.stream().map(Person::getName).sorted((p1, p2) -> p1.compareTo(p2)).limit(2).collect(Collectors.toList()));

        System.out.println(persons.stream().map(Person::getName).limit(2).sorted((p1, p2) -> p1.compareTo(p2)).collect(Collectors.toList()));

        persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        System.out.println(persons.stream().allMatch(person -> person.getAge()>18));;

    }

    private static void testStream() {
        System.out.println("hello world");

        IntStream.of(new int[]{1,2,3}).forEach(System.out::println);
        IntStream.range(1,3).forEach(System.out::println);
        System.out.println("---");
        IntStream.rangeClosed(1,3).forEach(System.out::println);

        List<String> words = Stream.of(new String[]{"abc", "efg"}).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(words);

        List<Integer> values = Arrays.asList(1,2,3,4).stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(values);

        values = Stream.of(Arrays.asList(1,2,3), Arrays.asList(4,5,6), Arrays.asList(7,8,9)).flatMap(childList -> childList.stream()).collect(Collectors.toList());
        System.out.println(values);

        Integer[] values2 = Stream.of(1,2,3,4,5,6).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.out.println(JSON.toJSONString(values2));

        words = Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3)
                .peek(e -> System.out.println("filter :" + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("map :" + e))
                .collect(Collectors.toList());

        Optional.ofNullable("123").ifPresent(System.out::println);
        Optional.ofNullable("").ifPresent(System.out::println);
        System.out.println("end");

        System.out.println(Optional.ofNullable("123").map(String::length).orElse(-1));
    }

    private static class Person {
        public int no;
        private String name;
        private int age;

        public Person(int no, String name, int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }

        public Person() {
        }

        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }

    }

    private static class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }

}
