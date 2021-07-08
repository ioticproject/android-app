package com.project.iotic.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.project.iotic.R
import com.project.iotic.data.Repo
import com.project.iotic.utils.launchMain
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : GFragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_button.setOnClickListener {
            launchMain {
                val userResp = Repo.login(
                        username_field.text.toString(),
                        password_field.text.toString()
                )

                if (userResp.message.isNotEmpty()) {
                    val toast: Toast = Toast.makeText(context, userResp.message, Toast.LENGTH_SHORT)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    toast.show()
                }
                else {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }
        }

        register_button.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        button.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_aboutFragment)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("qwe", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.w("qwe", "new token $token")
        })

        checkGooglePlayServices()
    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("qwe", "Notification Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i("qwe", "Notification Success")
            true
        }
    }
}