package com.dai.pandyapk.view.loginuser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dai.pandyapk.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainEmptyActivity:AppCompatActivity() {
        private val mAuth: FirebaseAuth = Firebase.auth

        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)

// Permite Verificar Si El Usuario Esta Logeado, y asi determinar a que activity debe ir, ademas de finalizar el proceso para no volver al MainEmptyActivity
            if (mAuth.currentUser == null){
                val goToActivityLogin = Intent(this, LoginFragment::class.java)
                goToActivityLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(goToActivityLogin)
            }
            else{
                val goToActivityMain = Intent(this, MainActivity::class.java)
                goToActivityMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(goToActivityMain)
            }
            finish()
        }

}