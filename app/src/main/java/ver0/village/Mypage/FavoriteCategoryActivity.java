package ver0.village.Mypage;

import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ver0.village.R;

public class FavoriteCategoryActivity extends AppCompatActivity {

    int favoritecategory;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10;
    ImageView devideline1, devideline2, devideline3, devideline4, devideline5, devideline6, devideline7, devideline8, devideline9, devideline10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_category);

        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        favoritecategory = sf.getInt("favofite_category",0);
        init();
        category_setting(favoritecategory);
    }

    private void init() {
        textView1 = findViewById(R.id.category_text1);
        imageView1 = findViewById(R.id.category_icon1);
        devideline1 = findViewById(R.id.category_devideline1);

        textView2 = findViewById(R.id.category_text2);
        imageView2 = findViewById(R.id.category_icon2);
        devideline2 = findViewById(R.id.category_devideline2);

        textView3 = findViewById(R.id.category_text3);
        imageView3 = findViewById(R.id.category_icon3);
        devideline3 = findViewById(R.id.category_devideline3);

        textView4 = findViewById(R.id.category_text4);
        imageView4 = findViewById(R.id.category_icon4);
        devideline4 = findViewById(R.id.category_devideline4);

        textView5 = findViewById(R.id.category_text5);
        imageView5 = findViewById(R.id.category_icon5);
        devideline5 = findViewById(R.id.category_devideline5);

        textView6 = findViewById(R.id.category_text6);
        imageView6 = findViewById(R.id.category_icon6);
        devideline6 = findViewById(R.id.category_devideline6);

        textView7 = findViewById(R.id.category_text7);
        imageView7 = findViewById(R.id.category_icon7);
        devideline7 = findViewById(R.id.category_devideline7);

        textView8 = findViewById(R.id.category_text8);
        imageView8 = findViewById(R.id.category_icon8);
        devideline8 = findViewById(R.id.category_devideline8);

        textView9 = findViewById(R.id.category_text9);
        imageView9 = findViewById(R.id.category_icon9);
        devideline9 = findViewById(R.id.category_devideline9);

        textView10 = findViewById(R.id.category_text10);
        imageView10 = findViewById(R.id.category_icon10);
        devideline10 = findViewById(R.id.category_devideline10);

    }

    public void categoryclose(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();


        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("favofite_category", favoritecategory);
        editor.commit();

        overridePendingTransition(0,0);
    }

    public void category1(View view) {

        boolean selected;
        selected = category_calculation(1);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView1.setTextColor(0xFFEEFAF5);
            imageView1.setImageResource(R.drawable.category_icon1);
            devideline1.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView1.setTextColor(0xFF3B4861);
            imageView1.setImageResource(R.drawable.category_black1);
            devideline1.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category2(View view) {

        boolean selected;
        selected = category_calculation(2);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView2.setTextColor(0xFFEEFAF5);
            imageView2.setImageResource(R.drawable.category_icon2);
            devideline2.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView2.setTextColor(0xFF3B4861);
            imageView2.setImageResource(R.drawable.category_black2);
            devideline2.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category3(View view) {

        boolean selected;
        selected = category_calculation(3);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView3.setTextColor(0xFFEEFAF5);
            imageView3.setImageResource(R.drawable.category_icon3);
            devideline3.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView3.setTextColor(0xFF3B4861);
            imageView3.setImageResource(R.drawable.category_black3);
            devideline3.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category4(View view) {

        boolean selected;
        selected = category_calculation(4);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView4.setTextColor(0xFFEEFAF5);
            imageView4.setImageResource(R.drawable.category_icon4);
            devideline4.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView4.setTextColor(0xFF3B4861);
            imageView4.setImageResource(R.drawable.category_black4);
            devideline4.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category5(View view) {
        boolean selected;
        selected = category_calculation(5);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView5.setTextColor(0xFFEEFAF5);
            imageView5.setImageResource(R.drawable.category_icon5);
            devideline5.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView5.setTextColor(0xFF3B4861);
            imageView5.setImageResource(R.drawable.category_black5);
            devideline5.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category6(View view) {
        boolean selected;
        selected = category_calculation(6);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView6.setTextColor(0xFFEEFAF5);
            imageView6.setImageResource(R.drawable.category_icon6);
            devideline6.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView6.setTextColor(0xFF3B4861);
            imageView6.setImageResource(R.drawable.category_black6);
            devideline6.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category7(View view) {
        boolean selected;
        selected = category_calculation(7);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView7.setTextColor(0xFFEEFAF5);
            imageView7.setImageResource(R.drawable.category_icon7);
            devideline7.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView7.setTextColor(0xFF3B4861);
            imageView7.setImageResource(R.drawable.category_black7);
            devideline7.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category8(View view) {
        boolean selected;
        selected = category_calculation(8);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView8.setTextColor(0xFFEEFAF5);
            imageView8.setImageResource(R.drawable.category_icon8);
            devideline8.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView8.setTextColor(0xFF3B4861);
            imageView8.setImageResource(R.drawable.category_black8);
            devideline8.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category9(View view) {
        boolean selected;
        selected = category_calculation(9);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView9.setTextColor(0xFFEEFAF5);
            imageView9.setImageResource(R.drawable.category_icon9);
            devideline9.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView9.setTextColor(0xFF3B4861);
            imageView9.setImageResource(R.drawable.category_black9);
            devideline9.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }

    public void category10(View view) {
        boolean selected;
        selected = category_calculation(10);
        if(selected) {
            view.setBackgroundColor(0xFF0AA864);
            textView10.setTextColor(0xFFEEFAF5);
            imageView10.setImageResource(R.drawable.category_icon_etc);
            devideline10.setVisibility(View.GONE);
        } else {
            view.setBackgroundColor(0xFFFFFFFF);
            textView10.setTextColor(0xFF3B4861);
            imageView10.setImageResource(R.drawable.category_blacketc);
            devideline10.setVisibility(View.VISIBLE);
        }

        Log.d("category_num", ""+favoritecategory);
    }



    private boolean category_calculation(int selecttedcategory){
        int temp = favoritecategory;
        switch (selecttedcategory){
            case 1:
                favoritecategory = favoritecategory | 0x00000001;
                break;
            case 2:
                favoritecategory = favoritecategory | 0x00000002;
                break;
            case 3:
                favoritecategory = favoritecategory | 0x00000004;
                break;
            case 4:
                favoritecategory = favoritecategory | 0x00000008;
                break;
            case 5:
                favoritecategory = favoritecategory | 0x00000010;
                break;
            case 6:
                favoritecategory = favoritecategory | 0x00000020;
                break;
            case 7:
                favoritecategory = favoritecategory | 0x00000040;
                break;
            case 8:
                favoritecategory = favoritecategory | 0x00000080;
                break;
            case 9:
                favoritecategory = favoritecategory | 0x00000100;
                break;
            case 10:
                favoritecategory = favoritecategory | 0x00000200;
                break;

        }
        if(temp == favoritecategory) {
            switch (selecttedcategory){
                case 1:
                    favoritecategory = favoritecategory - 0x00000001;
                    break;
                case 2:
                    favoritecategory = favoritecategory - 0x00000002;
                    break;
                case 3:
                    favoritecategory = favoritecategory - 0x00000004;
                    break;
                case 4:
                    favoritecategory = favoritecategory - 0x00000008;
                    break;
                case 5:
                    favoritecategory = favoritecategory - 0x00000010;
                    break;
                case 6:
                    favoritecategory = favoritecategory - 0x00000020;
                    break;
                case 7:
                    favoritecategory = favoritecategory - 0x00000040;
                    break;
                case 8:
                    favoritecategory = favoritecategory - 0x00000080;
                    break;
                case 9:
                    favoritecategory = favoritecategory - 0x00000100;
                    break;
                case 10:
                    favoritecategory = favoritecategory - 0x00000200;
                    break;

            }
            return false;
        }
        return true;
    }

    private void category_setting(int category_data){
        ConstraintLayout category1, category2, category3, category4, category5, category6, category7, category8, category9, category10;
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);
        category4 = findViewById(R.id.category4);
        category5 = findViewById(R.id.category5);
        category6 = findViewById(R.id.category6);
        category7 = findViewById(R.id.category7);
        category8 = findViewById(R.id.category8);
        category9 = findViewById(R.id.category9);
        category10 = findViewById(R.id.category10);
        if(category_data % 2 == 1){
            category1.setBackgroundColor(0xFF0AA864);
            textView1.setTextColor(0xFFEEFAF5);
            imageView1.setImageResource(R.drawable.category_icon1);
            devideline1.setVisibility(View.GONE);
        }
        if((category_data >>1 )% 2 == 1){
            category2.setBackgroundColor(0xFF0AA864);
            textView2.setTextColor(0xFFEEFAF5);
            imageView2.setImageResource(R.drawable.category_icon2);
            devideline2.setVisibility(View.GONE);
        }
        if((category_data >>2 )% 2 == 1){
            category3.setBackgroundColor(0xFF0AA864);
            textView3.setTextColor(0xFFEEFAF5);
            imageView3.setImageResource(R.drawable.category_icon3);
            devideline3.setVisibility(View.GONE);
        }
        if((category_data >>3 )% 2 == 1){
            category4.setBackgroundColor(0xFF0AA864);
            textView4.setTextColor(0xFFEEFAF5);
            imageView4.setImageResource(R.drawable.category_icon4);
            devideline4.setVisibility(View.GONE);
        }
        if((category_data >>4 )% 2 == 1){
            category5.setBackgroundColor(0xFF0AA864);
            textView5.setTextColor(0xFFEEFAF5);
            imageView5.setImageResource(R.drawable.category_icon5);
            devideline5.setVisibility(View.GONE);
        }
        if((category_data >>5 )% 2 == 1){
            category6.setBackgroundColor(0xFF0AA864);
            textView6.setTextColor(0xFFEEFAF5);
            imageView6.setImageResource(R.drawable.category_icon6);
            devideline6.setVisibility(View.GONE);
        }
        if((category_data >>6 )% 2 == 1){
            category7.setBackgroundColor(0xFF0AA864);
            textView7.setTextColor(0xFFEEFAF5);
            imageView7.setImageResource(R.drawable.category_icon7);
            devideline7.setVisibility(View.GONE);
        }
        if((category_data >>7 )% 2 == 1){
            category8.setBackgroundColor(0xFF0AA864);
            textView8.setTextColor(0xFFEEFAF5);
            imageView8.setImageResource(R.drawable.category_icon8);
            devideline8.setVisibility(View.GONE);
        }
        if((category_data >>8 )% 2 == 1){
            category9.setBackgroundColor(0xFF0AA864);
            textView9.setTextColor(0xFFEEFAF5);
            imageView9.setImageResource(R.drawable.category_icon9);
            devideline9.setVisibility(View.GONE);
        }
        if((category_data >>9 )% 2 == 1){
            category10.setBackgroundColor(0xFF0AA864);
            textView10.setTextColor(0xFFEEFAF5);
            imageView10.setImageResource(R.drawable.category_icon_etc);
            devideline10.setVisibility(View.GONE);
        }


    }
}
