package springbook.learningtest.spring.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import springbook.learningtest.spring.jdbc.Member;

import java.util.List;
import java.util.Map;

@Repository
public class MemberDao extends SqlMapClientDaoSupport {

    @Autowired
    protected void init(SqlMapClient sqlMapClient) {
        setSqlMapClient(sqlMapClient);
    }

    public void insert(Member member) {
        getSqlMapClientTemplate().insert("insertMember", member);
    }

    public void deleteAll() {
        getSqlMapClientTemplate().delete("deleteMemberAll");
    }

    public Member findById(int id) {
        return (Member) getSqlMapClientTemplate().queryForObject("findMemberById", id);
    }

    public List<Member> findAll() {
        return getSqlMapClientTemplate().queryForList("findMembers");
    }

    public Map searchMemberByName(String name) {
        name = "%" + name + "%";
        return getSqlMapClientTemplate().queryForMap("searchMemberByName", name, "id");
    }

    public void searchMemberByPoint(double point) {
        getSqlMapClientTemplate().queryWithRowHandler("searchMemberByPoint", point, new MemberRowHandler());
    }
}
