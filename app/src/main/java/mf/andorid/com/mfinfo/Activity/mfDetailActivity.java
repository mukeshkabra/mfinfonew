package mf.andorid.com.mfinfo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mf.andorid.com.mfinfo.Adapter.Product;
import mf.andorid.com.mfinfo.Adapter.wishlistAdapter;
import mf.andorid.com.mfinfo.Fragments.addToPortfolioFragment;
import mf.andorid.com.mfinfo.Fragments.mfHistoryFragment;
import mf.andorid.com.mfinfo.PagerAdapter;
import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.sharedPref.wishlistsharedPref;
import mf.andorid.com.mfinfo.testFragment2;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

;

/**
 * Created by 8398 on 29/11/16.
 */
public class mfDetailActivity extends FragmentActivity implements ServiceCallBack,addToPortfolioFragment.OnFragmentInteractionListener {
    public double lastdayNav;
    public double weeklychange;
    DecimalFormat df = new DecimalFormat("0.00");
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public wishlistsharedPref sharedPreference;
    Button addTo;
    ViewPagerAdapter pagerAdapter;
    ViewPager viewPager;
    HashMap<String,String> hm=new HashMap<>();
    HashMap<String,String> returns=new HashMap<>();

    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    String code;
    private String m_Text = "";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Button watchlist;
    mfHistoryFragment t1;
    String change;
    ViewPager  pager;
    TabLayout tabLayout1;
    String oneDayReturn;
    String weekly;
    ImageView img_addto;
    ImageView img_wishlist;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(mypreference,
                getBaseContext().MODE_PRIVATE);
        sharedPreference = new wishlistsharedPref();
        Intent intent=getIntent();
        FragmentManager fragMan = this.getSupportFragmentManager();

        code= (String) intent.getSerializableExtra("code");
        final String name= (String) intent.getSerializableExtra("Name");
        final String nav=(String) intent.getSerializableExtra("Nav");
        final String buttondispaly=(String)intent.getSerializableExtra("Button");
        final String date=(String) intent.getSerializableExtra("date");
        change="0";
        System.out.println(code);
        System.out.println(name);
        System.out.println(nav);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager
                .beginTransaction();
        /*t1=new mfHistoryFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("key1", code);
        t1.setArguments(bundle1);*/



        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaillayout);


        pager= (ViewPager) findViewById(R.id.view_pager);
        tabLayout1= (TabLayout) findViewById(R.id.tab_layout);
        img_addto=(ImageView) findViewById(R.id.imv_addto);
        img_wishlist=(ImageView) findViewById(R.id.imv_wishlist);

        /*FragmentManager manager=getSupportFragmentManager();
        PagerAdapter adapter=new PagerAdapter(manager,code);
        pager.setAdapter(adapter);

        tabLayout1.setupWithViewPager(pager);
        // mTabLayout.setupWithViewPager(mPager1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout1.setTabsFromPagerAdapter(adapter);*/

        gethistoryData(code);

        toolbar=(Toolbar) findViewById(R.id.toolbar_bottom);
        toolbar.setTitle("MF INFO");
        //t.setLogo(R.drawable.icon_wishlist);



        TextView mfname=(TextView) findViewById(R.id.mfname);
        mfname.setText(name);
        TextView mNavName=(TextView)findViewById(R.id.Navtext);
        mNavName.setText("NAV : ");
        TextView mNav=(TextView) findViewById(R.id.Nav);
        mNav.setText(nav);
        TextView mDate=(TextView) findViewById(R.id.navdate);
        mDate.setText("On "+date);






        addTo=(Button)findViewById(R.id.addto);
        watchlist=(Button) findViewById(R.id.addtowatchlist);


        addTo.setVisibility(View.GONE);
        watchlist.setVisibility(View.GONE);
        checkfundInWishlist(name);
        img_addto.setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        img_addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*System.out.println("Enter amount you invested");
                AlertDialog.Builder builder = new AlertDialog.Builder(mfDetailActivity.this);
                builder.setTitle("Enter Amount you Invested");

                // Set up the input
                final EditText input = new EditText(getApplicationContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                input.setTextColor(Color.BLACK);
                builder.setView(input);


// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/

                //builder.create().show();
                System.out.println("Ready for click");
                viewPager = (ViewPager) findViewById(R.id.viewpager_fr);
                //FragmentManager fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                addToPortfolioFragment wi1=new addToPortfolioFragment();
                Bundle wishlistBundle=new Bundle();
                wishlistBundle.putString("mfName",name);
                wishlistBundle.putString("mCode",code);
                wishlistBundle.putString("mNav", nav);
                wishlistBundle.putSerializable("history",hm);
                wi1.setArguments(wishlistBundle);
                transaction.replace(R.id.frame, wi1, "Hello");
                transaction.addToBackStack("Hello1").commit();
               // img_addto.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));

            }
        });

       /* TextView textView1=(TextView) findViewById(R.id.TextView1row1);
        textView1.setText("LastDay");
        TextView textView2=(TextView) findViewById(R.id.TextView2row1);
        textView2.setText(Double.toString(lastdayNav));
        TextView textView3=(TextView) findViewById(R.id.TextView1row2);
        textView3.setText("Weekly");
        TextView textView4=(TextView) findViewById(R.id.TextView2row2);
        //textView4.setText(Double.toString(weeklychange));
        textView4.setText(df.format(weeklychange)+"%");
        System.out.println(lastdayNav);
        if(weeklychange>0){
            textView4.setTextColor(Color.GREEN);
        }
        else{
            textView4.setTextColor(Color.RED);
        }*/
        if(Double.parseDouble(nav)>lastdayNav){
            mNav.setTextColor(Color.GREEN);
        }
        else{
            mNav.setTextColor(Color.RED);
        }

        if(!(buttondispaly.equals("true"))){
            watchlist.setVisibility(View.GONE);
            addTo.setVisibility(View.GONE);
        }
        img_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoSharedPre(name, nav, code,change,date);
                img_wishlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));

            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager_fr);


        // testFragment2 t2=new testFragment2();

        //transaction.replace(R.id.frame, t1, "Hello");
        //transaction.addToBackStack("Hello").commit();
        //transaction.add(R.id.frame, p1, "Hello");
        //transaction.commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("key1", code);
        mfHistoryFragment t1=new mfHistoryFragment();
        t1.setArguments(bundle);
        testFragment2 t2=new testFragment2();
        t2.setArguments(bundle);
        adapter.addFragment(t1, "History");
        adapter.addFragment(t2, "Returns");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        List<Fragment> fragmentss = fragmentManager.getFragments();
        System.out.println("BACK KEY PRESS =" + fragmentss.size());
        if (fragmentss != null && fragmentss.size() > 5) {
            Fragment fragment=fragmentss.get(0);
            //Fragment fragment1=fragmentss.get(1);
            //System.out.println(fragment.toString());

            //System.out.println("Mukesh ="+fragment.getFragmentManager().getBackStackEntryCount());
            if(fragment!=null){
                if (fragment.getFragmentManager().getBackStackEntryCount() > 0) {
                    fragment.getFragmentManager().popBackStackImmediate();

                }}
            else{
                super.finish();
                this.finish();
            }
        }
        else{
            System.out.println("Inside else");
            //super.onBackPressed();
            boolean check=false;
            for(int i=0;i<fragmentss.size();i++){
                System.out.println(fragmentss.get(i));
                if(fragmentss.get(i)!=null  && fragmentss.get(i).toString().contains("addToPortfolioFragment")){
                    Fragment fragmentadd=fragmentss.get(i);
                    if(fragmentadd!=null){
                        check=true;
                        fragmentadd.getFragmentManager().popBackStackImmediate();
                    }
                }
            }
            if(!check) {
                this.finish();
            }

        }
    }










    /*public String downloadUrl(int code)  {
        System.out.println("PID" + code);
        String url="http://127.0.0.1:8988/mf/navhistory?mcode="+Integer.toString(code);

        OkHttpHandler handler = new OkHttpHandler();
        String result = null;
        try {
            result = handler.execute(url).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(JsonPath.read(result,"$.[0].name"));
        // text.setText(result.toString());


        try {
            JSONArray array=new JSONArray(result.toString());
            for(int i=0;i<array.length();i++){
                JSONObject c=array.getJSONObject(i);
                String temp=c.getString("Lastday");
                lastdayNav=Double.parseDouble(temp);
                System.out.println(c.getString("Lastday"));
                weeklychange=c.getDouble("weekly");


            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println(result.toString());
        return result.toString();
    }*/
    public void AddtoSharedPre(String mname,String mNav,String code,String mchange,String date){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putString("Name",mname);
        //editor.putString("Nav",mNav);
        boolean check=true;
        ArrayList<Product> p1=sharedPreference.getFavorites(getApplicationContext());
        if(p1!=null){
            for(int i=0;i<p1.size();i++){
                if(p1.get(i).getName().equals(mname)){
                    Toast.makeText(getApplicationContext(), "Mutual Fund already in your Portfolio", Toast.LENGTH_LONG).show();
                    watchlist.setClickable(false);
                    check=false;
                    break;
                }


            }}
        if (check){
            Product p=new Product(mname,mNav,code,mchange,date);
            sharedPreference.addFavorite(getApplicationContext(), p);
            editor.commit();
            sharedPreference = new wishlistsharedPref();
            ArrayList<Product> p11=sharedPreference.getFavorites(getApplicationContext());
            ArrayList<String> name1=new ArrayList<>();
            ArrayList<String> nav1=new ArrayList<>();
            ArrayList<String> code1=new ArrayList<>();
            ArrayList<String> mchange1=new ArrayList<>();
            ArrayList<String> mdate1=new ArrayList<>();
            if(p11!=null) {
                System.out.println(p11.size());
                for (int i = 0; i < p11.size(); i++) {
                    System.out.println(p11.get(i).getName());
                    System.out.println(p11.get(i).getNav());
                    name1.add(p11.get(i).getName());
                    nav1.add(p11.get(i).getNav());
                    code1.add(p11.get(i).getCode());
                    mchange1.add(p11.get(i).gethowmuchChange());
                    mdate1.add(p11.get(i).getDate());

                }}
            // lv.setAdapter(new CustomAdapter(getActivity(), name.toArray(new String[0]), name.toArray(new String[0])));


            //System.out.println("VAlue"+mf.andorid.com.mfinfo.PortfolioAdapte);
            wishlistAdapter adapter=new wishlistAdapter();
            adapter.refereshData(name1.toArray(new String[0]), nav1.toArray(new String[0]),mchange1.toArray(new String[0]),mdate1.toArray(new String[0]));


        }


    }
    public void gethistoryData(String pid){
        BaseRequest baseRequest = new BaseRequest(this);
        baseRequest.setServiceCallBack(this);
        baseRequest.setRequestTag(RestClient.GET_MUTUAL_FUNDS);
        Api api = (Api) baseRequest.execute(Api.class);
        try {
            if (api != null) {
                api.getHistory(pid, baseRequest.requestCallback());
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onSuccess(int tag, String baseResponse) {
        hm.clear();
        try {
            JSONArray array=new JSONArray(baseResponse.toString());
            JSONObject o1=(JSONObject) array.get(0);
            System.out.println(o1.toString());
            oneDayReturn=o1.getString("In1Day");
            weekly=o1.getString("weekly");
            returns.put("In1Day",oneDayReturn);
            returns.put("Weekly",weekly);

            JSONObject obj = o1.getJSONObject("nav");
            System.out.println(oneDayReturn+"="+weekly+" = "+obj.toString());

            Iterator it=obj.keys();
            while(it.hasNext()){
                String keyvalue=it.next().toString();
                System.out.println("Size=" + keyvalue);

                //date.add(keyvalue.toString());

                hm.put(keyvalue.toString().replace("Jan", "01").replace("Oct", "10").replace("Nov", "11"), obj.get(keyvalue.toString()).toString());

            }
            FragmentManager manager=getSupportFragmentManager();
            PagerAdapter adapter=new PagerAdapter(manager,code,hm,returns);
            pager.setAdapter(adapter);

            tabLayout1.setupWithViewPager(pager);
            // mTabLayout.setupWithViewPager(mPager1);
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout1.setTabsFromPagerAdapter(adapter);


            System.out.println("Hellos");
            System.out.println(obj.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }





        /*try {
            JSONArray array=new JSONArray(baseResponse.toString());
            for(int i=0;i<array.length();i++){
                JSONObject c=array.getJSONObject(i);
                String temp=c.getString("Lastday");
                lastdayNav=Double.parseDouble(temp);
                System.out.println(c.getString("Lastday"));
                weeklychange=c.getDouble("weekly");


            }
        }
        catch(Exception e){
            System.out.println(e);
        }*/
    }

    @Override
    public void onFail(RetrofitError error) {

    }

    @Override
    public void onFragmentInteraction(String btn_value) {
        if(btn_value.toString().equals("save")){
            System.out.println("Buttonsave");
            img_addto.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));

        }else{
            System.out.println("Buttoncancel");
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    public void checkfundInWishlist(String mname){
        ArrayList<Product> p1=sharedPreference.getFavorites(getApplicationContext());
        if(p1!=null){
            for(int i=0;i<p1.size();i++){
                if(p1.get(i).getName().equals(mname)){
                    img_wishlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    img_wishlist.setEnabled(false);
                    break;
                }


            }}
    }

}
