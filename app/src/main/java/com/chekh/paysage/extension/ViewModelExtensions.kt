package com.chekh.paysage.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

fun <T : ViewModel> ViewModelProvider.Factory.get(
    activity: FragmentActivity,
    modelClass: KClass<T>
): T = ViewModelProvider(activity, this).get(modelClass.java)

fun <T : ViewModel> ViewModelProvider.Factory.get(
    fragment: Fragment,
    modelClass: KClass<T>
): T = ViewModelProvider(fragment, this).get(modelClass.java)