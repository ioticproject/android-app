package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


//Generic Fragment
abstract class GDialogFragment(val layoutId: Int) : DialogFragment(), OnBackPressListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutId, container, false)

    override fun onBackPressed() = true
}