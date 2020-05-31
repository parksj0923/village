package ver0.village.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONObject;

import ver0.village.Alarm.AlarmActivity;
import ver0.village.Item.HomeItem;
import ver0.village.Item.ItemRecyclerViewAdapter;
import ver0.village.Mypage.ChangeProfileActivity;
import ver0.village.R;
import ver0.village.utils.NetworkTask;

public class HomeFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener{


    Bitmap bmImg;
    String search_str;

    TextView searchbox;
    RecyclerView recyclerView;
    ItemRecyclerViewAdapter itemAdapter = new ItemRecyclerViewAdapter();
    SwipyRefreshLayout mSwipeRefreshLayout;
    ConstraintLayout category1, category2, category3, category4, category5, category6, category7, category8, category9, category10;
    TextView categoryAll, categoryStar, categorytitle_text;
    DrawerLayout drawerLayout;
    View drawerView;
    ImageView Alarm;

    Drawable[] drawables;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Context context = getContext();
        recyclerView = view.findViewById(R.id.recycle_view);
        searchbox = view.findViewById(R.id.searchbox);

        // 전체화면인 DrawerLayout 객체 참조
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        // Drawer 화면(뷰) 객체 참조
        drawerView = (View) view.findViewById(R.id.drawer);


        // 드로어 화면을 열고 닫을 버튼 객체 참조
        ImageView btnOpenDrawer =  view.findViewById(R.id.menu);
        ImageView btnCloseDrawer = view.findViewById(R.id.close);
        categoryButtonInit(view);

        // 드로어 여는 버튼 리스너
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
                categoryButtonListner();
            }
        });


        // 드로어 닫는 버튼 리스너
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        Alarm = view.findViewById(R.id.alarm_home);
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeFragmentSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("search_now",search_str);
                startActivityForResult(intent,10);
            }
        });

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        /*
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                ChangeCategory(itemAdapter, 10);
            }
        });
*/


        ChangeCategory(itemAdapter, 10);
        RecyclerView.LayoutManager mLayoutManager = null;
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration((Activity)context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }
    //search return
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the activity result was received from the "Get Car" request
        if (requestCode == 10) {
            // If the activity confirmed a selection
            if (Activity.RESULT_OK == resultCode) {
                // Grab whatever data identifies that car that was sent in
                // setResult(int, Intent)
                search_str = (data.getStringExtra("search_str"));
                ChangeSearch(itemAdapter, search_str);
            } else {
                // You can handle a case where no selection was made if you want
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void ChangeSearch(ItemRecyclerViewAdapter itemAdapter, String search_str) {
        itemAdapter.removeallitem();

        Log.d("additem", "start");

        if(search_str == null || search_str =="") {
            searchbox.setText("검색어를 입력해주세요");
            return;
        }
        else
            searchbox.setText(search_str);
        try{
            JSONObject paramMap = new JSONObject();

            if (search_str != null) {
                paramMap.put("search", search_str);
            }

            NetworkTask networkTask = new NetworkTask("product/list/search", "POST", paramMap);
            networkTask.execute();
            String r = networkTask.get();
            JSONArray result = new JSONArray(r);

            Log.d("universeList", r);

            for(int i=0; i<result.length(); i++){
                HomeItem homeItem = new HomeItem();
                homeItem.setProduct_key((int)((JSONObject)result.get(i)).get("product_key"));
                homeItem.setItem_name((String)((JSONObject)result.get(i)).get("product_name"));
                homeItem.setHour_cost((int) ((JSONObject)result.get(i)).get("product_price_hour"));
                homeItem.setDay_cost((int) ((JSONObject)result.get(i)).get("product_price_day"));

                String image_name = (String)((JSONObject)result.get(i)).get("product_image");
                homeItem.setImg_item_url("http://52.78.244.194/product/download/" + image_name);
                itemAdapter.addItem(homeItem);
            }
            itemAdapter.notifyDataSetChanged();
        } catch(Exception e){

        }



    }


    private void ChangeCategory(ItemRecyclerViewAdapter itemAdapter, int category) {
        mSwipeRefreshLayout.setRefreshing(true);

        itemAdapter.removeallitem();

        Log.d("additem", "start");

        try{
            JSONObject paramMap = new JSONObject();

            if (category > 0) {
                paramMap.put("category", ""+category);
            }

            NetworkTask networkTask = new NetworkTask("product/list/category", "GET", paramMap);
            networkTask.execute();
            String r = networkTask.get();
            JSONArray result = new JSONArray(r);

            Log.d("universeList", r);

            for(int i=0; i<result.length(); i++){
                HomeItem homeItem = new HomeItem();
                homeItem.setProduct_key((int)((JSONObject)result.get(i)).get("product_key"));
                homeItem.setItem_name((String)((JSONObject)result.get(i)).get("product_name"));
                homeItem.setHour_cost((int) ((JSONObject)result.get(i)).get("product_price_hour"));
                homeItem.setDay_cost((int) ((JSONObject)result.get(i)).get("product_price_day"));

                String image_name = (String)((JSONObject)result.get(i)).get("product_image");
                homeItem.setImg_item_url("http://52.78.244.194/product/download/" + image_name);
                itemAdapter.addItem(homeItem);
            }
            itemAdapter.notifyDataSetChanged();
        } catch(Exception e){

        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void categoryButtonInit(View view) {
        category1 = view.findViewById(R.id.category1);
        category2 = view.findViewById(R.id.category2);
        category3 = view.findViewById(R.id.category3);
        category4 = view.findViewById(R.id.category4);
        category5 = view.findViewById(R.id.category5);
        category6 = view.findViewById(R.id.category6);
        category7 = view.findViewById(R.id.category7);
        category8 = view.findViewById(R.id.category8);
        category9 = view.findViewById(R.id.category9);
        category10 = view.findViewById(R.id.category10);
        categoryAll = view.findViewById(R.id.category_text_home);
        categoryStar = view.findViewById(R.id.category_text_star);
        categorytitle_text = view.findViewById(R.id.menu_text);
    }

    private void categoryButtonListner(){
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 1);
                categorytitle_text.setText("전자제품");
                drawerLayout.closeDrawer(drawerView);

            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 2);
                categorytitle_text.setText("도서");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 3);
                categorytitle_text.setText("생활/잡화");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 4);
                categorytitle_text.setText("패션");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 5);
                categorytitle_text.setText("스포츠");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 6);
                categorytitle_text.setText("악기");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 7);
                categorytitle_text.setText("모빌리티");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 8);
                categorytitle_text.setText("화장품/미용");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 9);
                categorytitle_text.setText("가구/인테리어");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 10);
                categorytitle_text.setText("기타");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        categoryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 10);
                categorytitle_text.setText("카테고리");
                drawerLayout.closeDrawer(drawerView);
            }
        });

        categoryStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCategory(itemAdapter, 11);
                categorytitle_text.setText("즐겨찾기");
                drawerLayout.closeDrawer(drawerView);
            }
        });

    }


    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        mSwipeRefreshLayout.setRefreshing(true);
        ChangeCategory(itemAdapter, 5);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
