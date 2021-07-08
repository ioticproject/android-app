package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import com.project.iotic.R
import com.project.iotic.data.Repo
import com.project.iotic.data.model.SensorData
import com.project.iotic.utils.dateTimeFormatter
import com.project.iotic.utils.resStr
import com.project.iotic.utils.timeFormatter
import com.project.iotic.utils.toast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_sensor.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class SensorFragment : GFragment(R.layout.fragment_sensor) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type.text = resStr(R.string.sensor_type_label).replace("{type}", Repo.sensor!!.type)
        time.text = resStr(R.string.sensor_time_label).replace("{time}", Repo.sensor!!.timestamp)
        device.text = resStr(R.string.sensor_device_label).replace("{device}", Repo.device!!.name)

        GlobalScope.launch {
            val res = Repo.getSensorData(Repo.sensor!!)
            if (res.body != null) {
                setData(res.body.data)
            } else {
                toast(res.message)
            }
        }
    }

    private fun setData(data: List<SensorData>) {
        val reference = System.currentTimeMillis() / 1000


        val dataSet = LineDataSet(
            data.mapIndexed { index, it ->
                Entry(
                    (dateTimeFormatter.parse(it.timestamp)!!.time / 1000 - reference).toFloat(),
                    it.value.toFloat()
                )
            },
            "Data"
        )


        chart.data = LineData(dataSet)
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return timeFormatter.format(Date((value.toLong() + reference) * 1000))
            }
        }

        chart.description.isEnabled = false
        chart.invalidate()
    }
}