package ver0.village.Item;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ver0.village.R;

public class AnotherItemPhotoAdapter extends RecyclerView.Adapter<AnotherItemPhotoAdapter.ViewHolder> {

    private List<Integer> key;
    private List<String> title;
    private List<String> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public AnotherItemPhotoAdapter(Context context, List<String> itemList, List<String> title, List<Integer> key) {
        this.context = context;
        this.itemList = itemList;
        this.title = title;
        this.key = key;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.another_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context)
                .load("http://52.78.244.194/product/download/"+itemList.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public int getKey(int position) {return key.get(position);}


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemimg);
            textView = itemView.findViewById(R.id.itemname);
        }
    }
}
