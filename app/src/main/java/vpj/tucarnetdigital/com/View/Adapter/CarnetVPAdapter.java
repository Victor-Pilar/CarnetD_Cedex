package vpj.tucarnetdigital.com.View.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CarnetVPAdapter extends FragmentPagerAdapter {


    private final List<Fragment> mFragment = new ArrayList<>();
    private final List<String> mFragmentsTitles = new ArrayList<>();

    public CarnetVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }



    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitles.get(position);
    }

    public void addFragment(Fragment fragment, String titulo){
        mFragment.add(fragment);
        mFragmentsTitles.add(titulo);
    }
}
