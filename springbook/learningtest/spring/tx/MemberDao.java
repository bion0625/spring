package springbook.learningtest.spring.tx;

import springbook.learningtest.spring.jdbc.Member;

import java.util.List;

public interface MemberDao {
    public void add(Member m);
    public void add(List<Member> members);
    public void deleteAll();
    public long count();
}
