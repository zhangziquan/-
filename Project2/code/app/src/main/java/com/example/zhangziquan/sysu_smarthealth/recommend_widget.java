package com.example.zhangziquan.sysu_smarthealth;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class recommend_widget extends AppWidgetProvider {

    private static final String WIDGETSTATICACTION = "com.example.zhangziquan.sysu_smarthealth.MyWidgetStaticFilter";

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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.recommend_widget);//实例化RemoteView,其对应相应的Widget布局
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateView.setOnClickPendingIntent(R.id.widget, pi); //设置点击事件
        ComponentName me = new ComponentName(context, recommend_widget.class);
        appWidgetManager.updateAppWidget(me, updateView);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent ){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        if(intent.getAction().equals(WIDGETSTATICACTION)){
            //设置显示收藏的食品信息
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recommend_widget);
            views.setImageViewResource(R.id.widget_image,R.drawable.empty_star);
            views.setTextViewText(R.id.appwidget_text,"今日推荐 "+ bundle.getString("name"));
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtras(bundle);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pi); //设置点击事件
            ComponentName me = new ComponentName(context, recommend_widget.class);
            appWidgetManager.updateAppWidget(me,views);
        }
    }
}

