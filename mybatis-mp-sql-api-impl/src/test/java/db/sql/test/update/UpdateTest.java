package db.sql.test.update;


import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.executor.Delete;
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



        Table userTable = userTable();

        check("update", "update user set id=2 where id=1",
                new Update().update(userTable).set(userTable.$("id"),2).eq(userTable().$("id"), 1)
        );
        Table userTable2 = userTable();
        check("update", " update user t,user t2 set t2.id=2 where t.id=t2.id",
                new Update().update(userTable.as("t"),userTable2.as("t2")).set(userTable2.$("id"),2).eq(userTable.$("id"), userTable2.$("id"))
        );

        check("update", "update user t inner join user t2 on t.id=t2.id set t2.id=2",
                new Update().update(userTable).set(userTable2.$("id"),2).join(userTable,userTable2,on->on.eq(on.getJoin().getMainTable().$("id"), userTable2.$("id")))
        );

        userTable = userTable();
        check("update", "update user set t.id=2 where id=1",
                new Update().update(userTable).set(userTable.$("id"),2).eq(userTable.$("id"), 1)
        );


    }
}
