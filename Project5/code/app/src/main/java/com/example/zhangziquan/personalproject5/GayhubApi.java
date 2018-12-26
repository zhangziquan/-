package com.example.zhangziquan.personalproject5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GayhubApi extends AppCompatActivity {

    private String baseURL = "https://api.github.com/";
    private String user_name;

    private EditText et_username;
    private RecyclerView rv_repos;
    MyRecyclerViewAdapter myAdapter;

    List<Repo>repoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gayhub);
        init();
    }

    public void init(){
        repoList = new ArrayList<>();

        et_username = findViewById(R.id.et_user_name);
        rv_repos = findViewById(R.id.rv_repos);

        myAdapter = new MyRecyclerViewAdapter<Repo>(GayhubApi.this,R.layout.item_repos,repoList) {

            @Override
            public void convert(MyViewHolder holder, Repo repo) {
                //绑定仓库信息
                TextView tv_repo_name = holder.getView(R.id.tv_repo_name);
                tv_repo_name.setText(repo.getName());
                TextView tv_repo_id = holder.getView(R.id.tv_repo_id);
                tv_repo_id.setText("项目id: " + Integer.toString(repo.getId()));
                TextView tv_repo_issuenum = holder.getView(R.id.tv_repo_issuesnum);
                tv_repo_issuenum.setText("存在问题: " + Integer.toString(repo.getOpen_issues()));
                TextView tv_repo_desc = holder.getView(R.id.tv_repo_desc);
                tv_repo_desc.setText("项目描述: " + repo.getDescription());
            }
        };

        myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onClick(int position) {
                Repo repo = (Repo) myAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_name", user_name);
                bundle.putSerializable("repo_name", repo.getName());
                Intent intent = new Intent(GayhubApi.this, RepoDetail.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(int position) {
                Repo repo = (Repo) myAdapter.getItem(position);
                Toast.makeText(getApplicationContext(),"选中了"+ repo.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        rv_repos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_repos.setAdapter(myAdapter);
    }

    public void search_repos(View view){
        user_name = et_username.getText().toString();
        getRepos(user_name);
    }

    public void getRepos(String user_name){
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
        Observable<List<Repo>> userCall = service.getRepo(user_name);

        userCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repo>>() {
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
                    public void onNext(List<Repo> repos) {
                        try{
                            repoList.clear();
                            if(repos.size() == 0){
                                Toast.makeText(getApplicationContext(),"该用户没有仓库",Toast.LENGTH_SHORT).show();
                            }
                            for(int i =0;i<repos.size();i++){
                                if(repos.get(i).getHas_issues()){
                                    repoList.add(repos.get(i));
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                        catch (Exception e){
                            System.out.print(e);
                        }
                    }
                });
    }
}
