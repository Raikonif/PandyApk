package com.dai.pandyapk.ui.loginuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dai.pandyapk.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthVerificationEmptyFragment : Fragment(R.layout.fragment_auth_verification_empty) {
    private val mAuth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mAuth.currentUser != null)
        {
            val actionToDashB = AuthVerificationEmptyFragmentDirections
                .actionAuthVerificationEmptyFragmentToDashboardFragment()
            findNavController().navigate(actionToDashB)
        }
        else
        {
            val actionToLogin = AuthVerificationEmptyFragmentDirections
                .actionAuthVerificationEmptyFragmentToLoginFragment()
            findNavController().navigate(actionToLogin)
        }
//        activity?.finish()
    }

}