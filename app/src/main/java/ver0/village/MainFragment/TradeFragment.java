package ver0.village.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ver0.village.Alarm.AlarmActivity;
import ver0.village.R;
import ver0.village.Trade.TradeSlideAdapter;

public class TradeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    TradeSlideAdapter slideAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);

        tabLayout = view.findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setText("남에게 빌려주기"));
        tabLayout.addTab(tabLayout.newTab().setText("내가 빌리기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.viewPager);


        slideAdapter = new TradeSlideAdapter(getContext());
        viewPager.setAdapter(slideAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        ImageView Alarm = view.findViewById(R.id.alarm_trade);
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        ImageView filter = view.findViewById(R.id.filter_trade);
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;
    }
}
