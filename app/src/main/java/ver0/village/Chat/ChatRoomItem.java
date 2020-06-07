package ver0.village.Chat;

import android.graphics.drawable.Drawable;

public class ChatRoomItem {
    private String key;
    private String user_name, item_name, last_chat;
    private int alarm_num;
    private long last_chat_time;
    private Drawable img_user, img_item;

    public void setKey(String key) {
        this.key = key;
    }
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

    public String getKey() {
        return key;
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
    public long getLast_chat_time() {
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

    public ChatRoomItem(){

    }

    public ChatRoomItem(
            String key,
            String item_name, String user_name, String last_chat,
            long last_chattime, int alarmnum, Drawable img_item, Drawable img_user){
        this.key = key;
        this.item_name = item_name;
        this.user_name = user_name;
        this.last_chat = last_chat;
        this.last_chat_time = last_chattime;
        this.alarm_num = alarmnum;
        this.img_item = img_item;
        this.img_user = img_user;
    }

}
