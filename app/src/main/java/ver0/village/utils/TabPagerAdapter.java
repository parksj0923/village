package ver0.village.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ver0.village.MainFragment.ChatFragment;
import ver0.village.MainFragment.EmptyFragment;
import ver0.village.MainFragment.HomeFragment;
import ver0.village.MainFragment.MypageFragment;
import ver0.village.MainFragment.TradeFragment;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tabFragment1 = new HomeFragment();
                return tabFragment1;
            case 1:
                TradeFragment tabFragment2 = new TradeFragment();
                return tabFragment2;
            case 2:
                EmptyFragment tabFragment5 = new EmptyFragment();
                return tabFragment5;
            case 3:
                ChatFragment tabFragment3 = new ChatFragment();
                return tabFragment3;
            case 4:
                MypageFragment tabFragment4 = new MypageFragment();
                return tabFragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}