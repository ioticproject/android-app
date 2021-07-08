package com.project.iotic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

interface OnBackPressListener {
    fun onBackPressed(): Boolean
}


//Generic Fragment
abstract class GFragment(val layoutId: Int) : Fragment(), OnBackPressListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutId, container, false)

    override fun onBackPressed() = true
}