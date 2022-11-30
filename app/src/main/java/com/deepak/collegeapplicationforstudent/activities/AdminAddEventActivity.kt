package com.deepak.collegemanagementappdemo.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.database.CollegeDBHelper
import com.deepak.collegemanagementappdemo.models.Event
import java.util.*

class AdminAddEventActivity : Activity(), View.OnClickListener {

    lateinit var calendar : Calendar

    lateinit var etEventID : EditText
    lateinit var etEventTitle : EditText
    lateinit var etEventDescription : EditText
    lateinit var etEventDate : EditText
    lateinit var etEventTime : EditText
    lateinit var ivCalendar : ImageView
    lateinit var ivTCalendar : ImageView
    lateinit var tvAddEvent : TextView
    lateinit var spCourse : Spinner

    lateinit var collegeDBHelper : CollegeDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_add_event)

        spCourse = findViewById(R.id.spCourse)
        collegeDBHelper = CollegeDBHelper(this@AdminAddEventActivity)

        calendar  = Calendar.getInstance()

        etEventID  = findViewById(R.id.etEventID)
        etEventTitle  = findViewById(R.id.etEventTitle)
        etEventDescription  = findViewById(R.id.etEventDescription)
        etEventDate  = findViewById(R.id.etEventDate)
        etEventTime  = findViewById(R.id.etEventTime)
        ivCalendar  = findViewById(R.id.ivCalendar)
        ivTCalendar  = findViewById(R.id.ivTCalendar)
        tvAddEvent  = findViewById(R.id.tvAddEvent)

        tvAddEvent.setOnClickListener(this)
        ivCalendar.setOnClickListener(this)
        ivTCalendar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.ivCalendar ->{

                var dataPickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var dateFormat = ""+ dayOfMonth + "/" + "${month+1}" + "/"+ year
                    etEventDate.setText("$dateFormat")

                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
                dataPickerDialog.show()

            }

            R.id.ivTCalendar ->{

                var timePickerDialog = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    var timeFormat = "$hourOfDay : $minute"
                    etEventTime.setText(timeFormat)
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false)

                timePickerDialog.show()
            }

            R.id.tvAddEvent ->{

                var eventID = etEventID.text.toString()
                var eventTitle = etEventTitle.text.toString()
                var eventDescription = etEventDescription.text.toString()
                var eventDate = etEventDate.text.toString()
                var eventTime = etEventTime.text.toString()
                var studentCourse = spCourse.selectedItem.toString()

                var event = Event(eventID,eventTitle,eventDescription,eventDate,eventTime,"",0,studentCourse,0)
                var id = collegeDBHelper.insertEventData(event)
                if (id.toString()!= "-1"){
                    Toast.makeText(this,"Event is Added Successfully ",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Event is Not Added ",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
