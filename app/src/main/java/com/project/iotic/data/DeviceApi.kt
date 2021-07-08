package com.project.iotic.data

import com.project.iotic.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface DeviceApi {
    @POST("start")
    suspend fun startDevice(@Body body: DeviceStartInfo): Response<Unit>
}