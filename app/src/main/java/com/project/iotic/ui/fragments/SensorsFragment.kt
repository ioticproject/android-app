package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iotic.R
import com.project.iotic.adapters.SensorAdapter
import com.project.iotic.data.Repo
import com.project.iotic.data.model.Sensor
import kotlinx.android.synthetic.main.fragment_sensors.*

class SensorsFragment : GFragment(R.layout.fragment_sensors) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun setUpSensorsRecyclerView(sensors: List<Sensor>) {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val recyclerAdapter = context?.let { SensorAdapter(sensors, this) }
            sensors_menu_recycler_view.layoutManager = layoutManager
            sensors_menu_recycler_view.adapter = recyclerAdapter
        }

        setUpSensorsRecyclerView(arrayListOf())
    }

}