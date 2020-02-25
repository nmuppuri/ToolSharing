package com.example.toolsharing.Utils;

import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataServiceInterface {

   /* @GET("NewAdmin&{id}&{passwd}&{fname}&{lname}&{email}")//&{phone}&{address}")
    Call<StatusMessage_Pojo> getAdminRegister(@Path("id") int id, @Path("passwd") String pwd, @Path("fname") String fn,
                                              @Path("lname") String ln, @Path("email") String email/*, @Path("phone") Long phn,
                                         @Path("address") String addr*//*);*/


    @GET("NewStudent&{id}&{fname}&{lname}&{email}")
    Call<StatusMessage_Pojo> getStudentRegister(@Path("id") int id, @Path("fname") String fn,
                                              @Path("lname") String ln, @Path("email") String email);


    @GET("DeleteStudent&{id}")
    Call<StatusMessage_Pojo> studentDeleteReq(@Path("id") int id);


    @GET("StudentDeleteAccept&{student_id}")
    Call<StatusMessage_Pojo> studentDelAccept(@Path("student_id") int student_id);


    @GET("Login&{id}&{pwd}")
    Call<StatusMessage_Pojo> getlogin(@Path("id") int id, @Path("pwd") String pwd);

    @GET("StudentRegis")
    Call<StatusMessage_Pojo> getstudentRegisDetails();

    @GET("StudentRegisAccept&{student_id}&{status}")
    Call<StatusMessage_Pojo> getStudentRegisAccept(@Path("student_id") int sid, /*@Path("admin_id") int aid,*/ @Path("status") String decision);


    @GET("ForgotPassword&{email}")
    Call<StatusMessage_Pojo> getforgotPassword(@Path("email") String email);


    @GET("SearchToolsList")
    Call<StatusMessage_Pojo> getSearchTools();


    @GET("ToolOrder&{ptid}&{psid}&{bsid}&{fd}&{td}")
    Call<StatusMessage_Pojo> getToolOrder(@Path("ptid") int ptid, @Path("psid") int psid, @Path("bsid") int bsid, @Path("fd") String fd,
                                          @Path("td") String td);


    @GET("ToolsList")
    Call<StatusMessage_Pojo> getAllTools();


    @GET("AddTools&{psid}&{ptid}")
    Call<StatusMessage_Pojo> getAddMyTool(@Path("psid") int psid, @Path("ptid") int ptid);


    @GET("MyToolsList&{psid}")
    Call<StatusMessage_Pojo> getMyTools(@Path("psid") int psid);


    @GET("UpdateMyToolAvail&{psid}&{ptid}&{avail}")
    Call<StatusMessage_Pojo> updateAvailMyTool(@Path("psid") int psid, @Path("ptid") int ptid, @Path("avail") int avail);


    @GET("BorrowedToolsList&{bsid}")
    Call<StatusMessage_Pojo> getborrowedToolList(@Path("bsid") int bsid);

    @GET("BorrowedToolsListHistory&{bsid}")
    Call<StatusMessage_Pojo> getborrowedToolListHist(@Path("bsid") int bsid);

    @GET("BorrowedToolsPenalty&{bsid}")
    Call<StatusMessage_Pojo> getborrowedToolPenalty(@Path("bsid") int bsid);


    @GET("BorrowedToolsListReview&{psid}")
    Call<StatusMessage_Pojo> getBorrowedToolsListRev(@Path("psid") int psid);


    @GET("BorrowToolReturn&{order_id}&{penalty}")
    Call<StatusMessage_Pojo> returnBorrowTool(@Path("order_id") int order_id, @Path("penalty") String penalty);


    @GET("BorrowToolReview&{order_id}")
    Call<StatusMessage_Pojo> reviewBorrowTool(@Path("order_id") int order_id);


    @GET("UpdateRating&{psid}&{ptid}&{rating}&{ord_id}&{s_rat}&{s_com}")
    Call<StatusMessage_Pojo> updateToolRat(@Path("psid") int psid, @Path("ptid") int ptid, @Path("rating") String tool_rating,
                                           @Path("ord_id") int ord_id, @Path("s_rat") String s_rat, @Path("s_com") String s_com);


    @GET("AddFavoriteTools&{psid}&{ptid}&{lsid}")
    Call<StatusMessage_Pojo> addFavoriteTools(@Path("psid") int psid, @Path("ptid") int ptid, @Path("lsid") int lsid);


    @GET("FavoriteList&{lsid}")
    Call<StatusMessage_Pojo> favoriteTools(@Path("lsid") int lsid);


    @GET("RemoveFavorite&{psid}&{ptid}&{lsid}")
    Call<StatusMessage_Pojo> removeFavoriteTools(@Path("psid") int psid, @Path("ptid") int ptid, @Path("lsid") int lsid);


    @GET("ProfileInfo&{id}")
    Call<StatusMessage_Pojo> getProfileInfo(@Path("id") int id);


    @GET("UpdateProfile&{id}&{passwd}&{fname}&{lname}&{phone}&{addr}")
    Call<StatusMessage_Pojo> getUpdateProfile(@Path("id") int id, @Path("passwd") String pwd, @Path("fname") String fname,
                                              @Path("lname") String lname, @Path("phone") long phone, @Path("addr") String addr);


    @GET("MyMessages&{tsid}")
    Call<StatusMessage_Pojo> getMyMessages(@Path("tsid") int tsid);


    @GET("MessageDetails&{tsid}&{fsid}")
    Call<StatusMessage_Pojo> getMyMessageDetails(@Path("tsid") int tsid, @Path("fsid") int fsid);


    @GET("NewMessage&{fsid}&{tsid}&{message}")
    Call<StatusMessage_Pojo> getNewMessage(@Path("fsid") int fsid, @Path("tsid") int tsid, @Path("message") String message_text);


    @GET("CommentsList&{ptid}")
    Call<StatusMessage_Pojo> getComments(@Path("ptid") int ptid);

}
