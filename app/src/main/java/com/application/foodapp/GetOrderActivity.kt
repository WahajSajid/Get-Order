package com.application.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.application.foodapp.databinding.ActivityGetOrderBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION", "NAME_SHADOWING")
class GetOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetOrderBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var sectionsList: MutableList<Sections>
    private lateinit var databaseReference: DatabaseReference
    private val sectionsPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(this, mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_order)


        //Retrieving data from the intent and setting it to the text view
        val intent = intent
        val tableName = intent.getStringExtra("tableName")
        binding.tableNo.text = tableName

        sharedViewModel.tableName.value = tableName
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


        //Setting up tabLayout and viewPager
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabMode = TabLayout.MODE_FIXED

        //Creating object of FragmentStateAdapter to handle fragments
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter
        //Attaching tab layout with view pager on run time
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = sectionsPagerAdapter.sections[position].sectionName
//            tab.text = "Section"
        }.attach()


        databaseReference = FirebaseDatabase.getInstance().getReference("Sections")

        sectionsList = mutableListOf()


        fetchExistingSections()


    }


    private fun fetchExistingSections() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
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
                sectionsPagerAdapter.setSections(sectionsList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStop() {
        super.onStop()
        sectionsPagerAdapter.clearSections()
        viewPager.adapter = null
    }

}