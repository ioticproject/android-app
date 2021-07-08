package com.project.iotic.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.project.iotic.adapters.Wifi
import com.project.iotic.data.model.*
import com.project.iotic.ui.MainActivity
import com.project.iotic.utils.isOK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class ApiResult<T>(
    val body: T?,
    val code: Int,
    val message: String = ""
)

object Repo {
    private lateinit var server: Api
    var user: User? = null
    var token: String? = null
    var device: Device? = null
    var sensor: Sensor? = null
    var host: String = "iotic.mywire.org";
//    var host: String = "192.168.0.105";

    lateinit var activity: MainActivity
    lateinit var sharedPref: SharedPreferences

    fun init(activity: MainActivity) {
        this.activity = activity
        this.sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        server = Retrofit.Builder()
            .baseUrl("http://$host:5000/api/")
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(Api::class.java)
    }

    fun getDefaultWifi(): Wifi? {
        val ssid = sharedPref.getString("DEF_SSID", null)
        val pass = sharedPref.getString("DEF_PASS", null)
        if (ssid != null && pass != null) {
            return Wifi(ssid, "", pass)
        }
        return null
    }

    fun setDefaultWifi(wifi: Wifi) {
        if (wifi.pass != null) {
            sharedPref.edit(commit = true) {
                putString("DEF_SSID", wifi.ssid)
                putString("DEF_PASS", wifi.pass)
            }
        }
    }

    suspend fun createNewDevice() = makeReq { server.createNewDevice() }

    suspend fun initDevice(startInfo: DeviceStartInfo): ApiResult<out Unit> {
        val device = Retrofit.Builder()
            .baseUrl("http://192.168.4.1/")
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(DeviceApi::class.java)

        return makeReq {
            device.startDevice(startInfo)
        }
    }

    suspend fun login(login: String, password: String) = makeReq {
        val resp = server.login(LoginReq(login, password))
        if (resp.isOK()) {
            val body = resp.body()!!
            user = User(
                id = body.id,
                username = body.username,
                email = body.email
            )
            token = "jwt " + body.access_token
        }
        resp
    }

    suspend fun getDeviceSensors(deviceId: String) =
        makeReq { server.getDeviceSensors(deviceId = deviceId) }

    suspend fun getUserDevices() = makeReq { server.getUserDevices() }

    suspend fun register(login: String, email: String, password: String) = makeReq {
        server.register(RegisterReq(login, email, password))
    }

    suspend fun getSensorData(sensor: Sensor) = makeReq {
        server.getSensorData(deviceId = sensor.id_device, sensorId = sensor.id)
    }

    suspend fun <T> makeReq(req: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
        val resp = req()

        if (resp.isOK()) {
            return@withContext ApiResult(resp.body(), resp.code())
        }

        val errorMsg: String = JSONObject(resp.errorBody()?.string()!!)["error"].toString()
        return@withContext ApiResult(null, resp.code(), errorMsg)
    }
}
