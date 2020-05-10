package ver0.village.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ver0.village.R;

public class ContactType4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_type4);
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        finish();
    }
}
