package ver0.village.Item;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ver0.village.R;
import ver0.village.utils.NetworkTask;

public class ItemDetailActivity extends AppCompatActivity {

    private ArrayList<String> imageList;
    private ArrayList<String> anotherimgList;
    private ArrayList<String> anothertitleList;
    private ArrayList<Integer> anotherkeyList;
    TextView uploader_name, product_nametxt, product_explaintxt, pricehourtxt, pricedaytext, category_txt, another_text;
    ImageView category_img, profile_img;
    RecyclerView listView;

    String product_name, product_explain, nickname, image_name;
    int product_price_hour, product_price_day, category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent(); /*데이터 수신*/

        imageList = new ArrayList();
        anotherkeyList = new ArrayList();
        anotherimgList = new ArrayList();
        anothertitleList = new ArrayList();
        int itemkey = intent.getExtras().getInt("key");
        getItemInfoFromServer(itemkey);



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
        Intent intent = new Intent(ItemDetailActivity.this, ItemReservationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
