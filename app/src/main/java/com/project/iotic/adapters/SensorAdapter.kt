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
import com.project.iotic.data.Repo.device
import com.project.iotic.data.model.Sensor

class SensorAdapter(private val sensorList: List<Sensor>, private val fragment: Fragment) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onBindViewHolder(sensorViewHolder: SensorViewHolder, index: Int) {
        sensorViewHolder.typeTextView.text = sensorList[index].type
        sensorViewHolder.stateTextView.text = "Connected"
        sensorViewHolder.timeTextView.text = sensorList[index].timestamp
        sensorViewHolder.deviceTextView.text = device!!.name

        if (index % 2 == 1) {
            sensorViewHolder.itemView?.setBackgroundColor(fragment.resources.getColor(R.color.colorLight))
        } else {
            sensorViewHolder.itemView?.setBackgroundColor(fragment.resources.getColor(R.color.colorDiversity))
        }

        sensorViewHolder.parent.setOnClickListener {
            Repo.sensor = sensorList[index]
            if (fragment.javaClass?.simpleName == "SensorsFragment") {
                fragment.findNavController().navigate(R.id.action_sensorsFragment_to_sensorFragment)
            }
            else if (fragment.javaClass?.simpleName == "DeviceFragment"){
                fragment.findNavController().navigate(R.id.action_deviceFragment_to_sensorFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        return SensorViewHolder(LayoutInflater.from(fragment.context).inflate(R.layout.sensor_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    inner class SensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeTextView: TextView = view.findViewById(R.id.type)
        val stateTextView: TextView = view.findViewById(R.id.state)
        val timeTextView: TextView = view.findViewById(R.id.time)
        val deviceTextView: TextView = view.findViewById(R.id.time)
        val parent: View = view
    }
}