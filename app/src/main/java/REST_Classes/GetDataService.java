package REST_Classes;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetDataService {

    @FormUrlEncoded
    @POST("lognow.php")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("getCoreCourses.php")
    Call<List<CoreCourse>> getCoreCourses(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("registerCoreCourses.php")
    Call<ResponseBody> registerCoreCourses(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getOptionalCourses.php")
    Call<List<CoreCourse>> getOptionalCourses(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("registerOptionalCourses.php")
    Call<ResponseBody> registerOptionalCourses(@Field("user_id") String user_id, @Field("course") String course, @Field("degree_status") int degree_status);

    @FormUrlEncoded
    @POST("getNotices.php")
    Call<List<Notice>> getNotices(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("checkCallCombination.php")
    Call<ResponseBody> checkCallCombination(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("checkCourseRegistration.php")
    Call<ResponseBody> checkCourseRegistration(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("checkExamRegistration.php")
    Call<ResponseBody> checkExamRegistration(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getStream.php")
    Call<ResponseBody> getStream(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getCombinations.php")
    Call<Combinations> getCombinations(@Field("user_stream") String user_stream);

    @FormUrlEncoded
    @POST("getSubjects.php")
    Call<Subjects> getSubjects(@Field("stream_id") String stream_id);

    @FormUrlEncoded
    @POST("registerCombinations.php")
    Call<ResponseBody> registerCombinations(@Field("stu_id") String stream_id, @Field("ch1") String choice1, @Field("ch2") String choice2);

    @FormUrlEncoded
    @POST("getExamCourses.php")
    Call<List<ExamCourses>> getExamCourses(@Field("user_id") String user_id);
}
