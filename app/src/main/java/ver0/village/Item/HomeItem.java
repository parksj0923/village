package ver0.village.Item;

import android.graphics.drawable.Drawable;

public class HomeItem {
    private boolean isHeart = false;
    private String item_name = "Sample";
    //private String uploader_name = "Sample";
    private int hour_cost = 123, day_cost = 456;
    private int product_key = 0;
    private String img_item_url;

    public void setHeart(boolean isHeart){
        this.isHeart = isHeart;
    }

    public void setItem_name(String item_name){
        this.item_name = item_name;
    }

    //public void setUploader_name(String uploader_name){
   //     this.uploader_name = uploader_name;
    //}

    public void setDay_cost(int day_cost) {
        this.day_cost = day_cost;
    }

    public void setHour_cost(int hour_cost) {
        this.hour_cost = hour_cost;
    }


    public void setImg_item_url(String img_item) {
        this.img_item_url = img_item;
    }


    public String getItem_name(){
        return this.item_name;
    }

    //public String getUploader_name(){
     //   return this.uploader_name;
    //}

    public int getDay_cost() {
        return day_cost;
    }

    public int getHour_cost() {
        return hour_cost;
    }

    public boolean isHeart() {
        return isHeart;
    }

    public String getImg_item_url() {
        return img_item_url;
    }


    public int getProduct_key() {
        return product_key;
    }

    public void setProduct_key(int product_key) {
        this.product_key = product_key;
    }
}
