package com.chekh.paysage.common.data.db

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.chekh.paysage.common.data.model.AppCategory
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.common.data.model.IconColor
import com.chekh.paysage.core.extension.toIconColor
import com.chekh.paysage.core.ui.tools.createBitmap
import com.chekh.paysage.core.ui.tools.toBase64

object Converters {

    class BooleanTypeConverter {

        @TypeConverter
        fun toBoolean(value: Int) = value == 1

        @TypeConverter
        fun toInt(value: Boolean) = if (value) 1 else 0
    }

    class IconColorTypeConverter {

        @TypeConverter
        fun toIconColor(value: Int) = value.toIconColor()

        @TypeConverter
        fun toInt(value: IconColor) = value.color
    }

    class AppCategoryTypeConverter {

        @TypeConverter
        fun toAppCategory(value: String) = AppCategory.get(value)

        @TypeConverter
        fun toString(value: AppCategory) = value.id
    }

    class BitmapTypeConverter {

        @TypeConverter
        fun toBitmap(value: String) = createBitmap(value)

        @TypeConverter
        fun toString(value: Bitmap) = value.toBase64()
    }

    class DesktopWidgetTypeConverter {

        @TypeConverter
        fun toBDesktopWidgetType(value: String) = DesktopWidgetType.valueOf(value)

        @TypeConverter
        fun toString(value: DesktopWidgetType) = value.toString()
    }
}
