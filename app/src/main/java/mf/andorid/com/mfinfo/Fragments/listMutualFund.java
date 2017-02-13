package mf.andorid.com.mfinfo.Fragments;

/**
 * Created by 8398 on 11/11/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.himanshuvirmani.androidcache.CacheManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import mf.andorid.com.mfinfo.Activity.MyApplication;
import mf.andorid.com.mfinfo.Activity.mfactivity;
import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

public class listMutualFund extends ListFragment implements ServiceCallBack {
    ArrayList<String> al = new ArrayList<String>();
    HashMap<String, Integer> hm = new HashMap<>();
    TreeMap<String, Integer> tm = new TreeMap<>();
    //public ProgressDialog loading=new ProgressDialog(getContext().getApplicationContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);





    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getAllMutualFunds();

        System.out.println("Hellocreaateview=" + al.size());
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        getAllMutualFunds();
        return view;

    }
    public void showlist(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, al.toArray(new String[0]));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), al.get(position), Toast.LENGTH_LONG).show();
        Intent newActivity = new Intent(getActivity(), mfactivity.class);
        newActivity.putExtra("pId", hm.get(al.get(position)));
        startActivity(newActivity);

    }


    public void getAllMutualFunds() {
        MyApplication m=MyApplication.getInstance();
        m.prepareCache();
        CacheManager cacheManager = m.getCacheManager();




        //loading.show();
        BaseRequest baseRequest = new BaseRequest(getActivity());
        baseRequest.setServiceCallBack(this);
        baseRequest.setRequestTag(RestClient.GET_MUTUAL_FUNDS);
        Api api = (Api) baseRequest.execute(Api.class);
        try {
            if (api != null) {
                api.getAllMutualFunds(baseRequest.requestCallback());
            }
        } catch (Exception e) {

        }}



    @Override
    public void onSuccess(int tag, String baseResponse) {
        try {
            JSONArray array = new JSONArray(baseResponse.toString());
            System.out.println("Hellos");
            al.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                al.add(c.getString("name"));
                hm.put(c.getString("name"), c.getInt("id"));
            }
            System.out.println("Hello="+al.size());
            Collections.sort(al);


        } catch (Exception e) {
            System.out.println(e);
        }
        MyApplication m=MyApplication.getInstance();
        m.prepareCache();
        CacheManager cacheManager = m.getCacheManager();

        cacheManager.put("myKey", baseResponse);

        showlist();
    }

    @Override
    public void onFail(RetrofitError error) {
    }
}
