package ver0.village.Alarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ver0.village.R;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {
    private ArrayList<AlarmItem> itemList = new ArrayList<AlarmItem>();
    Context context;

    private static AlarmRecyclerViewAdapter.ClickListener clickListener;

    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        View totalView;;
        TextView alarm_title, alarm_text, alarm_time;
        ImageView img_alarm;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            totalView = view;

            alarm_text = view.findViewById(R.id.alarm_text);
            alarm_title = view.findViewById(R.id.alarm_title);
            alarm_time = view.findViewById(R.id.alarm_time);

            img_alarm = view.findViewById(R.id.img_alarm);

        }

        @Override
        public void onClick(View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public AlarmRecyclerViewAdapter(){

    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public AlarmRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_info, parent, false);
        context = parent.getContext();
        AlarmRecyclerViewAdapter.ViewHolder holder = new AlarmRecyclerViewAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AlarmRecyclerViewAdapter.ViewHolder holder, final int position){
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final AlarmItem item = itemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        holder.alarm_text.setText(item.getAlarm_text());
        holder.alarm_title.setText(item.getAlarm_title());
        holder.alarm_time.setText(""+item.getAlarm_time()+"~~전");

        holder.img_alarm.setBackground(item.getImg_alarm());

       /* holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                context.startActivity(intent);
            }
        });*/
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( String alarm_title, String alarm_text, int alarm_time, Drawable img_alarm) {
        AlarmItem item = new AlarmItem();
        item.setAlarm_text(alarm_text);
        item.setAlarm_title(alarm_title);
        item.setAlarm_time(alarm_time);
        item.setImg_alarm(img_alarm);

        itemList.add(item);
    }


    public void setOnItemClickListener(AlarmRecyclerViewAdapter.ClickListener clickListener){
        AlarmRecyclerViewAdapter.clickListener = clickListener;
    }
}
