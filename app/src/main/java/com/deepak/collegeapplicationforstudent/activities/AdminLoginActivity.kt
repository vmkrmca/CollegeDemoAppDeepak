package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.deepak.collegeapplicationforstudent.R

class AdminLoginActivity : Activity(), View.OnClickListener {

    lateinit var etUserName : EditText
    lateinit var etUserPassword : EditText
    lateinit var tvLogin : TextView
    lateinit var tvCancel  : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_login)

        etUserName = findViewById(R.id.etUserName)
        etUserPassword = findViewById(R.id.etUserPassword)
        tvLogin = findViewById(R.id.tvLogin)
        tvCancel = findViewById(R.id.tvCancel)

        tvLogin.setOnClickListener(this@AdminLoginActivity)
        tvCancel.setOnClickListener(this@AdminLoginActivity)
    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.tvLogin ->{

                var userName = etUserName.text.toString()
                var userPassword = etUserPassword.text.toString()

                if (userName == "ADMIN" && userPassword == "ADMIN"){

                    startActivity(Intent(this@AdminLoginActivity, AdminDashBoardActivity :: class.java))

                }else{
                    Toast.makeText(this@AdminLoginActivity,"Admin Crediantials are wrong please try again Later",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tvCancel ->{

                etUserName.setText("")
                etUserPassword.setText("")
            }
        }
    }
}