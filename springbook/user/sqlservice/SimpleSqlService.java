package springbook.user.sqlservice;

import java.util.Map;

public class SimpleSqlService implements SqlService{
    // 설정 파일에 <map>으로 정의된 SQL 정보를 가져오도록 프로퍼티로 등록해둔다.
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Override
    public String getSql(String key) throws SqlRetrivalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrivalFailureException(String.format("%s에 대한 SQL을 찾을 수 없습니다.", key));
        } 
        else 
            return sql;
    }
}
