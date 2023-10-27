package db.sql.test.delete;

import db.sql.core.api.cmd.executor.Delete;
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
        check("delete", "delete user from user where id=1",
                new Delete().from(userTable()).eq(userTable().$("id"), 1)
        );

        check("delete", "delete t from user t where id=1",
                new Delete().from(userTable().as("t").setPrefix("_aa")).eq(userTable().$("id"), 1)
        );

        check("delete", "delete t from deletetest t where t.id=1",
                new Delete().from(DeleteTest.class).eq(DeleteTest::getId, 1)
        );


    }
}
