package com.test.restaurantapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.test.restaurantapp.R
import com.test.restaurantapp.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushFragment(HomeFragment(),null,true)
    }

    fun pushFragment(fragment: Fragment, bundle: Bundle?, withOutBack: Boolean) {
        val tag = fragment.javaClass.simpleName
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment.isAdded) return
        if (bundle != null) fragment.arguments = bundle
        transaction.replace(R.id.fragment_nav_host, fragment, tag)
        if (!withOutBack) {
            //for back pressed
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
}