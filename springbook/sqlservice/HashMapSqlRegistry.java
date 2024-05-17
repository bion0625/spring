package springbook.sqlservice;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {
    Map<String, String> sqlMap = new HashMap<String, String>();

    @Override
    public String findSql(String key) throws SqlNotFountException {
        String sql = sqlMap.get(key);
        if (sql == null)
            throw new SqlNotFountException(String.format("%s를 이용해서 SQL을 찾을 수 없습니다.", key));
        else return sql;
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }
}
