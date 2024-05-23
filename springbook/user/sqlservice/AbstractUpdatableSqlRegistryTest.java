package springbook.user.sqlservice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springbook.user.sqlservice.SqlNotFountException;
import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class AbstractUpdatableSqlRegistryTest { // UpdatableSqlRegistry 인터페이스를 구현한 모든 클래스에 대한 테스트를 만들 때 사용할 수 있는 추상 테스트 클래스다.
    @Autowired
    UpdatableSqlRegistry sqlRegistry; // 테스트에서 사용할 픽스처는 인터페이스로 정의해두길 잘했다.

    @Before
    public void setUp() {
        sqlRegistry = createUpdatableSqlRegistry(sqlRegistry);

        // 각 테스트 메소드에서 사용할 초기 SQL 정보를 미리 등록한다.
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    // 테스트 픽스처를 생성하는 부분만 추상 메소드로 만들어두고 서브클래스에서 이를 구현하도록 만든다.
    abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry(UpdatableSqlRegistry sqlRegistry);

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    /*
    * 서브클래스에 테스트를 추가한다면 필요할 수 있다.
    * 따라서 서브클래스에서 접근이 가능하도록 protected로 변경한다.
    * */
    protected void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
    }

    /*
     * 주어진 키에 해당하는 SQL을 찾을 수 없을 때 예외가 발생하는지를 확인한다.
     * 예외상황에 대한 테스트는 빼먹기가 쉽기 때문에 항상 의식적으로 넣으려고 노력해야 한다.
     * */
    @Test(expected = SqlNotFountException.class)
    public void unknownKey() {
        sqlRegistry.findSql("SQL9999!@#$");
    }

    /*
     * 하나의 SQL을 변경하는 기능에 대한 테스트다.
     * 검증할 때는 변경된 SQL 외의 나머지 SQL은 그대로인지도 확인해주는 게 좋다.
     * */
    @Test
    public void updateSingle() {
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() { // 한 번에 여러 개의 SQL을 수정하는 기능을 검증한다.
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test(expected = SqlUpdateFailureException.class) // 존재하지 않는 키의 SQL을 변경하려고 시도할 때 예외가 발생하는 것을 검증한다.
    public void updateWithNotExistingKey() {
        sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
    }
}
