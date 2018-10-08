package com.laazer.wpe.tasks;

import com.laazer.wpe.spring.TaskConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Laazer
 */
@Component
public class ScheduledTasks {

    @Autowired
    private EmailTask emailTask;

    //@Scheduled(fixedRate = TaskConfig.EMAIL_TASK_RATE_MS)
    @Scheduled(cron = TaskConfig.TEST_CRON)
    public void runEmailTask() {
        this.emailTask.run();
    }
}
