package db.sql.api.impl.tookit;

import db.sql.api.DbType;

public class SqlConst {

    public static final String EMPTY = "";

    public static final String BLANK = " ";

    public static final String NULL = " NULL ";

    public static final String UNION = " UNION ";

    public static final String UNION_ALL = " UNION ALL ";

    public static final String ALL = "*";

    public static final String DOT = ".";

    public static final String DELIMITER = " , ";

    public static final String INTERVAL = "INTERVAL ";
    public static final String AS = " AS ";
    public static final String IS = " IS ";
    public static final String IS_NOT = " IS NOT ";
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String DELETE = "DELETE ";
    public static final String UPDATE = "UPDATE ";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String INTO = " INTO ";
    public static final String VALUES = " VALUES ";
    public static final String SET = " SET ";
    public static final String JOIN = " JOIN ";
    public static final String INNER_JOIN = " INNER JOIN ";
    public static final String LEFT_JOIN = " LEFT JOIN ";
    public static final String RIGHT_JOIN = " RIGHT JOIN ";
    public static final String ON = " ON ";
    public static final String WHERE = " WHERE ";
    public static final String EXISTS = " EXISTS ";

    public static final String NOT_EXISTS = " NOT EXISTS ";
    public static final String AND = " AND ";
    public static final String IN = " IN ";
    public static final String OR = " OR ";
    public static final String EQ = " = ";
    public static final String NE = " != ";
    public static final String LT = " < ";
    public static final String GT = " > ";
    public static final String LTE = " <= ";
    public static final String GTE = " >= ";
    public static final String BETWEEN = " BETWEEN ";
    public static final String NOT_BETWEEN = " NOT BETWEEN ";
    public static final String LIKE = " LIKE ";
    public static final String NOT_LIKE = " NOT LIKE ";
    public static final String BRACKET_LEFT = "(";
    public static final String BRACKET_RIGHT = ")";
    public static final String CONCAT = " CONCAT";
    public static final String CONCAT_WS = " CONCAT_WS";
    public static final String IF = " IF";
    public static final String IFNULL = " IFNULL";
    public static final String CASE = " CASE ";
    public static final String WHEN = " WHEN ";
    public static final String THEN = " THEN ";
    public static final String ELSE = " ELSE ";
    public static final String END = " END ";
    public static final String VAGUE_SYMBOL = "'%'";
    public static final String MAX = " MAX";
    public static final String MIN = " MIN";
    public static final String AVG = " AVG";
    public static final String ABS = " ABS";
    public static final String SUM = " SUM";
    public static final String CEIL = " CEIL";
    public static final String FLOOR = " FLOOR";
    public static final String RAND = " RAND";
    public static final String TRUNCATE = " TRUNCATE";
    public static final String SQRT = " SQRT";
    public static final String SIGN = " SIGN";
    public static final String PI =" PI";
    public static final String DIVIDE = " / ";
    public static final String MULTIPLY = " * ";
    public static final String SUBTRACT = " - ";
    public static final String PLUS = " + ";
    public static final String ROUND = " ROUND";
    public static final String POW = " POW";
    public static final String MOD = " MOD";
    public static final String EXP = " EXP";

    public static final String LOG = " LOG";

    public static final String LOG2 = " LOG2";

    public static final String LOG10 = " LOG10";
    public static final String SIN = " SIN";
    public static final String ASIN = " ASIN";
    public static final String COS = " COS";
    public static final String ACOS = " ACOS";
    public static final String TAN = " TAN";
    public static final String ATAN = " ATAN";
    public static final String COT = " COT";
    public static final String CHAR_LENGTH = " CHAR_LENGTH";
    public static final String LENGTH = " LENGTH";
    public static final String UPPER = " UPPER";
    public static final String LOWER = " LOWER";
    public static final String LEFT = " LEFT";
    public static final String RIGHT = " RIGHT";
    public static final String LPAD = " LPAD";
    public static final String RPAD = " RPAD";
    public static final String TRIM = " TRIM";
    public static final String LTRIM = " LTRIM";
    public static final String RTRIM = " RTRIM";
    public static final String REPEAT = " REPEAT";

    public static final String REPLACE = " REPLACE";
    public static final String REVERSE = " REVERSE";
    public static final String INSTR = " INSTR";
    public static final String STRCMP = " STRCMP";
    public static final String FILED = " FILED";
    public static final String CURRENT_DATE = " CURRENT_DATE";
    public static final String CURRENT_TIME = " CURRENT_TIME";
    public static final String UNIX_TIMESTAMP = " UNIX_TIMESTAMP";
    public static final String FROM_UNIXTIME = " FROM_UNIXTIME";
    public static final String YEAR = " YEAR";
    public static final String MONTH = " MONTH";

    public static final String DATE = " DATE";
    public static final String DAY = " DAY";
    public static final String WEEKDAY = " WEEKDAY";
    public static final String HOUR = " HOUR";
    public static final String DATE_DIFF = " DATEDIFF";
    public static final String DATE_ADD = " DATE_ADD";
    public static final String DATE_SUB = " DATE_SUB";
    public static final String MD5 = " MD5";
    public static final String INET_ATON = " INET_ATON";
    public static final String INET_NTOA = " INET_NTOA";
    public static final String COUNT = " COUNT";
    public static final String GROUP_BY = " GROUP BY ";
    public static final String HAVING = " HAVING ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";

    public static final String SINGLE_QUOT(DbType dbType) {
        switch (dbType){
            case MYSQL:{
                return "'";
            }
            case PGSQL:{
                return "\"";
            }
        }
        return "'";
    }

    public static String CURRENT_DATE_TIME(DbType dbType) {
//        switch (dbType){
//            case MYSQL:{
//                return " CURRENT_TIMESTAMP";
//            }
//        }
        return " CURRENT_TIMESTAMP";
    }
}
