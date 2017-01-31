package mf.andorid.com.mfinfo.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
/**
 * Created by 8398 on 23/12/16.
 */
public class DatePickerFragment extends DialogFragment {
    String code;
    public static String nav;
    OnDateSetListener ondateSet;
    private int year, month, day;

    public DatePickerFragment() {}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
       // code=getArguments().get("mCode").toString();
        //System.out.println("date="+code);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }
    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }




}
