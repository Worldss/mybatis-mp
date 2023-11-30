package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.ICmdFactory;
import db.sql.api.cmd.basic.IDataset;
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
 */
public interface ISubQuery<SELF extends ISubQuery,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends COLUMN,
        COLUMN extends Cmd,
        V,

        CMD_FACTORY extends ICmdFactory<TABLE, DATASET, TABLE_FIELD, DATASET_FILED>,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>,

        WITH extends IWith<WITH>,
        SELECT extends ISelect<SELECT>,
        FROM extends IFrom<DATASET>,
        JOIN extends IJoin<JOIN, DATASET, ON>,
        ON extends IOn<ON, DATASET, COLUMN, V, JOIN, CONDITION_CHAIN>,
        JOINS extends Joins<JOIN>,
        WHERE extends IWhere<WHERE, COLUMN, V, CONDITION_CHAIN>,
        GROUPBY extends IGroupBy<GROUPBY, COLUMN>,
        HAVING extends IHaving<HAVING>,
        ORDERBY extends IOrderBy<ORDERBY>,
        LIMIT extends ILimit<LIMIT>,
        FORUPDATE extends IForUpdate<FORUPDATE>,
        UNION extends IUnion
        > extends IQuery<
        SELF,
        TABLE,
        DATASET,
        TABLE_FIELD,
        DATASET_FILED,
        COLUMN,
        V,
        CMD_FACTORY,
        CONDITION_CHAIN,
        WITH,
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
        UNION
        >, IDataset<SELF, DATASET_FILED> {


}
