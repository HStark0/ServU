package com.app.servu

sealed class HomeItem {
    object ServiceOptions : HomeItem()
    data class Scheduled(val service: ScheduledService) : HomeItem()
}