package com.deepak.collegemanagementapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.listeners.OnStudentClickListener
import com.deepak.collegemanagementapp.models.Student

class StudentAdapter(private val context: Context, private val studentList : ArrayList<Student>,val onStudentClickListener : OnStudentClickListener) : BaseAdapter() {

    override fun getCount() = studentList.size

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = LayoutInflater.from(context).inflate(R.layout.student_row,parent,false)

        var tvMobileNumber = view.findViewById<TextView>(R.id.tvMobileNumber)
        var tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        var tvUserGender = view.findViewById<TextView>(R.id.tvUserGender)
        var tvCourse = view.findViewById<TextView>(R.id.tvCourse)
        var tvApprove = view.findViewById<TextView>(R.id.tvApprove)
        var tvDApprove = view.findViewById<TextView>(R.id.tvDApprove)

        var student = studentList[position]

        tvMobileNumber.text = student.studMobileNumber
        tvUserName.text = student.studName
        tvUserGender.text = student.studGender
        tvCourse.text = student.studCourse

        if (student.studStatus == 1){
            tvApprove.visibility = View.GONE
        }
        tvApprove.setOnClickListener { onStudentClickListener.onStudentClick(student) }
        tvDApprove.setOnClickListener { onStudentClickListener.onStudentClick(student) }

        return view

    }
}