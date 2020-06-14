package ver0.village.Chat;

public class ChatRoomFirebase {
//    private String key;
    private String user_name;
    private String user_key;
    private String item_name;
    private String item_key;
    private int hour_price;
    private int day_price;

//    public void setKey(String key){
//        this.key = key;
//    }
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
    public void setHour_price(int hour_price) {
        this.hour_price = hour_price;
    }
    public void setDay_price(int day_price) {
        this.day_price = day_price;
    }

//    public String getKey() {
//        return this.key;
//    }
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
    public int getHour_price() {
        return hour_price;
    }
    public int getDay_price(){
        return day_price;
    }

    public ChatRoomFirebase(String user_name, String user_key,
                            String item_name, String item_key,
                            Integer hour_price, Integer day_price){
        this.user_name = user_name;
        this.user_key = user_key;
        this.item_name = item_name;
        this.item_key = item_key;
        this.hour_price = hour_price;
        this.day_price = day_price;
    }
}
