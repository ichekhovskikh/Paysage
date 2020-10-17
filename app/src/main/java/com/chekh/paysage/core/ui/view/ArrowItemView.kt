package com.chekh.paysage.core.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.chekh.paysage.R
import kotlinx.android.synthetic.main.view_arrow_item.view.*

open class ArrowItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    var onExpandedListener: OnExpandedClickListener? = null

    var isExpanded = false
        private set

    init {
        initView()
        initAttributes(attrs, defStyle)
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_arrow_item, this)
    }

    private fun initAttributes(attrs: AttributeSet?, defStyle: Int) {
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.ArrowItemView,
            defStyle,
            0
        )
        setTittleAttributes(attributes)
        setIconAttributes(attributes)
        setArrowAttributes(attributes)
        attributes.recycle()
    }

    private fun setTittleAttributes(attributes: TypedArray) {
        tvTitle.text = attributes.getString(R.styleable.ArrowItemView_titleText)
        if (attributes.hasValue(R.styleable.ArrowItemView_colorTitleText)) {
            tvTitle.setTextColor(attributes.getColor(R.styleable.ArrowItemView_colorTitleText, -1))
        }
        if (attributes.hasValue(R.styleable.ArrowItemView_sizeTitleText)) {
            val sizePx = attributes.getDimension(R.styleable.ArrowItemView_sizeTitleText, -1f)
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx)
        }
        if (attributes.hasValue(R.styleable.ArrowItemView_fontTitleText)) {
            val fontId = attributes.getResourceId(R.styleable.ArrowItemView_fontTitleText, -1)
            tvTitle.typeface = ResourcesCompat.getFont(context, fontId)
        }
    }

    private fun setIconAttributes(attributes: TypedArray) {
        if (attributes.hasValue(R.styleable.ArrowItemView_icon)) {
            ivIcon.setImageDrawable(attributes.getDrawable(R.styleable.ArrowItemView_icon))
            ivIcon.visibility = View.VISIBLE
        }
    }

    private fun setArrowAttributes(attributes: TypedArray) {
        isArrowVisible = attributes.getBoolean(R.styleable.ArrowItemView_isArrowVisible, true)
        if (attributes.hasValue(R.styleable.ArrowItemView_arrowTint)) {
            val tintColor = attributes.getColor(R.styleable.ArrowItemView_arrowTint, -1)
            ivArrow.imageTintList = ColorStateList.valueOf(tintColor)
        }
    }

    var title: CharSequence
        get() = tvTitle.text
        set(value) {
            tvTitle.text = value
        }

    var icon: Drawable
        get() = ivIcon.drawable
        set(value) {
            ivIcon.setImageDrawable(value)
            ivIcon.visibility = View.VISIBLE
        }

    var isArrowVisible: Boolean
        get() = ivArrow.isVisible
        set(value) {
            ivArrow.isVisible = value
        }

    fun setTitle(@StringRes resId: Int) {
        tvTitle.setText(resId)
    }

    fun setTextSize(size: Float, typedValue: Int = COMPLEX_UNIT_SP) {
        tvTitle.setTextSize(typedValue, size)
    }

    fun setIcon(@DrawableRes resId: Int) {
        ivIcon.setImageResource(resId)
        ivIcon.visibility = View.VISIBLE
    }

    fun expand(
        expanded: Boolean,
        isAnimate: Boolean = false,
        duration: Long = ANIMATION_DURATION_DEFAULT
    ) {
        if (this.isExpanded == expanded) return
        this.isExpanded = expanded
        val newRotation = if (expanded) 90f else 0f
        if (isAnimate) {
            ObjectAnimator.ofFloat(ivArrow, View.ROTATION, ivArrow.rotation, newRotation)
                .setDuration(duration)
                .start()
        } else {
            ivArrow.rotation = newRotation
        }
        onExpandedListener?.onExpandedClick(expanded)
    }

    interface OnExpandedClickListener {
        fun onExpandedClick(isExpanded: Boolean)
    }

    companion object {
        private const val ANIMATION_DURATION_DEFAULT = 300L
    }
}
