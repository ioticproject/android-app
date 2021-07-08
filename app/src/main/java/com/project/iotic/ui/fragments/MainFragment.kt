package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.project.iotic.R
import com.project.iotic.data.Repo
import com.project.iotic.utils.resStr
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : GFragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hello_label.text = resStr(R.string.hello_label).replace("{username}",Repo.user!!.username)

        button_devices.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_devicesFragment)
        }

        button_sensors.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sensorsFragment)
        }

        button_statistics.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_statisticsFragment)
        }

        button_settings.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        button_logout.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
}