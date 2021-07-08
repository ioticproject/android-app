package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iotic.R
import com.project.iotic.adapters.SensorAdapter
import com.project.iotic.data.Repo
import com.project.iotic.data.model.Sensor
import com.project.iotic.utils.launchMain
import com.project.iotic.utils.resStr
import kotlinx.android.synthetic.main.fragment_device.*

class DeviceFragment: GFragment(R.layout.fragment_device) {
    val sensors = arrayListOf<Sensor>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.text = resStr(R.string.device_name_label).replace("{name}", Repo.device!!.name)
        description.text = resStr(R.string.device_description_label).replace("{description}", Repo.device!!.description ?: "")
        time.text = resStr(R.string.device_time_label).replace("{time}", Repo.device!!.timestamp)

        sensors_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sensors_recycler_view.adapter = SensorAdapter(sensors, this)


        loadSensors()
    }

    fun loadSensors() = launchMain{
        val newSensors = Repo.getDeviceSensors(Repo.device!!.id).body!!.sensors
        sensors.clear()
        sensors.addAll(newSensors)
        sensors_recycler_view?.adapter?.notifyDataSetChanged()
    }
}