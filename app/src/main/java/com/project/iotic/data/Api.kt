package com.project.iotic.data

import com.project.iotic.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @POST("users/login")
    suspend fun login(@Body body: LoginReq): Response<LoginResp>

    @POST("users")
    suspend fun register(@Body body: RegisterReq): Response<User>

    @GET("users/{user_id}/devices/{device_id}/sensors/{sensor_id}/data")
    suspend fun getSensorData(
        @Header("Authorization") token: String = Repo.token!!,
        @Path(value = "user_id", encoded = true) userId: String = Repo.user!!.id,
        @Path(value = "device_id", encoded = true) deviceId: String,
        @Path(value = "sensor_id", encoded = true) sensorId: String
    ): Response<SensorDataResp>

    @GET("users/{user_id}")
    suspend fun getUser(
        @Header("Authorization") token: String = Repo.token!!,
        @Path(value = "user_id", encoded = true) userId: String = Repo.user!!.id
    ): Response<UserResp>


    @GET("users/{user_id}/devices/{device_id}/sensors")
    suspend fun getDeviceSensors(
        @Header("Authorization") token: String = Repo.token!!,
        @Path(value = "user_id", encoded = true) userId: String = Repo.user!!.id,
        @Path(value = "device_id", encoded = true) deviceId: String,
    ): Response<SensorsResp>

    @GET("users/{user_id}/devices")
    suspend fun getUserDevices(
        @Header("Authorization") token: String = Repo.token!!,
        @Path(value = "user_id", encoded = true) userId: String = Repo.user!!.id,
    ): Response<DevicesResp>

    @POST("users/{user_id}/devices")
    suspend fun createNewDevice(
        @Header("Authorization") token: String = Repo.token!!,
        @Path(value = "user_id", encoded = true) userId: String = Repo.user!!.id,
        @Body body: Any = Object()
    ): Response<DeviceApiKey>
}