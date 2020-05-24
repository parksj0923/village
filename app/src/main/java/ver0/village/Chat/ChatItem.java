package ver0.village.Chat;

public class ChatItem {

    private String sender;
    private String message;
    private Long datetime;
    private Boolean read;

    public ChatItem(){

    }

    public ChatItem(String sender, String message, Long datetime) {
        this.sender = sender;
        this.message = message;
        this.datetime = datetime;
        this.read = false;
    }
    public ChatItem(String sender, String message, Long datetime, Boolean read) {
        this.sender = sender;
        this.message = message;
        this.datetime = datetime;
        this.read = read;
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
}
