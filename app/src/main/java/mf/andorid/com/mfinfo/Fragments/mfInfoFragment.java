package mf.andorid.com.mfinfo.Fragments;

/**
 * Created by 8398 on 11/11/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mf.andorid.com.mfinfo.R;
import mf.andorid.com.mfinfo.webrequests.Api;
import mf.andorid.com.mfinfo.webrequests.ApiClient;
import mf.andorid.com.mfinfo.webrequests.BaseRequest;
import mf.andorid.com.mfinfo.webrequests.RestClient;
import mf.andorid.com.mfinfo.webrequests.ServiceCallBack;
import retrofit.RetrofitError;


public class mfInfoFragment extends Fragment implements ServiceCallBack {
    TextView text,vers;
    String data;
    public mfInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        text=(TextView)view.findViewById(R.id.AndroidOs);
        text.setText(" Mutual funds are in the form of Trust \n that manages the  pool of money \n collected from various investors \n for investment in various classes of assets to achieve certain financial goals.  We can say that Mutual Fund is trusts which  pool the savings of large number of investors and then reinvests those funds for earning profits and then distribute the dividend among the investors.    In return for such services,  Asset Management Companies charge small fees.    Every Mutual Fund / launches different schemes, each  with a specific objective.   Investors who share the same objectives invests in that particular Scheme.   Each Mutual Fund Scheme is  managed by a Fund Manager with the help of his team of professionals (One Fund Manage may be managing more than one scheme also).   ");
        //getAllMutualFunds();
        return view;
    }




    private void getAllMutualFunds() {
        Api apiService =
                ApiClient.getClient().create(Api.class);
        BaseRequest baseRequest = new BaseRequest(getActivity());
        baseRequest.setServiceCallBack(this);
        baseRequest.setRequestTag(RestClient.GET_MUTUAL_FUNDS);
        Api api = (Api) baseRequest.execute(Api.class);
        try {
            if (api != null) {
                api.getAllMutualFunds(baseRequest.requestCallback());
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onSuccess(int tag, String baseResponse) {

        Log.v("Response",baseResponse);

    }

    @Override
    public void onFail(RetrofitError error) {

    }
}
