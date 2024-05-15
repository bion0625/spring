package springbook.user.sqlservice;

public interface SqlRegistry {
    void registerSql(String key, String sql); // SQL을 키와 함께 등록한다.

    String findSql(String key) throws SqlNotFountException; // 키로 SQL을 검색한다. 검색이 실패하면 예외를 던진다.
}
