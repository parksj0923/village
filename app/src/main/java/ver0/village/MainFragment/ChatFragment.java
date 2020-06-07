package ver0.village.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import ver0.village.R;
import ver0.village.database.ChatData;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatDatabaseReference = database.getReference("chat");
    DatabaseReference roomDatabaseReference = database.getReference("user");
    private ArrayList<ChatRoomItem> chatList = new ArrayList<ChatRoomItem>();
    private ChatRoomRecyclerViewAdapter itemAdapter = new ChatRoomRecyclerViewAdapter(chatList);
    private ChatDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Context context = getContext();
        recyclerView = view.findViewById(R.id.recycle_view);

        db = ChatDatabase.getAppDatabase(getContext());

        Constraints.Builder constraintsBuilder = new Constraints.Builder();
        constraintsBuilder.setRequiredNetworkType(NetworkType.CONNECTED);
        Constraints constraints = constraintsBuilder.build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ChatListener.class)
                .setInputData(createInputData("test_id_0_test_id_1_item_key"))
                .setConstraints(constraints).build();
        WorkManager.getInstance(getActivity()).enqueueUniqueWork(
                "ChatListener", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);

        ChatRoomItem testitem = new ChatRoomItem("asdfasdf","camera", "박성주", "안녕하세요", 100, 5,
                ContextCompat.getDrawable(context, R.drawable.sample_camera_chat),
                ContextCompat.getDrawable(context, R.drawable.sample_userimg));
        for(int i=0; i < 10; i ++) {
            chatList.add(testitem);
        }
        //createChatList();

        final RecyclerView.LayoutManager mLayoutManager =  new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                // ...
//            }
//        });
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

        roomDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                createChatList();
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

    private void createChatList(){
        List<ChatRoom> chatRoomList = db.chatRoomDao().getActiveChatRooms();
        ChatRoomItem chatRoomItem = new ChatRoomItem();
        for(int i=0; i < chatRoomList.size(); i++){
            ChatData chatData = db.chatDataDao().getLatestChat(chatRoomList.get(i).getId());
            chatRoomItem = new ChatRoomItem(
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
    }


}
