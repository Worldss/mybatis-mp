package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.CmdFactory;
import db.sql.api.cmd.basic.Dataset;
import db.sql.api.cmd.struct.*;
import db.sql.api.cmd.struct.query.*;

/**
 * 子查询
 *
 * @param <SELF>
 * @param <TABLE>
 * @param <DATASET>
 * @param <TABLE_FIELD>
 * @param <DATASET_FILED>
 * @param <COLUMN>
 * @param <V>
 * @param <CMD_FACTORY>
 * @param <CONDITION_CHAIN>
 * @param <SELECT>
 * @param <FROM>
 * @param <JOIN>
 * @param <ON>
 * @param <JOINS>
 * @param <WHERE>
 * @param <GROUPBY>
 * @param <HAVING>
 * @param <ORDERBY>
 * @param <LIMIT>
 * @param <FORUPDATE>
 * @param <UNION>
 * @param <UNIONS>
 */
public interface SubQuery<SELF extends SubQuery,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends COLUMN,
        SUB_QUERY_TABLE_FILED extends Cmd,
        COLUMN extends Cmd,
        V,

        CMD_FACTORY extends CmdFactory<TABLE, DATASET, TABLE_FIELD, DATASET_FILED>,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>,
        SELECT extends Select<SELECT>,
        FROM extends From<DATASET>,
        JOIN extends Join<JOIN, DATASET, ON>,
        ON extends On<ON, DATASET, COLUMN, V, JOIN, CONDITION_CHAIN>,
        JOINS extends Joins<JOIN>,
        WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>,
        GROUPBY extends GroupBy<GROUPBY, COLUMN>,
        HAVING extends Having<HAVING>,
        ORDERBY extends OrderBy<ORDERBY>,
        LIMIT extends Limit<LIMIT>,
        FORUPDATE extends ForUpdate<FORUPDATE>,
        UNION extends Union,
        UNIONS extends Unions<UNION>
        > extends Query<
        SELF,
        TABLE,
        DATASET,
        TABLE_FIELD,
        DATASET_FILED,
        SUB_QUERY_TABLE_FILED,
        COLUMN,
        V,
        CMD_FACTORY,
        CONDITION_CHAIN,
        SELECT,
        FROM,
        JOIN,
        ON,
        JOINS,
        WHERE,
        GROUPBY,
        HAVING,
        ORDERBY,
        LIMIT,
        FORUPDATE,
        UNION,
        UNIONS
        >, Dataset<SELF, DATASET_FILED> {


}
