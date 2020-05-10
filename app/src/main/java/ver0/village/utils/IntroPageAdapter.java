package ver0.village.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ver0.village.R;

public class IntroPageAdapter extends PagerAdapter {
        private Context mContext;
        private ArrayList<Integer> imageList;
        public IntroPageAdapter(Context context, ArrayList<Integer> imageList)
        {
            this.mContext = context;
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.pager_intro, null);

            TextView textView = view.findViewById(R.id.textView9);
            if(position == 0){
            }
            else if(position == 1){
                textView.setText("갑자기 물건이 필요할 때 빠르게 찾아보세요");
            } else if(position == 2){
                textView.setText("자동으로 계약서를 만들어드려요");
            } else if(position == 3){
                textView.setText("휴대폰과 대학교 인증으로 안전하게 빌리세요");
            }
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(imageList.get(position));

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return (view == (View)o);
        }
}
