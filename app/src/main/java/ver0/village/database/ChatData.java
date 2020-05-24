package ver0.village.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = ChatRoom.class,
        parentColumns = "id",
        childColumns = "room_id",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"id", "datetime", "room_id"},
                unique = true)})
public class ChatData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String key;
    private String sender;
    private String message;
    private Long datetime;
    private Boolean read;
    private int room_id;

    public ChatData(String key, String sender, String message,
                    Long datetime, Boolean read, int room_id){
        this.key = key;
        this.sender = sender;
        this.message = message;
        this.datetime = datetime;
        this.read = read;
        this.room_id = room_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }
    public void setRead(Boolean read) {
        this.read = read;
    }
    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getId() {
        return this.id;
    }
    public String getKey() {
        return this.key;
    }
    public String getSender() {
        return this.sender;
    }
    public String getMessage() {
        return this.message;
    }
    public Long getDatetime(){
        return this.datetime;
    }
    public Boolean getRead(){
        return this.read;
    }
    public Integer getRoom_id() {
        return this.room_id;
    }
}
