package mf.andorid.com.mfinfo.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;

/**
 * Created by 8398 on 23/12/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener ,ServiceCallBack {
    String code;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        code=getArguments().get("mCode").toString();
        System.out.println("date="+code);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(c.getTime());
        EditText editText=(EditText) getActivity().findViewById(R.id.editText_Date);
        editText.setText(formattedDate);
        System.out.println("Date = " + formattedDate);
        getNavonDate(code, "02-Nov-2016");
        //String nav=addToPortfolioFragment.downloadUrl(Integer.parseInt(code), "02-Nov-2016");
       // editText1.setText(nav);

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
        System.out.println(baseResponse);
        JsonParser j= new JsonParser();
        JsonObject obj =(JsonObject)j.parse(baseResponse.toString());
        //obj.get("nav")
        System.out.println("Hello result " + obj.get("nav"));
        System.out.println("Hello result " + obj.get("nav").toString().replaceAll("\"", ""));
        String navondate=obj.get("nav").toString().replaceAll("\"","");

        System.out.println(navondate);
        EditText editText1=(EditText)getActivity().findViewById(R.id.editText_nav);
        editText1.setText(navondate);
    }

    @Override
    public void onFail(RetrofitError error) {

    }
}
