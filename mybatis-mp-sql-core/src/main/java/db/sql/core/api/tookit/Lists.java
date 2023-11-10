package db.sql.core.api.tookit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lists {


    public final static <T> List<T> merge(List<T> list, T... ts) {
        if (ts == null || ts.length < 1) {
            return list;
        }
        list.addAll(Arrays.stream(ts).collect(Collectors.toList()));
        return list;
    }
}
