package ver0.village.Trade;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import ver0.village.R;

public class TradeBorrowRecyclerViewAdapter extends RecyclerView.Adapter<TradeBorrowRecyclerViewAdapter.ViewHolder> {
    private ArrayList<TradeItem> itemList = new ArrayList<TradeItem>();
    Context context;

    private static ClickListener clickListener;

    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        View totalView;
        TextView status_text;
        TextView title_text;
        TextView item_text;
        TextView username_text;
        ImageView status_img;
        ImageView item_img;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            totalView = view;
            status_text = view.findViewById(R.id.lendstatus_text);
            title_text = view.findViewById(R.id.titleText);
            item_text = view.findViewById(R.id.item_name);
            username_text = view.findViewById(R.id.user_name);
            status_img = view.findViewById(R.id.lendstatus);
            item_img = view.findViewById(R.id.item_img);
        }

        @Override
        public void onClick(View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public TradeBorrowRecyclerViewAdapter(){

    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_borrow_apater, parent, false);
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final TradeItem item = itemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        holder.title_text.setText(item.getTitle());
        holder.item_text.setText(item.getItem_name());
        holder.username_text.setText(item.getUser_name());


        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(item.getImg_item_url(), holder.item_img);

        switch (item.getStatus()){
            case 1:
                holder.status_img.setImageResource(R.drawable.lend_status1);
                holder.status_text.setText("수락 대기");
                break;
            case 2:
                holder.status_img.setImageResource(R.drawable.lend_status2);
                holder.status_text.setText("예약 확정");
                break;
            case 3:
                holder.status_img.setImageResource(R.drawable.lend_status3);
                holder.status_text.setText("빌리기");
                break;
            case 4:
                holder.status_img.setImageResource(R.drawable.lend_status4);
                holder.status_text.setText("대여중");
                break;
            case 5:
                holder.status_img.setImageResource(R.drawable.lend_status5);
                holder.status_text.setText("거래 완료");
                break;

        }

        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (item.getStatus()){
                    case 1:
                        Intent intent = new Intent(context, LendStatus1Activity.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context, LendStatus2Activity.class);
                        context.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(context, LendStatus3Activity.class);
                        context.startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(context, LendStatus4Activity.class);
                        context.startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(context, LendStatus5Activity.class);
                        context.startActivity(intent5);
                        break;

                }

            }
        });
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int status, String title, String item_name, String user_nae, String item_img_url) {
        TradeItem item = new TradeItem();
        item.setStatus(status);
        item.setTitle(title);
        item.setItem_name(item_name);
        item.setUser_name(user_nae);
        item.setImg_item_url(item_img_url);


        itemList.add(item);
    }


    public void setOnItemClickListener(ClickListener clickListener){
        TradeBorrowRecyclerViewAdapter.clickListener = clickListener;
    }
}

