package db.sql.api;

public class KeywordWrap {

    private final String prefix;
    private final String suffix;

    public KeywordWrap(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
