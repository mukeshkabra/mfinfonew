package mf.andorid.com.mfinfo.Fragments;

/**
 * Created by 8398 on 11/11/16.
 */

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mf.andorid.com.mfinfo.Adapter.mfHistorylistFragment;
import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

public class mfHistoryFragment extends Fragment implements ServiceCallBack {
    TextView text,vers;
    String data;
    public  ArrayList<String> date=new ArrayList<String>();
    public  ArrayList<String> nav=new ArrayList<String>();
    private ListView myList;
    public  Map<String ,String> hm=new HashMap<>();
    HashMap<String,String > datehm=new HashMap<>();
    String code;
    View view;

    public mfHistoryFragment() {

    }
    public static mfHistoryFragment newInstance() {
        mfHistoryFragment fragment = new mfHistoryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        code=getArguments().get("key1").toString();
        hm=(HashMap<String, String>) getArguments().getSerializable("hashMap");
        //getHistory(code);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view= inflater.inflate(R.layout.fragment_test, container, false);
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

                //date.add(keyvalue.toString());
                nav.add(obj.getString(keyvalue.toString()));

                hm.put(keyvalue.toString().replace("Jan", "01").replace("Oct", "10").replace("Nov", "11"), obj.get(keyvalue.toString()).toString());

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
        if (hm.size() > 0) {

            ArrayList<String> list = new ArrayList();
            ArrayList<String> list1 = new ArrayList();


            //for simplicity we will add the same name for 20 times to populate the list view
            /*for (int i = 0; i < date.size(); i++) {
                String str = date.get(i).replace("Nov", "11");
                String st1 = str.replace("Oct", "10");
                String str2 = st1.replace("Jan", "01");
                //list.add(str2);
                //list1.add(nav.get(i));
            }*/
            Map<String, String> treeMap = new TreeMap<String, String>(
                    new Comparator<String>() {
                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

                        @Override
                        public int compare(String o1, String o2) {
                            try {
                                return f.parse(o1).compareTo(f.parse(o2));
                            } catch (Exception e) {
                                throw new IllegalArgumentException(e);
                            }
                        }

                    });
            treeMap.putAll(hm);

            for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                list.add(entry.getKey());
                list1.add(entry.getValue());
                System.out.println("Key : " + entry.getKey()
                        + " Value : " + entry.getValue());
            }
            Collections.sort(list, new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                public int compare(String o1, String o2) {
                    try {
                        return f.parse(o1).compareTo(f.parse(o2));
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
            Collections.reverse(list);
            Collections.reverse(list1);


            //TableLayout tl = (TableLayout)view.findViewById(R.id.tableLayout1de);
            GraphView graph = (GraphView) view.findViewById(R.id.graph);
            List<DataPoint> d1 = new ArrayList<>();
            int i = 0;
            System.out.println(treeMap.size());
            for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                System.out.println("Key1 : " + entry.getKey()
                        + " Value1 : " + entry.getValue());
                DataPoint d2 = new DataPoint(i, Double.parseDouble(entry.getValue()));
                d1.add(d2);
                i++;

            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(d1.toArray(new DataPoint[d1.size()]));
            graph.addSeries(series);
            Viewport viewport = graph.getViewport();
            viewport.setXAxisBoundsManual(true);
            viewport.setMinX(0);
            viewport.setMaxX(treeMap.size() + 1);
            viewport.setScrollable(true);


            series.setDrawBackground(true);
            series.setBackgroundColor(Color.GREEN);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series.setCustomPaint(paint);
            series.setDataPointsRadius(1f);


            myList = (ListView) view.findViewById(R.id.list);

            myList.setAdapter(new mfHistorylistFragment(this.getActivity(), list, list1));


        } else {

        }
    }

    @Override
    public void onFail(RetrofitError error) {

    }
}
