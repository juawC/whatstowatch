package com.juawapps.whatstowatch.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.juawapps.whatstowatch.R

class LoadingScreen : Screen<LoadingScreen>() {
    val errorIcon = KImageView { withId(R.id.error_icon) }
    val errorTitle = KImageView { withId(R.id.error_text_title) }
    val errorButton = KImageView { withId(R.id.error_retry_button) }
    val loadingView = KView { withId(R.id.loadingView) }
}
