package ver0.village.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import ver0.village.R;
import ver0.village.database.ChatData;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ChatRoomItem> itemList = new ArrayList<ChatRoomItem>();
    Context context;
    private String myKey;
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

    public ChatRoomRecyclerViewAdapter(ArrayList<ChatRoomItem> itemList, String key){
        this.itemList = itemList;
        this.myKey = key;
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

        long time = item.getLast_chat_time();
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
                ChatRoomItem chatRoomItem = itemList.get(position);
                Drawable userDraw = chatRoomItem.getImg_user();
                Drawable itemDraw = chatRoomItem.getImg_item();
                Bitmap bitmap = ((BitmapDrawable)userDraw).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] userImg = stream.toByteArray();
                bitmap = ((BitmapDrawable)itemDraw).getBitmap();
                stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] itemImg = stream.toByteArray();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("userImg", userImg);
                intent.putExtra("itemImg", itemImg);
                intent.putExtra("id", position);
                intent.putExtra("key", chatRoomItem.getKey());
                intent.putExtra("room_id", chatRoomItem.getId());
                intent.putExtra("my_key", myKey);
                WorkManager.getInstance(context).cancelUniqueWork(
                        "Chat" + chatRoomItem.getKey() + "Listener");
                context.startActivity(intent);
            }
        });
        holder.totalView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Snackbar snackbar = Snackbar.make(holder.totalView, "", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                layout.setPadding(0,0,0,0);
                TextView textView = (TextView) layout.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setVisibility(View.INVISIBLE);

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View snackView = mInflater.inflate(R.layout.chat_snackbar, null);
                TextView leaveText = (TextView) snackView.findViewById(R.id.leave_text);
                TextView cancelText = (TextView) snackView.findViewById(R.id.cancel_text);
                leaveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChatRoomItem chatRoomItem = itemList.get(position);
                        itemList.remove(position);
                        removeChat removeChatTask = new removeChat();
                        removeChatTask.execute(chatRoomItem.getKey());
                        notifyDataSetChanged();
                        snackbar.dismiss();
                    }
                });
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                layout.addView(snackView, objLayoutParams);
                snackbar.show();
                return true;
            }
        });
    }

    public void addItem(Integer id, String key, String item_name, String user_name, String last_chat, int last_chattime, int alarmnum, Drawable img_item, Drawable img_user) {
        ChatRoomItem item = new ChatRoomItem(
                id, key,
                item_name, user_name, last_chat,
                last_chattime, alarmnum, img_item, img_user);
        itemList.add(item);
    }


    public void setOnItemClickListener(ChatRoomRecyclerViewAdapter.ClickListener clickListener){
        ChatRoomRecyclerViewAdapter.clickListener = clickListener;
    }

    private void myCustomSnackbar()
    {
        // Create the Snackbar

    }
    private class removeChat extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... chatKey) {
            ChatDatabase db = ChatDatabase.getAppDatabase(context);
            ChatRoom chatRoom = db.chatRoomDao().getChatRoom(chatKey[0]);
            db.chatDataDao().deleteRoomChat(chatRoom.getId());
            db.chatRoomDao().setActive(chatKey[0], false);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
        }
    }

}