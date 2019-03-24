package com.defate.mac.androidcommondemo.samples.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.defate.mac.androidcommondemo.R
import com.defate.mac.androidcommondemo.samples.adapter.MListAdapter
import com.defate.mac.androidcommondemo.samples.adapter.MViewHolder
import com.defate.mac.androidcommondemo.samples.room.Scp
import com.defate.mac.androidcommondemo.samples.utils.InjectorUtils
import com.defate.mac.androidcommondemo.samples.viewmodel.ScpListViewModel
import kotlinx.android.synthetic.main.fragment_scp_list.*

class ScpListFragment : Fragment() {

    lateinit var viewModel: ScpListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scp_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = InjectorUtils.provideScpListViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, factory).get(ScpListViewModel::class.java)


        recyclerList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerList.adapter = MListAdapter<String>(
            items = listOf("123","453","345"),
            layoutCallBack = {
                R.layout.items_normal_tv },
            convertCallBack = { mViewHolder: MViewHolder, s: String, i: Int ->
                mViewHolder.getView<TextView>(R.id.item_tv)?.setText(s + "position = i")
                mViewHolder.getView<TextView>(R.id.item_tv_content)?.setText("aaaaa")
                mViewHolder.getView<Button>(R.id.item_btn)?.setText(s)
                mViewHolder.getView<Button>(R.id.item_btn)?.setOnClickListener {
                    Navigation.findNavController(it).popBackStack()
                }
            })

        subscribeUi(recyclerList.adapter as MListAdapter<Scp>)
    }

    fun subscribeUi(adapter: MListAdapter<Scp>){
         viewModel.getAllScp().observe(viewLifecycleOwner, Observer {
                it -> it?.let { adapter.setData(it) }
        })
    }


}
