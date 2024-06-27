package springbook.service;

import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleService {

    @Scheduled(fixedDelay = 3000)
    public void schedule() {
        System.out.println("time check");
    }
}
