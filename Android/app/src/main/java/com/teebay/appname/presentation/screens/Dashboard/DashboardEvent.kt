package com.teebay.appname.presentation.screens.Dashboard

sealed class DashboardEvent {
    data class OnTabSelected(val tab: DashboardTab) : DashboardEvent()
}