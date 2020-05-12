package ver0.village.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ver0.village.R;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatRoomItem> itemList = new ArrayList<ChatRoomItem>();
    Context context;

    private static ChatRoomRecyclerViewAdapter.ClickListener clickListener;

    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        View totalView;;
        TextView text_username, text_itemname, text_lastchat, text_lastchat_time, text_alarmnum;
        ImageView img_user, img_item;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            totalView = view;

            text_itemname = view.findViewById(R.id.item_name);
            text_username = view.findViewById(R.id.user_name);
            text_lastchat = view.findViewById(R.id.last_chat);
            text_lastchat_time = view.findViewById(R.id.chat_time);
            text_alarmnum = view.findViewById(R.id.chat_alarm);

            img_user = view.findViewById(R.id.img_user);
            img_item = view.findViewById(R.id.img_item);



        }


        @Override
        public void onClick(View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public ChatRoomRecyclerViewAdapter(ArrayList<ChatRoomItem> itemList){
        this.itemList = (ArrayList<ChatRoomItem>) itemList.clone();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ChatRoomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_main, parent, false);
        context = parent.getContext();
        ChatRoomRecyclerViewAdapter.ViewHolder holder = new ChatRoomRecyclerViewAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ChatRoomRecyclerViewAdapter.ViewHolder holder, final int position){
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ChatRoomItem item = itemList.get(position);



        holder.text_itemname.setText(item.getItem_name());
        holder.text_username.setText(item.getUser_name());
        holder.text_lastchat.setText(item.getLast_chat());
        holder.text_alarmnum.setText(""+item.getAlarm_num());

        int time = item.getLast_chat_time();
        if(time < 60){
            holder.text_lastchat_time.setText(""+time+"분 전");
        } else if(time >= 60 && time < 1440){
            holder.text_lastchat_time.setText(""+time/60+"시간 전");
        } else if(time >= 1440){
            holder.text_lastchat_time.setText(""+time/1440+"일 전");
        } else{
            holder.text_lastchat_time.setText("time error");
        }

        holder.img_user.setBackground(item.getImg_user());
        holder.img_item.setBackground(item.getImg_item());

        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatInsideActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String item_name, String user_name, String last_chat, int last_chattime, int alarmnum, Drawable img_item, Drawable img_user) {
        ChatRoomItem item = new ChatRoomItem(item_name, user_name, last_chat,
                last_chattime, alarmnum, img_item, img_user);
        itemList.add(item);
    }


    public void setOnItemClickListener(ChatRoomRecyclerViewAdapter.ClickListener clickListener){
        ChatRoomRecyclerViewAdapter.clickListener = clickListener;
    }


}