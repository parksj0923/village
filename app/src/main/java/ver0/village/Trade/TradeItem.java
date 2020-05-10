package ver0.village.Trade;

public class TradeItem {
    private int status;
    private String title;
    private String item_name;
    private String user_name;
    private String img_item_url;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getStatus() {
        return status;
    }

    public String getImg_item_url() {
        return img_item_url;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setImg_item_url(String img_item_url) {
        this.img_item_url = img_item_url;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
