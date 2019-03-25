package java8.news;

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
