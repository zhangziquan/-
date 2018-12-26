package com.example.zhangziquan.personalproject5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepoDetail extends AppCompatActivity {

    private String token = "token cb6205c160bd6fd967ae8d8c0aca07dcc8c74e8b";
    private String baseURL = "https://api.github.com/";
    private String repo_name;
    private String user_name;

    private EditText et_username;
    private RecyclerView rv_issues;
    private EditText et_token;
    private EditText et_title;
    private EditText et_body;
    MyRecyclerViewAdapter myAdapter;

    List<Issue> issueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repodetail);
        init();
        initIssues();
    }

    public void init(){
        issueList = new ArrayList<>();

        rv_issues = findViewById(R.id.rv_issues);
        et_token = findViewById(R.id.tv_token);
        et_title = findViewById(R.id.et_title);
        et_body = findViewById(R.id.et_body);

        et_token.setText("Token: " + token);

        myAdapter = new MyRecyclerViewAdapter<Issue>(RepoDetail.this,R.layout.item_issues,issueList) {

            @Override
            public void convert(MyViewHolder holder, Issue issue) {
                //绑定问题信息
                TextView tv_issue_title = holder.getView(R.id.tv_issue_title);
                tv_issue_title.setText(issue.getTitle());
                TextView tv_issue_create = holder.getView(R.id.tv_issue_create);
                tv_issue_create.setText("创建时间: " + issue.getCreated_at());
                TextView tv_issue_state = holder.getView(R.id.tv_issue_state);
                tv_issue_state.setText("问题状态: " + issue.getState());
                TextView tv_issue_body = holder.getView(R.id.tv_issue_body);
                tv_issue_body.setText("问题描述: " + issue.getBody());
            }
        };
        rv_issues.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_issues.setAdapter(myAdapter);
    }

    public void initIssues(){
        Intent intent = getIntent();
        Serializable repo = intent.getSerializableExtra("repo_name");
        Serializable name = intent.getSerializableExtra("user_name");
        if(repo != null && name != null){
            repo_name = (String) repo;
            user_name = (String) name;
            getIssues(user_name,repo_name);
        }
    }

    public void getIssues(String user_name,String repo_name){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();

        //构建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build)
                .build();

        //创建 网络请求接口 的实例
        GitHubService service = retrofit.create(GitHubService.class);

        //对请求进行封装
        Observable<List<Issue>> userCall = service.getIssue(user_name,repo_name);
        userCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Issue>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(),"查询结束",Toast.LENGTH_SHORT).show();
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Issue> issues) {
                        try{
                            issueList.clear();
                            if(issues.size() == 0){
                                Toast.makeText(getApplicationContext(),"该项目无Issue",Toast.LENGTH_SHORT).show();
                            }
                            for(int i =0;i<issues.size();i++){
                                issueList.add(issues.get(i));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                        catch (Exception e){
                            System.out.print(e);
                        }
                    }
                });
    }

    public void creatIssue(View view){
        String title = et_title.getText().toString();
        String body = et_body.getText().toString();
        Map<String,String> issue = new HashMap<>();
        issue.put("title", title);
        issue.put("body",body);

        Gson gson=new Gson();
        String strEntity = gson.toJson(issue);
        RequestBody issuebody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),strEntity);
        postIssue(user_name,repo_name,issuebody);
    }

    public void postIssue(final String user_name, final String repo_name, RequestBody issueBody){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();

        //构建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build)
                .build();
        //创建 网络请求接口 的实例
        GitHubService service = retrofit.create(GitHubService.class);


        //对请求进行封装
        Observable<Issue> userCall = service.postIssue(token,user_name,repo_name,issueBody);
        userCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Issue>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(),"创建完成",Toast.LENGTH_SHORT).show();
                        getIssues(user_name,repo_name);
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Issue issues) {
                        try{
                            if (issues!=null){
                                Toast.makeText(getApplicationContext(),"创建完成",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
