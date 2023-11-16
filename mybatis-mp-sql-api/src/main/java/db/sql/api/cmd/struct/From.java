package db.sql.api.cmd.struct;

import java.util.List;

public interface From<TABLE> {

    List<TABLE> getTables();
}
