package ver0.village.Chat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;

import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;

public class RoomListener extends Worker {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private DatabaseReference userReference;
    private ChatDatabase db;
    private Context context;

    public RoomListener(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        db = ChatDatabase.getAppDatabase(getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        String key = getInputData().getString("key");
        userReference = databaseReference.child(key);
        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatRoomFirebase chatRoomFirebase = dataSnapshot.getValue(ChatRoomFirebase.class);
                String userKey = chatRoomFirebase.getUser_key();
                String itemKey = chatRoomFirebase.getItem_key();
                String chatRoomKey = dataSnapshot.getKey();

                final byte[][] img = new byte[2][1];
                StorageReference imgItemRef = storageReference.child("items/" + itemKey + ".jpg");
                StorageReference imgUserRef = storageReference.child("users/" + userKey + ".jpg");

                final long ONE_MEGABYTE = 1024 * 1024;
                imgItemRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        img[0] = Arrays.copyOf(bytes, bytes.length);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle errors
                    }
                });
                imgUserRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        img[1] = Arrays.copyOf(bytes, bytes.length);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle errors
                    }
                });

                // insert data to local db
                ChatRoom chatRoom = new ChatRoom(
                        chatRoomKey,
                        chatRoomFirebase.getUser_name(), userKey,
                        chatRoomFirebase.getItem_name(), itemKey,
                        img[0], img[1], true,
                        chatRoomFirebase.getHour_price(), chatRoomFirebase.getDay_price());
                db.chatRoomDao().insert(chatRoom);

                // add work
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ChatListener.class)
                        .setInputData(createInputData(chatRoomKey)).build();
                WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);

                // delete data
                imgItemRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
                imgUserRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
                dataSnapshot.getRef().removeValue();
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

    private Data createInputData(String key){
        Data data = new Data.Builder()
                .putString("key", key)
                .build();
        return data;
    }
}
