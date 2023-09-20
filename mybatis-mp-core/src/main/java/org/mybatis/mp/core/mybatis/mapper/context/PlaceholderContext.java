package org.mybatis.mp.core.mybatis.mapper.context;

public interface PlaceholderContext {

    String PARAM_PLACEHOLDER_NAME = "value";

    Object getValue();
}
