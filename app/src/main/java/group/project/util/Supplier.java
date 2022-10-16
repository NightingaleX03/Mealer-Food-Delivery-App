package group.project.util;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}