package com.dai.pandyapk

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resourceId, duration).show()

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)!!

//fun ImageView.loadByURL(url: String) = Picasso.get.load(url).into(this)

//fun ImageView.loadByResource(resource: Int) = Picaso.get().load(resource).fit().into(this)

//Para usar un intent
inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this, UCharacter.GraphemeClusterBreak.T::class.java)
    intent.init()
    startActivity(intent)
}
//
fun EditText.validate(validation: (String) -> Unit){
    this.addTextChangedListener(object: TextWatcher {
        override  fun afterTextChanged(editable: Editable){
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}
//valida el Email, ingresado
fun Activity.isValidateEmail(email: String):Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}
//valida el password ingresado, y si cumple con los patrones exigidos en el mismo, si cumple retorna true si cumple con los patrones
fun Activity.isValidatePassword(password: String):Boolean{
    //necesita contener --> 1 Num   /  1 Minuscula /1 Mayuscula / 1 especial / Min 4 Carecteres
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}
//comprueba que password y confirmpassword tienen igualdad de contenido entonces retorna true
fun Activity.isValidateConfirmPassword(password: String, confirmPassword: String):Boolean{
    return password == confirmPassword
}
