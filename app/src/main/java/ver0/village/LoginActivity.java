package ver0.village;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import ver0.village.utils.NetworkTask;
import ver0.village.utils.Universe;
import ver0.village.utils.UniversityListAdapter;
import ver0.village.utils.UserInfo;

public class LoginActivity extends AppCompatActivity {

    //data sets
    UserInfo userInfo;
    int certificationnum;
    String profileimg_incodedbitmap;

    //main
    boolean bottombuttonEnabled = false;
    enum State {
        PHONE, UNIVSELECT, UNIVCONFIRM, PROFILE
    }
    State state = State.PHONE;
    TextView toptext1, toptext2, toptext3, toptext4;
    TextView titletext, subtitletext;
    ImageView topdot1, topdot2, topdot3, topdot4;
    ConstraintLayout phonelayout, univselectlayout, univconfirmlayout, profilelayout;
    Button bottombutton;

    //phone
    TextView phonenumtext;
    EditText nameedittext;

    //univselect
    RecyclerView recyclerView;
    UniversityListAdapter adapter;

    //univconfirm
    EditText emailedittext, confirmeditText;
    TextView emaildomaintext, requestmailtext, confirmmailtext;
    ConstraintLayout sub_univconfirmlayout;

    //profile
    EditText nicknametext;
    ImageView camera, imgProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userInfo = new UserInfo();
        userInfo.setPhoneNumber(getIntent().getStringExtra("phone"));

        init();
        func_phone();
    }


    public void func_phone(){
        design_phone();
        bottombuttonEnabled = false;
        bottombutton.setBackgroundColor(0xFFF0F2F6);
        bottombutton.setTextColor(0xFF909FBB);


        phonenumtext.setText(userInfo.getPhoneNumber());

        nameedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nameedittext.setHint("");

            }
        });

        nameedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                bottombuttonEnabled = true;
                bottombutton.setBackgroundColor(0xFF0AA864);
                bottombutton.setTextColor(0xFFF0F2F6);
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

    }

    public void func_univselect(){
        design_univselect();
        bottombuttonEnabled = false;
        bottombutton.setBackgroundColor(0xFFF0F2F6);
        bottombutton.setTextColor(0xFF909FBB);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UniversityListAdapter();
        recyclerView.setAdapter(adapter);

        try{
            JSONObject paramMap = new JSONObject();

            NetworkTask networkTask = new NetworkTask("account/universityList", "GET", paramMap);
            networkTask.execute();
            String r = networkTask.get();
            JSONArray result = new JSONArray(r);

            Log.d("dd", (String)((JSONObject)result.get(0)).get("university_name"));
            Log.d("dd", (String)((JSONObject)result.get(0)).get("university_address"));
            Log.d("dd", (String)((JSONObject)result.get(0)).get("university_email"));

            Log.d("universeList", r);

            for(int i=0; i<result.length(); i++){
                Universe universe = new Universe();
                universe.setUniverseName((String)((JSONObject)result.get(i)).get("university_name"));
                universe.setAddress((String)((JSONObject)result.get(i)).get("university_address"));
                universe.setUniverseDomain((String) ((JSONObject)result.get(i)).get("university_email"));
                universe.setUniverseKey((int) ((JSONObject)result.get(i)).get("university_key"));

                adapter.addItem(universe);
                Log.d("dd", universe.toString());
            }
            adapter.notifyDataSetChanged();
        } catch(Exception e){

        }
        adapter.setOnItemClickListener(new UniversityListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                userInfo.setUniversityInfo(adapter.getItem(position));
                Log.d("university select", userInfo.getUniversityInfo().getUniverseName()+userInfo.getUniversityInfo().getUniverseDomain()+userInfo.getUniversityInfo().getAddress());
                func_univconfirm();
                state = State.UNIVCONFIRM;
            }
        }) ;

    }

    int requsetstatus = 0;

    public void func_univconfirm(){
        design_univconfirm();
        bottombuttonEnabled = false;
        bottombutton.setBackgroundColor(0xFFF0F2F6);
        bottombutton.setTextColor(0xFF909FBB);

        emaildomaintext.setText(userInfo.getUniversityInfo().getUniverseDomain());

        emailedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                emailedittext.setHint("");

            }
        });

        emailedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                requestmailtext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(requsetstatus == 0) {
                            sub_univconfirmlayout.setVisibility(View.VISIBLE);

                            AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                            popup.setMessage("인증번호가 메일로 발송되었습니다.");
                            popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            popup.show();

                            requestmailtext.setText("다시보내기");
                            requsetstatus = 1;

                            try {
                                JSONObject paramMap = new JSONObject();
                                userInfo.setUserEmail(emailedittext.getText().toString());
                                Log.d("email: ", emailedittext.getText().toString());
                                Log.d("domain: ", userInfo.getUniversityInfo().getUniverseDomain());
                                paramMap.put("email", emailedittext.getText().toString());
                                paramMap.put("domain", userInfo.getUniversityInfo().getUniverseDomain());
                                NetworkTask networkTask = new NetworkTask("account/cert", "GET", paramMap);
                                networkTask.execute();

                                String r = networkTask.get();
                                Log.d("emailcert", r);
                                JSONObject result = new JSONObject(r);
                                certificationnum = result.getInt("cert_number");
                                Log.d("cert_number", "" + certificationnum);

                            } catch (Exception e) {

                            }
                        } else {
                            AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                            popup.setMessage("인증번호를 다시 보내시겠습니까");
                            popup.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        JSONObject paramMap = new JSONObject();
                                        userInfo.setUserEmail(emailedittext.getText().toString());
                                        Log.d("email: ", emailedittext.getText().toString());
                                        Log.d("domain: ", userInfo.getUniversityInfo().getUniverseDomain());
                                        paramMap.put("email", emailedittext.getText().toString());
                                        paramMap.put("domain", userInfo.getUniversityInfo().getUniverseDomain());
                                        NetworkTask networkTask = new NetworkTask("account/cert", "GET", paramMap);
                                        networkTask.execute();

                                        String r = networkTask.get();
                                        Log.d("emailcert", r);
                                        JSONObject result = new JSONObject(r);
                                        certificationnum = result.getInt("cert_number");
                                        Log.d("cert_number", "" + certificationnum);

                                    } catch (Exception e) {

                                    }
                                }
                            });
                            popup.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            popup.show();
                        }



                    }
                });
                requestmailtext.setTextColor(0xFF0AA864);
            }
        });

        confirmmailtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(confirmeditText.getText().toString().equals("")){
                    AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                    popup.setMessage("인증번호를 입력해주세요.");
                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    popup.show();
                } else if(certificationnum == Integer.parseInt( "" + confirmeditText.getText())) {
                    AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                    popup.setMessage("인증되었습니다.");
                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    popup.show();

                    bottombuttonEnabled = true;
                    bottombutton.setBackgroundColor(0xFF0AA864);
                    bottombutton.setTextColor(0xFFF0F2F6);
                } else {
                    AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                    popup.setMessage("번호가 틀렸습니다. 다시 입력해 주세요.");
                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    popup.show();
                }
            }
        });




    }

    public void func_profile() {
        design_profilesetting();
        bottombuttonEnabled = true;
        bottombutton.setBackgroundColor(0xFF0AA864);
        bottombutton.setTextColor(0xFFF0F2F6);

        nicknametext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nicknametext.setHint("");
            }
        });

        /*
        nicknametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bottombuttonEnabled = true;
                bottombutton.setBackgroundColor(0xFF0AA864);
                bottombutton.setTextColor(0xFFF0F2F6);
            }
        });*/

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();

            }
        });

    }

    public static final int REQUEST_IMAGE = 100;

    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        } else {
                            // TODO - handle permission denied case
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(LoginActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(LoginActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void loadProfile(String url) {

        Glide.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Log.d("uri-------->", uri.toString());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();
                    profileimg_incodedbitmap= Base64.encodeToString(b, Base64.DEFAULT);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void bottombuttononClick(View view) {
        if(state == State.PHONE){
            if(bottombuttonEnabled == false){
                AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                popup.setMessage("사용자명을 입력해주세요.");
                popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                popup.show();
            } else {
                userInfo.setUserName(nameedittext.getText().toString());
                func_univselect();
                state = State.UNIVSELECT;
            }

        } else if(state == State.UNIVSELECT){
            if(bottombuttonEnabled == false){
                AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                popup.setMessage("대학교를 선택해주세요.");
                popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                popup.show();
            } else {
                func_univconfirm();
                state = State.UNIVCONFIRM;
            }
        } else if(state == State.UNIVCONFIRM){
            if(bottombuttonEnabled == false){
                AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                popup.setMessage("인증을 완료해주세요.");
                popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                popup.show();
            } else {
                func_profile();
                state = State.PROFILE;
            }
        } else if(state == State.PROFILE){
            if(bottombuttonEnabled == false){
                AlertDialog.Builder popup = new AlertDialog.Builder(LoginActivity.this);
                popup.setMessage("닉네임을 정해주세요.");
                popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                popup.show();
            } else {
                userInfo.setUserNickname(nicknametext.getText().toString());
                state = State.PHONE;

                Log.d("--register--", "-------start-------------");
                userInfo.logprtint();
                try {

                    JSONObject paramMap = new JSONObject();
                    paramMap.put("phone", userInfo.getPhoneNumber());
                    //paramMap.put("phone", "01012345678");
                    paramMap.put("name", userInfo.getUserName());
                    paramMap.put("universitykey", userInfo.getUniversityInfo().getUniverseKey());
                    paramMap.put("nickname", userInfo.getUserNickname());
                    paramMap.put("email", userInfo.getUserEmail());

                    NetworkTask networkTask = new NetworkTask("account/register", "POST", paramMap);
                    networkTask.execute();

                    String r = networkTask.get();
                    Log.d("register reply--->", r);
                    JSONObject result = new JSONObject(r);
                    userInfo.setUserKey(result.getInt("account_key"));

                } catch (Exception e) {

                }
                SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phone",userInfo.getPhoneNumber());
                editor.putInt("key", userInfo.getUserKey());
                editor.putString("name", userInfo.getUserName());
                editor.putString("nickname", userInfo.getUserNickname());
                editor.putString("email", userInfo.getUserEmail());
                editor.putString("profileimg", profileimg_incodedbitmap);
                editor.commit();
                Log.d("--register--", "-------complete-------------");

                userInfo.logprtint();

                Intent intent = new Intent(LoginActivity.this,
                        TabActivity.class);//첫번째로 나올 화면
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                LoginActivity.this.finish();
            }


        }
    }







    public void design_phone(){
        topdot1.setImageResource(R.drawable.logindot_black);
        topdot2.setImageResource(R.drawable.logindot_gray);
        topdot3.setImageResource(R.drawable.logindot_gray);
        topdot4.setImageResource(R.drawable.logindot_gray);

        bottombutton.setVisibility(View.VISIBLE);

        toptext1.setTextColor(0xFF3B4861);
        toptext2.setTextColor(0xFF909FBB);
        toptext3.setTextColor(0xFF909FBB);
        toptext4.setTextColor(0xFF909FBB);

        titletext.setVisibility(View.VISIBLE);
        subtitletext.setVisibility(View.VISIBLE);
        titletext.setText("휴대폰 인증");
        subtitletext.setText("빌리지는 휴대폰에 등록된 번호를 통해\n아이디를 생성하고 로그인합니다.");

        bottombutton.setText("대학교 선택하기");

        phonelayout.setVisibility(View.VISIBLE);
        univselectlayout.setVisibility(View.GONE);
        univconfirmlayout.setVisibility(View.GONE);
        profilelayout.setVisibility(View.GONE);
    }

    public void design_univselect(){
        topdot1.setImageResource(R.drawable.logindot_black);
        topdot2.setImageResource(R.drawable.logindot_black);
        topdot3.setImageResource(R.drawable.logindot_gray);
        topdot4.setImageResource(R.drawable.logindot_gray);

        bottombutton.setVisibility(View.GONE);

        toptext1.setTextColor(0xFF3B4861);
        toptext2.setTextColor(0xFF3B4861);
        toptext3.setTextColor(0xFF909FBB);
        toptext4.setTextColor(0xFF909FBB);

        titletext.setVisibility(View.VISIBLE);
        subtitletext.setVisibility(View.VISIBLE);
        titletext.setText("대학교 선택");
        subtitletext.setText("본인이 속한 대학교를 선택해주세요.");

        bottombutton.setText("대학교 인증하기");

        phonelayout.setVisibility(View.GONE);
        univselectlayout.setVisibility(View.VISIBLE);
        univconfirmlayout.setVisibility(View.GONE);
        profilelayout.setVisibility(View.GONE);
    }

    public void design_univconfirm(){
        topdot1.setImageResource(R.drawable.logindot_black);
        topdot2.setImageResource(R.drawable.logindot_black);
        topdot3.setImageResource(R.drawable.logindot_black);
        topdot4.setImageResource(R.drawable.logindot_gray);

        bottombutton.setVisibility(View.VISIBLE);

        toptext1.setTextColor(0xFF3B4861);
        toptext2.setTextColor(0xFF3B4861);
        toptext3.setTextColor(0xFF3B4861);
        toptext4.setTextColor(0xFF909FBB);

        titletext.setVisibility(View.VISIBLE);
        subtitletext.setVisibility(View.VISIBLE);
        titletext.setText("대학교 인증");
        subtitletext.setText("대학교 인증을 위해 본인의 이메일을 입력해주세요.");

        bottombutton.setText("프로필 설정하기");

        phonelayout.setVisibility(View.GONE);
        univselectlayout.setVisibility(View.GONE);
        univconfirmlayout.setVisibility(View.VISIBLE);
        profilelayout.setVisibility(View.GONE);

    }

    public void design_profilesetting(){
        topdot1.setImageResource(R.drawable.logindot_black);
        topdot2.setImageResource(R.drawable.logindot_black);
        topdot3.setImageResource(R.drawable.logindot_black);
        topdot4.setImageResource(R.drawable.logindot_black);

        bottombutton.setVisibility(View.VISIBLE);

        toptext1.setTextColor(0xFF3B4861);
        toptext2.setTextColor(0xFF3B4861);
        toptext3.setTextColor(0xFF3B4861);
        toptext4.setTextColor(0xFF3B4861);

        titletext.setVisibility(View.GONE);
        subtitletext.setVisibility(View.GONE);

        bottombutton.setText("가입 완료하기");

        phonelayout.setVisibility(View.GONE);
        univselectlayout.setVisibility(View.GONE);
        univconfirmlayout.setVisibility(View.GONE);
        profilelayout.setVisibility(View.VISIBLE);

        titletext.setVisibility(View.GONE);
        subtitletext.setVisibility(View.GONE);



    }

    public void init(){

        //main
        toptext1 = findViewById(R.id.logintext1);
        toptext2 = findViewById(R.id.logintext2);
        toptext3 = findViewById(R.id.logintext3);
        toptext4 = findViewById(R.id.logintext4);

        topdot1 = findViewById(R.id.logindot1);
        topdot2 = findViewById(R.id.logindot2);
        topdot3 = findViewById(R.id.logindot3);
        topdot4 = findViewById(R.id.logindot4);

        titletext = findViewById(R.id.logintitle);
        subtitletext = findViewById(R.id.subtitle);

        phonelayout = findViewById(R.id.phonelayout);
        univselectlayout = findViewById(R.id.univselectlayout);
        univconfirmlayout = findViewById(R.id.univconfirmlayout);
        profilelayout = findViewById(R.id.profilelayout);

        bottombutton = findViewById(R.id.bottombutton);


        //phone
        phonenumtext = findViewById(R.id.phonenumtext);
        nameedittext = findViewById(R.id.nameText);

        //univselect
        recyclerView = findViewById(R.id.recycler_view);

        //univconfirm
        emailedittext = findViewById(R.id.emailText);
        emaildomaintext = findViewById(R.id.emaildomain);
        requestmailtext = findViewById(R.id.requestemail);
        confirmmailtext = findViewById(R.id.confirmmail);
        confirmeditText = findViewById(R.id.confirmText);
        sub_univconfirmlayout = findViewById(R.id.confirmnumlayout);

        //profile
        nicknametext = findViewById(R.id.nicknametext);
        imgProfile = findViewById(R.id.profileimg);
        camera = findViewById(R.id.camerabackgrd);
    }



    @Override
    public void onBackPressed() {
        if(state == State.PHONE){
            finish();
        } else if(state == State.UNIVSELECT){
            func_phone();
            state = State.PHONE;
        } else if(state == State.UNIVCONFIRM){
            func_univselect();
            state = State.UNIVSELECT;
        } else if(state == State.PROFILE){
            func_univconfirm();
            state = State.UNIVCONFIRM;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}
