package com.example.zhangziquan.personalproject5;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubService {
    @GET("/users/{user_name}/repos")
        // 这里的List<Repo>即为最终返回的类型，需要保持一致才可解析
        // 之所以使用一个List包裹是因为该接口返回的最外层是一个数组
    Observable<List<Repo>> getRepo(@Path("user_name") String user_name);

    @GET("/repos/{user_name}/{repo_name}/issues")
    Observable<List<Issue>> getIssue(@Path("user_name") String user_name, @Path("repo_name") String repo_name);

    @POST("/repos/{user_name}/{repo_name}/issues")
    Observable<Issue> postIssue(@Header("Authorization") String token,@Path("user_name") String user_name, @Path("repo_name") String repo_name, @Body RequestBody body);
}
