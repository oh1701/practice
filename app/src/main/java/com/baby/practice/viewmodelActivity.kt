package com.baby.practice

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


private class viewmodel : ViewModel(){

    private var text = MutableLiveData<String>()

    fun getName() = text

    fun setname(newtext:String){
        text.value = newtext
    }

    override fun onCleared() {
        text.value = "no text"
        super.onCleared()
    }
}

class viewmodelActivity : AppCompatActivity() {
    lateinit var binding:viewmodelActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewmodel_practice)

        var sampleText = findViewById<TextView>(R.id.sample_text)
        var editText = findViewById<EditText>(R.id.edit_text)
        var okButton = findViewById<Button>(R.id.ok_button)
        var clear = findViewById<Button>(R.id.clear_button)



        val viewmodel = ViewModelProvider(this).get(viewmodel::class.java)
        //viewmodel 객체의 viewmodelstoreowner를 넘겨줌, 여기서 액티비티나 프래그먼트 설정에 따라 viewmodel 생명주기 결정

        viewmodel.getName().observe(this, {
            sampleText.text = it
        })
        //

        okButton.setOnClickListener {
            viewmodel.setname(editText.text.toString())
        }

        clear.setOnClickListener{

        }
    }
}