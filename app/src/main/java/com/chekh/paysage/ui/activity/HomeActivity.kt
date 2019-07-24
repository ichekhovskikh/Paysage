package com.chekh.paysage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.ui.fragment.BaseFragment
import com.chekh.paysage.ui.fragment.HomeFragment
import com.chekh.paysage.ui.inTransaction

class HomeActivity : AppCompatActivity() {

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        addFragmentIfNeed()
    }

    private fun addFragmentIfNeed() {
        fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            fragment = HomeFragment.instance()
            supportFragmentManager.inTransaction {
                add(R.id.container, fragment!!)
            }
        }
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        var handled = false
        for (fragment in fragments) {
            if (fragment is BaseFragment) {
                handled = fragment.onBackPressed()
                if (handled) break
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }
}
