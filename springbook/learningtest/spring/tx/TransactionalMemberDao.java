package springbook.learningtest.spring.tx;

import org.springframework.transaction.annotation.Transactional;
import springbook.learningtest.spring.jdbc.Member;

import java.util.List;

@Transactional
public interface TransactionalMemberDao {
    public void add(Member m);
    public void add(List<Member> members);
    public void deleteAll();

    @Transactional(readOnly = true)
    public long count();
}
