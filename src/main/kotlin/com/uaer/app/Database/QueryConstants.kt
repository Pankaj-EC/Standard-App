package com.uaer.app.Database

class QueryConstants {
    companion object{
        const val GET_CUSTOMER_DETAILS = """"SELECT * FROM user_db.Register_Recode WHERE user_id = :USER_ID"""
    }
}