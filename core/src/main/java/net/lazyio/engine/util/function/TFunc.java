package net.lazyio.engine.util.function;

@FunctionalInterface
public interface TFunc<T, U, V, R> {

    R apply(T t, U u, V v);
}
