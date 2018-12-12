# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016级 | 专业（方向） | 软件工程（计算机应用软件） |
| 学号 | 16340296 | 姓名 | 张子权 |
| 电话 | 13415401985 | Email | ziquanzhang@126.com |
| 开始日期 | 2018/10/20 | 完成日期 | 2018/10/27 |

---

## 一、实验题目

### Broadcast 使用，AppWidget 使用

### 实验目的

**第七周**

   1. 掌握 Broadcast 编程基础。  
   2. 掌握动态注册 Broadcast 和静态注册 Broadcast。
   3. 掌握Notification 编程基础。
   4. 掌握 EventBus 编程基础。

**第八周**

   1. 复习 Broadcast 编程基础。  
   2. 复习动态注册 Broadcast 和静态注册 Broadcast 。
   3. 掌握 AppWidget 编程基础。

---

## 二、实现内容

**第七周**

在第六周任务的基础上，实现静态广播、动态广播两种改变Notification 内容的方法。  

本次实验模拟实现一个健康食品列表，有两个界面，第一个界面用于呈现食品列表 如下所示  
![img1](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img1.jpg)
数据在"manual/素材"目录下给出。  
点击右下方的悬浮按钮可以切换到收藏夹  
![img2](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img2.jpg)
上面两个列表点击任意一项后，可以看到详细的信息：  
![img3](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img3.jpg)

### 要求  

* 在启动应用时，会有通知产生，随机推荐一个食品。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_static_notification.jpg)
* 点击通知跳转到所推荐食品的详情界面。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_static_jump.jpg)
* 点击收藏图标，会有对应通知产生，并通过Eventbus在收藏列表更新数据。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_requirement3.jpg)
* 点击通知返回收藏列表。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_requirement4.jpg)
* 实现方式要求:启动页面的通知由静态广播产生，点击收藏图标的通知由动态广播产生。

**第八周**

在第七周任务的基础上，实现静态广播、动态广播两种改变widget内容的方法。  

#### 要求

* widget初始情况如下：
    ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week8_begin.PNG) 
* 点击widget可以启动应用，并在widget随机推荐一个食品。
    ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week8_recommendation.PNG)
* 点击widget跳转到所推荐食品的详情界面。
    ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week8_jump.PNG) 
* 点击收藏图标，widget相应更新。
    ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week8_update.PNG)
* 点击widget跳转到收藏列表。
    ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week8_collection.PNG)
* 实现方式要求:启动时的widget更新通过静态广播实现，点击收藏图标时的widget更新通过动态广播实现。

---

## 三、课堂实验结果

### (1)实验截图

**第七周**

1. 启动应用时，产生通知，随机推荐一个食品。  
    ![静态广播通知](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week7_static_notification.png?x-oss-process=style/Android)

2. 点击通知信息，可以跳转到相应的详情界面。  
    ![静态通知跳转](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week7_static_jump.png?x-oss-process=style/Android)

3. 点击收藏，产生已收藏通知。  
    ![动态广播通知](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week7_dynamic_notification.png?x-oss-process=style/Android)

4. 点击已收藏信息，跳转到收藏列表  
    ![动态通知跳转](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week7_dynamic_jump.png?x-oss-process=style/Android)

**第八周**

1. 启动应用时，添加一个widget，显示目前没有任何信息  
    ![widget](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week8_widget.png?x-oss-process=style/Android)

2. 点击widget，启动应用，后显示今日推荐的食品。  
    ![week8_static_action](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week8_static_action.png?x-oss-process=style/Android)

3. 再次点击widget，跳转到推荐食品的详情页面中去。  
    ![week8_static_jump](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week8_static_jump.png?x-oss-process=style/Android)

4. 点击收藏按钮，之后返回桌面，可以看到widget显示已收藏的信息，且图标变为实星。  
    ![week8_dynamic_action](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week8_dynamic_action.png?x-oss-process=style/Android)

5. 再次点击widget，跳转到应用的收藏夹中，发现被收藏的十字花科蔬菜。  
    ![week8_dynamic_jump](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project2/week8_dynamic_jump.png?x-oss-process=style/Android)

### (2)实验步骤以及关键代码

**第七周**

* 实验步骤

    1. 首先实现静态广播，在AndroidMainfest.xml注册静态广播，设置一个静态广播类重写onReceive，即收到广播时，作出相应的通知，因为启动APP即发出广播，则在MainActivity中的create中发送静态广播，使用随机数随机列表中的物品，利用intent发送内容。

    2. 在静态广播内使用通知，提取bundle中的物品信息，且将接收到的bundle再用intent封装，使用pendingIntent用作点击通知后的跳转。构造notification然后通过通知管理显示出来。

    3. 因为要进行数据更新，且在点击收藏后不跳转界面，则要用EventBus来进行数据更新，在详情页面中声明一个事件类，里面封装一个食品，编写方法获得食品信息。在MainActivity中声明订阅方法，当事件传递后，可进行相应的操作，如切换到收藏界面，添加一个收藏的食品。随后在onCreate中注册订阅，重写ondestroy，当销毁时注销订阅。当在详情页面时点击收藏按钮传递事件。

    4. 实现动态广播，同样在收到广播后，发出通知并可跳转。类似于静态广播，只是在使用时，要动态地注册和注销。

* 关键代码
    1. 构造静态广播
        ```java
            Intent perIntent = new Intent(context,DetailActivity.class);
            perIntent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,perIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);
            //对Builder进行配置，此处仅选取了几个
            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
                    .setContentText(bundle.getString("name"))   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON（通知栏），可以用以前的素材，例如空星
                    .setFullScreenIntent(pendingIntent,true)
                    .setContentIntent(pendingIntent);   //传递内容

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        ```
    2. 编写事件类
        ```java
        public static class MessageEvent {
            private Collection recipe;
            public MessageEvent(Collection recipe){
                this.recipe = recipe;
            }

            public Collection getMessage(){
                return recipe;
            }

            public void setMessage(Collection recipe) {
                this.recipe = recipe;
            }
        }
        ```
    3. 编写订阅事件
        ```java
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(DetailActivity.MessageEvent event) {
            /* Do something */
            //调整为收藏界面
            RecyclerView food_list = (RecyclerView) findViewById(R.id.food_list);
            ListView favorites = (ListView) findViewById(R.id.favorites);
            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
            floatingActionButton.setImageResource(R.drawable.mainpage);
            food_list.setVisibility(View.INVISIBLE);
            favorites.setVisibility(View.VISIBLE);
            tag = true;

            //更新ListView列表
            MyListViewAdapter myListViewAdapter =(MyListViewAdapter) favorites.getAdapter();
            favorite_list.add(event.getMessage());
            myListViewAdapter.refresh(favorite_list);
        };
        ```
    4. 注册动态广播
        ```java
        private static final String DYNAMICACTION = "com.example.zhangziquan.sysu_smarthealth.MyDynamicFilter";
        private DynamicReceiver dynamicReceiver;

        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);    //注册自定义动态广播消息
        ```

**第八周**

* 实验步骤

    1. 和上一周一样，先添加widget页面，设计页面的xml，添加图片控件，显示图标和文字。在java文件中重写onUpdate，使得当添加这个widget小部件时，为其增加一个点击事件，使得点击小部件的时候可以启动应用。

    2. 使用静态广播来实现今日推荐的显示，在widget中注册广播，重写onReceive方法，利用RemoteView来更新widget。在应用中的reOnStart周期中发送广播，这就能够使得每次打开应用时在widget中显示今日推荐。

    3. 使用动态广播实现点击widget已收藏消息，进入收藏夹。

* 关键代码
    1. 在onRestart中发送静态广播。
        ```java
        Intent widgetIntentBroadcast = new Intent();
        widgetIntentBroadcast.setAction(WIDGETSTATICACTION);

        int index = random.nextInt(recipe_list.size());
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",recipe_list.get(index));
        bundle.putString("name",recipe_list.get(index).getName());

        widgetIntentBroadcast.putExtras(bundle);
        sendBroadcast(widgetIntentBroadcast);
        ```
    2. 动态广播接收器接收到广播后，根据对应的action作出反应。添加点击事件，更改widget图标，文本信息等等。
        ```java
        if (intent.getAction().equals(WIDGETDYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            //设置显示收藏的食品信息
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recommend_widget);
            views.setImageViewResource(R.id.widget_image,R.drawable.full_star);
            views.setTextViewText(R.id.appwidget_text,"已收藏 "+ bundle.getString("name"));
            Intent i = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pi); //设置点击事件
            ComponentName me = new ComponentName(context, recommend_widget.class);
            appWidgetManager.updateAppWidget(me,views);
        }
        ```
    3. 初始化widget。在添加widget时调用onUpdate再调用updateAppWidget，改变其样式。
        ```java
        static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                    int appWidgetId) {

            CharSequence widgetText = context.getString(R.string.appwidget_text);
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recommend_widget);
            views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setImageViewResource(R.id.widget_image,R.drawable.empty_star);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        ```

### (3)实验遇到的困难以及解决思路

**第七周**

* #### 遇到的困难
    1. 8.0以上的Android系统发送通知中不适用以上的代码，因此要进行修改才能在8.0以上的系统发送通知。
    2. pendingIntent其特性使得在另一个task中进行，结果主程序结束了，但是今日推荐的详情界面仍在。
* #### 解决思路
    1. 8.0后要指定对应信道才可发送通知，可以给予静态广播一个信道，这样就可以愉快地发送通知了。
        ```java
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID,PUSH_CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        ```
    2. 可以在MainActivity中的启动模式更为singleTask可以解决多个task问题，这样使得pendingIntent在同一个task中，再结束主程序的时候，就不会弹出一个详情页面了。若使用其中还有一个问题就是在退出主程序时，并未清除掉所有activity，下一次再进入今日推荐的时候就会重新使用上一次的。
        ```java
        perIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        ```
        这样可以使得当新建一个activity时，将旧的清除掉放入新的。

**第八周**

* #### 遇到的困难及解决思路
    1. 在实机上运行发现安卓5.0的手机会自动点击通知栏的通知，后来在设置中调成悬浮框显示解决了问题。
    2. 在实机上测试失败，搞了很久才发现魅族的轻触返回其实是退出程序，按下才是返回桌面，这时候一个是程序退出，一个是程序还在后台运行，是有很大区别的。如果是退出程序再点击widget，这时会创建一个新的activity，而不是跳转到对应的界面。其余的都和上一周的差不多，都是静态广播和动态广播。

---

## 四、实验思考及感想

* **实验思考**
    在本次的实验实验中学习了如何使用消息订阅模式，使得每一个界面能够方便地传输数据，而不必使用intent来传输，十分的方便友好。学习通知栏如何进行弹出了跳转，以及设置悬浮的操作。最主要的还是广播的应用，使用广播能够让两个activity进行操作，进行类似于异步的操作，让交互性变得更加强。此外还学习了widget这一类有助于应用显示的小部件，有助于我们将应用中一些关键信息显示在桌面上，便于用户操作。

* **实验感想**
    在项目二中我对一个activity的生命周期的理解更加多了，因为我们很多的操作都是在某个生命周期后实现的，有各种各样状态，特别是在跳转等方面，使用返回键和直接intent跳转又是不一样的。其中的消息订阅模式和很多应用开发很相似，都是这种通过订阅发布消息的。另外在做这次作业的时候查询了不少东西，学习了很多关于widget，eventBus，以及notification的用法，特别是了解了8.0以上的版本如何发送通知。
