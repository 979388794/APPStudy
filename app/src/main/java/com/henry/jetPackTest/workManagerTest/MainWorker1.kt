package com.henry.jetPackTest.workManagerTest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *
 * @author: henry.xue
 * @date: 2024-03-27
 */
class MainWorker1(context: Context, val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    companion object {
        const val TAG = "Henry"
    }

    //后台任务  并且是异步的   (原理： 线程池执行Runnable)
    /*  override fun doWork(): Result {

          Log.d(TAG, "MainWorker1 doWork :run started ...")
          try {
              Thread.sleep(8000)//睡眠8s
          } catch (e: Exception) {
              e.printStackTrace()
              Result.failure() //本次任务失败
          } finally {
              Log.d(TAG, "MainWorker1 doWork :run ended ...")
          }
          return Result.success()  //本次任务成功
      }*/
    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {

        Log.d(TAG, "MainWorker1 doWork :run started ...")
        val datastring = workerParameters.inputData.getString("Henry")
        Log.d(TAG, "MainWorker1 doWork :接受MainActivity传递过来的数据：$datastring")

        //反馈数据到MainActivity中去
        //把任务中的数据回传到MainActivity
        val outputData = Data.Builder().putString("Henry", "接收者发送过去的数据").build()

//        return Result.Failure()    //本地执行doWork任务时失败
//        return Result.Retry()      //本地执行doWork任务时重试一次
//        return Result.Success()    //本地执行doWork任务时成功执行完毕
        return Result.Success(outputData)   //对应 WorkInfo.state.isFinished
    }


}