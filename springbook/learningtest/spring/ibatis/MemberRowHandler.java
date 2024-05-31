package springbook.learningtest.spring.ibatis;

import com.ibatis.sqlmap.client.event.RowHandler;
import springbook.learningtest.spring.jdbc.Member;

public class MemberRowHandler implements RowHandler {
    @Override
    public void handleRow(Object o) {
        Member member = (Member) o;
        System.out.println(
                String.format(
                        "ID: %s, NAME: %s, POINT: %.1f",
                        member.getId(), member.getName(), member.getPoint()
                )
        );
    }
}
