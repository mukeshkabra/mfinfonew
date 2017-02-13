package mf.andorid.com.mfinfo.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mf.andorid.com.mfinfo.Adapter.portfolioAdapter;
import mf.andorid.com.mfinfo.Constant.PortfolioInfo;
import mf.andorid.com.mfinfo.OkHttpHandler;
import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.sharedPref.portfolio;
import mf.andorid.com.mfinfo.sharedPref.portfolioPref;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

/**
 * Created by 8398 on 19/12/16.
 */

public class addToPortfolioFragment extends Fragment implements ServiceCallBack {
        ListView lv;
        Context context;
        ArrayList prgmName;
        ArrayList<String> name=new ArrayList<>();
        ArrayList<String> nav=new ArrayList<>();
        ArrayList<String> code=new ArrayList<>();
    public portfolioPref sharedPreference;

    private ImageButton ib;
    private Calendar cal;
    private int pDay;
    private int pMonth;
    private int pYear;
    private EditText et;
    //EditText edit_date;
    private DatePicker dpResult;
    public EditText edit_Nav;
    EditText edit_units;
    EditText editText_amount;
    private Button btnChangeDate;
    private String mcode;
    private String mfName;
    private String currentNav;
    private ImageView img_selectdate;
    SharedPreferences sharedpreferences;
    public String navondate;
    OnDateSetListener ondate;
    EditText edit_date;

    String btn_status;
    static final int DATE_DIALOG_ID = 999;

    private OnFragmentInteractionListener mListener;
    HashMap<Integer,String> datehm=new HashMap<>();

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcode=getArguments().get("mCode").toString();
        mfName=getArguments().get("mfName").toString();
        currentNav=getArguments().get("mNav").toString();
        System.out.println("Code ="+mcode);
        System.out.println("Code ="+mfName);
        datehm.put(01,"Jan");
        datehm.put(02,"Feb");
        datehm.put(03,"March");
        datehm.put(12,"Dec");
        datehm.put(11,"Nov");


    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View vu = inflater.inflate(R.layout.wishlist, container, false);
            sharedPreference = new portfolioPref();
            System.out.println("Hello");
            edit_date=(EditText)vu.findViewById(R.id.editText_Date);
            img_selectdate=(ImageView)vu.findViewById(R.id.img_selectdate);
            final Button btn_save=(Button) vu.findViewById(R.id.btn_Save);
            btn_save.setEnabled(false);

            ondate = new OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    edit_date=(EditText) getActivity().findViewById(R.id.editText_Date);
                    edit_date.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                            + "-" + String.valueOf(year));

                    getNavonDate(mcode,String.valueOf(dayOfMonth) + "-" +datehm.get(monthOfYear+1) + "-" + String.valueOf(year));
                    //getNavonDate(mcode, "02-Nov-2016");
                }
            };
            img_selectdate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   /* DatePickerFragment picker = new DatePickerFragment();
                    Bundle datepick = new Bundle();
                    datepick.putString("mCode", mcode);
                    picker.setArguments(datepick);

                    picker.show(getFragmentManager(), "datePicker");
                    picker.setCallBack(ondate);*/
                    showDatePicker();


                }
            });



            TextView textView_schemaname=(TextView)vu.findViewById(R.id.textViewt);
            textView_schemaname.setText(mfName);
            edit_Nav=(EditText)vu.findViewById(R.id.editText_nav);
            //edit_Nav.setText("20");
            edit_units=(EditText)vu.findViewById(R.id.editText_units);
            editText_amount=(EditText)vu.findViewById(R.id.editText_amount);
            editText_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                        System.out.println("TextWather" + s.toString());
                    //edit_units.setText(s.toString());
                   try {
                       Double temp1 = Double.valueOf(s.toString());
                       Double temp2 = Double.valueOf(edit_Nav.getText().toString());
                       System.out.println(temp1);
                       System.out.println(temp2);
                       System.out.println(temp1 / temp2);
                       Double temp = temp1 / temp2;
                       //String s1 = String.format("%.2f", Double.toString(temp));

                       edit_units.setText(Double.toString(temp));
                       btn_save.setEnabled(true);
                   }
                   catch(Exception e){
                       edit_units.getText().clear();
                       btn_save.setEnabled(false);
                   }


                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("Hello");
                    FragmentManager f=getFragmentManager();

                    AddtoSharedPrePortfolio(mfName,mcode,Double.parseDouble(currentNav),Double.parseDouble(edit_Nav.getText().toString()),0.0,Double.parseDouble(editText_amount.getText().toString()),Double.parseDouble(edit_units.getText().toString()),edit_date.getText().toString());
                    System.out.println(f.getBackStackEntryCount());
                    f.popBackStack();
                    if(f.getBackStackEntryCount()==2){
                        f.popBackStack();
                    }
                    btn_status="save";
                    onButtonPressed(btn_status);

                }
            });
            Button btn_cancel=(Button) vu.findViewById(R.id.btn_cancel);

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("HelloCancel");
                    FragmentManager f=getFragmentManager();
                    f.popBackStack();
                    if(f.getBackStackEntryCount()==2){
                        f.popBackStack();
                    }
                btn_status="cancel";
                    onButtonPressed(btn_status);
                }
            });

                /*if (Integer.valueOf(editText_amount.getText().toString()) > 0 && (Integer.valueOf((edit_Nav.getText().toString())) > 0))
                    editText_amount = (EditText) vu.findViewById(R.id.editText_amount);
                edit_units.setText(Integer.valueOf(editText_amount.getText().toString()) / Integer.valueOf((edit_Nav.getText().toString())));
*/

            return vu;
        }


    public static String downloadUrl(int pid,String date)  {
        System.out.println("PID" + pid);
        String url = "http://127.0.0.1:8988/mf/getMfondate?pId="+Integer.toString(pid)+"&date="+date;

        OkHttpHandler handler = new OkHttpHandler();
        String result = null;
        try {
            result = handler.execute(url).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
            e.printStackTrace();
        }
        //System.out.println(JsonPath.read(result,"$.[0].name"));
        // text.setText(result.toString());
        JsonParser j= new JsonParser();
        JsonObject obj =(JsonObject)j.parse(result.toString());
        //obj.get("nav")
        System.out.println("Hello result " + obj.get("nav"));
        System.out.println("Hello result " + obj.get("nav").toString().replaceAll("\"", ""));
        return obj.get("nav").toString().replaceAll("\"","");
    }

    public void AddtoSharedPrePortfolio(String mname,String code,Double mcurrentNav,Double mdateNav,Double mChange,Double mAmountInvested,Double mUnits ,String mCurrentDate) {
        if (updatesharedpref(code, mcurrentNav, mCurrentDate, mUnits, mAmountInvested)) {
            System.out.println("INSIDE");
            boolean check = true;
            ArrayList<portfolio> p1 = sharedPreference.getFavorites(this.getActivity().getApplicationContext());

            portfolio p = new portfolio(mname, mdateNav, mcurrentNav, code, mChange, mCurrentDate, mUnits, mAmountInvested);
            sharedPreference.addFavorite(this.getActivity().getApplicationContext(), p);
            sharedPreference = new portfolioPref();
            ArrayList<portfolio> pSharePre = sharedPreference.getFavorites(this.getActivity().getApplicationContext());
            ArrayList<String> Sname = new ArrayList<>();
            ArrayList<Double> ScurrentNav = new ArrayList<>();
            ArrayList<String> Scode = new ArrayList<>();
            ArrayList<Double> SdateNav = new ArrayList<>();
            ArrayList<Double> Schange = new ArrayList<>();
            ArrayList<Double> SAmountInvested = new ArrayList<>();
            ArrayList<Double> SUnits = new ArrayList<>();
            ArrayList<String> Sdate = new ArrayList<>();

            if (pSharePre != null) {
                System.out.println(pSharePre.size());
                for (int i = 0; i < pSharePre.size(); i++) {
                    Sname.add(pSharePre.get(i).getName());
                    ScurrentNav.add(pSharePre.get(i).getCurrentNav());
                    Scode.add(pSharePre.get(i).getCode());
                    SdateNav.add((pSharePre.get(i).getdateNav()));
                    Schange.add(pSharePre.get(i).getChange());
                    SAmountInvested.add(pSharePre.get(i).getAmoutInv());
                    SUnits.add(pSharePre.get(i).getUnits());
                    Sdate.add(pSharePre.get(i).getdate());


                }
            }
            // lv.setAdapter(new CustomAdapter(getActivity(), name.toArray(new String[0]), name.toArray(new String[0])));


            System.out.println(userPortfolioFragment.adapter);
            portfolioAdapter a=new portfolioAdapter();

            List<PortfolioInfo> resultPortfolio = new ArrayList<PortfolioInfo>();
            PortfolioInfo pi=new PortfolioInfo();
            pi.mAmountInvested=mAmountInvested;
            pi.mCode=code;
            pi.mdate=mCurrentDate;
            pi.mName=mname;
            pi.mNav=mcurrentNav;
            pi.mUnit=mUnits;
            resultPortfolio.add(pi);
            //a.refereshData(resultPortfolio);



            PortfolioInfo pte=new PortfolioInfo();
            pte.setmAmountInvested(mAmountInvested);
            pte.setmCode(code);
            pte.setMdate(mCurrentDate);
            pte.setmName(mname);
            pte.setmNav(mcurrentNav);
            pte.setmUnit(mUnits);

            portfolioAdapter ptemp=new portfolioAdapter(resultPortfolio);
            ptemp.notifyDataSetChanged();
            //ptemp.refereshData(pte);
            //TwoFragment.adapter.refereshData(Sname.toArray(new String[0]), Sname.toArray(new String[0]),Sname.toArray(new String[0]),Sdate.toArray(new String[0]));


        }
    }

   // public void updatesharedpref(String mname,String code,Double mcurrentNav,Double mdateNav,Double mChange,Double mAmountInvested,Double mUnits ,String mCurrentDate){
     public boolean updatesharedpref(String code,Double tnav,String date,Double Iunits,Double Iamount){
         boolean check=true;
        ArrayList<portfolio> p1=sharedPreference.getFavorites(this.getActivity().getApplicationContext());
         if(p1!=null) {
             for (int i = 0; i < p1.size(); i++) {
                 System.out.println("Shared pref =" + p1.get(i).getCode());
                 if (p1.get(i).getCode().equals(code)) {
                     check=false;
                     System.out.println("Found out");
                     Double pr_amount = p1.get(i).getAmoutInv() + Iamount;
                     Double units = p1.get(i).getUnits() + Iunits;
                     String name = p1.get(i).getName();
                     Double changes = p1.get(i).getChange();
                     String sdate = p1.get(i).getdate();
                     p1.remove(p1.get(i));
                     portfolio ptemp = new portfolio(name, tnav, tnav, code, changes, sdate, units, pr_amount);
                     p1.add(ptemp);
                     sharedPreference.clearsavedpref(this.getActivity().getApplicationContext());
                     sharedPreference.saveFavorites(this.getActivity().getApplicationContext(), p1);


                 }
             }
         }
         return check;
    }
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
    public void getNavonDate(String pid,String date){
        BaseRequest baseRequest = new BaseRequest(getActivity());
        baseRequest.setServiceCallBack(this);
        baseRequest.setRequestTag(RestClient.GET_MUTUAL_FUNDS);
        Api api = (Api) baseRequest.execute(Api.class);
        try {
            if (api != null) {
                api.getNavondate(pid, date, baseRequest.requestCallback());
            }
        } catch (Exception e) {

        }}



    @Override
    public void onSuccess(int tag, String baseResponse) {
        System.out.println("base response=" + baseResponse);
        JsonParser j = new JsonParser();
        JsonObject obj = (JsonObject) j.parse(baseResponse.toString());
        //obj.get("nav")
        System.out.println(obj.isJsonNull());
        if (obj.get("nav") != null) {
            System.out.println("Hello result " + obj.get("nav"));
            System.out.println("Hello result " + obj.get("nav").toString().replaceAll("\"", ""));
            String navondate = obj.get("nav").toString().replaceAll("\"", "");

            System.out.println(navondate);
            EditText editText1 = (EditText) getActivity().findViewById(R.id.editText_nav);
            // System.out.println(editText1);
            editText1.setText(navondate);
        } else {
            Toast.makeText(getActivity().getApplicationContext(),"Please enter proper date",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onFail(RetrofitError error) {

    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String btn_value);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    public void onButtonPressed(String btn_value) {
        if (mListener != null) {
            mListener.onFragmentInteraction(btn_value);
        }
    }
}








