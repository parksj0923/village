package ver0.village;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import ver0.village.utils.IntroPageAdapter;

public class IntroActivity extends AppCompatActivity {

    String phone;
    private ArrayList<Integer> imageList;
    private static final int DP = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        phone = getIntent().getStringExtra("phone");

        this.initializeData();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new IntroPageAdapter(this, imageList));

        final ImageView indicator1, indicator2, indicator3, indicator4;
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        indicator4 = findViewById(R.id.indicator4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                if(position == 0){
                    indicator1.setImageResource(R.drawable.indicator_large);
                    indicator2.setImageResource(R.drawable.indicator_small);
                    indicator3.setImageResource(R.drawable.indicator_small);
                    indicator4.setImageResource(R.drawable.indicator_small);
                } else if(position == 1){
                    indicator1.setImageResource(R.drawable.indicator_small);
                    indicator2.setImageResource(R.drawable.indicator_large);
                    indicator3.setImageResource(R.drawable.indicator_small);
                    indicator4.setImageResource(R.drawable.indicator_small);
                } else if(position == 2){
                    indicator1.setImageResource(R.drawable.indicator_small);
                    indicator2.setImageResource(R.drawable.indicator_small);
                    indicator3.setImageResource(R.drawable.indicator_large);
                    indicator4.setImageResource(R.drawable.indicator_small);
                } else if(position == 3){
                    indicator1.setImageResource(R.drawable.indicator_small);
                    indicator2.setImageResource(R.drawable.indicator_small);
                    indicator3.setImageResource(R.drawable.indicator_small);
                    indicator4.setImageResource(R.drawable.indicator_large);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }

    public void initializeData()
    {
        imageList = new ArrayList();

        imageList.add(R.drawable.intro1);
        imageList.add(R.drawable.intro2);
        imageList.add(R.drawable.intro3);
        imageList.add(R.drawable.intro4);
    }
    public void onClick(View view) {
        Intent intent = new Intent(IntroActivity.this,
                LoginActivity.class);
        intent.putExtra("phone", phone);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
//        Intent intent = new Intent(IntroActivity.this,
//                TabActivity.class);//첫번째로 나올 화면
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        IntroActivity.this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}
