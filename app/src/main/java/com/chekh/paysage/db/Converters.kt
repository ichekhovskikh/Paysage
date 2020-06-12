package com.chekh.paysage.db

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.room.TypeConverter
import com.chekh.paysage.PaysageApp.Companion.launcher
import com.chekh.paysage.model.CategoryTitle
import com.chekh.paysage.model.IconColor
import com.chekh.paysage.ui.tool.createBitmap
import com.chekh.paysage.ui.tool.toBase64

object Converters {
    class BooleanTypeConverter {
        @TypeConverter
        fun toBoolean(value: Int): Boolean {
            return value == 1
        }

        @TypeConverter
        fun toInt(value: Boolean): Int {
            return if (value) 1 else 0
        }
    }

    class IconColorTypeConverter {
        @TypeConverter
        fun toIconColor(value: Int): IconColor {
            return IconColor.get(value)
        }

        @TypeConverter
        fun toInt(value: IconColor): Int {
            return value.color
        }
    }

    class CategoryTitleTypeConverter {
        @TypeConverter
        fun toCategoryTitle(value: String): CategoryTitle {
            return CategoryTitle.get(value)
        }

        @TypeConverter
        fun toString(value: CategoryTitle): String {
            return value.id
        }
    }

    class DrawableTypeConverter {
        @TypeConverter
        fun toDrawable(value: String): Drawable {
            return createBitmap(value).toDrawable(launcher.resources)
        }

        @TypeConverter
        fun toString(value: Drawable): String {
            return value.toBitmap().toBase64()
        }
    }
}