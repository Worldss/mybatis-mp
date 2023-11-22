package db.sql.test.insert;

import db.sql.api.impl.cmd.executor.Insert;
import db.sql.test.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class InsertTest extends BaseTest {

    @Test
    void insertTest() {
        check("insert常规插入", "insert into user (id,name) values (1,'2')"
                , new Insert().insert(userTable())
                        .field(userTable().$("id"), userTable().$("name"))
                        .values(Arrays.asList(1, "2"))
        );

        check("insert批量插入", "insert into user (id,name) values (1,'2'),(2,'3')"
                , new Insert().insert(userTable())
                        .field(userTable().$("id"), userTable().$("name"))
                        .values(Arrays.asList(1, "2"))
                        .values(Arrays.asList(2, "3"))
        );
    }
}
