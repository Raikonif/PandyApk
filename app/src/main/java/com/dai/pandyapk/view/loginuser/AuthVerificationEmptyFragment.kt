package com.dai.pandyapk.view.loginuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dai.pandyapk.R
import com.dai.pandyapk.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthVerificationEmptyFragment : Fragment(R.layout.fragment_auth_verification_empty) {
    private val mAuth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mAuth.currentUser == null)
        {
            val actionToLogin = AuthVerificationEmptyFragmentDirections
                .actionAuthVerificationEmptyFragmentToLoginFragment()
            findNavController().navigate(actionToLogin)
        }
        else
        {
            val actionToDashB = AuthVerificationEmptyFragmentDirections
                .actionAuthVerificationEmptyFragmentToDashboardFragment()
            findNavController().navigate(actionToDashB)
        }
//        activity?.finish()
    }

}