package ver0.village.Chat;

import android.graphics.drawable.Drawable;

public class ChatItem {
    private String user_name;
    private String item_name, last_chat;
    private int last_chat_time, alarm_num;
    private Drawable img_user, img_item;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setLast_chat(String last_chat) {
        this.last_chat = last_chat;
    }

    public void setAlarm_num(int alarm_num) {
        this.alarm_num = alarm_num;
    }

    public void setImg_user(Drawable img_user) {
        this.img_user = img_user;
    }

    public void setImg_item(Drawable img_item) {
        this.img_item = img_item;
    }

    public void setLast_chat_time(int last_chat_time) {
        this.last_chat_time = last_chat_time;
    }

    public Drawable getImg_item() {
        return img_item;
    }

    public Drawable getImg_user() {
        return img_user;
    }

    public int getAlarm_num() {
        return alarm_num;
    }

    public int getLast_chat_time() {
        return last_chat_time;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getLast_chat() {
        return last_chat;
    }

    public String getUser_name() {
        return user_name;
    }


}
