package com.juawapps.whatstowatch.common.ui

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.size.ViewSizeResolver
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

    // We are getting the height because its the value that we defined on the view instead of
    // the width, we are defining the height as constant and not the width because currently we are
    // using this on a RecyclerView. In the future this should be fixed so it supports a measured
    // width.
    val width = imageView.layoutParams?.height ?: Int.MAX_VALUE

    imageView.load(url.getUrl(width)) {
        size(ViewSizeResolver(imageView))
        crossfade(true)
    }
}

@BindingAdapter("textColorAttr")
fun textColorAttr(textView: TextView, colorAttr: Int?) {
    if (colorAttr == null) return
    textView.setTextColor(textView.context.getColorByAttr(colorAttr))
}

@BindingAdapter("backgroundTintColorAttr")
fun backgroundTintColorAttr(textView: TextView, colorAttr: Int?) {
    if (colorAttr == null) return
    val background = textView.background as GradientDrawable
    textView.backgroundTintMode = PorterDuff.Mode.SRC_IN
    textView.backgroundTintList = ColorStateList.valueOf(textView.context.getColorByAttr(colorAttr))
}

