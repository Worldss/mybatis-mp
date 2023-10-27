package db.sql.api;

import java.util.List;

public interface From<TABLE> {

    List<TABLE> getTables();
}
