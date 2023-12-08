package db.sql.api.impl.tookit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Lists {


    public static <T> List<T> merge(List<T> list, T... ts) {
        if (ts == null || ts.length < 1) {
            return list;
        }
        list.addAll(Arrays.stream(ts).collect(Collectors.toList()));
        return list;
    }
}
