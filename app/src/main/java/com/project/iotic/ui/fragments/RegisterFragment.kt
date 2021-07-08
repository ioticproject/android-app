package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.project.iotic.R
import com.project.iotic.data.Repo
import com.project.iotic.utils.hideKeyboard
import com.project.iotic.utils.launchMain
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : GFragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_button.setOnClickListener {
            launchMain {
                val resp = Repo.register(
                        username_field.text.toString(),
                        email_field.text.toString(),
                        password_field.text.toString()
                )

                hideKeyboard()

                if (resp.body != null) {
                    Repo.user = resp.body
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                } else {
                    Toast.makeText(context, resp.message, Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }
}