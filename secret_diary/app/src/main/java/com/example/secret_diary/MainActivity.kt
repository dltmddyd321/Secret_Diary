package com.example.secret_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.np1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.np2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.np3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.open)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.chg)
    }

    private var changePasswordMode = false //오류를 막기 위한 상태변수(전역)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener{

            if(changePasswordMode) {
                Toast.makeText(this,"비밀번호 변경 중",Toast.LENGTH_SHORT).show()
                return@setOnClickListener //반환에 대한 명시
           }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE) //해당 앱에서만 기능 사용

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            //유저로부터 받아낸 패스워드 값

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)){//초기 비밀번호는 000으로 설정
                //패스워드 일치
                //TODO 다이어리 페이지 작성 후에 넘겨줘야 함.
                startActivity(Intent(this,DiaryActivity::class.java))
            }else {
                showErrorAlertDialog()
            }

        }
        changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE) //해당 앱에서만 기능 사용

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}" //유저로부터 받아낸 패스워드 값
            //다른 동작을 할 수 없도록 예외처리 필요
            if(changePasswordMode){
                //번호를 저장하는 기능

                passwordPreferences.edit{
                    putString("Password", passwordFromUser) //입력받은 값을 비밀번호로서 가져온다.
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                //changePasswordMode가 활성화 :: 비밀번호가 일치하는 지 확인 필요

                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)){//초기 비밀번호는 000으로 설정

                    changePasswordMode = true
                    Toast.makeText(this,"변경할 패스워드를 입력하시오.", Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.RED)

                }else {
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this) //비밀번호 오류에 대한 팝업 메시지 생성
            .setTitle("실패!")
            .setMessage("비밀번호 오류")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}