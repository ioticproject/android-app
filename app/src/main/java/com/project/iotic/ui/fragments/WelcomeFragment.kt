package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.project.iotic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : GFragment(R.layout.fragment_welcome){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}