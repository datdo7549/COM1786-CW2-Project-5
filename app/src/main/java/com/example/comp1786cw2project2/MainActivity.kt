package com.example.comp1786cw2project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.com1786_cw2project1.R
import com.example.comp1786cw2project2.feature.home.HomeFragment
import com.tunjid.androidx.navigation.stackNavigationController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val stackNavigator by stackNavigationController(R.id.navRootHost)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stackNavigator.push(HomeFragment.newInstance())
    }
}