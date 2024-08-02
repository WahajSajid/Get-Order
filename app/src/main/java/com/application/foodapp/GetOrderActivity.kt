package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.application.foodapp.databinding.ActivityGetOrderBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION", "NAME_SHADOWING")
class GetOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetOrderBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var sectionsList: MutableList<Sections>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var app: MyApp
    private val sectionsPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(this, mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_order)

        //Initializing the firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Sections")

        //Initializing app
        app = application as MyApp

        val foodItems = app.foodItems


        //Retrieving data from the intent and setting it to the text view
        val intent = intent
        val tableName = intent.getStringExtra("tableName")
        binding.tableNo.text = tableName
        sharedViewModel.tableName.value = tableName

        retrieveOrder(tableName!!, foodItems)

        //Setting up on click listener for imageButton on the toolbar
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, ConfirmOrderActivity::class.java)
            intent.putExtra("tableName", tableName)
            startActivity(intent)
        }


        //Setting up toolbar action button
        val toolBar = binding.toolBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Get Order for $tableName"

        binding.spinnerLayout.visibility = View.VISIBLE


        //Setting up tabLayout and viewPager
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        //Initializing sectionsList
        sectionsList = mutableListOf()
        checkInternetAvailability()
        binding.refreshButton.setOnClickListener {
            binding.noInternetConnectionLayout.visibility = View.GONE
            checkInternetAvailability()
        }
    }


    //This function retrieve the data from the firebase database if there are any order already placed or not.
    private fun retrieveOrder(tableName: String, foodItems: ArrayList<OrderItems>) {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<OrderItems>>() {}
        val databaseReference = firebaseDatabase.getReference().child("Tables").child(tableName)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val order = snapshot.child("Orders").getValue(genericTypeIndicator)
                foodItems.addAll(order!!)
                object : LoadDataCallBack {
                    override fun onDataLoaded() {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GetOrderActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Function which check availability of internet and behave accordingly.
    private fun checkInternetAvailability() {
        //Checking if network available or not if available load the data from the firebase otherwise show error
        if (NetworkUtil.isNetworkAvailable(this)) {
            HasInternetAccess.hasInternetAccess(object : HasInternetAccessCallback {
                override fun onInternetAccessAvailable() {
                    runOnUiThread {
                        binding.noInternetConnectionLayout.visibility = View.GONE
                        fetchExistingSections(tabLayout, viewPager)
                    }
                }

                override fun onInternetAccessNotAvailable() {
                    runOnUiThread {
                        Toast.makeText(
                            this@GetOrderActivity,
                            "Connection Timeout",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.noInternetConnectionLayout.visibility = View.VISIBLE
                    }
                }
            })
        } else binding.noInternetConnectionLayout.visibility = View.VISIBLE
    }

    private fun fetchExistingSections(tabLayout: TabLayout, viewPager: ViewPager2) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                sectionsList.clear()
                for (sectionSnapshot in snapshot.children) {
                    val sectionName =
                        sectionSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val itemsSnapshot = sectionSnapshot.child("Items")
                    val itemsMap = itemsSnapshot.children.associate {
                        it.key!! to it.getValue(Items::class.java)!!
                    }
                    val section = Sections(sectionName, itemsMap)
                    sectionsList.add(section)
                }
                if (sectionsList.isNotEmpty()) {
                    binding.sectionListIsEmptyImageView.visibility = View.GONE
                    binding.spinnerLayout.visibility = View.GONE
                    binding.tabLayout.visibility = View.VISIBLE
                    sectionsPagerAdapter.setSections(sectionsList)
                    sectionsPagerAdapter.notifyDataSetChanged()
                    updateTabLayoutMode()
                    viewPager.adapter = sectionsPagerAdapter
                    //Attaching tab layout with view pager on run time
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = sectionsPagerAdapter.sections[position].sectionName
                    }.attach()
                } else {
                    sectionsPagerAdapter.notifyDataSetChanged()
                    binding.sectionListIsEmptyImageView.visibility = View.VISIBLE
                    binding.spinnerLayout.visibility = View.GONE
                    binding.tabLayout.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GetOrderActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        app.foodItems.clear()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateTabLayoutMode() {
        val screenWidthDp = getScreenWidthInDp()
        val tabMaxWidth = 72 // Approximate width of each tab in dp
        val maxTabsWithoutScroll = screenWidthDp / tabMaxWidth

        tabLayout.tabMode = if (sectionsPagerAdapter.itemCount <= maxTabsWithoutScroll) {
            TabLayout.MODE_FIXED
        } else {
            TabLayout.MODE_SCROLLABLE
        }

        // Adjust tab width for fixed mode
        if (tabLayout.tabMode == TabLayout.MODE_FIXED && sectionsPagerAdapter.itemCount > 0) {
            for (i in 0 until tabLayout.tabCount) {
                val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
                tab.layoutParams.width = 0
                tab.layoutParams = tab.layoutParams
            }
        }

    }


    //Function to get the width length of mobile screen on which  the app is running.
    private fun getScreenWidthInDp(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.widthPixels / displayMetrics.density).toInt()
    }

}