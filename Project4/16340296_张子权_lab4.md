# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016级 | 专业（方向） | 软件工程（计算机应用软件） |
| 学号 | 16340296 | 姓名 | 张子权 |
| 电话 | 13415401985 | Email | ziquanzhang@126.com |
| 开始日期 | 2018/11/24 | 完成日期 | 2018/11/25 |

---

## 一、实验题目

### 简单音乐播放器

### 实验目的

1. 学会使用MediaPlayer
2. 学会简单的多线程编程，使用Handler更新UI
3. 学会使用Service进行后台工作
4. 学会使用Service与Activity进行通信
5. 学习rxJava，使用rxJava更新UI

---

## 二、实现内容

实现一个简单的播放器，要求功能有：  
<table>
    <tr>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig1.jpg" >打开程序主页面</td>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig2.jpg" >开始播放</td>
    </tr>
    <tr>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig3.jpg" >暂停</td>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig1.jpg" >停止</td>
    </tr>
</table>

1. 播放、暂停、停止、退出功能，按停止键会重置封面转角，进度条和播放按钮；按退出键将停止播放并退出程序
2. 后台播放功能，按手机的返回键和home键都不会停止播放，而是转入后台进行播放
3. 进度条显示播放进度、拖动进度条改变进度功能
4. 播放时图片旋转，显示当前播放时间功能，圆形图片的实现使用的是一个开源控件CircleImageView

**附加内容（加分项，加分项每项占10分）**

1.选歌

用户可以点击选歌按钮自己选择歌曲进行播放，要求换歌后不仅能正常实现上述的全部功能，还要求选歌成功后不自动播放，重置播放按钮，重置进度条，重置歌曲封面转动角度，最重要的一点：需要解析mp3文件，并更新封面图片。

---

## 三、课堂实验结果

### (1)实验截图

**第十周**

1. 打开应用，首页为一个音乐播放器，显示专辑页面，歌曲名，艺术家，可从本地资源打开曲目，可控制播放暂停，停止，退出。
   ![首页](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_prepare.png?x-oss-process=style/Android)
2. 播放曲目，专辑封面旋转，进度条移动，显示当前播放的进度时间，以及结束时间。  
   ![播放](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_play.png?x-oss-process=style/Android)
3. 打开音乐文件曲目进行播放。  
   ![音乐1](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_openFile.png?x-oss-process=style/Android)  ![音乐2](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_openFile2.png?x-oss-process=style/Android) ![音乐3](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_openFile3.png?x-oss-process=style/Android)
4. 当曲目播放完毕或者手动点击停止按钮，封面停止，进度条拉回初始状态，播放按钮变为play。  
   ![登陆失败](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_complete.png?x-oss-process=style/Android) ![登陆成功](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_player_complete2.png?x-oss-process=style/Android)

### (2)实验步骤以及关键代码

* 实验步骤
    1. 设计音乐播放器界面，添加四个按钮用于控制播放器打开音乐文件，播放暂停，停止以及退出，加入新控件CircleViewImage，用于显示封面，插入seekbar进度条显示音乐的播放进度以及textview显示所剩时间，以及音乐的各种信息。
    **关键代码：**
        ![设计页面](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project4/week12_design.PNG)
    2. 构造Service，在里面定义mediaplayer，实现功能，如音乐初始化，播放暂停等功能。在主界面开始时绑定Service并进行调用，从而实现服务后台运行，退出进程时取消绑定，从而销毁Service。
    **关键代码：**
        ```java
        public void onServiceConnected(ComponentName name, IBinder service) {
            ms = ((MusicService.MyBinder)service).getService();
        }
        ···
        if(!isBinded){
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, sc, BIND_AUTO_CREATE);
            isBinded = true;
        }
        ···
        public boolean onUnbind(Intent intent) {
            return super.onUnbind(intent);
        }

        public class MyBinder extends Binder {
            MusicService getService() {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource("/sdcard/Download/山高水长.mp3");
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return MusicService.this;
            }
        }
        ```
    3. 使用Handle接收子线程的数据，用来更新UI的界面，但是我们只用到了runnable，执行其中的run方法，run操作和主线程还是处在同一个线程中。使用run方法更新进度条，播放时间。
    **关键代码：**
        ```java
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String cp = time.format(ms.getCurrentPosition());
                String dr = time.format(ms.getDuration());
                tv_cp.setText(cp);
                tv_dr.setText(dr);

                sb_playbar.setProgress(ms.getCurrentPosition());
                sb_playbar.setMax(ms.getDuration());

                mHandler.postDelayed(mRunnable,200);
            }
        };
        mHandler.postDelayed(mRunnable,200);
        ```
    4. 设置监听器监听seekbar的滑动变化，从而改变音乐播放器的进度，注意的是要判断是否是人为改变的进度条，否则会和run中的更新冲突导致不断发生事件执行。
    **关键代码：**
        ```java
        sb_playbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    ms.seekTo((progress));
                }
            }
        ```
    5. 使用手机自带的资源管理器选歌,主要方式是找到文件的绝对路径，然后交给mediaplayer处理。
    **关键代码：**
        ```java
        public void readfile(View view){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
        ···
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            }
        ···
        ```
    6. 使专辑封面旋转，调用CircleViewImage的旋转方法，并运用在runnable中，每隔0.2s调用一次，角度+1，从而使得封面可以进行旋转。
    **关键代码：**
        ```java
        Imagerun = new Runnable() {
            @Override
            public void run() {
                civ_mc.setRotation(civ_mc.getRotation()+(float)0.2);
                mHandler.postDelayed(Imagerun,10);
            }
        };
        ```
    7. 解析MP3文件中的歌曲信息,通过MediaMetadataRetriever来进行解析，展示到界面上。
    **关键代码：**
        ```java
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        tv_title.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        tv_artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        byte[] imagedata = mmr.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
        civ_mc.setImageBitmap(bitmap);
        mmr.release();
        ```
    8. Rxjava代码：
    **关键代码：**
        ```java
        rx.Observable observable = rx.Observable.create(new rx.Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                Subscriber subscriber = (Subscriber)o;
                while(true){
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    try {
                        ms.transact(POS_CODE,data,reply,0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    int[] pos = new int[2];
                    reply.readIntArray(pos);
                    subscriber.onNext(pos);
                    if(reply.readInt() == 1){
                        isPlaying = false;
                        isStoping = true;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                int[] pos = (int[]) o;
                String cp = time.format(pos[0]);
                String dr = time.format(pos[1]);
                tv_cp.setText(cp);
                tv_dr.setText(dr);
                sb_playbar.setProgress(pos[0]);
                sb_playbar.setMax(pos[1]);
            }
        });
        ```

### (3)实验遇到的困难以及解决思路

* #### 遇到的困难以及解决思路
    1. 绑定service并不是很明白是什么意思，service的构造过程也并不是很了解。
    解决思路：在网上查询之后了解到Service其实是一种无UI的能够在后台保持运行的应用组件，所以将mediaplayer放在Service中，利用Service进行调用从而播放音乐，这样当退出程序界面时，Service能够存在继续播放。知道了这一点之后，就能完成这个后台播放的任务了，了解到Service有两种启动方式，因为我们要控制保持通信，所以采用绑定的方式，通过IBinder获得Service对象。当程序结束时要取消绑定，使Service自动销毁。

    2. 点击back按钮再回来发现程序重新构造界面，取不回原来的Service。
    解决思路：因为在back的时候activity已经finish，之后就destory了，在这时因为没写unbind所以失去了控制，debug会出警告提示。所以我选择重写了系统back返回键，让他转入后台而不是finish，这样从后台回来还能得到service，进行操作。

    3. 使用Rxjava进行UI的更新，理解困难。
    解决思路：反复查看观察者模式之后，发现原来其实这个和Runnable没什么区别，都是形成一个多线程来执行耗时操作。只是Rxjava更加清晰简洁，在处理一些比较复杂的情况容易进行处理，而runnable虽然容易理解，但是做完之后不容易复查。

* #### 加分项实现方法
    1. 选歌使用了获取文件路径的方法，使用资源管理器打开文件，然后得到文件的路径，mediaPlayer获得资源就可以直接播放了。获取MP3的文件信息则是通过MediaMetadataRetriever来进行解析。

---

## 四、实验思考及感想

* **实验思考**

1. 在第二次的作业中总算是真正使用了Ibinder这种跨进程之间交互调用的方法，这样能够安全地进行远程对象的调用。假如直接返回服务的实例，那么就没有用到IBinder这种模式，而且很容易会导致对远程对象的修改，使得远程对象不可用。

* **实验感想**

本次的实验让我收获很多，首先是Service服务的编写让我了解到了这些安卓应用是如何保持后台的运行的，以及为什么有时候退出了应用仍然存在。在完成作业中我还了解到还能写一些不死的服务，即使退出了后台也不会停止，同时Service还能够重新启动这个应用。这样就能让一些频繁使用的应用可以被快速地打开，例如微信等等常驻后台。另外多线程的操作也收获了很多，就例如很多情况都能用到多线程，例如从网路加载图片，避免加载被阻塞。以及下载文件能转到后台持续下载等等。最近做的计算机网络FTP也是用到了多线程服务多个客户端同时访问，以及建立缓存区读写等等，有能利用到多线程。关于Rxjava和handle的选择来说，的确Rxjava比较简洁，提供了订阅模式更加容易地去理解。