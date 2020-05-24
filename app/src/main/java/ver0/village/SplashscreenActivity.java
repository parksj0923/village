package ver0.village;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import ver0.village.utils.NetworkTask;

import static android.content.Context.MODE_PRIVATE;

public class SplashscreenActivity extends AppCompatActivity {

    String phone = "01000000000";
    int key = -1;
    boolean olduser = false;
    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String phonenumber = sf.getString("phone","");

        findingPhonenumber();
        /*if(phonenumber.equals("")) {
            findingPhonenumber();
        } else {
            phone = phonenumber;
            olduser = true;
        }*/

        try{
            JSONObject paramMap = new JSONObject();
            paramMap.put("phone", phone);
            NetworkTask networkTask = new NetworkTask("account/check", "GET", paramMap);
            networkTask.execute();
            String r = networkTask.get();
            Log.d("phone", r);
            JSONArray result = new JSONArray(r);
            key = (int)((JSONObject)result.get(0)).get("account_key");
            Log.d("key log", ""+key);
        } catch(Exception e){
        }

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2000) {
                        sleep(100);
                        waited += 100;
                    }



/*

                    Intent intent = new Intent(SplashscreenActivity.this,
                            TabActivity.class);//첫번째로 나올 화면
                    intent.putExtra("phone", phone);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashscreenActivity.this.finish();
*/


                    if(key == -1) {
                        Log.d("newuser", "intro start");
                        Intent intent = new Intent(SplashscreenActivity.this,
                                IntroActivity.class);//첫번째로 나올 화면
                        intent.putExtra("phone", phone);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashscreenActivity.this.finish();
                    } else {
                        Log.d("olduser", "mainstart");
                        Intent intent = new Intent(SplashscreenActivity.this,
                                TabActivity.class);//첫번째로 나올 화면
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashscreenActivity.this.finish();
                    }


                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashscreenActivity.this.finish();
                }

            }
        };
        splashTread.start();
    }

    private void findingPhonenumber() {
        /*
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String isphone = sf.getString("phone","");
        if(isphone.equals("")){
            olduser = false;*/

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(SplashscreenActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE},
                        0);
            }

            TelephonyManager tm = (TelephonyManager) getSystemService(Context. TELEPHONY_SERVICE);
            if(tm != null) {
                phone = tm.getLine1Number();
                if (phone.startsWith("+82")) {
                    phone = phone.replace("+82", "0");
                }
            }
      //  }
    }

}
