package mf.andorid.com.mfinfo.webrequests;


import retrofit.RequestInterceptor;

/**
 * Created by K on 9/24/2015.
 */
public class SessionRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        //   request.addHeader("Authorization", ServerApi.AUTH_HEADER);
//        request.addHeader("Device-Type", "android_ver");
//        request.addHeader("Version", "v1");
        //   request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        //     request.addHeader("Content-Type", "application/json");
        request.addHeader("Content-Type", "application/json");
    }
}
