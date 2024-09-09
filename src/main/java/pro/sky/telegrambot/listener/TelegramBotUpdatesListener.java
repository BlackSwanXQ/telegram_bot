package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

//    @Autowired
    private final TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;

    Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");

    TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository,TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            if (update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Hello " + update.message().chat().firstName()));
                return;
            } else if (update.message().text().equals("/stop")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Goodbye " + update.message().chat().firstName()));
                return;
            }
//            } else if (update.message().text().equals("/delete/" + id)) {
//                NotificationTask notificationTask = notificationTaskRepository.findById(id)
//                        .orElseThrow(() -> new RuntimeException("Notification task not found"));
//                notificationTaskRepository.delete(notificationTask);
//            }
            createNotification(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void createNotification(Update update) {

        Matcher matcher = pattern.matcher(update.message().text());
        if (matcher.matches()) {
            // обрабатываем ситуацию, когда строка соответствует паттерну
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            NotificationTask notificationTask = new NotificationTask();
            notificationTask.setId(null);
            notificationTask.setDate(dateTime);
            notificationTask.setChatID(update.message().chat().id());
            notificationTask.setNotification(matcher.group(3));
            notificationTaskRepository.save(notificationTask);
        }
    }

}



