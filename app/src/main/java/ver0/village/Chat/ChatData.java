package ver0.village.Chat;

public class ChatData {

    private String sender;
    private String message;
    private String datetime;
    private Boolean read;

    public ChatData(){

    }

    public ChatData(String sender, String message, String datetime) {
        this.sender = sender;
        this.message = message;
        this.datetime = datetime;
        this.read = false;
    }
    public ChatData(String sender, String message, String datetime, Boolean read) {
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

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getsender() {
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDatetime(){
        return this.datetime;
    }

    public Boolean getRead(){
        return this.read;
    }
}
