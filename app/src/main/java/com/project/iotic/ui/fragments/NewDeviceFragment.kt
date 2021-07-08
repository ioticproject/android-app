package com.project.iotic.ui.fragments

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iotic.R
import com.project.iotic.adapters.Wifi
import com.project.iotic.adapters.WifiAdapter
import com.project.iotic.data.Repo
import com.project.iotic.data.model.DeviceStartInfo
import com.project.iotic.utils.launchMain
import com.project.iotic.utils.toast
import com.thanosfisherman.wifiutils.WifiUtils
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener
import kotlinx.android.synthetic.main.fragment_new_device.*
import kotlinx.coroutines.delay

class NewDeviceFragment : GFragment(R.layout.fragment_new_device) {

    val wifiList = arrayListOf<Wifi>()
    var defaultWifi: Wifi? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        device_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        device_recycler_view.adapter = WifiAdapter(wifiList, this) { onDeviceClick(it) }

        wifi_edit_button.setOnClickListener { setDefaultWifiAction() }

        defaultWifi = Repo.getDefaultWifi()
        if (defaultWifi != null) {
            defaultWifiSetMode()
        } else {
            noDefaultWifiSetMode()
        }
    }


    override fun onResume() {
        super.onResume()
        if (!checkGPS()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    fun setDefaultWifiAction() {
        SetWifiDialog.show {
            defaultWifi = it
            defaultWifiSetMode()
        }
    }

    fun noDefaultWifiSetMode() {
        wifi_msg.text = "There is no default wifi network set!"
        wifi_name.isVisible = false
        wifi_edit_button.isVisible = false
        bottom_button.text = "Set the default wifi network"
        bottom_button.setOnClickListener { setDefaultWifiAction() }
    }

    fun defaultWifiSetMode() {
        wifi_msg.text = "The device will be connected to"
        wifi_name.isVisible = true
        wifi_name.text = defaultWifi!!.ssid
        wifi_edit_button.isVisible = true
        bottom_button.text = "Start scanning for devices"
        bottom_button.setOnClickListener { startScan() }
    }

    fun checkGPS() =
        (requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager)
            .isProviderEnabled(LocationManager.GPS_PROVIDER)


    fun onDeviceClick(wifi: Wifi) {
        progress_bar.isVisible = true

        launchMain {
            val device = Repo.createNewDevice().body

            if (device?.apiKey == null) {
                toast("Failed to create new device!")
                progress_bar.isVisible = false
                return@launchMain
            }

            delay(1000)

            WifiUtils.withContext(requireActivity().applicationContext).connectWith(wifi.ssid, wifi.bssid,"123321123")
                .onConnectionResult(object : ConnectionSuccessListener {
                    override fun success() {
                        launchMain {
                            toast("Connected Successfully!\nConfiguring...")
                        }

                        onDeviceConnected(
                            DeviceStartInfo(
                                apiKey = device.apiKey,
                                id = device._id,
                                ssid = defaultWifi!!.ssid,
                                pass = defaultWifi!!.pass!!
                            )
                        )
                    }

                    override fun failed(errorCode: ConnectionErrorCode) {
                        launchMain {
                            toast("Failed to connect")
                        }
                        progress_bar.isVisible = false
                    }
                })
                .start()
        }
    }

    fun onDeviceConnected(deviceStartInfo: DeviceStartInfo) {

        suspend fun afterReconnect(){
            delay(8000) //Wait for device auto-configuring
            Repo.getUserDevices()
            progress_bar.isVisible = false
            findNavController().navigateUp()
        }

        launchMain {

            //Wait for dhcp
            delay(2000)

            Repo.initDevice(deviceStartInfo)

            WifiUtils.withContext(requireContext())
                .connectWith(deviceStartInfo.ssid, deviceStartInfo.pass)
                .onConnectionResult(object : ConnectionSuccessListener {
                    override fun success() {
                        launchMain {
                            toast("Device configured successfully!")
                            afterReconnect()
                        }
                    }

                    override fun failed(errorCode: ConnectionErrorCode) {
                        launchMain {
                            toast("Failed to reconnect back!")
                            afterReconnect()
                        }
                    }
                }).start()
        }

    }

    fun startScan() {
        progress_bar.isVisible = true
        toast("Scanning for devices...")
        WifiUtils.withContext(requireContext()).enableWifi {
            WifiUtils.withContext(requireContext()).scanWifi { newWifiList ->
                wifiList.clear()
                wifiList.addAll(newWifiList.filter { it.SSID.contains("Iotic") }
                    .map { Wifi(it.SSID, it.BSSID) })
                device_recycler_view?.adapter?.notifyDataSetChanged()
                progress_bar?.isVisible = false
            }.start()
        }
    }
}