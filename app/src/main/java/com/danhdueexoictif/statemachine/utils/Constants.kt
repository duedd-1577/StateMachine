package com.danhdueexoictif.statemachine.utils

object Constants {
    object NetworkRequestParam {
        const val OS_TYPE = "os-type"
        const val CONTENT_TYPE = "Content-Type"
        const val APP_VERSION = "version"
        const val AUTHORIZATION = "Authorization"
    }

    object NetworkRequestValue {
        const val OS_NAME = "Android"
        const val CONTENT_TYPE = "application/json"
    }

    object NetworkSettings {
        const val TIMEOUT = 10 // seconds
        const val DOUBLE_CHECK_CONNECTION_TIME_OUT = 1500 // milliseconds
    }
}
