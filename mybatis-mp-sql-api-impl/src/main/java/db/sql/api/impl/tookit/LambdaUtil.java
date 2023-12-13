/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package db.sql.api.impl.tookit;


import db.sql.api.Getter;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LambdaUtil {

    private static final Map<Getter, LambdaFieldInfo> LAMBDA_GETTER_FIELD_MAP = new ConcurrentHashMap<>(65535);

    private volatile static boolean CACHE_ENABLE = true;

    static {
        getName(LambdaFieldInfo::getName);
        getName(LambdaFieldInfo::getName);
        CACHE_ENABLE = (LAMBDA_GETTER_FIELD_MAP.size() == 1);
        if (!CACHE_ENABLE) {
            LAMBDA_GETTER_FIELD_MAP.clear();
        }
    }

    private LambdaUtil() {

    }

    public static <T> String getName(Getter<T> getter) {
        if (CACHE_ENABLE) {
            return getFieldInfo(getter).getName();
        } else {
            return getLambdaFieldName(getter);
        }
    }

    public static String getLambdaName(Getter getter) {
        return getSerializedLambda(getter).getImplMethodName();
    }

    public static String getLambdaFieldName(Getter getter) {
        return getLambdaFieldName(getSerializedLambda(getter));
    }

    private static String getLambdaFieldName(SerializedLambda serializedLambda) {
        String methodName = serializedLambda.getImplMethodName();
        return PropertyNamer.methodToProperty(methodName);
    }

    private static LambdaFieldInfo getLambdaFieldInfo(SerializedLambda serializedLambda, ClassLoader classLoader) {
        Class type = getClass(serializedLambda, classLoader);
        String methodName = serializedLambda.getImplMethodName();
        String fieldName = PropertyNamer.methodToProperty(methodName);
        return new LambdaFieldInfo(type, fieldName);
    }

    public static <T> LambdaFieldInfo getFieldInfo(Getter<T> getter) {
        if (CACHE_ENABLE) {
            return LAMBDA_GETTER_FIELD_MAP.computeIfAbsent(getter, (key) -> getLambdaFieldInfo(getSerializedLambda(getter), getter.getClass().getClassLoader()));
        } else {
            return getLambdaFieldInfo(getSerializedLambda(getter), getter.getClass().getClassLoader());
        }
    }

    private static SerializedLambda getSerializedLambda(Getter getter) {
        try {
            Method method = getter.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            return (SerializedLambda) method.invoke(getter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getClass(SerializedLambda lambda, ClassLoader classLoader) {
        String className = getClassName(lambda);
        try {
            return Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getClassName(SerializedLambda lambda) {
        String type = lambda.getInstantiatedMethodType();
        return type.substring(2, type.indexOf(";")).replace("/", ".");
    }

    public static void main(String[] args) {
        System.out.println(getName(LambdaFieldInfo::getName));
        System.out.println(getName(LambdaFieldInfo::getName));
        System.out.println(LAMBDA_GETTER_FIELD_MAP.size());
    }

    public static class LambdaFieldInfo {

        private final Class type;
        private final String name;

        public LambdaFieldInfo(Class type, String name) {
            this.type = type;
            this.name = name;
        }

        public Class getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

}
