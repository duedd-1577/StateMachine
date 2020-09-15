package com.danhdueexoictif.statemachine.utils

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment

/**
 * Hide keyboard.
 */
fun Fragment.hideKeyBoard() {
    activity?.apply {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this@hideKeyBoard.view?.windowToken, 0)
    }
}

/**
 * create animator from resource id.
 */
fun Fragment.createAnimator(@AnimatorRes animatorId: Int, target: View): Animator {
    val animator = AnimatorInflater.loadAnimator(context, animatorId)
    animator.setTarget(target)
    return animator
}
