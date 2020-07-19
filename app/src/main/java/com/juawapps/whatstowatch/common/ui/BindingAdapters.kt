package com.juawapps.whatstowatch.common.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import coil.api.load
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.domain.ImageUrl


@BindingAdapter("setVisible")
fun setVisible(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: ImageUrl?) {
    if (url == null || url.getUrl().isEmpty()) return
    imageView.load(url.getUrl()) {
        crossfade(true)
    }
}


@BindingAdapter("textColorAttr")
fun textColorAttr(textView: TextView, colorAttr: Int?) {
    if (colorAttr == null) return
    textView.setTextColor(textView.context.getColorByAttr(colorAttr))
}

