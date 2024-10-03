package pro.sky.telegrambot.sheduled;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class SchedulerTasks {

    @Autowired
    private TelegramBot telegramBo;

    private final NotificationTaskRepository notificationTaskRepository;

    SchedulerTasks(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Scheduled(cron = "0 02 01 * * ?") //every day at 01:02
    public void run() {
        System.out.println("hello world");
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void doneHomeWork() {
        LocalDateTime dateTimeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        findNotifications(dateTimeNow).forEach(notification -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy");
            String dateTime = notification.getDate().format(formatter);
            telegramBo.execute(new SendMessage(notification.getChatID(),
                    "в " + dateTime + " года " + notification.getNotification()));
        });
    }

    public List<NotificationTask> findNotifications(LocalDateTime localDateTime) {
        return notificationTaskRepository.findNotificationTaskByDate(localDateTime);
    }
}
