package db.sql.api;

import java.util.function.Consumer;

public interface HavingMethod<SELF extends HavingMethod, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>, HAVING extends Having<HAVING>> {

    SELF having(Consumer<HAVING> consumer);


}
