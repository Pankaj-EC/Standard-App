package com.uaer.app.Service.impls

import com.uaer.app.Service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl : EmailService {

    @Autowired
    private val mailSender: JavaMailSender? = null

    /**
     * SIMPLE MAIL SERVICE
     */
    override fun sendEmail(
            toEmail: String?,
            body: String?,
            subject: String?
    ) {
        val message = SimpleMailMessage()
        message.from = "spring.email.from@gmail.com"
        message.setTo(toEmail)
        message.text = body!!
        message.subject = subject!!
        mailSender!!.send(message)
    }
}