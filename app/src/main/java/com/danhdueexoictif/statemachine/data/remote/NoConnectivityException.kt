package com.danhdueexoictif.statemachine.data.remote

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}
