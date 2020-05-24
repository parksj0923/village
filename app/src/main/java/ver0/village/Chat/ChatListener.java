package ver0.village.Chat;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ver0.village.database.ChatData;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

public class ChatListener extends Worker {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");
    DatabaseReference roomReference;
    ChatDatabase db;

    public ChatListener(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = ChatDatabase.getAppDatabase(getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        String roomKey = getInputData().getString("key");
        ChatRoom chatRoom = db.chatRoomDao().getChatRoom("key");
        int roomId = chatRoom.getId();
        roomReference = databaseReference.child(roomKey);
        roomReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String chatKey = dataSnapshot.getKey();
                ChatItem chatItem = dataSnapshot.getValue(ChatItem.class);
                ChatData chatData = new ChatData(chatKey,
                        chatItem.getSender(), chatItem.getMessage(),
                        chatItem.getDatetime(), chatItem.getRead(), roomId);
                db.chatDataDao().insert(chatData);
                db.chatRoomDao().setActive(roomKey, true);

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
        return Result.success();
    }
}
