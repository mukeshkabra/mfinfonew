package mf.andorid.com.mfinfo.webrequests;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

/**
 * Created by Admin on 9/21/2015.
 */
public interface Api {


    @GET("/getAllmutualFund")
    public void getAllMutualFunds(Callback<String> callback);

    @GET("/gethistorydata")
    public void getHistory(@Query("pId") String id, Callback<String> callback);

    @GET("/getsubMutualFund")
    public void getsub(@Query("pId") String id, Callback<String> callback);

    @GET("/getMfondate")
    public void getNavondate(@Query("pId") String id,@Query("date") String date,Callback<String> callback);

    @POST("/updatewatchlist")

    public void updatewishlist(@Body TypedInput json,Callback<String> callback);

    //public void updatewishlist(@Body JsonObject data,Callback<String> callback);





}
