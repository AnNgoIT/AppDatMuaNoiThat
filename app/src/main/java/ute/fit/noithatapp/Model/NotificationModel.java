package ute.fit.noithatapp.Model;

public class NotificationModel {
    private int notificationId;
    private String description;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationModel(int notificationId, String description) {
        this.notificationId = notificationId;
        this.description = description;
    }
}
