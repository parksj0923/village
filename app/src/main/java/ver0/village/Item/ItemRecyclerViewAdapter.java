package ver0.village.Item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ver0.village.R;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HomeItem> itemList = new ArrayList<HomeItem>();
    Context context;

    private static ClickListener clickListener;

    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        View totalView;;
        TextView text_itemname, text_uploadername, text_hourcost, text_daycost;
        ImageView img_heart, img_item;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            totalView = view;

            text_itemname = view.findViewById(R.id.item_name);
            //text_uploadername = view.findViewById(R.id.text_uploader_name);
            text_hourcost = view.findViewById(R.id.text_hour_price);
            text_daycost = view.findViewById(R.id.text_day_price);

            img_heart = view.findViewById(R.id.img_heart_or_not);
            img_item = view.findViewById(R.id.itemimg);

        }

        @Override
        public void onClick(View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public ItemRecyclerViewAdapter(){

    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final HomeItem item = itemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if(item.isHeart()){
            holder.img_heart.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_red));
        }
        else{
            holder.img_heart.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_white));
        }
        holder.img_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.isHeart()) {
                    holder.img_heart.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_white));
                    item.setHeart(false);
                }
                else{
                    holder.img_heart.setBackground(ContextCompat.getDrawable(context, R.drawable.heart_red));
                    item.setHeart(true);
                }
            }
        });

        holder.text_itemname.setText(item.getItem_name());
        //holder.text_uploadername.setText(item.getUploader_name());
        holder.text_daycost.setText(""+item.getDay_cost()+"원");
        holder.text_hourcost.setText(""+item.getHour_cost()+"원");


        Glide.with(context)
                .load(item.getImg_item_url())
                .into(holder.img_item);


        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("key", item.getProduct_key());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    /*public void addItem(boolean isHeart, String item_name, String uploader_name, int hour_cost, int day_cost,
                        double uploader_review_score, int uploader_review_num, Drawable img_item) {
        HomeItem item = new HomeItem();
        item.setHeart(isHeart);
        item.setItem_name(item_name);
        item.setUploader_name(uploader_name);
        item.setHour_cost(hour_cost);
        item.setDay_cost(day_cost);
        item.setUploader_review_score(uploader_review_score);
        item.setUploader_review_num(uploader_review_num);
        item.setImg_item(img_item);

        itemList.add(item);
    }*/


    public void addItem(HomeItem item){
        itemList.add(item);
    }

    public void removeallitem(){
        itemList.removeAll(itemList);
    }


    public void setOnItemClickListener(ClickListener clickListener){
        ItemRecyclerViewAdapter.clickListener = clickListener;
    }
}
