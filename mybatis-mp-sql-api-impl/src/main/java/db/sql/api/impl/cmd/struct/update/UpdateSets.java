package db.sql.api.impl.cmd.struct.update;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdateSets implements db.sql.api.cmd.struct.update.UpdateSets<TableField, Cmd, UpdateSet>, Cmd {

    private List<UpdateSet> updateSets;

    public UpdateSets set(TableField field, Cmd value) {
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
