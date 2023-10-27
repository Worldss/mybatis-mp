package db.sql.api;

import java.util.function.Consumer;

public interface Nested<SELF, CHAIN> {

    SELF andNested(Consumer<CHAIN> consumer);

    SELF orNested(Consumer<CHAIN> consumer);
}
