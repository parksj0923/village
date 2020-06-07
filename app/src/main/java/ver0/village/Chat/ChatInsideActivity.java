package ver0.village.Chat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import ver0.village.R;

public class ChatInsideActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private RecyclerView recyclerView;
    private String my_key, user_key, item_key;
    private EditText editText;
    private Button send_button, more_option_button;
    private ImageView product_img;
    private ArrayList<ChatItem> chatItemList = new ArrayList<ChatItem>();
    static final String[] OPTIONS = {"신고하기", "차단하기", "채팅방 알림 해제하기", "채팅방 나가기"};

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");
    DatabaseReference roomReference;
    private ChildEventListener chatEventListener;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;

    private String chatRoomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatinside);
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 0);
        byte[] byteUserImg = intent.getByteArrayExtra("userImg");
        byte[] byteItemImg = intent.getByteArrayExtra("itemImg");
        Bitmap userImg = BitmapFactory.decodeByteArray(byteUserImg, 0, byteUserImg.length);
        Bitmap itemImg = BitmapFactory.decodeByteArray(byteItemImg, 0, byteItemImg.length);
        Integer user_id;
        my_key = "test_id_" + id;
        if (id == 0){
            user_id = 1;
        } else {
            user_id = 0;
        }

        chatRoomKey = "test_id_0_test_id_1_item_key";
        roomReference = databaseReference.child(chatRoomKey);
        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(this, chatItemList, my_key, userImg);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        editText = (EditText)findViewById(R.id.edit_text_chat);
        send_button = (Button)findViewById(R.id.button_chat);
        more_option_button = (Button)findViewById(R.id.more_option_button);
        product_img = (ImageView)findViewById(R.id.image_product);

        setSupportActionBar(toolbar);
        toolbar_title.setText("박성주");
        product_img.setImageBitmap(itemImg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_arrow_backward);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(chatRecyclerViewAdapter);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                if(!message.equals("")) {
                    roomReference.push().setValue(new ChatItem(my_key, message, Calendar.getInstance().getTime().getTime()));
                    editText.setText("");
                }
            }
        });


        more_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        chatEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                ChatItem chatItem = dataSnapshot.getValue(ChatItem.class);
                chatItemList.add(chatItem);
                String sender = chatItem.getSender();
                chatRecyclerViewAdapter.notifyDataSetChanged();
                if (!my_key.equals(sender)){
                    chatItem.setRead(true);
                    dataSnapshot.getRef().setValue(chatItem);
                }
//                dataSnapshot.getRef().removeValue();


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
        };

        roomReference.addChildEventListener(chatEventListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override protected void onStop() {
        super.onStop();
        Constraints.Builder constraintsBuilder = new Constraints.Builder();
        constraintsBuilder.setRequiredNetworkType(NetworkType.CONNECTED);
        Constraints constraints = constraintsBuilder.build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ChatListener.class)
                .setInputData(createInputData("test_id_0_test_id_1_item_key"))
                .setConstraints(constraints).build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork(
                "ChatListener", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);
        roomReference.removeEventListener(chatEventListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        roomReference.addChildEventListener(chatEventListener);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatInsideActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.chat_dialog, null);
        builder.setView(view);

        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        ArrayAdapter arrayAdapter = new ArrayAdapter(ChatInsideActivity.this,  R.layout.chat_dialog_item, OPTIONS);

        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Data createInputData(String key){
        Data data = new Data.Builder()
                .putString("key", key)
                .build();
        return data;
    }

}
