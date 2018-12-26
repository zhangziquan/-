# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016级 | 专业（方向） | 软件工程（计算机应用软件） |
| 学号 | 16340296 | 姓名 | 张子权 |
| 电话 | 13415401985 | Email | ziquanzhang@126.com |
| 开始日期 | 2018/10/13 | 完成日期 | 2018/10/14 |

---

## 一、实验题目

### Intent、Bundle的使用以及RecyclerView、ListView的应用

### 实验目的

   1. 复习事件的处理。  
   2. 学习Intent、Bundle在Activity跳转中的作用。
   3. 学习RecyclerView、ListView以及各类适配器的用法。
   4. 学习FloatingActionBar的用法。

---

## 二、实现内容

实现一个Android应用，界面呈现如图中的效果。  

本次实验模拟实现一个健康食品列表，有两个界面，第一个界面用于呈现食品列表 如下所示  
![img1](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img1.jpg)
数据在"manual/素材"目录下给出。  
点击右下方的悬浮按钮可以切换到收藏夹  
![img2](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img2.jpg)
上面两个列表点击任意一项后，可以看到详细的信息：  
![img3](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img3.jpg)

### UI要求  

* 食品列表  
      每一项为一个圆圈和一个名字，圆圈和名字都是垂直居中。圆圈内的内容是该食品的种类，内容要处于圆圈的中心，颜色为白色。食品名字为黑色，圆圈颜色自定义，只需能看见圆圈内的内容即可。
* 收藏夹  
      与食品列表相似
* 食品详情界面  
   1. 界面顶部  
   ![img4](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img4.jpg)  
   顶部占整个界面的1/3。每个食品详情的顶部颜色在数据中已给出。返回图标处于这块区域的左上角，食品名字处于左下角，星标处于右下角，边距可以自己设置。 **返回图标与名字左对齐，名字与星标底边对齐。** 建议用RelativeLayout实现，以熟悉RelativeLayout的使用。  
   2. 界面中部  
   ![img5](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img5.jpg)  
   使用的黑色argb编码值为#D5000000，稍微偏灰色的“富含”“蛋白质”的argb编码值为#8A000000。"更多资料"一栏上方有一条分割线，argb编码值为#1E000000。右边收藏符号的左边也有一条分割线，要求与收藏符号高度一致，垂直居中。字体大小自定。"更多资料"下方分割线高度自定。这部分所有的分割线argb编码值都是#1E000000。  
   3. 界面底部  
   ![img6](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img6.jpg)  
   使用的黑色argb编码值为#D5000000。  
* 标题栏  
      两个界面的标题栏都需要去掉

### 功能要求

* 使用RecyclerView实现食品列表。点击某个食品会跳转到该食品的详情界面，呈现该食品的详细信息。长按列表中某个食品会删除该食品，并弹出Toast，提示 **"删除XX"** 。
* 点击右下方的FloatingActionButton，从食品列表切换到收藏夹或从收藏夹切换到食品列表，并且该按钮的图片作出相应改变。
* 使用ListView实现收藏夹。点击收藏夹的某个食品会跳转到食品详情界面，呈现该食品的详细信息。长按收藏夹中的某个食品会弹出对话框询问是否移出该食品，点击确定则移除该食品，点击取消则对话框消失。如长按“鸡蛋”，对话框内容如下图所示。

![img7](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img7.jpg)

* 商品详情界面中点击返回图标会返回上一层。点击星标会切换状态，如果原本是空心星星，则会变成实心星星；原本是实心星星，则会变成空心星星。点击收藏图表则将该食品添加到收藏夹并弹出Toast提示 **"已收藏"** 。

---

## 三、课堂实验结果

### (1)实验截图

1. 开始界面，输入HealthFoodList进入
    ![StartActivity](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539774917.png?x-oss-process=style/Android)

2. RecycleView及其删除操作
    ![RecycleView](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539776125.png?x-oss-process=style/Android)&emsp;&emsp;![RecycleView删除](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539829580.png?x-oss-process=style/Android)

3. 详情页面
    ![详情页面1](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539776134.png?x-oss-process=style/Android)&emsp;&emsp;![详情页面2](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539829451.png?x-oss-process=style/Android)

4. 收藏页面和删除操作
    ![ListView](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539776162.png?x-oss-process=style/Android)&emsp;&emsp;![ListView删除](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539829635.png?x-oss-process=style/Android)&emsp;&emsp;![删除结束](https://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539829641.png?x-oss-process=style/Android)

### (2)实验步骤以及关键代码

* 实验步骤

    1. 实现一个Activity的页面跳转使得在输入"HealthFoodList"后，会跳转到新的窗口，并设计好新页面的布局即MainActivity.xml，放置ListView 和 RecycleView，均占满整个页面，并放置一个floatingActionButton在右下角。

    2. 首先先实现RecycleView的显示，需要使用一个Adapter适配器来填充数据，先实现viewHolder存储每一个item的子view。之后关键在于构造MyRecyclerViewAdapter，在里面实现创建一个视图，并返回viewholder，接着将数据集合绑定在view上，在适配器中定义一个list来存储数据。绑定数据的时候要实现一个convert转化函数，获取每一个数据的位置，以便于绑定到对应的子view中。

    3. 因为RecycleView和ListView不一样，没有OnItemClickListener的方法，因此要做点击子项的事件，要在Adapter中添加监听器，当某个子view被点击时，传递它的位置。接着就可以得知哪一项被点击了，接着就可以对那一项在List中进行操作，再更新一下就可以实现效果。

    4. 因为数据也是自己的自定义类，不是简单的数据类型，所以也要自定义ListView的Adapter来适配，重写几个方法，如getview，让每一项得到一个view，并返回去。要先判断某一项是否已经有了一个视图，若无则创建一个视图，加载布局，绑定数据，否则取出原有view的viewHolder更新数据，以此来减少布局的载入。

    5. 利用浮动按钮变换两个列表的显示以达到切换的效果，从而使得数据都在一个activity中，不用传来传去，方便收藏的进行。

    6. 设计详情页面，利用linerlayout结合layout_weight来确定顶部所占的百分比，再使用view来做分割线，4个操作选项用简单的listView完成。当转到详情页面时，传递一个Collection对象，将数据绑定到各个控件上。

    7. 完成收藏事件，当发生收藏动作时，返回上一界面将传回一个Collection对象，否则不传，这样能使得主界面的数据列表得到更新，另外重写了返回键，这样能够让自带的返回键和本身的返回具有一样的效果。

* 关键代码
    1. 为每一项创建viewHolder
        ```java
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = MyViewHolder.get(context, parent, layoutId);
            return holder;
        }
        ```
    2. 绑定数据到列表中去，实现项目显示
        ```java
        public void convert(MyViewHolder holder, Collection s) {
            TextView name = holder.getView(R.id.recipeName);
            name.setText(s.getName().toString());
            Button first = holder.getView(R.id.img);
            first.setText(s.getFirst().toString());
        }
        ```
    3. 从主页面跳转到详情页面时，传递对应的对象
        ```java
        Collection food = (Collection) myAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",food);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
        ```
    4. 从详情页面跳转到主页面时，接受传递的对象，并进行操作
        ```java
        Bundle bundle = data.getExtras();
        Collection recipe = (Collection) bundle.getSerializable("recipe");
        ······
        if (recipe.isCollected())
        {
            favorite_list.add(recipe);
        }
        ```
    5. ViewHolder管理各个列表的项的界面控件，实现绑定数据的操作
        ```java
        public <T extends View> T getView(int _viewId) {
            View _view = views.get(_viewId);
            if (_view == null) {
                // 创建view
                _view = view.findViewById(_viewId);
                // 将view存入views
                views.put(_viewId, _view);
            }
            return (T)_view;
        }
        ```

### (3)实验遇到的困难以及解决思路

* #### 遇到的困难
    1. 如何把数据信息存储起来，并且绑定在RecycleView和ListView的各个子View上。
    2. <font color = "red"> 给列表的每一项添加事件监听器，使得可以长按删除，点击进入详情界面。</font>
    3. 如何在页面切换时，将一个数据项从一个Activity传递到另一个Activity。
    4. 在详情页面结束时，将原本的数据项进行修改，然后传递回上一级界面。
    5. 保存每一项的星星，并且按名称的种类进行标识，当该种类被标识，旗下所有项均被标识。
* #### 解决思路
    1. 创建一个名为Colletion的类，将物品的名称，类型，背景色，成分等整合成一个类，提供各种方法来实现物品的读取。
        在RecycleViewAdapter中绑定viewholder时，读入一个类的对象，通过一个转换的函数，将封装的信息赋值到view中的控件里，从而绑定信息。
    2. 因为RecycleViewAdapter和ListView不一样，没有setOnItemClickListener()这个方法，所以我们要给每一项的子View设置监听器，当子View被点击时，调用我们自己的点击事件，并且传递该位置到点击事件中。
        ```java
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(holder.getAdapterPosition());
            }
        });
        ```
    3. 在自定义类时定义类可序列化，当要传递该对象时，将对象序列化用Bundle封装，使用Intent当页面切换的时候使用`startActivityForResult(intent,1);`即可传递参数。注意其中的1指的是requestCode，返回上一级时可使用这一参数判断Intent的来去。
    4. 由于在主界面中使用了`startActivityForResult`，`onActivityResult(requestCode, resultCode, data);`,因此只要在详情页面中使用finnish即可回到主界面并调用上一函数。在这时可以决定是否传递结果，即result，没有做修改时，可直接finish(),否则同3一样传递一个修改过的对象到主界面。
    5. 保留星星时，我考虑的是不仅仅是保留单一对象的标识，而且是同一名称的对象都应当被改变，因此当对一对象操作返回时，搜索所有在list中同名的对象，并对其进行修改。
        ```java
        for (Collection food : recipe_list){
            if (food.getName().equals(recipe.getName())){
                food.setIsFavor(recipe.getFavor());
            }
        }
        ```
* #### 项目改进
    1. 点击列表中的项目时，当点击到Button亦可触发点击事件，在设计item即每一项的界面是，设置其整个界面的layout的`descendantFocusability="blocksDescendants"`，使得当有点击事件时，viewgroup会覆盖子类控件而直接获得焦点，使得item首先获得焦点触发点击事件。
    2. 保存了详情页面的star的状态，具体操作在解决思路5。
    3. 当点击Android的返回键时也可进行收藏等操作。进行`onBackPressed()`重写，使得其事件和自定义的返回按钮的事件一致。
        ```java
        public void onBackPressed(){
            if (recipe.isCollected() || favorchanged){
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe",recipe);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(2,intent);
                finish();
            }
            else{
                finish();
            }
        }
        ```
---

## 四、实验思考及感想

* **实验思考**
    在这次实验中,我最大的体会就是AS列表的设计思路和MVVM的模式很相似，特别是各种数据绑定，其列表的实现将数据，界面，事件分离开来使得能够动态增加、删除，而且还能自定义界面。特别的是做点击事件时，当列表不提供方法时，通过对列表中的View绑定事件监听函数，达到点击效果，这种方法非常好，不过我在网上搜了一下，发现还有一种方法就是在onCreateViewHolder中进行绑定，因为创建ViewHolder的次数很少，能够减少这个操作，而如果在onBindViewHolder中进行的话，那么每次绑定数据都会调用一次，被调用多次。
* **实验感想**
    在这次实验中，虽然各种格式分离得很好，但是总感觉缺少了什么，就比如食物的这一数据作为一个局部变量就不是很方便，觉得应该设置一个数据的全局变量，然后弄成单例模式，使得能在各个activity都能够进行修改等等各种操作，而不必要在每个界面传来传去，还要考虑各种数据刷新，添加等等，就很方便进行数据更新。
    在无数次闪退中，终于在终端看到了被刷上去的错误提示，报错不会自动跳转到错误的地方这一点就很不爽。
    另外为了更好的输入数据，写了一个函数用于读取文本，发现放在app中的自己新建文件夹也能够在build时打包起来，这样就能读取到放进去的txt了。
* **NOTE**
    输入"HealthFoodList"进入界面