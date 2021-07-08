package com.project.iotic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.iotic.R
import com.project.iotic.data.Repo
import com.project.iotic.data.model.Device

class DeviceAdapter(private val deviceList: List<Device>, private val fragment: Fragment) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    override fun onBindViewHolder(deviceViewHolder: DeviceViewHolder, index: Int) {
        deviceViewHolder.nameTextView.text = deviceList[index].name
        deviceViewHolder.descriptionTextView.text = deviceList[index].description
        deviceViewHolder.timeTextView.text = deviceList[index].timestamp

        if (index % 2 == 1) {
            deviceViewHolder.itemView?.setBackgroundColor(fragment.resources.getColor(R.color.colorLight))
        } else {
            deviceViewHolder.itemView?.setBackgroundColor(fragment.resources.getColor(R.color.colorDiversity))
        }

        deviceViewHolder.parent.setOnClickListener {
            Repo.device = deviceList[index]
            fragment.findNavController().navigate(R.id.action_devicesFragment_to_deviceFragment2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(LayoutInflater.from(fragment.context).inflate(R.layout.device_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    inner class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val descriptionTextView: TextView = view.findViewById(R.id.description)
        val timeTextView: TextView = view.findViewById(R.id.time)
        val parent: View = view
    }
}