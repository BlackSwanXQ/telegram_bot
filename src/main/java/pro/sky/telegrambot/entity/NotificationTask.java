package pro.sky.telegrambot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;
//    @Column (name = "chatid")
    private Long chatID;
    private LocalDateTime date;
    private String notification;

    public NotificationTask(Long id, Long chatID, LocalDateTime date, String notification) {
        this.id = id;
        this.chatID = chatID;
        this.date = date;
        this.notification = notification;
    }

    public NotificationTask() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return chatID == that.chatID && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(notification, that.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatID, date, notification);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatID=" + chatID +
                ", dateTime=" + date +
                ", notification='" + notification + '\'' +
                '}';
    }
}
