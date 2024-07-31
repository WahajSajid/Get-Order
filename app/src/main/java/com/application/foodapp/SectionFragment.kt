package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.application.foodapp.databinding.FragmentSectionBinding
import kotlinx.coroutines.sync.Mutex

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
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_section, container, false)

        val recyclerView = binding.recyclerView
        val layout = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layout

        //Initializing the sectionList
        sectionsList = mutableListOf()

        section = arguments?.getParcelable(ARG_SECTION)!!
        if (section.items.isNotEmpty()) {

            val myApp = requireActivity().application as MyApp

            binding.noMenuItemsAddedYet.visibility = View.GONE
            section.let {
                adapter = ItemsFoodAdapter(it.items.values.toList(), requireContext())
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()

                adapter.itemClickListener(object : ItemsFoodAdapter.OnItemClickListener {
                    override fun addItemClickListener(
                        availabilityTextView: TextView,
                        nameTextView: TextView,
                        priceTextView: TextView,
                        quantityTextView: TextView,
                        position: Int,
                    ) {
                        val availability = availabilityTextView.text.toString()
                        val itemName = nameTextView.text.toString()
                        val price = priceTextView.text.toString()
                        val quantity = quantityTextView.text.toString().toInt()
                        val foodItem = FoodItem(itemName, price,availability)
                        val orderItem = OrderItems(foodItem,quantity,false)
                        myApp.foodItems.add(orderItem)
                        Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                    }

                    override val mutex: Mutex
                        get() = Mutex()
                })

            }
        } else binding.noMenuItemsAddedYet.visibility = View.VISIBLE


        return binding.root
    }

}
