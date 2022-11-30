package com.deepak.collegemanagementappdemo.models

data class Event(
    var eventID: String,
    var eventTitle: String,
    var eventDescription: String,
    var eventDate: String,
    var eventTime: String,
    var eventStudMobileNumber: String,
    var eventStatus: Int,
    var studentCourse : String,
    var eventRequest : Int
)
