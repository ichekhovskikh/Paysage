package com.chekh.paysage.feature.tooltipdialog.widget.presentation

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.addWithBackStack
import com.chekh.paysage.core.extension.applyArgs
import com.chekh.paysage.core.extension.exit
import com.chekh.paysage.core.extension.onClick
import com.chekh.paysage.core.ui.fragment.TooltipFragment
import com.chekh.paysage.feature.tooltipdialog.widget.presentation.extension.setWidgetTooltipResult
import com.chekh.paysage.feature.tooltipdialog.widget.presentation.model.WidgetTooltipItem
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_tooltip_widget.*
import javax.inject.Inject

@AndroidEntryPoint
class WidgetTooltipFragment : TooltipFragment(R.layout.fragment_tooltip_widget) {

    private val tooltipViewModel: WidgetTooltipViewModel by viewModels()

    @Inject
    override lateinit var args: Args

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        return (view as? ViewGroup)?.also { addTooltipItems(inflater, it) } ?: view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun addTooltipItems(inflater: LayoutInflater, view: ViewGroup) {
        WidgetTooltipItem.values().forEach { item ->
            inflater.inflate(R.layout.layout_tooltip_item, view, true).apply {
                findViewById<ImageView>(R.id.ivIcon).setImageResource(item.icon)
                findViewById<TextView>(R.id.tvLabel).setText(item.label)
                onClick {
                    setWidgetTooltipResult(item)
                    exit() // todo or onBackPressed()
                }
            }
        }
    }

    private fun setupViewModel() {
        tooltipViewModel.widgetApp.observe(viewLifecycleOwner, ::setWidgetAppInfo)
    }

    private fun setWidgetAppInfo(widgetApp: AppForWidgetModel) {
        widgetApp.icon?.let(ivAppIcon::setImageBitmap)
        tvAppName.text = widgetApp.label
    }

    @Parcelize
    data class Args(
        val id: String?,
        val packageName: String,
        val className: String,
        override val point: PointF
    ) : TooltipFragment.Args.PointArgs(point)

    companion object {

        fun showIn(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            args: Args
        ) {
            fragmentManager.commit {
                addWithBackStack(containerViewId, WidgetTooltipFragment().applyArgs(args))
            }
        }
    }
}
