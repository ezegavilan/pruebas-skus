package com.gavilan.modeloproductos.utils;

import org.apache.commons.collections4.Transformer;

public interface TypedTransformer<K> extends Transformer {
    K transform(Object var1);
}
