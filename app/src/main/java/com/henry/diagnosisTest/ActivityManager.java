package com.henry.diagnosisTest;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

public final class ActivityManager implements Application.ActivityLifecycleCallbacks {
    /**
     * Activity栈
     */
    private final Stack<Activity> activities = new Stack<>();
    /**
     * 单例
     */
    private static volatile ActivityManager activityManager;

    private ActivityManager() {
    }

    /**
     * 单例
     *
     * @return activityManager instance
     */
    public static ActivityManager getInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                if (activityManager == null) {
                    activityManager = new ActivityManager();
                }
            }
        }

        return activityManager;
    }

    /**
     * 获取Activity任务栈
     *
     * @return activity stack
     */
    public Stack<Activity> getActivityStack() {
        return activities;
    }

    /**
     * Activity 入栈
     *
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Activity出栈
     *
     * @param activity Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束某Activity
     *
     * @param activity Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 关闭所有的activity
     */
    public void recreateAllActivity() {
        for (Activity activity : activities) {
            activity.recreate();
        }
    }

    /**
     * 获取当前Activity
     *
     * @return current activity
     */
    public Activity getCurrentActivity() {
        return activities.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        finishActivity(activities.lastElement());
    }

    /**
     * 关闭最后一个
     */
    public void finishLastActivity() {
        if (activities.size() >= 1) {
            Activity remove = activities.remove(activities.size() - 1);
            finishActivity(remove);
        }
    }

    /**
     * 关闭所有的activity
     */
    public void finishAllActivity() {
        while (activities.size() > 0) {
            Activity pop = activities.pop();
            finishActivity(pop);
        }
    }

    /**
     * 关闭指定数量的页面
     *
     * @param count 需要关闭页面的数量
     */
    public void finishSpecificNumberActivity(int count) {
        for (int i = 0; i < count; i++) {
            finishLastActivity();
        }
    }

    /**
     * 重新登录
     */
    public void loginAgain() {
//        Activity lastActivity = activities.get(activities.size() - 1);
//        Intent intent = new Intent(lastActivity, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        lastActivity.startActivity(intent);
//        finishActivity(lastActivity);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }
}
