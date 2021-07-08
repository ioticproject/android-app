package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.project.iotic.R
import com.project.iotic.adapters.Wifi
import com.project.iotic.data.Repo
import kotlinx.android.synthetic.main.dialog_set_wifi.*

class SetWifiDialog(val onChanged: (wifi: Wifi) -> Unit) : GDialogFragment(R.layout.dialog_set_wifi) {
    companion object {
        fun show(onChanged: (wifi: Wifi) -> Unit) = SetWifiDialog(onChanged)
            .show(Repo.activity.supportFragmentManager, "Wifi Dialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defaultWifi = Repo.getDefaultWifi()

        if(defaultWifi != null) {
            wifi_field.setText(defaultWifi.ssid)
            pass_field.setText(defaultWifi.pass)
        }

        ok_button.setOnClickListener {
            val ssid = wifi_field.text?.toString()
            val pass = pass_field.text?.toString()

            if(ssid.isNullOrBlank() || pass.isNullOrBlank()){
                Toast.makeText(context,"Not enough information!", Toast.LENGTH_SHORT).show()
            } else {
                val newWifi = Wifi(ssid, "", pass)
                Repo.setDefaultWifi(newWifi)
                dismiss()
                onChanged(newWifi)
            }
        }

        cancel_button.setOnClickListener {
            dismiss()
        }
    }
}