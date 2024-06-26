package com.uaer.app.Utils.app

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CommonUtils {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }


    fun isValidPassword(password: String): Boolean {
        // Password must be at least 8 characters long
        if (password.length < 8) return false

        // Password must contain at least one uppercase letter
        val uppercasePattern = Regex(".*[A-Z].*")
        if (!uppercasePattern.containsMatchIn(password)) return false

        // Password must contain at least one lowercase letter
        val lowercasePattern = Regex(".*[a-z].*")
        if (!lowercasePattern.containsMatchIn(password)) return false

        // Password must contain at least one digit
        val digitPattern = Regex(".*\\d.*")
        if (!digitPattern.containsMatchIn(password)) return false

        // Password must contain at least one special character
        val specialCharacterPattern = Regex(".*[!@#\$%^&*()-+=].*")
        if (!specialCharacterPattern.containsMatchIn(password)) return false

        return true
    }

    fun generateOtp(): String {
        val otp = Random.nextInt(100000, 1000000) // Generates a random number between 100000 and 999999 (inclusive)
        return otp.toString()
    }
}