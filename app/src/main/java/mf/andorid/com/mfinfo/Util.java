package mf.andorid.com.mfinfo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Admin on 2/9/2016.
 */
public class Util {


    public static String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final boolean IS_DEBUG = true;
    public static String base64key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtBVf4+CUAulcAMUcQdmUNG+8nlDCLY9NwtOWaVkS6PBvbgn5Wee+sL5F3T4tzOeGM6O+O4An+L4Xjq3UBU15TN6x8Djy4RlybxBKCHvTwA/7/KFDyev6q4kA3WT9CUsKg5oW6e5X7IxjmZMbd5H0KcUyNbzAyjpkPNUayE9OWJmwvc02diTdXaFr9XgdURxYX2dHb6flU+qApy/d+CEHxfD/M+lEfP+chs8mVA7Ix0gEXMEKKG1ugMgYHNVwNGjCHUtCTzjvSStRV3KuRVNgMkswG1xVzkQM2Ur+mZtNmWcRxHZiSg8Ulqs0wnYAh7GalRCgOYD/o8c0Od626efrJwIDAQAB";

    public static void showToast(Context mContext, String msg) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }


    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("OK", okListener).show().setCancelable(false);
    }

    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("OK", okListener).setNegativeButton("Cancel", cancelListener).show().setCancelable(false);
    }

    public static void showAlertBoxForConfirmation(Context context, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("YES", okListener).setNegativeButton("NO", cancelListener).show().setCancelable(false);
    }

    public static void showLog(String logMessage) {
        if (IS_DEBUG) {
            Log.d("GRABGEINE LOG :", logMessage);
        }
    }

    public static void showLog(String tag, String logMessage) {
        if (IS_DEBUG) {
            Log.d(tag, logMessage);
        }
    }

    public static boolean isValidMobile(String number) {
        return number.length() == 10 && !number.startsWith("0", 0) && Patterns.PHONE.matcher(number.trim()).matches();
    }

    public static boolean isValidEmail(String emailId) {
        return Patterns.EMAIL_ADDRESS.matcher(emailId.trim()).matches();
    }

    public static final int REQUEST_CAMERA = 123;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 12345;
    public static final int REQUEST_CAMERA_READ_WRITE = 1234;


    public static void RequestReadWriteAndCameraPermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_READ_WRITE);
    }


    public static void RequestReadWritePermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void hideKeyBorad(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void validationPassword(Context context, String password) {


    }

    public static void showErrorMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public static void getGridview(GridView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }

        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getColumnWidth() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        myListView.requestLayout();

        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }

        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        myListView.requestLayout();
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    public static int getWidth(Activity activity)

    {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int val = height - width;

        return ((width - val)-50);
    }

    public static int getHeight(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int val = height - width;
        return ((height - val)-50);
    }


}
