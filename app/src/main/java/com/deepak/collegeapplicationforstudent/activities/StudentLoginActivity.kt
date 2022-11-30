package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.database.CollegeDBHelper

class StudentLoginActivity : Activity(), View.OnClickListener {

    lateinit var tvRegister : TextView
    lateinit var etMobileNumber : EditText
    lateinit var etPassword : EditText
    lateinit var tvLogin : TextView
    lateinit var collegeDBHelper: CollegeDBHelper

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_login)

        sharedPreferences = getSharedPreferences("CollegePrefName", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        collegeDBHelper = CollegeDBHelper(this@StudentLoginActivity)
        tvLogin = findViewById(R.id.tvLogin)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etUserPassword)
        tvRegister = findViewById(R.id.tvRegister)
        tvRegister.setOnClickListener(this@StudentLoginActivity)
        tvLogin.setOnClickListener(this@StudentLoginActivity)
    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.tvRegister ->{
                startActivity(Intent(this@StudentLoginActivity, StudentRegisterActivity :: class.java))
            }

            R.id.tvLogin ->{

                var mobileNumber = etMobileNumber.text.toString()
                var password = etPassword.text.toString()

                var isLoggedInState = collegeDBHelper.checkMobileNumberAndPassword(mobileNumber,password)
                if (isLoggedInState){

                    var studStatus = collegeDBHelper.checkApprovalStatus(mobileNumber)
                    if (studStatus != 0){

                        editor.putString("MOBILE_NUMBER",mobileNumber)
                        editor.commit()

                        var intent = Intent(this,StudentDashBoardActivity :: class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Admin Still Not Approved",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Invalid MobileNumber AND Password",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}