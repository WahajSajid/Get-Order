package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.application.foodapp.databinding.FragmentFoodMenuBinding
import com.application.foodapp.databinding.FragmentSectionBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class SectionFragment : Fragment() {
    private lateinit var binding: FragmentSectionBinding
    private lateinit var section: Sections
    private lateinit var adapter: ItemsFoodAdapter
    private lateinit var sectionsList: MutableList<Sections>

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(section: Sections): SectionFragment {
            val fragment = SectionFragment()
            val args = Bundle().apply {
                putParcelable(ARG_SECTION, section)
            }
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_section, container, false)

        val recyclerView = binding.recyclerView
        val layout = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layout

        //Initializing the sectionList
        sectionsList = mutableListOf<Sections>()

        section = arguments?.getParcelable(ARG_SECTION)!!
        section.let {
            adapter = ItemsFoodAdapter(it.items.values.toList())
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        Toast.makeText(context, section.sectionName, Toast.LENGTH_SHORT).show()
        listenForItemUpdates()
        return binding.root
    }




    private fun listenForItemUpdates() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Sections").child(section.sectionName)

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                for(data in snapshot.children){
                    val itemsData = data.getValue(Items::class.java)
                    if(itemsData != null){
                        section.items = itemsData
                    }
                }
                val itemsMap = snapshot.children.associate {
                    it.key!! to it.getValue(Items::class.java)!!
                }
                section.items = itemsMap
                adapter.updateItems(section.items.values.toList())
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load items", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
