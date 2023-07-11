package com.magma.DivingApp.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle

object Utilities {
    fun intent(context: Context, c: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, c)
        if (bundle != null) intent.putExtras(bundle)
        context.startActivity(intent)
    }
}