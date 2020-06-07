package ver0.village.Item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemPhotoAdapter extends PagerAdapter {
    Context context;

    private ArrayList<String> imageList;
    public ItemPhotoAdapter(Context context, ArrayList<String> imageList)
    {
        this.context = context;
        this.imageList = imageList;
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Glide.with(context)
                .load("http://52.78.244.194/product/download/"+imageList.get(position))
                .into(imageView);
        //ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        //imageLoader.displayImage("http://52.78.244.194/product/download/"+imageList.get(position), imageView);
        ((ViewPager) container).addView(imageView, 0);


        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
