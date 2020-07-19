package com.juawapps.whatstowatch.common.ui

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes


fun Context.getColorByAttr(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}