package com.lucastan.chat.util

import android.content.Context
import android.util.TypedValue

class Measurement {
    /** Converts a DP value to PX **/
    fun convertDpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}