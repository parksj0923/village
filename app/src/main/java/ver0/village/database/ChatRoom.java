package ver0.village.database;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity
public class ChatRoom {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String key;
    private String user_name;
    private String user_key;
    private String item_name;
    private String item_key;
    private byte[] img_user;
    private byte[] img_item;
    private boolean active;

    public void setId(Integer id) {
        this.id = id;
    }
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
    public void setImg_user(byte[] img_user) {
        this.img_user = img_user;
    }
    public void setImg_item(byte[] img_item) {
        this.img_item = img_item;
    }
    public void setActive(boolean flag) {
        this.active = flag;
    }

    public Integer getId() {
        return this.id;
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
    public byte[] getImg_item() {
        return img_item;
    }
    public byte[] getImg_user() {
        return img_user;
    }
    public boolean getActive() {
        return active;
    }

    public ChatRoom(String key,
                    String user_name, String user_key,
                    String item_name, String item_key,
                    byte[] img_item, byte[] img_user,
                    boolean active){
        this.key = key;
        this.user_name = user_name;
        this.user_key = user_key;
        this.item_name = item_name;
        this.item_key = item_key;
        this.img_item = img_item;
        this.img_user = img_user;
        this.active = active;
    }
}
