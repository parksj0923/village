package ver0.village.MainFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ver0.village.Mypage.ContactVillageActivity;
import ver0.village.Mypage.FavoriteCategoryActivity;
import ver0.village.Mypage.MyHeartItemActivity;
import ver0.village.Mypage.MyPostActivity;
import ver0.village.Mypage.MyProfileActivity;
import ver0.village.R;

import static android.content.Context.MODE_PRIVATE;

public class MypageFragment extends Fragment {

    ConstraintLayout card_setting, category_setting, keyword_setting, alarm_setting, help, contact_to_village, village_tos, village_rentalterm, village_privacy;
    ImageView myprofile, postsetting, heartlist;

    //userinfo
    ImageView profileimg;
    TextView name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        init(view);
        setClicklistener();
        getInfofromshraredPre();

        return view;
    }

    private void getInfofromshraredPre() {
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = this.getActivity().getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String profileimg_str = sf.getString("profileimg","");
        String name_str = sf.getString("nickname", "");

        if(!profileimg_str.equals("")) {
            byte[] imageAsBytes = Base64.decode(profileimg_str.getBytes(), Base64.DEFAULT);
            profileimg.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }

        name.setText(name_str);

    }

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {

                // Get String data from Intent
                int result = data.getIntExtra("revised", -1);

                if(result == 1){
                    getInfofromshraredPre();
                }
        }
    }

    private void setClicklistener() {
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
        postsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPostActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        heartlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyHeartItemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        card_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        category_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        keyword_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        alarm_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        contact_to_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactVillageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {
        card_setting = view.findViewById(R.id.cardsetting);
        category_setting = view.findViewById(R.id.categorysetting);
        keyword_setting = view.findViewById(R.id.keywordsetting);
        alarm_setting = view.findViewById(R.id.alarm_setting);
        help = view.findViewById(R.id.help);
        contact_to_village = view.findViewById(R.id.contacttovillage);
        village_tos = view.findViewById(R.id.village_tos);
        village_rentalterm = view.findViewById(R.id.village_rentalterm);
        village_privacy = view.findViewById(R.id.village_privacypolicy);

        myprofile = view.findViewById(R.id.icon_profile);
        postsetting = view.findViewById(R.id.icon_post);
        heartlist = view.findViewById(R.id.icon_heart);

        //userinfo
        profileimg = view.findViewById(R.id.img_profile);
        name = view.findViewById(R.id.text_user_name);
    }

}
