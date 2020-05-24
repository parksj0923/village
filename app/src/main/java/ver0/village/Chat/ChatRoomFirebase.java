package ver0.village.Chat;

public class ChatRoomFirebase {
    private String key;
    private String user_name;
    private String user_key;
    private String item_name;
    private String item_key;

    public void setKey(String key){
        this.key = key;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public void setItem_key(String item_key) {
        this.item_key = item_key;
    }

    public String getKey() {
        return this.key;
    }
    public String getUser_name() {
        return user_name;
    }
    public String getUser_key() {
        return user_key;
    }
    public String getItem_name() {
        return item_name;
    }
    public String getItem_key() {
        return item_key;
    }
}
