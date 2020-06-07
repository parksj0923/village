package ver0.village.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ver0.village.R;

public class HomeFragmentSearchActivity extends AppCompatActivity {

    String search_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_search);


        EditText searchedittext = findViewById(R.id.search_editText);
        Intent intent = getIntent();
        search_str = intent.getStringExtra("search_now");
        if(search_str != null)
            searchedittext.setText(search_str);

        showKeyboard(searchedittext);

        searchedittext.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_str = s.toString();

            }
        });

    }

    public void showKeyboard(final EditText ettext){
        ettext.requestFocus();
        ettext.postDelayed(new Runnable(){
                               @Override public void run(){
                                   InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext,0);
                               }
                           }
                ,200);
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        finish();
    }

    public void func_search(View view) {

        Intent i = new Intent();
        setResult(RESULT_OK, i);
        if(search_str == null)
            finish();
        i.putExtra("search_str",search_str);
        finish();
    }
}
