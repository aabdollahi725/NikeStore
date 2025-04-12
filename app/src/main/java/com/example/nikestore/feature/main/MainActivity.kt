package com.example.nikestore.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.cart.CountResponse
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.sevenlearn.nikestore.common.convertDpToPixel
import com.sevenlearn.nikestore.common.setupWithNavController
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {
    private var currentNavController: LiveData<NavController>? = null
    lateinit var bottomNavigationView: BottomNavigationView
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        AppCompatDelegate.setDefaultNightMode(if (viewModel.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangesEvent(countResponse: CountResponse) {
        val badge = bottomNavigationView.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.TOP_START
        badge.backgroundColor =
            MaterialColors.getColor(bottomNavigationView, android.R.attr.colorPrimary)
        badge.text = countResponse.count.toString()
        badge.verticalOffset = convertDpToPixel(14F, this).toInt()
        badge.isVisible = countResponse.count > 0
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragmentContainerView,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() == true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()
    }
}