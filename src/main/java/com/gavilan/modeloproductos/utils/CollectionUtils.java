package com.gavilan.modeloproductos.utils;

import java.util.Collection;

public class CollectionUtils {

    public CollectionUtils() {

    }

    public static <T> Collection<T> collect(Collection inputCollection, TypedTransformer<T> transformer) {
        return org.apache.commons.collections4.CollectionUtils.collect(inputCollection, transformer);
    }

}
