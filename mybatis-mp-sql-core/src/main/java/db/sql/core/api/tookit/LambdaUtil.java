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
package db.sql.core.api.tookit;


import db.sql.api.Getter;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LambdaUtil {

    private static final Map<Serializable, SerializedLambda> SERIALIZED_LAMBDA_MAP = new ConcurrentHashMap<>();
    private static final Map<Getter, String> LAMBDA_GETTER_FIELD_MAP = new ConcurrentHashMap<>();
    private static final Map<Getter, Class<?>> LAMBDA_GETTER_CLASS_MAP = new ConcurrentHashMap<>();

    private LambdaUtil() {

    }

    public static <T> String getName(Getter<T> getter) {
        return LAMBDA_GETTER_FIELD_MAP.computeIfAbsent(getter, (key) -> {
            SerializedLambda lambda = getSerializedLambda(key);
            String methodName = lambda.getImplMethodName();
            return PropertyNamer.methodToProperty(methodName);
        });
    }

    public static <T> Class<?> getClass(Getter<T> getter) {
        return LAMBDA_GETTER_CLASS_MAP.computeIfAbsent(getter, (key) -> {
            SerializedLambda lambda = getSerializedLambda(getter);
            return getClass(lambda, getter.getClass().getClassLoader());
        });
    }

    private static SerializedLambda getSerializedLambda(Getter getter) {
        return SERIALIZED_LAMBDA_MAP.computeIfAbsent(getter, key -> {
            try {
                Method method = key.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                return (SerializedLambda) method.invoke(key);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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

}
