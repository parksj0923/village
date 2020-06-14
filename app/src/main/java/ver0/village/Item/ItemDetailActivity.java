package ver0.village.Item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import ver0.village.Chat.ChatInsideActivity;
import ver0.village.Chat.ChatItem;
import ver0.village.Chat.ChatRoomFirebase;
import ver0.village.R;
import ver0.village.database.ChatDatabase;
import ver0.village.database.ChatRoom;
import ver0.village.utils.NetworkTask;

public class ItemDetailActivity extends AppCompatActivity {

    private ArrayList<String> imageList;
    private ArrayList<String> anotherimgList;
    private ArrayList<String> anothertitleList;
    private ArrayList<Integer> anotherkeyList;
    TextView uploader_name, product_nametxt, product_explaintxt, pricehourtxt, pricedaytext, category_txt, another_text;
    ImageView category_img, profile_img;
    RecyclerView listView;

    //data
    String product_name, product_explain, nickname, image_name;
    int product_price_hour, product_price_day, category;
    int uploader_account_key, user_account_key, item_key;
    private byte[] data, data_;
    private String chatRoomKey;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDatabaseReference = database.getReference("user");
    DatabaseReference chatDatabaseReference = database.getReference("chat");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        user_account_key = sf.getInt("key", -1);

        Intent intent = getIntent(); /*데이터 수신*/
        Log.e("check", "check");
        imageList = new ArrayList();
        anotherkeyList = new ArrayList();
        anotherimgList = new ArrayList();
        anothertitleList = new ArrayList();
        item_key = intent.getExtras().getInt("key");
        getItemInfoFromServer(item_key);

        user_account_key = 0;

    }

    private void init() {
        uploader_name = findViewById(R.id.text_user_name);
        product_nametxt = findViewById(R.id.item_name);
        product_explaintxt = findViewById(R.id.item_information);
        pricehourtxt = findViewById(R.id.text_hour_price);
        pricedaytext = findViewById(R.id.text_day_price);
        category_txt = findViewById(R.id.text_category);
        category_img = findViewById(R.id.img_category);
        profile_img = findViewById(R.id.img_profile);
        listView = findViewById(R.id.listview);
        another_text = findViewById(R.id.text_another_name);

        uploader_name.setText(nickname);
        another_text.setText("\'"+nickname+"\'님의 또 다른 물건");
        product_nametxt.setText(product_name);
        product_explaintxt.setText(product_explain);
        pricehourtxt.setText(product_price_hour+"원");
        pricedaytext.setText(product_price_day+"원");
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        imageLoader.displayImage("http://52.78.244.194/account/download/" + image_name, profile_img);

        switch (category){
            case 1:
                category_img.setImageResource(R.drawable.category_icon1);
                category_txt.setText("전자제품");
                break;
            case 2:
                category_img.setImageResource(R.drawable.category_icon2);
                category_txt.setText("도서");
                break;
            case 3:
                category_img.setImageResource(R.drawable.category_icon3);
                category_txt.setText("생활/잡화");
                break;
            case 4:
                category_img.setImageResource(R.drawable.category_icon4);
                category_txt.setText("패션");
                break;
            case 5:
                category_img.setImageResource(R.drawable.category_icon5);
                category_txt.setText("스포츠");
                break;
            case 6:
                category_img.setImageResource(R.drawable.category_icon6);
                category_txt.setText("악기");
                break;
            case 7:
                category_img.setImageResource(R.drawable.category_icon7);
                category_txt.setText("모빌리티");
                break;
            case 8:
                category_img.setImageResource(R.drawable.category_icon8);
                category_txt.setText("화장품/미용");
                break;
            case 9:
                category_img.setImageResource(R.drawable.category_icon9);
                category_txt.setText("가구/인테리어");
                break;
            case 10:
                category_img.setImageResource(R.drawable.category_icon_etc);
                category_txt.setText("기타");
                break;

        }
    }

    private void getItemInfoFromServer(int itemkey) {
        try{
            JSONObject paramMap = new JSONObject();

            paramMap.put("product", itemkey);

            NetworkTask networkTask = new NetworkTask("product/list/specific", "GET", paramMap);
            networkTask.execute();
            String r = networkTask.get();
            JSONArray result = new JSONArray(r);

            Log.d("productdtail", r+"dfdafadfadfdafadfafaf"+result.length());


            uploader_account_key = (int)((JSONObject)result.get(0)).get("account_key");
            category = (int)((JSONObject)result.get(0)).get("category_key");
            product_name = (String)((JSONObject)result.get(0)).get("product_name");
            product_price_hour = (int)((JSONObject)result.get(0)).get("product_price_hour");
            product_price_day= (int)((JSONObject)result.get(0)).get("product_price_day");
            product_explain = (String)((JSONObject)result.get(0)).get("product_explain");
            nickname = (String)((JSONObject)result.get(0)).get("account_nickname");
            image_name = (String)((JSONObject)result.get(0)).get("account_profile");


            JSONObject detail_img = (JSONObject) result.get(2) ;

            for(int ii = 0; ii<5 ; ii++){
                String a  = (String)detail_img.get(""+ii);
                if(!a.equals("")) {
                    Log.d("test", a);
                    imageList.add(a);
                }
            }

            JSONArray another_array = (JSONArray) result.get(1);
            for(int i = 0; i<another_array.length() ; i++) {
                JSONObject another_item = (JSONObject) another_array.get(i);
                anotherimgList.add((String) another_item.get("product_image"));
                anotherkeyList.add((int) another_item.get("product_key"));
                anothertitleList.add((String) another_item.get("product_name"));
            }



            wait();

        } catch(Exception e){

        }


        init();


        ViewPager pager = findViewById(R.id.viewPager);
        PagerAdapter adapter = new ItemPhotoAdapter(this, imageList);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);

        AnotherItemPhotoAdapter adapter_another = new AnotherItemPhotoAdapter( this, anotherimgList, anothertitleList, anotherkeyList);
        listView.setAdapter(adapter_another);
    }


    public void prevBtnClick(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void reservation(View view) {
//        Intent intent = new Intent(ItemDetailActivity.this, ItemReservationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);

        ChatRoomFirebase roomData = new ChatRoomFirebase(
                nickname,
                uploader_account_key+"",
                product_name,
                item_key+"",
                product_price_hour,
                product_price_day);
        userDatabaseReference.child(uploader_account_key+"").push().setValue(roomData);

        Bitmap bitmap = ((BitmapDrawable) profile_img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        data = baos.toByteArray();

        Bitmap bitmap_ = ((BitmapDrawable) getDrawable(R.drawable.sample_camera)).getBitmap();
        ByteArrayOutputStream baos_ = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos_);
        data_ = baos.toByteArray();

        UploadTask uploadTask = storageReference.child("users/" + uploader_account_key + ".jpg").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

        UploadTask uploadTask_ = storageReference.child("items/" + item_key + ".jpg").putBytes(data_);
        uploadTask_.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        chatRoomKey = "user" + user_account_key + "item" + item_key;
        ChatItem startChatItem = new ChatItem(
                user_account_key+"",
                "\'" + nickname +  "\'님의 \'" +
                image_name + "\'의 예약이 확정되었습니다.",
                Calendar.getInstance().getTime().getTime());

        chatDatabaseReference.child(chatRoomKey).push().setValue(startChatItem);
        storeChatRoom storeTask = new storeChatRoom();
        storeTask.execute();
    }

    private class storeChatRoom extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {

            ChatRoom chatRoom = new ChatRoom(chatRoomKey,
                    nickname, uploader_account_key+"",
                    product_name, item_key+"",
                    data_, data, true,
                    product_price_hour, product_price_day);
            ChatDatabase db = ChatDatabase.getAppDatabase(getApplicationContext());
            db.chatRoomDao().insert(chatRoom);

            return db.chatRoomDao().getChatRoom(chatRoomKey).getId();
        }

        @Override
        protected void onPostExecute(Integer room_id) {
            Intent intent = new Intent(ItemDetailActivity.this, ChatInsideActivity.class);
            intent.putExtra("key", chatRoomKey);
            intent.putExtra("room_id", room_id);
            intent.putExtra("my_key", user_account_key+"");
            startActivity(intent);
        }
    }
}
