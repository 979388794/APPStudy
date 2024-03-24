package com.henry.jetPackTest.viewModelTest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.henry.basic.R

/**
 *
 * @author: henry.xue
 * @date: 2024-03-24
 */
class viewmodelActivity : AppCompatActivity() {

    private lateinit var myViewModel: myViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)

        myViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(myViewModel::class.java)

    }


}