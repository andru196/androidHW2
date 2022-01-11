package com.example.poke.presentation.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.poke.R

fun FragmentManager.navigate(fragment: Fragment) {
    commit (allowStateLoss = true) {
        setCustomAnimations(
            R.anim.slide_from_right,
            R.anim.slide_to_left,
            R.anim.slife_from_left,
            R.anim.slide_to_right)
        replace(R.id.main_activity_container, fragment)
        addToBackStack(null)
    }
}