package mf.andorid.com.mfinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

import mf.andorid.com.mfinfo.Fragments.mfHistoryFragment;
import mf.andorid.com.mfinfo.Fragments.mfPortfolio;
import mf.andorid.com.mfinfo.Fragments.returnFragment;
/**
 * Created by kundan on 10/16/2015.
 */
public class PagerAdapter  extends FragmentStatePagerAdapter{
    String code;
    HashMap<String,String> hm=new HashMap<>();
    HashMap<String,String> returns=new HashMap<>();
    String oneday;
    String weekly;

    public PagerAdapter(FragmentManager fm,String code,HashMap<String,String> hm,HashMap<String,String> returns) {
        super(fm);
        this.code=code;
        this.hm=hm;
        this.returns=returns;

    }



    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new mfHistoryFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("key1", code);
                bundle1.putSerializable("hashMap",hm);
                frag.setArguments(bundle1);
                break;
            case 1:
                frag=new returnFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("key1", code);
                bundle2.putSerializable("returns", returns);
                frag.setArguments(bundle2);
                break;
            case 2:
                frag=new mfPortfolio();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="History";
                break;
            case 1:
                title="Return";
                break;
            case 2:
                title="PortFolio";
                break;
        }

        return title;
    }
}
