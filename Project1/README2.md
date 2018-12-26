# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016级 | 专业（方向） | 软件工程（计算机应用软件） |
| 学号 | 16340296 | 姓名 | 张子权 |
| 电话 | 13415401985 | Email | ziquanzhang@126.com |
| 开始日期 | 2018/10/8 | 完成日期 | 2018/10/8 |

---

## 一、实验题目

### 基础的事件处理

### 实验目的

   1. 了解Android编程基础。  
   2. 熟悉Button、RadioButton、EditText等基本控件，能够处理这些控件的基本事件。
   3. 学会弹出对话框，并定制对话框中的内容，能对确定和取消按钮的事件做处理。

---

## 二、实现内容

实现一个Android应用，界面呈现如图中的效果。  

![preview](/manual/images/preview.jpg)

### 要求  

* 该界面为应用启动后看到的第一个界面。  
* 各控件处理的要求
   1. 点击搜索按钮：
      * 如果搜索内容为空，弹出Toast信息“**搜索内容不能为空**”。
      * 如果搜索内容为“Health”，根据选中的RadioButton项弹出如下对话框。  

![success](/manual/images/success.jpg)  
点击“确定”，弹出Toast信息——**对话框“确定”按钮被点击**。  
点击“取消”，弹出Toast 信息——**对话框“取消”按钮被点击**。  
否则弹出如下对话框，对话框点击效果同上。  
![fail](/manual/images/fail.jpg)  
   2. RadioButton选择项切换：选择项切换之后，弹出Toast信息“**XX被选中**”，例如从图片切换到视频，弹出Toast信息“**视频被选中**”

---

## 三、课堂实验结果

### (1)实验截图

![内容为空](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833211.png?x-oss-process=style/Android)&emsp;&emsp;![选中选项](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833226.png?x-oss-process=style/Android)&emsp;&emsp;![搜索失败](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833232.png?x-oss-process=style/Android)
![点击确认](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833240.png?x-oss-process=style/Android)&emsp;&emsp;![搜索成功](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833260.png?x-oss-process=style/Android)&emsp;&emsp;![点击确认](http://ziquanzhang-image.oss-cn-shenzhen.aliyuncs.com/Android%20Project/Project1/Screenshot_1539833266.png?x-oss-process=style/Android)

### (2)实验步骤以及关键代码

* 实验步骤
    1. 给按钮添加一个点击事件，然后在事件中通过控件的id获得对应的控件，从而得到输入框输入的内容。
    2. 判断内容是否为空，若为空，则定义一个Toast内容为“搜索内容不能为空”，然后在应用上显示出来。
    3. 在判断内容是否为“Health”，若是，则new一个dialog对话框输出搜索成功，否则输出搜索失败。
    4. 在上一步的dialog对话框中添加正按钮和否按钮，点击后产生一个Toast输出正确/取消按钮被点击。
    5. 在创建页面时给RadioGroup添加一个监听事件，当选项发生改变时，找到对应的控件获得其text，然后输出Toast“xxx被选中”。
* 关键代码
    1. 获得控件及对应内容
        ```java
        EditText editText = (EditText)findViewById(R.id.editText);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        String content = editText.getText().toString();
        ```
    2. 创建对话框
        ```java
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("提示").setMessage(checkText+success).setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "对话框“确定”按钮被点击。", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "对话框“取消”按钮被点击。", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
        alertDialog.show();
        ```
    3. 添加对选项的监听事件
        ```java
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                String checkText = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(),
                        checkText+"被选中", Toast.LENGTH_SHORT).show();
            }
        });
        ```

### (3)实验遇到的困难以及解决思路

* 遇到的困难
    1. 不太熟悉如何得到目标控件。
    2. 判断字符串时使用了 == ，导致判断失败。
* 解决思路
    1. 查询到findViewById的方法从而根据id得到目标控件。
    2. 在java中要学会使用isempty()和equal()来进行字符串是否为空和是否相等的比较。

---

## 四、实验思考及感想

* 实验思考
    1. 在这次实验中，大致熟悉了Android-studio的基础编程，和UWP相类似，都是通过绑定监听器，或者是各种事件在控件上，从而对用户的操作进行响应，基本上了解了这一点之后的大部分问题都能通过查询其控件的各种方法等等来解决。
    2. 在写事件的时候，发现和UWP一样，Android也可以通过代码构造控件，从而产生动态的布局，这样方便我们对动态变化的应用进行设计，否则仅仅通过可视化布局只能作出静态的应用，无法进行动态变化。
* 实验感想
    1. 在做这个实验的参考了老师的pdf，里面详细的写了Toast和dialog的各种构造方法，所以非常轻松，而且各种方法的定义都很清晰明了，非常适合使用。
    2. 到了基础编程这里要懂得多去尝试控件的方法，监听器，从而得到一些方便解决问题的路径，否则若是自己造轮子则太过于麻烦，而且还不能保证没有BUG。