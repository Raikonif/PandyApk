package com.dai.pandyapk.loginuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dai.pandyapk.databinding.ActivityForgotPasswordBinding
import com.dai.pandyapk.isValidateEmail
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
//    private val mAuth: FirebaseAuth by lazy { Firebase.auth}
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding!!.btnResetPassword.setOnClickListener{
            val email = binding.editTextEmailVerification.text.toString()

            if (isValidateEmail(email)){
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                    Toast.makeText(this, "Un E-mail Fue Enviado para resetear tu password", Toast.LENGTH_SHORT).show()
                    val goToLoginActivity = Intent(this, LoginActivity::class.java)
                    startActivity(goToLoginActivity)
                    goToLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
            else{
                Toast.makeText(this, "E-mail Invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}