package com.chekh.paysage.model

enum class CategoryTitle(val id: Long) {
    GOOGLE(0L),
    GAMES(1L),
    PRODUCTIVITY(2L),
    FOOD_AND_DRINK(3L),
    SHOPPING(4L),
    MESSAGING(5L),
    PERSONALIZATION(6L),
    EDUCATION(7L),
    PHOTO(8L),
    MEDIA_AND_VIDEO(9L),
    FINANCE_AND_BUSINESS(10L),
    TRANSPORTATION(11L),
    SPORT(12L),
    TRAVEL_AND_LOCAL(13L),
    NEWS(14L),
    TOOLS(15L),
    OTHER(16L);

    companion object {
        fun get(id: Long) = values().find { it.id == id } ?: OTHER
    }
}