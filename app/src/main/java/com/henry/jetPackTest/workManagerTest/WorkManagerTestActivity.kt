package com.henry.jetPackTest.workManagerTest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.henry.basic.R

/**
 *
 * @author: henry.xue
 * @date: 2024-03-27
 */
class WorkManagerTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityworkmanager);
    }

    //    fun test1(view: View) {
//        //OneTimeWorkRequest  单个  一次的
//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MainWorker1::class.java).build()
//        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
//    }
//
    fun test2(view: View) {
        val oneTimeWorkRequest: OneTimeWorkRequest

        //数据
        val sendData = Data.Builder().putString("Henry", "发送过去的数据").build()

        //请求对象初始化
        oneTimeWorkRequest = OneTimeWorkRequest.Builder(MainWorker1::class.java)
            .setInputData(sendData)//数据的携带 发送一般都是携带到Request里面去 发送给Workmanager
            .build()

        //一般都是通过状态机接受WorkManager回馈的数据
        //状态机(LiveData) 才能接收WorkManager回馈的数据
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this,
            { WorkInfo ->
                //ENQUENUD,RUNNING,SUCCEED
                Log.d(MainWorker1.TAG, "状态: " + WorkInfo.state.name)
                //ENQUENUD,RUNNING都取不到回馈的数据   都是null


                if (WorkInfo.state.isFinished) {  //判断成功状态
                    Log.d(
                        MainWorker1.TAG,
                        "接受WorkManager回馈的数据: " + WorkInfo.outputData.getString("Henry")
                    )
                }
            })
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

    }
//
//    fun test3(view: View) {
//
//
//    }
//
//    fun test4(view: View) {
//
//
//    }
//
//    fun test5(view: View) {
//
//
//    }
//
//    fun test6(view: View) {
//
//
//    }


}