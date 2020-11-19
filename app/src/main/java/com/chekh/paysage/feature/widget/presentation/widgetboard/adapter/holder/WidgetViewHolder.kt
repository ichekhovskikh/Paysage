package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.isWhite
import com.chekh.paysage.core.extension.setHeight
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_widget_card.*

class WidgetViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val context: Context
        get() = containerView.context

    fun bind(widget: WidgetModel) {
        setPreviewImage(widget.previewImage)
        tvLabel.text = widget.label
        tvGridSize.text = context.getString(R.string.grid_size, widget.minWidth, widget.minHeight)
    }

    private fun setPreviewImage(previewImage: Bitmap?) {
        if (previewImage == null) {
            ivPreviewImage.setImageDrawable(null)
            ivPreviewImage.setHeight(ivPreviewImage.layoutParams.width)
            cvContent.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            setTextColors(R.color.black, R.color.mediumGrayColor)
            return
        }
        val scale = ivPreviewImage.layoutParams.width.toFloat() / previewImage.width
        val newHeight = (previewImage.height * scale).toInt()
        val scaledPreview = previewImage.scale(ivPreviewImage.layoutParams.width, newHeight)
        ivPreviewImage.setImageBitmap(scaledPreview)
        ivPreviewImage.setHeight(newHeight)
        val swatch = Palette.from(scaledPreview).generate().vibrantSwatch
        val cardColor = swatch?.rgb ?: ContextCompat.getColor(context, R.color.white)
        cvContent.setCardBackgroundColor(cardColor)
        if (swatch == null || isWhite(swatch.hsl)) {
            setTextColors(R.color.black, R.color.mediumGrayColor)
        } else {
            setTextColors(R.color.white, R.color.lightGrayColor)
        }
    }

    private fun setTextColors(@ColorRes labelColorRes: Int, @ColorRes gridSizeColorRes: Int) {
        tvLabel.setTextColor(ContextCompat.getColor(context, labelColorRes))
        tvGridSize.setTextColor(ContextCompat.getColor(context, gridSizeColorRes))
    }
}
