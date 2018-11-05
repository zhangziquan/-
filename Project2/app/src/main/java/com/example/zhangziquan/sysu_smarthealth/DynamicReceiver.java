package com.example.zhangziquan.sysu_smarthealth;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.example.zhangziquan.sysu_smarthealth.MyDynamicFilter";
    private static final String WIDGETDYNAMICACTION = "com.example.zhangziquan.sysu_smarthealth.WIDGETDYNAMICACTION";
    private static int num = 1;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分

            Intent perIntent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,perIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);
            //对Builder进行配置，此处仅选取了几个
            builder.setContentTitle("已收藏")   //设置通知栏标题：发件人
                    .setContentText(bundle.getString("name"))   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON（通知栏），可以用以前的素材，例如空星
                    .setFullScreenIntent(pendingIntent,true)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);   //传递内容

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(num++,notify);
        }

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
    }
}