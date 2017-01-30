package mf.andorid.com.mfinfo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mf.andorid.com.mfinfo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class mfPortfolio extends Fragment {


    public mfPortfolio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.android_frag, container, false);
        TextView textportfolio=(TextView)view.findViewById(R.id.tv_mfportfolio);
        textportfolio.setText("Coming soon...");
        return view;
    }


}
