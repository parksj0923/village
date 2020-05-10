package ver0.village.utils;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ver0.village.R;

public class UniversityListAdapter extends RecyclerView.Adapter<UniversityListAdapter.ItemViewHolder> {



    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // adapter에 들어갈 list 입니다.
    private ArrayList<Universe> listData = new ArrayList<>();

    @Override
    public UniversityListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.universitylist, parent, false) ;
        UniversityListAdapter.ItemViewHolder vh = new UniversityListAdapter.ItemViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder( ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(Universe Universe) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(Universe);
    }

    public Universe getItem(int pos){
        return listData.get(pos);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name, address, email;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.universityname);
            address = itemView.findViewById(R.id.universityaddress);
            email = itemView.findViewById(R.id.universitymail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
        }

        void onBind(Universe Universe) {
            name.setText(Universe.getUniverseName());
            address.setText(Universe.getAddress());
            email.setText(Universe.getUniverseDomain());
        }
    }
}