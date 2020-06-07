package ver0.village.WritingUpload;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ver0.village.R;
import ver0.village.utils.NetworkTask;

public class WritingActivity extends AppCompatActivity {



    enum State {
        MAIN, CATEGORY
    }
    State state = State.MAIN;

    //imagePicker
    List<Image> images;
    ArrayList<Image> images_arr;
    ConstraintLayout imagePickerFirstLayout, imagePickerReLayout;
    RecyclerView listView;
    TextView picure_num;

    //data
    int category = -1;
    String item_name, explanation_string;
    int hourprice_num = -1, dayprice_num = -1;

    ConstraintLayout mainlayout, category_selectedlayout;
    ScrollView categorylayout;

    //category
    TextView category_text, category_selected_text, category_warning, category_title;
    ImageView category_selected_icon, category_warning_line;

    //item name
    EditText itemname;
    TextView itemname_warning, itemname_title;
    ImageView itemname_warning_line;

    //price
    EditText hourcost, daycost;
    TextView costwarning;
    ImageView hour_warning_line, day_warning_line;

    //explanation
    EditText explanation;

    //upload
    TextView uploadBtn;

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void close(View view) {
        if(a||b||c||d||e) {
            AlertDialog.Builder popup = new AlertDialog.Builder(WritingActivity.this);
            popup.setMessage("나가시면 저장되지 않습니다. 정말 나가시겠습니까?");
            popup.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            popup.setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            popup.show();
        } else{
            finish();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        init();
        editTextFunction();

    }

    public void imagePicker(View view) {
        if(images != null) {
            images_arr = new ArrayList<Image>(images);
            ImagePicker.create(this)
                    //.returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    //.folderMode(true) // folder mode (false by default)
                    //.toolbarFolderTitle("Folder") // folder selection title
                    .toolbarImageTitle("물품 사진을 골라주세요.") // image selection title
                    .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                    //.includeVideo(true) // Show video on image picker
                    //.single() // single mode
                    .multi() // multi mode (default mode)
                    .limit(5) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    //.imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    .origin(images_arr) // original selected images, used in multi mode
                    //.exclude(images) // exclude anything that in image.getPath()
                    //.excludeFiles(files) // same as exclude but using ArrayList<File>
                    .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                    //.enableLog(false) // disabling log
                    .start(); // start image picker activity with request code
        } else {
            ImagePicker.create(this)
                    //.returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    //.folderMode(true) // folder mode (false by default)
                    //.toolbarFolderTitle("Folder") // folder selection title
                    .toolbarImageTitle("물품 사진을 골라주세요.") // image selection title
                    .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                    //.includeVideo(true) // Show video on image picker
                    //.single() // single mode
                    .multi() // multi mode (default mode)
                    .limit(5) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    //.imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    //.origin(images_arr) // original selected images, used in multi mode
                    //.exclude(images) // exclude anything that in image.getPath()
                    //.excludeFiles(files) // same as exclude but using ArrayList<File>
                    .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                    //.enableLog(false) // disabling log
                    .start(); // start image picker activity with request code
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            images= ImagePicker.getImages(data);

            imagePickerReLayout.setVisibility(View.VISIBLE);
            imagePickerFirstLayout.setVisibility(View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            listView.setLayoutManager(layoutManager);

            WritingItemPhotoAdapter adapter = new WritingItemPhotoAdapter( this, images);
            listView.setAdapter(adapter);

            picure_num.setText("("+images.size()+"/5)");
            bottombtn_enabledornot(0, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void editTextFunction() {
        itemname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                itemname.setHint("");

            }
        });

        itemname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()==0){
                    bottombtn_enabledornot(1, false );
                    itemname_warning.setVisibility(View.VISIBLE);
                    itemname_warning_line.setBackground(getDrawable(R.drawable.warning_line));
                    itemname_title.setTextColor(0xFF3B4861);
                } else {
                    itemname_warning.setVisibility(View.GONE);
                    itemname_warning_line.setBackground(getDrawable(R.drawable.green_line));
                    item_name = s.toString();
                    itemname_title.setTextColor(0xFF0AA864);
                    bottombtn_enabledornot(1, true );
                }



                /*
                if (s.toString().getBytes().length >= 12) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InputSerial.this);
                    builder.setMessage("번호는 11자리 입니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }*/
            }
        });


        hourcost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hourcost.setHint("");

            }
        });

        hourcost.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hourcost.setSelection(hourcost.getText().length()-1);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEdiging) return;
                isEdiging = true;


                String str = s.toString().replaceAll("[^\\d]", "");
                int num = 0;

                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                hourprice_num = num;

                NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
                ((DecimalFormat) nf2).applyPattern("###,###,###,###.###");

                bottombtn_enabledornot(3, true );

                hourcost.setText(nf2.format(num)+"원");
                if (s.toString().equals("0원")) {
                    costwarning.setTextColor(0xFFE81862);
                    costwarning.setText("* 반드시 입력해야 하는 창입니다.");
                    bottombtn_enabledornot(3, false );
                    hour_warning_line.setBackground(getDrawable(R.drawable.warning_line));
                    hourcost.setText("");
                }
                hour_warning_line.setBackground(getDrawable(R.drawable.green_line));



                isEdiging = false;
            }
        });

        daycost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                daycost.setHint("");

            }
        });



        daycost.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                daycost.setSelection(daycost.getText().length()-1);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEdiging) return;
                isEdiging = true;


                String str = s.toString().replaceAll("[^\\d]", "");
                int num = 0;

                try {
                    num = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                dayprice_num = num;

                NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
                ((DecimalFormat) nf2).applyPattern("###,###,###,###.###");

                bottombtn_enabledornot(4, true );

                daycost.setText(nf2.format(num)+"원");
                if (s.toString().equals("0원")) {
                    costwarning.setTextColor(0xFFE81862);
                    costwarning.setText("* 반드시 입력해야 하는 창입니다.");
                    bottombtn_enabledornot(4, false );
                    day_warning_line.setBackground(getDrawable(R.drawable.warning_line));
                }
                day_warning_line.setBackground(getDrawable(R.drawable.green_line));
                costwarning.setTextColor(0xFF0AA864);
                costwarning.setText("* 1일 요금은 10시간을 기준으로 자동 계산되며, 수정 가능합니다.");



                isEdiging = false;

            }
        });

        explanation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                explanation.setHint("");

            }
        });



        explanation.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                explanation_string = s.toString();

            }
        });

    }



    boolean a = false, b = false, c = false, d = false, e = false;

    private void bottombtn_enabledornot(int where, boolean TorF){
        if(where == 0){
            a = TorF;
        } else if(where == 1){
            b = TorF;
        } else if(where == 2){
            c = TorF;
        } else if(where == 3){
            d = TorF;
        } else{
            e = TorF;
        }

        if(a && b && c && d && e){
            uploadBtn.setBackgroundColor(0xFF0AA864);
            uploadBtn.setTextColor(0xFFF0F2F6);
            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();

                    logprint();
                    try{
                        JSONObject paramMap = new JSONObject();
                        paramMap.put("itemname", item_name);
                        paramMap.put("category", category);
                        paramMap.put("hourprice", hourprice_num);
                        paramMap.put("dayprice", dayprice_num);
                        paramMap.put("explain", explanation_string);
                        paramMap.put("account", 1);
                        paramMap.put("image0", images.get(0).getName());
                        if(images.size()>1)
                            paramMap.put("image1", images.get(1).getName());
                        if(images.size()>2)
                            paramMap.put("image2", images.get(2).getName());
                        if(images.size()>3)
                            paramMap.put("image3", images.get(3).getName());
                        if(images.size()>4)
                            paramMap.put("image4", images.get(4).getName());

                        NetworkTask networkTask = new NetworkTask("product/upload", "POST", paramMap);
                        networkTask.execute();
                        String r = networkTask.get();
                        JSONArray result = new JSONArray(r);

                        Log.d("upload", r);
                        wait();
                    } catch(Exception e){

                    }

                    AlertDialog.Builder popup = new AlertDialog.Builder(WritingActivity.this);
                    popup.setMessage("업로드 되었습니다!");
                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    popup.show();

                }
            });
        } else {
            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder popup = new AlertDialog.Builder(WritingActivity.this);
                    popup.setMessage("필수 항목을 적어주시길 바랍니다.");
                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    popup.show();

                }
            });
            uploadBtn.setBackgroundColor(0xFFF0F2F6);
            uploadBtn.setTextColor(0xFf909FBB);
        }


    }
    private void uploadImage(){
        for(int i = 0; i< images.size() ; i++) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(images.get(i).getPath(), options);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody postBodyImage = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file[]", images.get(i).getName(), RequestBody.create(MediaType.parse("file[]/*jpg"), byteArray))
                    .build();

            /*
            TextView responseText = findViewById(R.id.responseText);
            responseText.setText("Please wait ...");
            */
            postRequest("http://52.78.244.194/product/upload/image/more", postBodyImage);
        }
    }

    private void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*
                        TextView responseText = findViewById(R.id.responseText);
                        responseText.setText("Failed to Connect to Server");
                         */
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*
                        TextView responseText = findViewById(R.id.responseText);
                        try {
                            responseText.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
            }
        });
    }


    private void logprint(){
        Log.d("Log of item", "item name -->"+item_name+"\nitem category -->"+category+"\nitem cost hour/day -->" + hourprice_num +"/"+ dayprice_num + "\nexplain -->" + explanation_string);
    }

    private void init() {
        mainlayout = findViewById(R.id.mainlayout);

        //imagePicker
        listView = findViewById(R.id.listview);
        picure_num = findViewById(R.id.picture_reupload_num);

        imagePickerFirstLayout = findViewById(R.id.imagepickerfirst);
        imagePickerReLayout = findViewById(R.id.imagepickerRe);

        //item name
        itemname = findViewById(R.id.itemintroduce_edittext);
        itemname_warning = findViewById(R.id.iteminroduce_warning);
        itemname_warning_line = findViewById(R.id.itemintroduce_warningline);
        itemname_title = findViewById(R.id.text3444);


        //category
        categorylayout = findViewById(R.id.categorylayout);
        category_selectedlayout = findViewById(R.id.category_selected_view);

        category_title = findViewById(R.id.text4444);
        category_text = findViewById(R.id.category_text);
        category_selected_text = findViewById(R.id.category_selected_text);

        category_selected_icon = findViewById(R.id.category_selected_icon);
        category_warning = findViewById(R.id.category_warning);
        category_warning_line = findViewById(R.id.category_warningline);

        //price
        hourcost = findViewById(R.id.hourcost);
        daycost = findViewById(R.id.daycost);

        hour_warning_line = findViewById(R.id.hourcost_warningline);
        day_warning_line = findViewById(R.id.daycost_warningline);
        costwarning = findViewById(R.id.cost_warning);

        //explanation
        explanation = findViewById(R.id.post_edittext);

        //upload
        uploadBtn = findViewById(R.id.uploadBtn);
    }

    @Override
    public void onBackPressed() {
        if(state == State.MAIN){
            if(a||b||c||d||e) {
                AlertDialog.Builder popup = new AlertDialog.Builder(WritingActivity.this);
                popup.setMessage("나가시면 저장되지 않습니다. 정말 나가시겠습니까?");
                popup.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                popup.setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                popup.show();
            } else{
                finish();
            }
        } else if(state == State.CATEGORY){
            categorylayout.setVisibility(View.GONE);
            mainlayout.setVisibility(View.VISIBLE);
            state = State.MAIN;
        }
    }



    public void categorystart(View view) {
        categorylayout.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);

        state = State.CATEGORY;
    }

    public void categoryclose(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        state = State.MAIN;
    }

    public void category1(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black1);
        category_selected_text.setText("전자제품");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);


        bottombtn_enabledornot(2, true );
        category = 1;
        state = State.MAIN;
    }

    public void category2(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black2);
        category_selected_text.setText("도서");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 2;
        state = State.MAIN;
    }

    public void category3(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black3);
        category_selected_text.setText("생활/잡화");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 3;
        state = State.MAIN;
    }

    public void category4(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black4);
        category_selected_text.setText("패션");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);


        bottombtn_enabledornot(2, true );
        category = 4;
        state = State.MAIN;
    }

    public void category5(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black5);
        category_selected_text.setText("스포츠");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 5;
        state = State.MAIN;

    }

    public void category6(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black6);
        category_selected_text.setText("악기");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);


        bottombtn_enabledornot(2, true );
        category = 6;
        state = State.MAIN;
    }

    public void category7(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black7);
        category_selected_text.setText("모빌리티");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 7;
        state = State.MAIN;
    }

    public void category8(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black8);
        category_selected_text.setText("화장품/미용");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 8;
        state = State.MAIN;
    }

    public void category9(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_black9);
        category_selected_text.setText("가구/인테리어");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 9;
        state = State.MAIN;
    }

    public void category10(View view) {
        categorylayout.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);

        category_selectedlayout.setVisibility(View.VISIBLE);
        category_text.setVisibility(View.GONE);

        category_selected_icon.setImageResource(R.drawable.category_blacketc);
        category_selected_text.setText("기타");

        category_warning.setVisibility(View.GONE);
        category_warning_line.setBackground(getDrawable(R.drawable.green_line));
        category_title.setTextColor(0xFF0AA864);

        bottombtn_enabledornot(2, true );
        category = 10;
        state = State.MAIN;
    }


}


