package db.sql.core.api.cmd.struct.update;


import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.TableField;
import db.sql.core.api.cmd.basic.Value;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class UpdateSets implements db.sql.api.cmd.struct.update.UpdateSets<TableField, Value, UpdateSet>, Cmd {

    private List<UpdateSet> updateSets;

    public UpdateSets set(TableField field, Value value) {
        if (this.updateSets == null) {
            this.updateSets = new ArrayList<>();
        }
        this.updateSets.add(new UpdateSet(field, value));
        return this;
    }

    @Override
    public List<UpdateSet> getUpdateSets() {
        return updateSets;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.SET);
        return CmdUtils.join(this, context, sqlBuilder, this.updateSets, SqlConst.DELIMITER);
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.updateSets);
    }
}
