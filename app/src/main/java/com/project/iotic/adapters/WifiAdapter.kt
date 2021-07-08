package com.project.iotic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.project.iotic.R

data class Wifi(val ssid: String,val bssid: String, val pass: String? = null)

class WifiAdapter(
    private val wifiList: List<Wifi>,
    private val fragment: Fragment,
    private val onClick: (wifi: Wifi) -> Unit
) : RecyclerView.Adapter<WifiAdapter.WifiViewHolder>() {

    override fun onBindViewHolder(deviceViewHolder: WifiViewHolder, index: Int) {
        deviceViewHolder.nameTextView.text = wifiList[index].ssid

        if (index % 2 == 1) {
            deviceViewHolder.itemView.setBackgroundColor(fragment.resources.getColor(R.color.colorLight))
        } else {
            deviceViewHolder.itemView.setBackgroundColor(fragment.resources.getColor(R.color.colorDiversity))
        }

        deviceViewHolder.parent.setOnClickListener { onClick(wifiList[index]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        return WifiViewHolder(
            LayoutInflater.from(fragment.context)
                .inflate(R.layout.device_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return wifiList.size
    }

    inner class WifiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val parent: View = view
    }
}