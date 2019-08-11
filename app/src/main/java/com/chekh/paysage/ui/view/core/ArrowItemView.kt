package com.chekh.paysage.ui.view.core

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
import com.chekh.paysage.R
import com.chekh.paysage.ui.util.convertPxToDp
import kotlinx.android.synthetic.main.view_arrow_item.view.*

class ArrowItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    RelativeLayout(context, attrs, defStyle) {

    private var expanded = false
    private var duration = DEFAULT_DURATION
    private var onExpandedListener: OnExpandedClickListener? = null

    private val defaultClickListener: OnClickListener by lazy {
        OnClickListener {
            isExpanded = !isExpanded
            onExpandedListener?.onExpandedClick(isExpanded)
        }
    }

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.view_arrow_item, this, true)
        initAttributes(attrs, defStyle)
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
            titleTextView.textSize = convertPxToDp(sizePx)
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

    fun setIcon(drawable: Drawable) {
        titleIcon.setImageDrawable(drawable)
        titleIcon.visibility = View.VISIBLE
    }

    fun setIcon(@DrawableRes resId: Int) {
        titleIcon.setImageResource(resId)
        titleIcon.visibility = View.VISIBLE
    }

    fun isArrowVisible(isVisible: Boolean, expanded: Boolean = false) {
        isExpanded = expanded
        if (isVisible) {
            arrowIcon.visibility = View.VISIBLE
            setOnClickListener(defaultClickListener)
        } else {
            arrowIcon.visibility = View.GONE
            setOnClickListener(null)
        }
    }

    fun setAnimationDuration(duration: Long) {
        this.duration = duration
    }

    fun nonAnimationExpand(expanded: Boolean) {
        if (expanded == this.expanded) return
        this.expanded = expanded
        arrowIcon.rotation = if (expanded) 90f else 0f
    }

    var isExpanded
        get() = expanded
        set(value) {
            if (value == expanded) return
            expanded = value
            val start = if (expanded) 0f else 90f
            val end = if (expanded) 90f else 0f
            ObjectAnimator.ofFloat(arrowIcon, View.ROTATION, start, end).setDuration(duration).start()
        }

    fun setOnExpandedClickListener(listener: (Boolean) -> Unit) {
        onExpandedListener = object : OnExpandedClickListener {
            override fun onExpandedClick(isExpanded: Boolean) {
                listener(isExpanded)
            }
        }
    }

    fun setOnExpandedClickListener(listener: OnExpandedClickListener) {
        onExpandedListener = listener
    }

    interface OnExpandedClickListener {
        fun onExpandedClick(isExpanded: Boolean)
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
    }
}
