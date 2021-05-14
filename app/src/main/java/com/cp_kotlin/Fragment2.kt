package com.cp_kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.RecyclerView.Result
import com.cp_kotlin.RecyclerView.ResultAdapter
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.result_recycler.*
import java.util.*

class Fragment2 : Fragment() {
    var name = ArrayList<String>()
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<ResultAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.result_recycler, container, false)
        return rootView
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        for (i in 0..10) {
            name.add("Result $i")
        }

        recyclerViewResults.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ResultAdapter(name)
        }
    }
}