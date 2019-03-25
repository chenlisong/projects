package java8.news;

/**
 * 参考文档：http://www.importnew.com/10360.html
 * @author chenlisong
 *
 */
public class Lambda {
	
	static int outerStaticNum;
	
	int outerNum;
	
	public static void main(String[] args) {
		
//		List<String> names = Arrays.asList("chenlisong", "baiyu", "wangsanchao");
//		System.out.println("init list:" + names);
//		Collections.sort(names, (a, b) -> a.compareTo(b));
//		
//		System.out.println(names);
//		
//		Collections.sort(names, (String a, String b)-> b.compareTo(a));
//		System.out.println(names);
//		
//		Collections.sort(names, (a, b)->a.compareTo(b));
//		System.out.println(names);
		
//		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
//		Integer converted = converter.convert("123");
//		System.out.println(converted);    // 123

//		Converter<String, Integer> converter = Integer::valueOf;
//		Integer converted = converter.convert("123");
//		System.out.println(converted);   // 123
		
//		Something someThing = new Something();
//		Converter<String, String> converter = someThing::startsWith;
//		String converted = converter.convert("Java");
//		System.out.println(converted);//print J
		
//		PersonFactory<Person> personFactory = Person::new;
//		Person person = personFactory.create("cls", "song");
//		System.out.println(person.toString());
		
//		final int num = 1;
//		Converter<Integer, String> converter = (form) -> String.valueOf(form + num);
//		System.out.println(converter.convert(2));
		
//		Converter<Integer, String> converter1 = (form) -> {
//			outerStaticNum = 100;
//			return String.valueOf(form);
//		};
//		System.out.println(outerStaticNum);
//		System.out.println(converter1.convert(10));
//		System.out.println(outerStaticNum);
		
		//无法访问到非static变量
//		Converter<Integer, String> converter2 = (form) -> {
//			outerNum = 100;
//		};

//		Predicate<String> predicate = (s) -> s.length() > 0;
//		System.out.println(predicate.test("abc"));
//		System.out.println(predicate.negate().test("abc"));
//		
//		Predicate<Boolean> notNull = Objects::nonNull;
//		System.out.println(notNull.test(true));
//		
//		Function<String, Integer> toInteger = Integer::valueOf;
//		Function<String, String> backToString = toInteger.andThen(String::valueOf);
//		System.out.println(backToString.apply("123"));
		
//		Supplier<Person> personSupplier = Person::new;
//		System.out.println(personSupplier.get().firstName);;
		
//		Consumer<Person> greeter = (p) -> System.out.println("hello " + p.firstName);
//		greeter.accept(new Person("cls", "song"));
		
//		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
//		Person p1 = new Person("John", "Doe");
//		Person p2 = new Person("Alice", "Wonderland");
//		System.out.println(comparator.compare(p1, p2));
//		System.out.println(comparator.reversed().compare(p1, p2));
		
//		Optional<String> optional = Optional.of("bam");
//		System.out.println(optional.isPresent());
//		System.out.println(optional.get());
//		System.out.println(optional.orElse("fallback"));
//		optional.ifPresent((s) -> System.out.println(s.charAt(0)));
		
//		List<String> stringCollection = new ArrayList<>();
//		stringCollection.add("ddd2");
//		stringCollection.add("aaa2");
//		stringCollection.add("bbb1");
//		stringCollection.add("aaa1");
//		stringCollection.add("bbb3");
//		stringCollection.add("ccc");
//		stringCollection.add("bbb2");
//		stringCollection.add("ddd1");
//		
//		stringCollection.stream().filter((s) -> s.startsWith("a"))
//			.forEach(System.out::println);
//		
//		//一定要记住，sorted只是创建一个流对象排序的视图，而不会改变原来集合中元素的顺序。
//		//原来string集合中的元素顺序是没有改变的。
//		stringCollection.stream().sorted().filter((s) -> s.startsWith("a"))
//			.forEach(System.out::println);
//		
//		stringCollection.stream().map(String::toUpperCase)
//			.sorted((a, b) -> a.compareTo(b))
//			.forEach(System.out::println);;
//		
//		System.out.println(stringCollection.stream()
//				.anyMatch((s) -> s.startsWith("a")));
//		
//		System.out.println(stringCollection.stream()
//				.allMatch((s) -> s.startsWith("a")));
//		
//		System.out.println(stringCollection.stream()
//				.noneMatch((s) -> s.startsWith("z")));
//		
//		System.out.println(stringCollection.stream()
//				.filter((s) -> s.startsWith("a"))
//				.count());
//		
//		Optional<String> optional = stringCollection.stream().sorted().reduce((s1, s2) -> s1+"#"+s2);
//		optional.ifPresent(System.out::println);
		
//		int max = 1000000;
//		List<String> values = new ArrayList<>(max);
//		for (int i = 0; i < max; i++) {
//		    UUID uuid = UUID.randomUUID();
//		    values.add(uuid.toString());
//		}
//		
//		long t0 = System.nanoTime();
//
//		long count = values.parallelStream().sorted().count();
//		long count = values.stream().sorted().count();
//		
//		System.out.println(count);
//		 
//		long t1 = System.nanoTime();
//		 
//		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
//		System.out.println(String.format("sequential sort took: %d ms", millis));
		
//		Map<Integer, String> map = new HashMap<>();
//		 
//		for (int i = 0; i < 10; i++) {
//		    map.putIfAbsent(i, "val" + i);
//		}
//		
//		map.forEach((id, val) -> System.out.println(val));
//		
//		map.computeIfPresent(3, (num, val) -> val + num);
//		System.out.println(map.get(3));
//		
//		map.computeIfPresent(9, (num, val) -> null);
//		System.out.println(map.containsKey(9));     // false
//		 
//		map.computeIfAbsent(23, num -> "val" + num);
//		System.out.println(map.containsKey(23));    // true
//		 
//		map.computeIfAbsent(3, num -> "bam");
//		System.out.println(map.get(3));             // val33
//		
//		System.out.println(map.getOrDefault(31, "not find"));
		
//		Clock clock = Clock.systemDefaultZone();
//		long millis = clock.millis();
//		System.out.println(millis);
//		
//		Instant instant = clock.instant();
//		Date legacyDate = Date.from(instant);   // legacy java.util.Date
		
//		System.out.println(ZoneId.getAvailableZoneIds());
//		// prints all available timezone ids
//		 
//		ZoneId zone1 = ZoneId.of("Europe/Berlin");
//		ZoneId zone2 = ZoneId.of("Brazil/East");
//		System.out.println(zone1.getRules());
//		System.out.println(zone2.getRules());
		
		
	}
}
