package springbook.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MockMailSender implements MailSender {

    // UserService로부터 전송 요청을 받은 메일 주소를 저장해 두고 이를 읽을 수 있게 한다.
    private List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
        return requests;
    }

    @Override
    public void send(SimpleMailMessage mailMessages) throws MailException {
        requests.add(mailMessages.getTo()[0]); // 전송 요청을 받은 이메일 주소를 저장해둔다. 간단하게 첫 번째 수신자 메일 주소만 저장했다.
    }

    @Override
    public void send(SimpleMailMessage[] mailMessages) throws MailException {
    }
}
