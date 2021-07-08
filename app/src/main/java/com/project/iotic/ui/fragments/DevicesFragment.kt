package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iotic.R
import com.project.iotic.adapters.DeviceAdapter
import com.project.iotic.data.Repo
import com.project.iotic.data.model.Device
import com.project.iotic.utils.launchMain
import kotlinx.android.synthetic.main.fragment_devices.*

class DevicesFragment : GFragment(R.layout.fragment_devices) {

    val devices = arrayListOf<Device>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        device_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        device_recycler_view.adapter = DeviceAdapter(devices, this)
        swipe_refresh_layout.setOnRefreshListener { loadDevices() }
        add_device_button.setOnClickListener { findNavController().navigate(R.id.action_devicesFragment_to_newDeviceFragment) }

        loadDevices()
    }

    private fun loadDevices() = launchMain {
        swipe_refresh_layout?.isRefreshing = true

        val newDevices = Repo.getUserDevices().body!!.devices
        devices.clear()
        devices.addAll(newDevices)

        device_recycler_view?.adapter?.notifyDataSetChanged()
        swipe_refresh_layout?.isRefreshing = false
    }

}