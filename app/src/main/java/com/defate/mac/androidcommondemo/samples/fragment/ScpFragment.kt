package com.defate.mac.androidcommondemo.samples.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_scp.*
import com.defate.mac.androidcommondemo.R

class ScpFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scp_list_btn.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_fragment_scp_to_scpListFragment)
        }
    }


}