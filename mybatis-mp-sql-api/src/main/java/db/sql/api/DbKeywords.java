package db.sql.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbKeywords {

    private static final Map<DbType, List<String>> KEYWORDS_MAP = new HashMap<>();

    public static List<String> getKeywords(DbType dbType) {
        return KEYWORDS_MAP.get(dbType);
    }

}
