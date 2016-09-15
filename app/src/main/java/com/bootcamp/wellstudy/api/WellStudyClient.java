package com.bootcamp.wellstudy.api;

import com.bootcamp.wellstudy.model.Faculty;
import com.bootcamp.wellstudy.model.Group;
import com.bootcamp.wellstudy.model.Student;
import com.bootcamp.wellstudy.model.StudentDto;
import com.bootcamp.wellstudy.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WellStudyClient {

    @GET ("admin/imageDisplay")
    Call <ResponseBody> downloadFileWithDynamicUrlSync(@Query("id") Integer id);

    @GET("api/students/")
    Call<List<Student>> students(
            @Query("page") Integer page,
            @Query("amount") Integer amount);

    @POST("api/students/")
    Call<Void> createStudent(@Body StudentDto student);

    @GET("api/students/getAmount")
    Call<Integer> getNumberOfStudents();

    @GET("api/login/")
    Call <User> login();


    @DELETE("api/students/{ssoid}")
    Call<Void> deleteStudent(@Path("ssoid") String ssoid);


    @GET("api/faculties/")
    Call<List<Faculty>> getfaculties();

    @GET("api/groups/findallbyfaculty/{id}")
    Call<List<Group>> getGroupsOfFaculty(@Path("id") Integer id);

    @GET("api/students/{ssoid}")
    Call <Student> getStudent(@Path("ssoid") String ssoId);

    @GET("api/students/searchbylastname/{lastname}")
    Call <List<Student>> searchByLastname(@Path("lastname") String lastName);

    @GET("api/faculties/{id}")
    Call <Faculty> getFaculty(@Path("id") Integer id);

    @GET("api/groups/{id}")
    Call <Group> getGroup(@Path("id") Integer id);


}
