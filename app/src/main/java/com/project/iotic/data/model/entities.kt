package com.project.iotic.data.model

import com.project.iotic.data.Repo
import com.squareup.moshi.Json


data class DeviceStartInfo(
    var apiKey: String,
    var id: String,
    var ssid: String,
    var pass: String,
    var host: String = Repo.host
)

data class DevicesResp(
    var devices: List<Device>,
)

data class SensorsResp(
    var sensors: List<Sensor>,
)

data class DeviceApiKey(
    var apiKey: String,
    var _id: String,
)

data class User(
    @field:Json(name = "_id")
    var id: String,
    var username: String,
    var email: String
)


data class LoginResp (
    @field:Json(name = "_id")
    var id: String,
    var username: String,
    var email: String,
    var access_token: String
)

data class Device(
    @field:Json(name = "_id")
    var id: String,
    var name: String,
    var description: String? = null,
    var id_user: String,
    var timestamp: String
)

data class Sensor(
    @field:Json(name = "_id")
    var id: String,
    var id_device: String,
    var id_user: String,
    var measure_unit: String?,
    var type: String,
    var timestamp: String
)


data class SensorData(
    @field:Json(name = "_id")
    var id: String,
    var id_sensor: String,
    var timestamp: String,
    var value: Double
)