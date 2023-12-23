package cn.mybatis.mp.core;

public class NotTableClassException extends RuntimeException {

    public NotTableClassException(Class clazz) {
        super(clazz.getName() + " has no @Table annotation");
    }
}
