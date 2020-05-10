package ver0.village.Trade;


import android.app.Activity;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ver0.village.R;

public class TradeSlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;



    public TradeSlideAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view==(ConstraintLayout)obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trade_slide,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = null;

        if(position==0){

            TradeLendRecyclerViewAdpater borrowAdapter = new TradeLendRecyclerViewAdpater();

            borrowAdapter.addItem(1,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(2,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(3,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(4,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(5,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(1,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(2,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(3,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(4,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(5,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(1,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(2,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(3,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(4,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            borrowAdapter.addItem(5,"예약 요청을 수락해 주세요.", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");

            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(borrowAdapter);


        }
        else{

            TradeBorrowRecyclerViewAdapter lendAdapter = new TradeBorrowRecyclerViewAdapter();

            lendAdapter.addItem(1,"3월 28일 (화), 오후 3시 대여 예정", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            lendAdapter.addItem(2,"3월 28일 (화), 오후 3시 대여 예정", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            lendAdapter.addItem(3,"3월 28일 (화), 오후 3시 대여 예정", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            lendAdapter.addItem(4,"3월 28일 (화), 오후 3시 대여 예정", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");
            lendAdapter.addItem(5,"3월 28일 (화), 오후 3시 대여 예정", "Cannon EOS 80D DSLR", "박성주", "http://52.78.244.194/product/download/bicycle.png");



            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(lendAdapter);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
