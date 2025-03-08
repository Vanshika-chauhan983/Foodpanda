package com.vanshika.foodpanda.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.foodpanda.R
import com.vanshika.foodpanda.databinding.HomeBinding
import com.vanshika.foodpanda.fragments.CartFragment
import com.vanshika.foodpanda.fragments.FAQsFragment
import com.vanshika.foodpanda.fragments.FavouriteFragment
import com.vanshika.foodpanda.fragments.HistoryFragment
import com.vanshika.foodpanda.fragments.HomeFragment
import com.vanshika.foodpanda.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigation: NavigationView
    private lateinit var binding: HomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

        val toolbar= findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout=findViewById(R.id.drawerlayout)
        val toggle=ActionBarDrawerToggle(this,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        openHome()

        navigation=findViewById(R.id.navigation)
        navigation.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home -> {
                    openHome()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FavouriteFragment()
                    ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Favourite Restaurants"
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "My Profile"
                }
                R.id.history -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HistoryFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title= "Order History"
                }
                R.id.FAQs -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, FAQsFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title= "FAQs"
                }
                R.id.cart -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, CartFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title= "My Cart"
                }
                R.id.logout -> {
                    firebaseAuth.signOut()
                    val intent=Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    private fun openHome() {
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title="Home"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        drawerLayout=findViewById(R.id.drawerlayout)
        val id = item.itemId
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}