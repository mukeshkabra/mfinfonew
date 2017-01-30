package mf.andorid.com.mfinfo.Fragments;

/**
 * Created by 8398 on 11/11/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mf.andorid.com.mfinfo.Adapter.mfHistorylistFragment;
import mf.andorid.com.mfinfo.OkHttpHandler;
import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

public class returnFragment extends Fragment implements ServiceCallBack {
    TextView text,vers;
    String data;
    public  ArrayList<String> date=new ArrayList<String>();
    public  ArrayList<String> nav=new ArrayList<String>();
    private ListView myList;
    HashMap<String,String > returns=new HashMap<>();
    String code;
    View view;

    public returnFragment() {

    }
    public static returnFragment newInstance() {
        returnFragment fragment = new returnFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        code=getArguments().get("key1").toString();
        returns=(HashMap<String, String>) getArguments().getSerializable("returns");
        //getHistory(code);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view= inflater.inflate(R.layout.fragment_returns, container, false);
        showdata(view);
        return view;
    }



    private void getHistory(String code) {
        BaseRequest baseRequest = new BaseRequest(getActivity());
        baseRequest.setServiceCallBack(this);
        baseRequest.setRequestTag(RestClient.GET_HISTORY);
        Api api = (Api) baseRequest.execute(Api.class);
        try {
            if (api != null) {
                api.getHistory(code, baseRequest.requestCallback());
            }
        } catch (Exception e) {

        }
    }

    public  String downloadUrl(String code) {
        String url = "http://127.0.0.1:8988/mf/gethistorydata?pId="+code;
        OkHttpHandler handler = new OkHttpHandler();
        String result = null;

        return result.toString();
    }


    @Override
    public void onSuccess(int tag, String baseResponse) {

        // text.setText(result.toString());
        try {
            JSONArray array=new JSONArray(baseResponse.toString());
            System.out.println("Size="+array.length());
            JSONObject obj = array.getJSONObject(0);
            Iterator it=obj.keys();
            while(it.hasNext()){
                String keyvalue=it.next().toString();
                System.out.println("Size=" + keyvalue);

                date.add(keyvalue.toString());
                nav.add(obj.getString(keyvalue.toString()));

                returns.put(keyvalue.toString().replace("Jan", "01").replace("Oct", "10").replace("Nov", "11"), obj.get(keyvalue.toString()).toString());

            }
            showdata(view);


            System.out.println("Hellos");
            System.out.println(obj.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void showdata(View view) {
        if (returns.size() > 0) {

            ArrayList<String> list = new ArrayList();
            ArrayList<String> list1 = new ArrayList();


            //for simplicity we will add the same name for 20 times to populate the list view
            for (int i = 0; i < date.size(); i++) {
                String str = date.get(i).replace("Nov", "11");
                String st1 = str.replace("Oct", "10");
                String str2 = st1.replace("Jan", "01");
                //list.add(str2);
                //list1.add(nav.get(i));
            }


            for (Map.Entry<String, String> entry : returns.entrySet()) {
                list.add(entry.getKey());
                list1.add(entry.getValue());
                System.out.println("Key : " + entry.getKey()
                        + " Value : " + entry.getValue());
            }



            //TableLayout tl = (TableLayout)view.findViewById(R.id.tableLayout1de);
           // GraphView graph = (GraphView) view.findViewById(R.id.graph);
            //graph.setVisibility(view.GONE);
            List<DataPoint> d1 = new ArrayList<>();
            int i = 0;
            System.out.println(returns.size());
            for (Map.Entry<String, String> entry : returns.entrySet()) {
                System.out.println("Key1 : " + entry.getKey()
                        + " Value1 : " + entry.getValue());
                DataPoint d2 = new DataPoint(i, Double.parseDouble(entry.getValue()));
                d1.add(d2);
                i++;

            }

            /*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(d1.toArray(new DataPoint[d1.size()]));
            graph.addSeries(series);
            Viewport viewport = graph.getViewport();
            viewport.setXAxisBoundsManual(true);
            viewport.setMinX(0);
            viewport.setMaxX(returns.size() + 1);
            viewport.setScrollable(true);


            series.setDrawBackground(true);
            series.setBackgroundColor(Color.GREEN);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series.setCustomPaint(paint);
            series.setDataPointsRadius(1f);*/


            myList = (ListView) view.findViewById(R.id.list_returns);

            myList.setAdapter(new mfHistorylistFragment(this.getActivity(), list, list1));


        } else {

        }
    }

    @Override
    public void onFail(RetrofitError error) {

    }
}
