package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.database.CollegeDBHelper
import com.deepak.collegemanagementapp.models.Student
import java.util.Calendar

class StudentRegisterActivity : Activity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    lateinit var etMobileNumber : EditText
    lateinit var etUserName : EditText
    lateinit var etUserPassword : EditText
    lateinit var etUserCPassword : EditText
    lateinit var rbMale : RadioButton
    lateinit var rbFeMale : RadioButton
    lateinit var rbOthers : RadioButton
    lateinit var etDateOfBirth : EditText
    lateinit var ivCalendar : ImageView
    lateinit var spCourse : Spinner
    lateinit var etUserAge : EditText
    lateinit var cbAccept : CheckBox
    lateinit var tvRegister : TextView
    lateinit var tvCancel : TextView
    lateinit var calendar : Calendar

    lateinit var collegeDBHelper: CollegeDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        collegeDBHelper = CollegeDBHelper(this@StudentRegisterActivity)
        calendar  = Calendar.getInstance()
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etUserName = findViewById(R.id.etUserName)
        etUserPassword = findViewById(R.id.etUserPassword)
        etUserCPassword = findViewById(R.id.etUserCPassword)
        rbMale = findViewById(R.id.rbMale)
        rbFeMale = findViewById(R.id.rbFeMale)
        rbOthers = findViewById(R.id.rbOthers)
        etDateOfBirth = findViewById(R.id.etDOB)
        ivCalendar = findViewById(R.id.ivCalendar)
        spCourse = findViewById(R.id.spCourse)
        etUserAge = findViewById(R.id.etUserAge)
        cbAccept = findViewById(R.id.cbAcceptTerms)

        tvRegister = findViewById(R.id.tvRegister)
        tvCancel = findViewById(R.id.tvCancel)
        tvRegister.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        ivCalendar.setOnClickListener(this)

        cbAccept.setOnCheckedChangeListener(this)


    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.tvRegister -> {

                var userMobileNumber = etMobileNumber.text.toString()
                var userName = etUserName.text.toString()
                var userPassword = etUserPassword.text.toString()
                var userCPassword = etUserCPassword.text.toString()
                var userGender = ""

                if (rbMale.isChecked) {
                    userGender = "Male"
                } else if (rbFeMale.isChecked) {
                    userGender = "FeMale"
                } else if (rbOthers.isChecked) {
                    userGender = "Others"
                }

                var userDOB = etDateOfBirth.text.toString()
                var course = spCourse.selectedItem.toString()
                var userAge = etUserAge.text.toString().toInt()

                if (userPassword == userCPassword) {
                    var student = Student(
                        userMobileNumber,
                        userName,
                        userPassword,
                        userGender,
                        course,
                        userDOB,
                        userAge,0)

                    var id = collegeDBHelper.insertStudRecord(student)

                    var dbID = id.toString()
                    if (dbID != "-1"){
                        startActivity(Intent(this@StudentRegisterActivity,StudentLoginActivity :: class.java))
                        Toast.makeText(this,"Wait for Admin Approval",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Mobile Number is Already Exists",Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(
                        this,
                        "Password and Confirm password is not Match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.tvCancel ->{

            }

            R.id.ivCalendar ->{

                var dataPickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var dateFormat = ""+ dayOfMonth + "/" + "${month+1}" + "/"+ year
                    etDateOfBirth.setText("$dateFormat")

                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
                dataPickerDialog.show()
            }

        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        tvRegister.isEnabled = isChecked
    }

}
