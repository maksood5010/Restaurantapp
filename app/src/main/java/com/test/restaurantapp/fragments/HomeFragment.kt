package com.test.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.maqader.restaurantapp.viewmodel.HomeViewModel
import com.test.restaurantapp.R
import com.test.restaurantapp.datamodel.ProductModel
import com.test.restaurantapp.helpers.ProductListAdapter
import java.util.*

class HomeFragment : Fragment() {
    private var query: String = ""
    private var productListAdapter: ProductListAdapter? = null
    private val list: MutableList<ProductModel> = mutableListOf()
    private val allList: MutableList<ProductModel> = mutableListOf()

    private var tvNoItem: TextView? = null
    private lateinit var viewModel: HomeViewModel

    private fun showNoData(visible: Boolean) {
        if (visible) {
            tvNoItem!!.visibility = View.VISIBLE
        } else {
            tvNoItem!!.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            if (it != null) {
                onResponse(it.body()!!)
            }
        })

        val rvProducts: RecyclerView = view.findViewById(R.id.rvProducts)
        val etSearch: EditText = view.findViewById(R.id.etSearch)
        tvNoItem = view.findViewById(R.id.tvNoItem)

        productListAdapter = ProductListAdapter(requireContext(), list)
        rvProducts.adapter = productListAdapter

        etSearch.addTextChangedListener {
            searchByName(it.toString())
        }
        viewModel.getData(requireContext())

    }


    private fun searchByName(name: String?) {
        if (name == null) return
        query = name;
        val subList = ArrayList<ProductModel>()
        for (model in allList) {
            if (model.name.toLowerCase().contains(name)) {
                subList.add(model)
            }
        }
        list.clear()
        list.addAll(subList)
        showNoData(list.isEmpty())
        productListAdapter?.notifyDataSetChanged()
    }

    private fun onResponse(response: JsonArray) {
        val gson = Gson()
        val array: Array<ProductModel> = gson.fromJson(response, Array<ProductModel>::class.java)
        allList.clear()
        allList.addAll(array)
        searchByName(query)
    }


}