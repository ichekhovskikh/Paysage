package com.chekh.paysage.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.chekh.paysage.R

class AppsCategoryAdapter(private val appsCategory: List<String>) : RecyclerView.Adapter<AppsCategoryAdapter.AppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apps_category_view_holder, parent, false)
        return AppsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return appsCategory.size
    }

    inner class AppsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}