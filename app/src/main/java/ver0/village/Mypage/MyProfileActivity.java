package ver0.village.Mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ver0.village.R;

public class MyProfileActivity extends AppCompatActivity {


    TextView title, name, otheritem;
    EditText introtext;
    ImageView profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        init();
    }

    private void init() {
        title = findViewById(R.id.title);
        name = findViewById(R.id.text_user_name);
        introtext = findViewById(R.id.intro_text);
        profileimg = findViewById(R.id.img_profile);
        otheritem = findViewById(R.id.text_another_name);

        getInfofromshraredPre();
    }

    private void getInfofromshraredPre() {
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String profileimg_str = sf.getString("profileimg","");
        String name_str = sf.getString("nickname", "");
        String intro_str = sf.getString("intro", "");



        if(!profileimg_str.equals("")) {
            byte[] imageAsBytes = Base64.decode(profileimg_str.getBytes(), Base64.DEFAULT);
            profileimg.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }

        introtext.setText(intro_str);
        otheritem.setText(name_str + " 님의 물건");
        title.setText("안녕하세요, " + name_str + "입니다.");
        name.setText(name_str);

    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("revised", 1);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }


    int revise_state = 0;
    public void introrevise(View view) {
        TextView result = view.findViewById(R.id.intro_revise);
        if(revise_state == 0) {
            revise_state = 1;
            result.setText("확인");
            result.setTextColor(0xFF0AA864);
            introtext.setFocusableInTouchMode(true);
            introtext.requestFocus();
            introtext.setSelection(introtext.getText().length());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(introtext, InputMethodManager.SHOW_IMPLICIT);
        } else{
            revise_state = 0;
            result.setText("수정하기");
            result.setTextColor(0xFF909FBB);
            introtext.setFocusable(false);
            SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("intro", introtext.getText().toString());
            editor.commit();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    public void profilerevise(View view) {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                int result = data.getIntExtra("revised", -1);

                if(result == 1){
                    getInfofromshraredPre();
                }

            }
        }
    }
}
