package ver0.village.Chat;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ver0.village.R;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatItem> itemList = new ArrayList<ChatItem>();
    private Bitmap profileImage;
    private String id;

    public ChatRecyclerViewAdapter(ArrayList<ChatItem> chatItemArrayList, String id){
        this.itemList = chatItemArrayList;
        this.id = id;
    }

    public ChatRecyclerViewAdapter(ArrayList<ChatItem> chatItemArrayList, Bitmap profileImage){
        this.itemList = chatItemArrayList;
        this.profileImage = profileImage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View totalView;;
        ConstraintLayout my_layout, op_layout;
        TextView text_my_chat, text_op_chat, text_my_datetime, text_op_datetime, text_my_read;
        ImageView profile_image;

        public ViewHolder(View view){
            super(view);

            totalView = view;

            my_layout = view.findViewById(R.id.layout_my_chat);
            op_layout = view.findViewById(R.id.layout_op_chat);

            text_my_chat = view.findViewById(R.id.text_my_message);
            text_op_chat = view.findViewById(R.id.text_op_message);
            text_my_datetime = view.findViewById(R.id.text_my_datetime);
            text_op_datetime = view.findViewById(R.id.text_op_datetime);
            text_my_read = view.findViewById(R.id.text_my_read);
            profile_image = view.findViewById(R.id.image_op_profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public ChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ChatRecyclerViewAdapter.ViewHolder holder = new ChatRecyclerViewAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ChatRecyclerViewAdapter.ViewHolder holder, final int position){
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ChatItem item = itemList.get(position);

        Boolean isMine = TRUE;
        String sender = item.getSender();
        if(!sender.equals(id)){
            isMine = FALSE;
        }

        if(isMine){
            holder.op_layout.setVisibility(View.GONE);
//            if(item.getRead()) holder.text_my_read.setText("읽음");
//            holder.text_my_datetime.setText(item.getDatetime());
            holder.text_my_chat.setText(item.getMessage());
        } else{
            holder.my_layout.setVisibility(View.GONE);
//            holder.profile_image.setImageBitmap(profileImage);
            holder.text_op_chat.setText(item.getMessage());
//            holder.text_op_datetime.setText(item.getDatetime());
        }

        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void addChatItem(String sender, String message, Long datetime) {
        ChatItem item = new ChatItem();
        item.setSender(sender);
        item.setMessage(message);
        item.setDatetime(datetime);
        itemList.add(item);
    }
}