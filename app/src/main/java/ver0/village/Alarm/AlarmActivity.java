package ver0.village.Alarm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import ver0.village.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        AlarmRecyclerViewAdapter alarmAdapter = new AlarmRecyclerViewAdapter();

        alarmAdapter.addItem("공지 제목", "공지 내용", 10, getDrawable(R.drawable.alarm_time));
        alarmAdapter.addItem("공지 제목", "공지 내용", 10, getDrawable(R.drawable.alarm_time));
        alarmAdapter.addItem("공지 제목", "공지 내용", 10, getDrawable(R.drawable.alarm_time));
        alarmAdapter.addItem("공지 제목", "공지 내용", 10, getDrawable(R.drawable.alarm_time));
        alarmAdapter.addItem("공지 제목", "공지 내용", 10, getDrawable(R.drawable.alarm_time));




        RecyclerView.LayoutManager mLayoutManager = null;
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(alarmAdapter);
    }

    public void prevBtnClick(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}
