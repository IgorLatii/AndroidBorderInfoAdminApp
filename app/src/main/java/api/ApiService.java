package api;

import api.models.BorderInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users_adm/{name}")
    Call<ResponseBody> authenticateUser(
            @Path("name") String name,
            @Query("password") String password
    );

    @GET("border_info/{key_word}")
    Call<BorderInfo> getBorderInfoByKeyWord(@Path("key_word") String keyWord);

    @POST("border_info")
    Call<BorderInfo> createBorderInfo(@Body BorderInfo borderInfo);

    @PUT("border_info/{key_word}")
    Call<BorderInfo> updateBorderInfo(@Path("key_word") String keyWord, @Body BorderInfo borderInfo);

    @DELETE("border_info/{key_word}")
    Call<Void> deleteBorderInfo(@Path("key_word") String keyWord);
}


