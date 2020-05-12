package ver0.village.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ver0.village.Chat.ChatRoomItem;
import ver0.village.Chat.ChatRoomRecyclerViewAdapter;
import ver0.village.R;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");
    private ArrayList<ChatRoomItem> chatList = new ArrayList<ChatRoomItem>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Context context = getContext();
        recyclerView = view.findViewById(R.id.recycle_view);

        ChatRoomItem testitem = new ChatRoomItem("camera", "박성주", "안녕하세요", 100, 5,
                ContextCompat.getDrawable(context, R.drawable.sample_userimg),
                ContextCompat.getDrawable(context, R.drawable.sample_camera_chat));
        for(int i=0; i < 10; i ++) {
            chatList.add(testitem);
        }
        ChatRoomRecyclerViewAdapter itemAdapter = new ChatRoomRecyclerViewAdapter(chatList);

        final RecyclerView.LayoutManager mLayoutManager =  new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }

}
