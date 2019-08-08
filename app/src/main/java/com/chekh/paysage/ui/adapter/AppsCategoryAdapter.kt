package com.chekh.paysage.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.chekh.paysage.R
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.ui.view.ArrowItemView

class AppsCategoryAdapter :
    RecyclerView.Adapter<AppsCategoryAdapter.AppsViewHolder>() {

    private lateinit var appsCategory: List<AppsGroupByCategory>

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>) {
        this.appsCategory = appsCategory
        sortByPosition()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apps_category_view_holder, parent, false)
        return AppsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
        holder.bind(appsCategory[position])
    }

    override fun getItemCount(): Int {
        return if (::appsCategory.isInitialized) appsCategory.size else 0
    }

    private fun sortByPosition() {
        appsCategory = appsCategory.sortedBy { it.category.position }
    }

    inner class AppsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(category: AppsGroupByCategory) {
            val headerView = itemView.findViewById<ArrowItemView>(R.id.title)
            headerView.setIcon(category.category.title.iconRes)
            headerView.setTitleText(category.category.title.titleRes)
        }
    }
}