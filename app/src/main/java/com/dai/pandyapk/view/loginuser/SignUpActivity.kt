package com.dai.pandyapk.view.loginuser

import com.dai.pandyapk.model.User
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dai.pandyapk.*
import com.dai.pandyapk.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.Manifest
import android.app.Activity
import android.net.Uri
import android.view.View
import com.dai.pandyapk.view.isValidateConfirmPassword
import com.dai.pandyapk.view.isValidateEmail
import com.dai.pandyapk.view.isValidatePassword
import com.dai.pandyapk.view.toast
import com.google.firebase.storage.ktx.storage

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val mAuth: FirebaseAuth by lazy{ Firebase.auth }

    private val REQUEST_PERMISSION_CODE = 100
    private val REQUEST_IMAGE_GALLERY = 101
    private var selectedPhotoUri: Uri? = null
//    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectphotoButtonProfile.setOnClickListener {

            checkPermissions()

        }
//boton que verifica los datos introducidos, entonces ejecuta la funcion signUpByEmail
        binding.btnAccountCreated.setOnClickListener{
            val email = binding.editTextNewUser.text.toString()
            val password = binding.editTextNewPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            val nicknameUser = binding.editTextId.text.toString()

            if(isValidateEmail(email)&&isValidatePassword(password)&&isValidateConfirmPassword(password, confirmPassword)){
                signUpByEmail(email,password,nicknameUser)
                Toast.makeText(this, "Se le ha enviado Un E-mail de verificación a su Correo",Toast.LENGTH_LONG).show()
            }
            else{
                toast("Ha introducido datos erroneos, por favor vuelve a Intentarlo Nuevamente")
            }
        }

//        boton permite retornar al login
        binding.btnBackToLogin.setOnClickListener {
            val backToLoginActivity = Intent(this, LoginActivity::class.java)
            backToLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(backToLoginActivity)
        }

//        Mostrar error en el Editext para determinar si el dato es introducido de forma previa a la creacion de la cuenta
        binding.editTextNewUser.validate {
            binding.editTextNewUser.error = if (isValidateEmail(it)) {
            null
            }
            else {
                "E-mail No Valido"
            }
        }

        binding.editTextNewPassword.validate {
            binding.editTextNewUser.error = if (isValidatePassword(it)) null else "Password debe Contener al menos minuscula, mayuscula, numero y caracter especial"

        }
        binding.editTextConfirmPassword.validate {
            val password = binding.editTextNewPassword.text.toString()
            binding.editTextConfirmPassword.error = if (isValidateConfirmPassword(password, it)) {
                null
            } else {
                "El password y su confirmación no coinciden"
            }
        }
    }

    private fun checkPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//                permisos no aceptado por el momento
//                requestExternalStoragePermision()
                openGallery()
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
            }
        }
        else{
//            abrir galeria
            openGallery()
        }
    }

    private fun requestExternalStoragePermision() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
//            el usuario ya ha rechazado los permisos
        }
        else{
//            pedir permisos
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery()
            }
            else{
                toast("Permisos rechazados por primera vez")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_GALLERY && data != null){
            if(resultCode == Activity.RESULT_OK){
                selectedPhotoUri = data.data
                val img = binding.selectphotoImageviewProfile
                img.setImageURI(selectedPhotoUri)
                img.visibility = View.VISIBLE

                val btnProfile = binding.selectphotoButtonProfile
                btnProfile.alpha = 0f
            }
            else{

                toast("no se selecciono ninguna foto de la galeria")
            }
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }



    //    funcion que permite crear una cuenta en base a los datos introducidos
    private fun signUpByEmail(email: String, password: String, nickname: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) {
                    uploadImgProfile(email, nickname)

                    val goToLoginActivity = Intent(this, LoginActivity::class.java)
                    goToLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(goToLoginActivity)

                }
            } else {
                toast("Ha ocurrido un Error inesperado, por favor intenta nuevamente")
            }
        }.addOnFailureListener{
            toast("Una Excepcion ha ocurrido")
        }
    }

    private fun uploadImgProfile(email: String, nickname: String){
//        val filename = UUID.randomUUID().toString()
        val uid = mAuth.uid ?:""
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("/images/users/$uid/imgProfile")

        imageRef.putFile(selectedPhotoUri!!).addOnCompleteListener{ Task ->

            imageRef.downloadUrl.addOnSuccessListener { Uri ->

                createUserFirebaseFirestore(email, nickname,Uri.toString())
            }
        }
    }

    private fun createUserFirebaseFirestore(email: String, nickname: String, imgProfile: String){

        val uid = mAuth.uid ?: ""
        val refDb = Firebase.firestore

        val user = User(uid, email, nickname, imgProfile)

        refDb.collection("user").document("$email").set(user).addOnCompleteListener{

            toast("Se creado la cuenta satisfactoriamente")


        }.addOnFailureListener{
            toast("No se pudo crear la cuenta correctamente")
        }
    }

}