package com.chekh.paysage.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chekh.paysage.R

enum class AppCategory(val id: String, @StringRes val titleRes: Int, @DrawableRes val iconRes: Int) {
    MESSAGING("MESSAGING", R.string.category_messaging, R.drawable.ic_messaging),
    MEDIA_AND_VIDEO("MEDIA_AND_VIDEO", R.string.category_media_and_video, R.drawable.ic_media_and_video),
    PHOTO("PHOTO", R.string.category_photo, R.drawable.ic_photo),
    GAMES("GAMES", R.string.category_games, R.drawable.ic_games),
    GOOGLE("GOOGLE", R.string.category_google, R.drawable.ic_google),
    PRODUCTIVITY("PRODUCTIVITY", R.string.category_productivity, R.drawable.ic_productivity),
    EDUCATION("EDUCATION", R.string.category_education, R.drawable.ic_education),
    FINANCE_AND_BUSINESS("FINANCE_AND_BUSINESS", R.string.category_finance_and_business, R.drawable.ic_finance_and_business),
    NEWS("NEWS", R.string.category_news, R.drawable.ic_news),
    TRANSPORTATION("TRANSPORTATION", R.string.category_transportation, R.drawable.ic_transportation),
    TRAVEL_AND_LOCAL("TRAVEL_AND_LOCAL", R.string.category_travel_and_local, R.drawable.ic_travel_and_local),
    SHOPPING("SHOPPING", R.string.category_shopping, R.drawable.ic_shopping),
    FOOD_AND_DRINK("FOOD_AND_DRINK", R.string.category_food_and_drink, R.drawable.ic_food_and_drink),
    SPORT("SPORT", R.string.category_sport, R.drawable.ic_sport),
    PERSONALIZATION("PERSONALIZATION", R.string.category_personalization, R.drawable.ic_personalization),
    TOOLS("TOOLS", R.string.category_tools, R.drawable.ic_tools),
    OTHER("OTHER", R.string.category_other, R.drawable.ic_other);

    companion object {
        fun get(id: String) = values().find { it.id == id } ?: OTHER
    }
}