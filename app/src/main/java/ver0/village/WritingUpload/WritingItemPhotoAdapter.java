package ver0.village.WritingUpload;

import android.content.Context;
import android.graphics.BitmapFactory;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esafirm.imagepicker.model.Image;

import java.util.List;

import ver0.village.R;

public class WritingItemPhotoAdapter   extends RecyclerView.Adapter<WritingItemPhotoAdapter.ViewHolder> {

    private List<Image> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public WritingItemPhotoAdapter(Context context, List<Image> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.writing_item_picture, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(itemList.get(position).getPath()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemimg);
        }
    }
}
