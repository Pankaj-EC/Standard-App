package com.uaer.app.Service

interface EmailService {
    fun sendEmail(toEmail: String?, body: String?, subject: String?)
}