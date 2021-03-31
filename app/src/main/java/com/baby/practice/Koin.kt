package com.baby.practice

import android.accounts.AbstractAccountAuthenticator
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


data class dd(
    val a:AA,
    val b:BB,
    val c:CC,
    val d:DD
)

data class AA(var an:String)
data class BB(var af:String)
data class CC(var ff:String)
data class DD(var gg:String)

class Koin : AppCompatActivity() {  //Koin 사용할 시 AndroidManifest에 application 상속받는 클래스의 이름을 넣어준다.

    private val schoolService: SchoolService by inject()
    private val StudentController:StudentController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koin)

        schoolService.moveSchool("영성중학교")
        StudentController.Log()

        //Koin을 쓰지 않을 경우의 번거로움.
        var afaf = dd(AA("adfas"), BB("asfaf"), CC("dghsh"), DD("gsh"))

        //Koin을 쓸 경우의 편리성
        val parn:dd by inject()
        parn.a.an = "Afaf"
        parn.b.af = "aaf"
        parn.c.ff = "asdf"
        parn.d.gg = "sgsdgh"


    }
}

class Practicekoin:Application(){
    override fun onCreate() {
        super.onCreate()

        val appmodule = module {
            single{SchoolService()}
            single { StudentController(get()) } //여기만 get 생성한 이유는 생성자로 매개변수 입력받아서.
        }

        startKoin {
            androidContext(applicationContext)
            modules(appmodule)
        }
    }
}
/*
module	코틀린 모듈을 생성할 때 사용한다.
factory	매번 inject 할 때마다 새로운 인스턴스를 생성한다.
single	전역적으로 사용 가능한 인스턴스를 생성한다. (싱글톤 생성)
bind	생성할 객체를 다른 타입으로 변환할 때 사용한다.
get	필요로하는 곳에 알맞게 의존성을 주입한다.
applicationContext	Contexxt를 주입한다.
 */
class SchoolService{
    var schoolName = "대구소프트웨어고등학교"

    fun moveSchool(newSchoolName : String){
        schoolName = newSchoolName
    }
}

class StudentController(val schoolService: SchoolService) {
    fun Log(){
        Log.d("TAG 학교 :", "${schoolService.schoolName}")
    }
}
