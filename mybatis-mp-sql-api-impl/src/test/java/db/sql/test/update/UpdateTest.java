package db.sql.test.update;


import db.sql.api.impl.cmd.executor.Update;
import db.sql.test.BaseTest;
import org.junit.jupiter.api.Test;

public class UpdateTest extends BaseTest {

    @Test
    void deleteTest() {
        check("update 简单", "update user set name='xx' where id=1",
                new Update().update(userTable()).set(userTable().$("name"), "xx").eq(userTable().$("id"), 1)
        );

        check("update 别名", "update user t set t.name='xx' where t.id=1",
                new Update().update(userTable().as("t")).set(userTable().as("t").$("name"), "xx").eq(userTable().as("t").$("id"), 1)
        );
    }
}
