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
        Table userTable2 = userTable();
        check("delete", "delete t,t2 from user t,user t2 where t.id=t2.id",
                new Delete().delete(userTable, userTable2).from(userTable.as("t").setPrefix("_aa"), userTable2.as("t2")).eq(userTable.$("id"), userTable2.$("id"))
        );

        check("delete", "delete t,t2 from user t inner join user t2 on t.id=t2.id",
                new Delete().delete(userTable, userTable2).from(userTable.as("t").setPrefix("_aa")).join(userTable, userTable2, on -> on.eq(on.getJoin().getMainTable().$("id"), userTable2.$("id")))
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
