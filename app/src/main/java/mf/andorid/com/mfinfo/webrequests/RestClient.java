package mf.andorid.com.mfinfo.webrequests;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

import mf.andorid.com.mfinfo.Util;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Admin on 9/21/2015.
 */
public class RestClient {
    public static int GET_MUTUAL_FUNDS = 10001;
    public static int GET_HISTORY = 10002;


    private BaseRequest baseRequest;

    private ProgressDialog pDialog = null;
    private Context context;
    public Callback<String> callback = new Callback<String>() {


        @Override
        public void success(String o, Response response) {
            Util.showLog("success: " + o);
            baseRequest.getServiceCallBack().onSuccess(baseRequest.getRequestTag(), o);
            dismissProgressDialog();

        }

        @Override
        public void failure(RetrofitError error) {
            //Util.showLog("failure: true");
            baseRequest.getServiceCallBack().onFail(error);
            dismissProgressDialog();
            //Util.showAlertBox(context, "We are facing some technical issue, please try after some time.", null);
        }
    };
    private DialogInterface.OnClickListener failureListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (((Activity) context).isFinishing()) {
                return;
            }
            ((Activity) context).finish();

        }
    };

    public RestClient(Context context, BaseRequest _baseRequest) {
        this.baseRequest = _baseRequest;
        this.context = context;
    }

    public Object execute(Class classes) {
        showProgressDialog(context);
        return setUpRestClient(classes);
    }

    private Object setUpRestClient(Class apiClasses) {
        /*ServerSocket s = null;
        try
        {
             s = new ServerSocket(0);
        }
        catch (Exception e)
        {

        }

        Log.v("ssss",""+s);
*/
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("http://127.0.0.1:8988/mf/").setClient(new OkClient(getClient()));
        builder.setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new StringConverter());
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(apiClasses);
    }

    public ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        // if the program gets here, no port in the range was found
        throw new IOException("no free port found");
    }
    public void showProgressDialog(Context context) {
        try {
            if (Util.isOnline(context)) {
                if (baseRequest.isProgressShow()) {
                    try {
                        if (((Activity) context).isFinishing()) {
                            return;
                        }
                        pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(false);
                        pDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Util.showAlertBox(context, "No Internet Connection", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (pDialog != null)
                pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class StringConverter implements Converter {

        @Override
        public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
            String text = null;
            try {
                text = fromStream(typedInput.in());
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
            return text;
        }

        @Override
        public TypedOutput toBody(Object o) {

            return null;
        }

        private String fromStream(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }
            return out.toString();
        }
    }

    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.MINUTES);
        client.setReadTimeout(5, TimeUnit.MINUTES);
        return client;
    }
}
