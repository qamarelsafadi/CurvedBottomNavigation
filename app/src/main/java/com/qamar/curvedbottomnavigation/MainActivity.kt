package com.qamar.curvedbottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.qamar.curvedbottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    companion object {
        // you can put any unique id here, but because I am using Navigation Component I prefer to put it as
        // the fragment id.
        const val HOME_ITEM = R.id.homeFragment
        const val OFFERS_ITEM = R.id.offersFragment
        const val MORE_ITEM = R.id.moreFragment
        const val SECTION_ITEM = R.id.sectionFragment
        const val CART_ITEM = R.id.cartFragment
        const val BLANK_ITEM = R.id.blankFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            initNavHost()
            setUpBottomNavigation()
        }
    }

    private fun ActivityMainBinding.setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(HOME_ITEM, getString(R.string.home), R.drawable._01_home),
            CurvedBottomNavigation.Model(OFFERS_ITEM, getString(R.string.offers), R.drawable.offers),
            CurvedBottomNavigation.Model(SECTION_ITEM, getString(R.string.sections), R.drawable.section),
            CurvedBottomNavigation.Model(CART_ITEM, getString(R.string.cart), R.drawable.cart),
            CurvedBottomNavigation.Model(MORE_ITEM, getString(R.string.more), R.drawable.more),
            CurvedBottomNavigation.Model(BLANK_ITEM, getString(R.string.more), R.drawable.more),
        )
        bottomNavigation.apply {
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                navController.navigate(it.id)
            }
            // optional
            setupNavController(navController)
        }
    }

    private fun initNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }


    // if you need your backstack of your items always back to home please override this method
    override fun onBackPressed() {
        if (navController.currentDestination!!.id == HOME_ITEM)
            super.onBackPressed()
        else {
            when (navController.currentDestination!!.id) {
              OFFERS_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
               SECTION_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
                CART_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
               MORE_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
                else -> {
                    navController.navigateUp()
                }
            }
        }
    }

}