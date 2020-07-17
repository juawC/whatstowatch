package com.juawapps.whatstowatch.common.ui

interface LceViewState : ViewState {
    val isLoading: Boolean
    val errorMessage: Int?
}