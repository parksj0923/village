package ver0.village.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ver0.village.Chat.ChatItem;
import ver0.village.Chat.ChatListener;
import ver0.village.Chat.ChatRoomFirebase;
import ver0.village.Chat.ChatRoomItem;
import ver0.village.Chat.ChatRoomRecyclerViewAdapter;
import ver0.village.Chat.RoomListener;
import ver0.village.R;
import ver0.village.database.ChatData;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatDatabaseReference = database.getReference("chat");
    DatabaseReference roomDatabaseReference = database.getReference("user");
    private ArrayList<ChatRoomItem> chatList = new ArrayList<ChatRoomItem>();
    private ChatRoomRecyclerViewAdapter itemAdapter;
    private ChatDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Context context = getContext();
        recyclerView = view.findViewById(R.id.recycle_view);

        SharedPreferences sf = getActivity().getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        Integer key = sf.getInt("key", -1);

        itemAdapter = new ChatRoomRecyclerViewAdapter(chatList, key+"");
        db = ChatDatabase.getAppDatabase(getContext());

        roomDatabaseReference = roomDatabaseReference.child("1");
//        Constraints.Builder constraintsBuilder = new Constraints.Builder();
//        constraintsBuilder.setRequiredNetworkType(NetworkType.CONNECTED);
//        Constraints constraints = constraintsBuilder.build();

//        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ChatListener.class)
//                .setInputData(createInputData("test_id_0_test_id_1_item_key"))
//                .setConstraints(constraints).build();
//        WorkManager.getInstance(getActivity()).enqueueUniqueWork(
//                "ChatListener", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);

//        ChatRoomItem testitem = new ChatRoomItem("asdfasdf","camera", "박성주", "안녕하세요", 100, 5,
//                ContextCompat.getDrawable(context, R.drawable.sample_camera_chat),
//                ContextCompat.getDrawable(context, R.drawable.sample_userimg));
//        for(int i=0; i < 10; i ++) {
//            chatList.add(testitem);
//        }
        createChatList createChatListTask = new createChatList();
        createChatListTask.execute();

        final RecyclerView.LayoutManager mLayoutManager =  new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        roomDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                createChatList createChatListTask = new createChatList();
                createChatListTask.execute();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        chatDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String chatKey = dataSnapshot.getKey();
                ChatItem chatItem = dataSnapshot.getValue(ChatItem.class);
                //datetime, chat content, unread number

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private Data createInputData(String key){
        Data data = new Data.Builder()
                .putString("key", key)
                .build();
        return data;
    }

//    private void createChatList(){
//        List<ChatRoom> chatRoomList = db.chatRoomDao().getActiveChatRooms();
//        ChatRoomItem chatRoomItem = new ChatRoomItem();
//        for(int i=0; i < chatRoomList.size(); i++){
//            ChatData chatData = db.chatDataDao().getLatestChat(chatRoomList.get(i).getId());
//            chatRoomItem = new ChatRoomItem(
//                    chatRoomList.get(i).getKey(),
//                    chatRoomList.get(i).getItem_name(),
//                    chatRoomList.get(i).getUser_name(),
//                    chatData.getMessage(),
//                    chatData.getDatetime(),
//                    db.chatDataDao().getUnreadChatNumber(chatRoomList.get(i).getId()),
//                    new BitmapDrawable(BitmapFactory.decodeByteArray(
//                            chatRoomList.get(i).getImg_item(), 0, chatRoomList.get(i).getImg_item().length)),
//                    new BitmapDrawable(BitmapFactory.decodeByteArray(
//                            chatRoomList.get(i).getImg_user(), 0, chatRoomList.get(i).getImg_user().length))
//            );
//            chatList.add(chatRoomItem);
//        }
//        itemAdapter.notifyDataSetChanged();
//    }

    private class createChatList extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            List<ChatRoom> chatRoomList = db.chatRoomDao().getActiveChatRooms();
            ChatRoomItem chatRoomItem = new ChatRoomItem();
            chatList.clear();
            for(int i=0; i < chatRoomList.size(); i++){
                ChatData chatData = db.chatDataDao().getLatestChat(chatRoomList.get(i).getId());
                chatRoomItem = new ChatRoomItem(
                        chatRoomList.get(i).getId(),
                        chatRoomList.get(i).getKey(),
                        chatRoomList.get(i).getItem_name(),
                        chatRoomList.get(i).getUser_name(),
                        chatData.getMessage(),
                        chatData.getDatetime(),
                        db.chatDataDao().getUnreadChatNumber(chatRoomList.get(i).getId()),
                        new BitmapDrawable(BitmapFactory.decodeByteArray(
                                chatRoomList.get(i).getImg_item(), 0, chatRoomList.get(i).getImg_item().length)),
                        new BitmapDrawable(BitmapFactory.decodeByteArray(
                                chatRoomList.get(i).getImg_user(), 0, chatRoomList.get(i).getImg_user().length))
                );
                chatList.add(chatRoomItem);
            }
            itemAdapter.notifyDataSetChanged();
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
        }
    }


    private class updateChatList extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... chatKey) {

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
        }
    }
}
