package com.cs.it.wecode.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Covering {

    public static <T> ConvertResult<T> to(Object source, T target, String... ignoreProperties) {
        // TODO BeanUtils.copyProperties(source,target,ignoreProperties);
        return new ConvertResult<>(target);
    }

    public static class ConvertResult<T> {

        private final T data;

        public ConvertResult(T data) {
            this.data = data;
        }

        public <R> ConvertResult<R> map(Function<T, R> function) {
            return new ConvertResult<>(Objects.requireNonNull(function).apply(data));
        }

        public T get() {
            return data;
        }

        public T get(Consumer<T> consumer) {
            Objects.requireNonNull(consumer).accept(data);
            return data;
        }

    }

}
