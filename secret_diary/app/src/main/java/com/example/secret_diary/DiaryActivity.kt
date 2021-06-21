package com.example.secret_diary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity :AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper()) //Main Thread에 연결된 Handler 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEdt)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        //키-값 쌍이 포함된 파일을 가리키며 키-값 쌍을 읽고 쓸 수 있는 간단한 메서드를 제공

        diaryEditText.setText(detailPreferences.getString("detail","")) //비어있는 초기 페이지

        val runnable = Runnable { //Thread를 위한 runnable 생성
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit {
                putString("detail",diaryEditText.text.toString())
            }
            Log.d("DiaryActivity","SAVE! ${diaryEditText.text.toString()}")
        }

        diaryEditText.addTextChangedListener {

            Log.d("DiaryActivity", "TextChanged :: $it")
            handler.removeCallbacks(runnable) //Handler가 가지고 있는 runnable 호출
            handler.postDelayed(runnable,500) //0.5초 딜레이

        }
    }
}