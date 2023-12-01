package db.sql.test.delete;

import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.executor.Delete;
import db.sql.test.BaseTest;
import org.junit.jupiter.api.Test;

public class DeleteTest extends BaseTest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Test
    void deleteTest() {

        Table userTable = userTable();

        check("delete", "delete from user where id=1",
                new Delete().delete(userTable).from(userTable.as("t").setPrefix("_aa")).eq(userTable().$("id"), 1)
        );

        userTable = userTable();
        check("delete", "delete from user where id=1",
                new Delete().delete(userTable).from(userTable).eq(userTable.$("id"), 1)
        );


        check("delete", "delete from deletetest where id=1",
                new Delete().delete(DeleteTest.class).from(DeleteTest.class).eq(DeleteTest::getId, 1)
        );


    }
}
