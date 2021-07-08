package com.project.iotic.data.model

data class LoginReq(
    var username: String,
    var password: String
)

data class RegisterReq(
    var username: String,
    var email: String,
    var password: String
)

data class UserResp(
    var users: List<User>,
    var devices: List<Device>,
    var sensors: List<Sensor>
)

data class SensorDataResp(
    var data: List<SensorData>
)
