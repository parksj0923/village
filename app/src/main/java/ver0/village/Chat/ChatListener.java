package ver0.village.Chat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.internal.ListenerClass;
import ver0.village.R;
import ver0.village.database.ChatData;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

public class ChatListener extends Worker {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");
    DatabaseReference roomReference;
    Query startQuery;
    ChatDatabase db;
    private Context context;
    private int roomId = 0;
    private String startKey = "";

    public ChatListener(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        db = ChatDatabase.getAppDatabase(getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        String roomKey = getInputData().getString("key");
//        String startKey = getInputData().getString("startKey");
        ChatRoom chatRoom = db.chatRoomDao().getChatRoom(roomKey);
        roomId = 0;
        if(chatRoom != null) {
            String userName = db.chatRoomDao().getUserName(roomKey);
            roomId = chatRoom.getId();
            ChatData chatData = db.chatDataDao().getLatestChat(roomId);
            startKey = chatData.getKey();
        }
        roomReference = databaseReference.child(roomKey);
        int finalRoomId = roomId;

        createNotificationChannel();
        roomReference.orderByKey().startAt(startKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String chatKey = dataSnapshot.getKey();
                ChatItem chatItem = dataSnapshot.getValue(ChatItem.class);
                if (chatRoom != null) {
                    ChatData chatData = new ChatData(chatKey,
                            chatItem.getSender(), chatItem.getMessage(),
                            chatItem.getDatetime(), chatItem.getRead(), finalRoomId);
                    db.chatDataDao().insert(chatData);
                    db.chatRoomDao().setActive(roomKey, true);
                }
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext(), "chat")
                        .setSmallIcon(R.drawable.sample_userimg)
                        .setContentTitle(chatItem.getSender())
                        .setContentText(chatItem.getMessage())
                        .setAutoCancel(true);
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chat noti";
            String description = "chat noti";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("chat", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
