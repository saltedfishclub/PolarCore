package cc.sfclub.service;

@FunctionalInterface
public interface Registry<T> {
    T get();
}
