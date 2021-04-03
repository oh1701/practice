package com.baby.practice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.*
import androidx.room.Room
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

/* Room은 ORM(Object Relational Mapping) 라이브러리. DB 객체를 자바 opr 코틀린 객체로 매핑해주는 것.
 SQLite 추상레이어 위에 제공, SQList의 모든 기능 제공하며 편한 DB 접근 허용
 ROOM에는 3가지 구성요소 (Database, Entity, Dao)가 존재.
 DB : 데이터베이스 보유자.
 Entity : Database 내 테이블
 Dao : DB에 엑세스하는데 사용되는 메소드 보유. select, insert, delete, join 등 데이터 읽고 쓸때 사용

 def room_version = "2.1.0-alpha03"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version" // use kapt for Kotlin

    // optional - RxJava support for Room
    implementation "androidx.room:room-rxjava2:$room_version"

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation "androidx.room:room-guava:$room_version"

    // optional - Coroutines support for Room
    implementation "androidx.room:room-coroutines:$room_version"

    // Test helpers
    testImplementation "androidx.room:room-testing:$room_version"
*/

/*
@Entity
data class User(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name="first_name") val firsename:String,
        @ColumnInfo(name="last_name") val lastname:String) //{constructor() = this()} 로 줄시 default 값 설정


@Dao
interface UserDao{
    @Query("SELECT * FROM user") //
    suspend fun getAll(): List<User> //List<User> 로 받아올시 리스트형식으로 받아와져서 생성자 형으로 출력된다. 코루틴 사용시 suspend 추가

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    suspend fun findByName(first: String, last: String): User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)

} //Room DB에서 기본 자료형이 아닌 객체를 사용하기 위한 Annotation : @TypeConverter

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}*/

@Entity
data class Practice(
        @PrimaryKey(autoGenerate = true) val id:Int,
        @ColumnInfo(name = "animal") val animal:String,
        @ColumnInfo(name = "name") val name:String
)

@Dao
interface PracDao{
    @Query("SELECT name FROM Practice WHERE animal = '개'")
    suspend fun getname():String

    @Insert
    suspend fun InsertAll(vararg practice: Practice)
}

@Database(entities = [Practice::class], version = 1)
abstract class PracDB:RoomDatabase(){
    abstract fun pracdao():PracDao
}


class Room : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        var room_te = findViewById<TextView>(R.id.room_text)

        var db = Room.databaseBuilder(
                applicationContext,
                PracDB::class.java, "database-na"
        )
                .build()

        CoroutineScope(Dispatchers.IO).launch{
            db.pracdao().InsertAll(Practice(0, "개", "미니"))
            room_te.text = db.pracdao().getname()
        }
/*

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
        )
                .build()

        GlobalScope.launch(Dispatchers.IO) {
            db.userDao().insertAll(User(0, "밥", "주셈"))
            room_te.text = db.userDao().getAll().toString()
            Log.d("TAG", "스코프")
        }*/
    }

}