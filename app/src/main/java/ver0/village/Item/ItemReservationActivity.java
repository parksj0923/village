package ver0.village.Item;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ver0.village.R;
import ver0.village.utils.TimePickerDialog;

public class ItemReservationActivity extends AppCompatActivity {

    TextView starttime_dialog, endtime_dialog, starttime_title, endtime_title, rental_time, from, to, text;
    enum State {
        NONE, STARTTIME, ENDTIME, BOTH
    }
    State state = State.NONE;

    String start_str, end_str;
    Calendar start_cal, end_cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reservation);

        itit();
    }

    private void itit() {
        starttime_dialog = findViewById(R.id.starttime_dialog);
        endtime_dialog = findViewById(R.id.endtime_dialog);

        starttime_title = findViewById(R.id.timestart);
        endtime_title = findViewById(R.id.timeend);

        rental_time = findViewById(R.id.tottime);

        from = findViewById(R.id.tt);
        to = findViewById(R.id.tt2);
        text = findViewById(R.id.text);
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        finish();
    }

    public void starttime(View view) {
        TimePickerDialog pd = new TimePickerDialog();
        pd.setCancelable(false);

        pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
        pd.setDialogResult(new TimePickerDialog.OnMyDialogResult() {
            @Override
            public void finish(String result) {
                start_str = result;
                if(state == State.NONE){
                    state = State.STARTTIME;
                } else if(state == State.STARTTIME){
                } else {
                    to.setVisibility(View.VISIBLE);
                    from.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);

                    state = State.BOTH;
                    endtime_title.setText(end_str);
                    starttime_title.setText(start_str);
                }
                starttime_dialog.setText(start_str);

            }
        });
    }



    public void endtime(View view) {
        TimePickerDialog pd = new TimePickerDialog();
        pd.setCancelable(false);

        pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
        pd.setDialogResult(new TimePickerDialog.OnMyDialogResult() {
            @Override
            public void finish(String result) {
                end_str = result;
                if(state == State.NONE){
                    state = State.ENDTIME;
                } else if(state == State.ENDTIME){
                } else {
                    to.setVisibility(View.VISIBLE);
                    from.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);

                    state = State.BOTH;
                    endtime_title.setText(end_str);
                    starttime_title.setText(start_str);
                }
                endtime_dialog.setText(end_str);
            }
        });
    }

}
