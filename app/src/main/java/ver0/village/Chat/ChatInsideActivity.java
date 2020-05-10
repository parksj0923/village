package ver0.village.Chat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ver0.village.R;

public class ChatInsideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatinside);
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}
