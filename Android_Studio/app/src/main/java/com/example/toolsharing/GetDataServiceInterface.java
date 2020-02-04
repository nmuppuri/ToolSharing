package com.example.toolsharing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataServiceInterface {

    @GET("NewAdmin&{id}&{passwd}&{fname}&{lname}&{email}")//&{phone}&{address}")
    Call<NewRegis_Pojo> getAdminRegister(@Path("id") int id, @Path("passwd") String pwd, @Path("fname") String fn,
                                         @Path("lname") String ln, @Path("email") String email/*, @Path("phone") Long phn,
                                         @Path("address") String addr*/);


}
