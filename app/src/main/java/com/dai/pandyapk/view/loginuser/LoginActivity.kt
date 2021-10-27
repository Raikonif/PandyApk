package com.dai.pandyapk.view.loginuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dai.pandyapk.*
import com.dai.pandyapk.databinding.ActivityLoginBinding
import com.dai.pandyapk.view.MainActivity
import com.dai.pandyapk.view.isValidateEmail
import com.dai.pandyapk.view.isValidatePassword
import com.dai.pandyapk.view.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val mAuth: FirebaseAuth by lazy { Firebase.auth}
//    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//boton lleva al activity principal, si el email y password son de una cuenta existente y el email ha sido verificado
        binding.btnGoActivityMain.setOnClickListener {

            val email = binding.editTextTextUser.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (isValidateEmail(email)&&isValidatePassword(password)) {
                userLogin(email, password)
            }
            else{
                toast("confirma que todos los datos introducidos son correctos")
            }
        }
//boton que lleva al ForgotPasswordActivity
        binding.btnGoResetPassword.setOnClickListener {

            val goToForgotPasswordActivity = Intent(this, ForgotPasswordActivity::class.java)
            goToForgotPasswordActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(goToForgotPasswordActivity)
        }
        binding.textSignUpAccount.setOnClickListener{

            val goToSignUpActivity = Intent(this, SignUpActivity::class.java)
            goToSignUpActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(goToSignUpActivity)
        }
    }

    private fun userLogin(email: String, password:String){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    if(mAuth.currentUser!!.isEmailVerified){
                        val goToMainActivity = Intent(this, MainActivity::class.java)
                        goToMainActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(goToMainActivity)
                    }
                    else{
                        toast("Confirma tu E-Mail primero")
                    }
                }
                else{
                    toast("Ha Ocurrido Un Error Inesperado, Intente Nuevamente")
                }
        }
            .addOnFailureListener{
                toast("error al iniciar Sesion")
            }
    }
}