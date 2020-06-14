package ver0.village;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.internal.ListenerClass;
import ver0.village.Chat.ChatListener;
import ver0.village.Chat.RoomListener;
import ver0.village.WritingUpload.WritingActivity;
import ver0.village.database.ChatDatabase;
import ver0.village.utils.CustomViewPager;
import ver0.village.utils.TabPagerAdapter;

public class TabActivity extends AppCompatActivity {

    TabLayout tabLayout;
    CustomViewPager viewPager;

    int prevTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        tabLayout = findViewById(R.id.tab);

        AddListener listenerTask = new AddListener();
        listenerTask.execute();

        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("홈", R.drawable.iconhome_click, false)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("거래 진행", R.drawable.icontrade, false)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("글쓰기", R.drawable.iconwrite, false)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("채팅", R.drawable.iconchat, false)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("마이페이지", R.drawable.iconmypage, false)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        final TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setPagingEnabled(false);

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView tabicon = tab.getCustomView().findViewById(R.id.tapimage);
                TextView tabtext = tab.getCustomView().findViewById(R.id.taptext);

                if (tab.getPosition() == 2) {
                    if(prevTab == 1){
                        tabLayout.setScrollPosition(1, 0f, true);
                    }
                    Intent intent = new Intent(TabActivity.this, WritingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    tabLayout.getTabAt(prevTab).select();
                } else if(tab.getPosition() == 0) {
                    tabicon.setImageResource(R.drawable.iconhome_click);
                    tabtext.setTextColor(0xFF0AA864);
                    viewPager.setCurrentItem(tab.getPosition(), false);
                    prevTab = tab.getPosition();
                } else if(tab.getPosition() == 1) {
                    tabicon.setImageResource(R.drawable.icontrade_click);
                    tabtext.setTextColor(0xFF0AA864);
                    viewPager.setCurrentItem(tab.getPosition(), false);
                    prevTab = tab.getPosition();
                } else if(tab.getPosition() == 3) {
                    tabicon.setImageResource(R.drawable.iconchat_click);
                    tabtext.setTextColor(0xFF0AA864);
                    viewPager.setCurrentItem(tab.getPosition(), false);
                    prevTab = tab.getPosition();
                } else if(tab.getPosition() == 4) {
                    tabicon.setImageResource(R.drawable.iconmypage_click);
                    tabtext.setTextColor(0xFF0AA864);
                    viewPager.setCurrentItem(tab.getPosition(), false);
                    prevTab = tab.getPosition();
                } else {
                }




                /*
                if(tab.getPosition()!=3){
                    viewPager.setCurrentItem(tab.getPosition());
                    prevTab = tab.getPosition();
                }
                else{
                    UploadDialog customDialog = new UploadDialog(TabActivity.this);
                    customDialog.callFunction();
                    tabLayout.getTabAt(prevTab).select();
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView tabicon = tab.getCustomView().findViewById(R.id.tapimage);
                TextView tabtext = tab.getCustomView().findViewById(R.id.taptext);

                switch (tab.getPosition()){
                    case 0 :
                        tabicon.setImageResource(R.drawable.iconhome);
                        tabtext.setTextColor(0xFF909FBB);
                        break;
                    case 1 :
                        tabicon.setImageResource(R.drawable.icontrade);
                        tabtext.setTextColor(0xFF909FBB);
                        break;
                    case 2 :
                        break;
                    case 3 :
                        tabicon.setImageResource(R.drawable.iconchat);
                        tabtext.setTextColor(0xFF909FBB);
                        break;
                    case 4 :
                        tabicon.setImageResource(R.drawable.iconmypage);
                        tabtext.setTextColor(0xFF909FBB);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    Intent intent = new Intent(TabActivity.this, WritingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    tabLayout.getTabAt(prevTab).select();
                }
            }
        });
    }


    private View createTabView(String tapText, int tapImage, boolean tapalarm) {
        View tabView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tap, null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.taptext);
        ImageView img_tap = tabView.findViewById(R.id.tapimage);
        ImageView img_alarm = tabView.findViewById(R.id.tapalarm);

        if(tapImage == R.drawable.iconhome_click){
            txt_name.setTextColor(0xFF0AA864);
        }
        if(!tapalarm){
            img_alarm.setVisibility(View.GONE);
        }
        img_tap.setImageResource(tapImage);
        txt_name.setText(tapText);

        return tabView;
    }

    private Data createRoomInputData(String key){
        Data data = new Data.Builder()
                .putString("key", key)
                .build();
        return data;
    }

//    private Data createChatInputData(String key, String startKey){
//        Data data = new Data.Builder()
//                .putString("key", key)
//                .putString("startKey", startKey)
//                .build();
//        return data;
//    }

    private class AddListener extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            ChatDatabase db = ChatDatabase.getAppDatabase(getApplicationContext());
            List<String> chatRoomKeys = db.chatRoomDao().getChatRoomKeys();
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RoomListener.class)
                    .setInputData(createRoomInputData("1")).build();
            WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork(
                    "RoomListener",ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);

            for(int i = 0; i < chatRoomKeys.size(); i++){
                String key = chatRoomKeys.get(i);
                oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ChatListener.class)
                        .setInputData(createRoomInputData(key)).build();
                WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork(
                        "Chat" + key + "Listener",ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
        }
    }


}
