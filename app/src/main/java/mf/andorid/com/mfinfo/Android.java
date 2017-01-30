package mf.andorid.com.mfinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 8398 on 27/01/17.
 */

public class Android extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.android_frag, container, false);
        ((TextView)android.findViewById(R.id.textView)).setText("Android");
        return android;
    }}