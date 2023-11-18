package tdtu.edu.lab8;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @GET("get-students.php")
    Call<ApiResponse> getStudents();
    @FormUrlEncoded
    @POST("update-student.php")
    Call<ApiResponse> updateStudent(@Field("id") int id, @Field("name") String name, @Field("email") String email, @Field("phone") String phone);


    @FormUrlEncoded
    @POST("add-student.php")
    Call<ApiResponse> addStudent(@Field("name") String name, @Field("email") String email, @Field("phone") String phone);


    @FormUrlEncoded
    @POST("delete-student.php")
    Call<ApiResponse> deleteStudent(@Field("id") int id);

}
