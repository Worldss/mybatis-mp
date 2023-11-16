package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.JoinMode;

import java.util.function.Consumer;

public interface JoinMethod<SELF extends JoinMethod, TABLE, ON> {

    default SELF join(TABLE mainTable, TABLE secondTable, Consumer<ON> consumer) {
        return this.join(JoinMode.INNER, mainTable, secondTable, consumer);
    }

    SELF join(JoinMode mode, TABLE mainTable, TABLE secondTable, Consumer<ON> consumer);

    default SELF join(JoinMode mode, Class mainTable, Class secondTable) {
        return join(mode, mainTable, secondTable, null);
    }

    default SELF join(Class mainTable, Class secondTable) {
        return join(JoinMode.INNER, mainTable, secondTable);
    }

    default SELF join(Class mainTable, Class secondTable, Consumer<ON> consumer) {
        return join(JoinMode.INNER, mainTable, secondTable, consumer);
    }

    default SELF join(JoinMode mode, Class mainTable, Class secondTable, Consumer<ON> consumer) {
        return join(mode, mainTable, 1, secondTable, 1, consumer);
    }

    default SELF join(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey) {
        return join(JoinMode.INNER, mainTable, mainTableStorey, secondTable, secondTableStorey);
    }

    default SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey) {
        return join(mode, mainTable, mainTableStorey, secondTable, secondTableStorey, null);
    }

    default SELF join(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<ON> consumer) {
        return join(JoinMode.INNER, mainTable, mainTableStorey, secondTable, secondTableStorey, consumer);
    }

    SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<ON> consumer);


    default SELF join(Class mainTable, TABLE secondTable, Consumer<ON> consumer) {
        return join(JoinMode.INNER, mainTable, secondTable, consumer);
    }

    default SELF join(JoinMode mode, Class mainTable, TABLE secondTable, Consumer<ON> consumer) {
        return join(mode, mainTable, 1, secondTable, consumer);
    }

    default SELF join(Class mainTable, int mainTableStorey, TABLE secondTable, Consumer<ON> consumer) {
        return join(JoinMode.INNER, mainTable, mainTableStorey, secondTable, consumer);
    }

    SELF join(JoinMode mode, Class mainTable, int mainTableStorey, TABLE secondTable, Consumer<ON> consumer);
}
