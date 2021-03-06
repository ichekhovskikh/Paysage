package com.chekh.paysage.common.data.db

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.chekh.paysage.common.data.model.AppCategory
import com.chekh.paysage.common.data.model.IconColor
import com.chekh.paysage.core.extension.toIconColor
import com.chekh.paysage.core.ui.tools.createBitmap
import com.chekh.paysage.core.ui.tools.toBase64

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
            return value.toIconColor()
        }

        @TypeConverter
        fun toInt(value: IconColor): Int {
            return value.color
        }
    }

    class AppCategoryTypeConverter {

        @TypeConverter
        fun toAppCategory(value: String): AppCategory {
            return AppCategory.get(value)
        }

        @TypeConverter
        fun toString(value: AppCategory): String {
            return value.id
        }
    }

    class BitmapTypeConverter {

        @TypeConverter
        fun toBitmap(value: String): Bitmap {
            return createBitmap(value)
        }

        @TypeConverter
        fun toString(value: Bitmap): String {
            return value.toBase64()
        }
    }
}
