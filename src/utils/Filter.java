package utils;

@FunctionalInterface
public interface Filter<T> {
    boolean test(T item);
}

