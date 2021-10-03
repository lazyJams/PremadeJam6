package net.lazyio.engine.util.function;

@FunctionalInterface
public interface VoidTFunc<T, U, V> {

    void apply(T t, U u, V v);
}
