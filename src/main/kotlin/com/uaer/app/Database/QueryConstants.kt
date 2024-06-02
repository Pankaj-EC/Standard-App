package com.uaer.app.Database

class QueryConstants {
    companion object{
        const val GET_CUSTOMER_DETAILS = """"SELECT * FROM Register_Recode WHERE email = :USER_ID"""
    }
}