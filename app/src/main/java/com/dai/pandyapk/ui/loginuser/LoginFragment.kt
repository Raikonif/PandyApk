package com.dai.pandyapk.ui.loginuser

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentLoginBinding
import com.dai.pandyapk.extfun.toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2
    private var showOneTapUI = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
//        firebaseUIAuth()
//        oneTapClient = Identity.getSignInClient(requireContext())

        binding.btnGoogleSignIn.setOnClickListener {
            googleSignInConfig()
//            signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build()
//                )
//                .build()
        }

    }

    private fun firebaseUIAuth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)

            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) activity?.toast("Welcome")
            }
        }.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        )
    }

    private fun googleSignInConfig() {
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//        val signInIntent: Intent = client.signInIntent
        signIn(googleSignInClient)
//        activity?.startActivityForResult(signInIntent, 1)
        // [END config_signin]
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!

                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText( requireContext(),"Algo Salio Mal", Toast.LENGTH_SHORT).show()
            }
        }
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                            auth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener(requireActivity()) { task ->
//                                    if (task.isSuccessful) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "signInWithCredential:success")
//                                        val user = auth.currentUser
//                                        updateUI(user)
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                        updateUI(null)
//                                    }
//                                }
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            Log.d(TAG, "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    when (e.statusCode) {
//                        CommonStatusCodes.CANCELED -> {
//                            Log.d(TAG, "One-tap dialog was closed.")
//                            // Don't re-prompt the user.
//                            showOneTapUI = false
//                        }
//                        else -> {
//                            Log.d(TAG, "Error getting ID token: " + e.statusCode)
//                        }
//
//                    }
//                }
//            }
//            RC_SIGN_IN -> {
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//
//                firebaseAuthWithGoogle(account.idToken!!)
//
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//            }
//        }
//        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)

                    val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
                    findNavController().navigate(action)

                } else {
                    // If sign in fails, display a message to the user.
                    updateUI(null)
                    activity?.toast("Error al Iniciar Sesion")
                }
            }.addOnFailureListener {
                activity?.toast("Error al Iniciar Sesion")
            }
    }
    // [END auth_with_google]

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(requireContext(), "Welcome ${user?.displayName}", Toast.LENGTH_SHORT).show()
    }


}
