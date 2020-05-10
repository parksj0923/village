package ver0.village.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ver0.village.R;

public class FavoriteKeywordActivity extends AppCompatActivity {

    EditText keyword_edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_keyword);
    }

    public void close(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        /*
        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("favofite_category", favoritecategory);
        editor.commit();*/

        overridePendingTransition(0,0);
    }

    public void addKeyword(View view) {

    }
}
