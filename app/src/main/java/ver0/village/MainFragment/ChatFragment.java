package ver0.village.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ver0.village.Chat.ChatRecyclerViewAdapter;
import ver0.village.R;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Context context = getContext();
        recyclerView = view.findViewById(R.id.recycle_view);



        ChatRecyclerViewAdapter itemAdapter = new ChatRecyclerViewAdapter();

        itemAdapter.addItem("camera", "박성주", "안녕하세요", 100, 5,
                ContextCompat.getDrawable(context, R.drawable.sample_userimg), ContextCompat.getDrawable(context, R.drawable.sample_camera_chat));

        itemAdapter.addItem("camera", "박성주", "안녕하세요", 100, 5,
                ContextCompat.getDrawable(context, R.drawable.sample_userimg), ContextCompat.getDrawable(context, R.drawable.sample_camera_chat));
        itemAdapter.addItem("camera", "박성주", "안녕하세요", 100, 5,
                ContextCompat.getDrawable(context, R.drawable.sample_userimg), ContextCompat.getDrawable(context, R.drawable.sample_camera_chat));



        RecyclerView.LayoutManager mLayoutManager = null;
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }

}
