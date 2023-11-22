package com.example.PreferenceTest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * AsyncTack泛型的三个参数
 * 1. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
 * 2. Progress：异步任务执行过程中，返回下载进度值的类型
 * 3. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致
 * 注：
 * 1. 使用时并不是所有类型都被使用
 * 2. 若无被使用，可用java.lang.Void类型代替
 * 3. 若有不同业务，需额外再写1个AsyncTask的子类
 */
public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    private TextView textView;
    private ProgressBar progressBar;

    public MyAsyncTask(TextView textView, ProgressBar progressBar) {
        super();
        this.textView = textView;
        this.progressBar = progressBar;
    }

    //最先执行的方法
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText("加载完成!");
    }

    /**
     * 在doInBackground方法中每次调用publishProgress方法都会触发此方法
     * 此方法运行在ui线程中，可操作ui控件
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(final Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
        //setText的参数是string，不然报错
        textView.setText("loading..." + values[0] + "%");
    }

    //后台执行的方法
    @Override
    protected String doInBackground(Integer... integers) {
        int i = 0;
        for (i = 10; i <= 100; i += 10) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        Log.d("xuejie", String.valueOf(integers[0]));
        return i + integers[0].intValue() + "";
    }
}
