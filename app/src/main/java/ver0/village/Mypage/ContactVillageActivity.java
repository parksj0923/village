package ver0.village.Mypage;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ver0.village.R;

public class ContactVillageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_village);
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        finish();
    }

    public void case1(View view) {
        Intent intent = new Intent(ContactVillageActivity.this, ContactType1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void case2(View view) {
    }

    public void case3(View view) {
    }

    public void case4(View view) {
    }

    public void case5(View view) {
    }

    public void case6(View view) {
    }
}
