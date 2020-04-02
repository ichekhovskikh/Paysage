package com.chekh.paysage.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.chekh.paysage.R
import com.chekh.paysage.ui.util.MetricsConverter
import kotlinx.android.synthetic.main.view_arrow_item.view.*

open class ArrowItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    private var expanded = false
    var onExpandedListener: OnExpandedClickListener? = null

    var isExpanded
        get() = expanded
        set(value) {
            if (expanded == value) return
            expanded = value
            arrowIcon.rotation = if (expanded) 90f else 0f
            onExpandedListener?.onExpandedClick(expanded, false)
        }

    init {
        initView()
        initAttributes(attrs, defStyle)
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_arrow_item, this)
    }

    private fun initAttributes(attrs: AttributeSet?, defStyle: Int) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ArrowItemView, defStyle, 0)
        setTittleAttributes(attributes)
        setTitleIconAttributes(attributes)
        setArrowAttributes(attributes)
        attributes.recycle()
    }

    private fun setTittleAttributes(attributes: TypedArray) {
        titleTextView.text = attributes.getString(R.styleable.ArrowItemView_titleText)
        if (attributes.hasValue(R.styleable.ArrowItemView_colorTitleText)) {
            titleTextView.setTextColor(attributes.getColor(R.styleable.ArrowItemView_colorTitleText, -1))
        }
        if (attributes.hasValue(R.styleable.ArrowItemView_sizeTitleText)) {
            val sizePx = attributes.getDimension(R.styleable.ArrowItemView_sizeTitleText, -1f)
            titleTextView.textSize = MetricsConverter(context).pxToDp(sizePx)
        }
        if (attributes.hasValue(R.styleable.ArrowItemView_fontTitleText)) {
            val fontId = attributes.getResourceId(R.styleable.ArrowItemView_fontTitleText, -1)
            titleTextView.typeface = ResourcesCompat.getFont(context, fontId)
        }
    }

    private fun setTitleIconAttributes(attributes: TypedArray) {
        if (attributes.hasValue(R.styleable.ArrowItemView_icon)) {
            titleIcon.setImageDrawable(attributes.getDrawable(R.styleable.ArrowItemView_icon))
            titleIcon.visibility = View.VISIBLE
        }
    }

    private fun setArrowAttributes(attributes: TypedArray) {
        setOnClickListener { animatedExpand(!isExpanded) }
        isArrowVisible(attributes.getBoolean(R.styleable.ArrowItemView_isArrowVisible, true))
        if (attributes.hasValue(R.styleable.ArrowItemView_arrowTint)) {
            val tintColor = attributes.getColor(R.styleable.ArrowItemView_arrowTint, -1)
            arrowIcon.imageTintList = ColorStateList.valueOf(tintColor)
        }
    }

    val title: CharSequence
        get() = titleTextView.text

    val icon: Drawable
        get() = titleIcon.drawable

    fun setTitleText(text: CharSequence) {
        titleTextView.text = text
    }

    fun setTitleText(@StringRes resId: Int) {
        titleTextView.setText(resId)
    }

    fun setTitleTextSize(size: Float) {
        titleTextView.textSize = size
    }

    fun setIcon(drawable: Drawable) {
        titleIcon.setImageDrawable(drawable)
        titleIcon.visibility = View.VISIBLE
    }

    fun setIcon(@DrawableRes resId: Int) {
        titleIcon.setImageResource(resId)
        titleIcon.visibility = View.VISIBLE
    }

    fun isArrowVisible(isVisible: Boolean) {
        arrowIcon.isVisible = isVisible
    }

    fun animatedExpand(expanded: Boolean, duration: Long = DEFAULT_DURATION) {
        if (this.expanded == expanded) return
        this.expanded = expanded
        val start = if (expanded) 0f else 90f
        val end = if (expanded) 90f else 0f
        ObjectAnimator.ofFloat(arrowIcon, View.ROTATION, start, end).setDuration(duration).start()
        onExpandedListener?.onExpandedClick(expanded, true)
    }

    interface OnExpandedClickListener {
        fun onExpandedClick(isExpanded: Boolean, isAnimate: Boolean)
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
    }
}
