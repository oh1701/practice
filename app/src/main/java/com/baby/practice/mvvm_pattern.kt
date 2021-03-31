package com.baby.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baby.practice.databinding.ActivityMvvmpatternBinding

class Mvvmview:ViewModel(){
    var text = MutableLiveData<String>()

    fun gettext() = text

    init {
        text.value = 0.toString()
    }

    fun setonClick(){
        text.value = 1.toString()
    }

    fun clickbtn(){
        text.value = (text.value?.toInt()?.plus(1)).toString()
        Log.d("TAG", text.value!!)
    }
}

class Mvvmpattern : AppCompatActivity() {

    private lateinit var binding:ActivityMvvmpatternBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmpattern)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this).get(Mvvmview::class.java)

        binding.viewmodel = viewModel

        viewModel.gettext().observe(this, {
            binding.text.text = it
        })
    }
}