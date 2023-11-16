package db.sql.api.cmd.struct;

import java.util.function.Consumer;

public interface Nested<SELF, CHAIN> {

    SELF andNested(Consumer<CHAIN> consumer);

    SELF orNested(Consumer<CHAIN> consumer);
}
