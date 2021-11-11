package com.dai.pandyapk.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.ActivityMainBinding
import com.dai.pandyapk.view.loginuser.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.dai.pandyapk.view.notes.NotesActivity
import com.dai.pandyapk.viewmodel.PandyViewModel
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    private lateinit var binding: ActivityMainBinding
    private val mAuth: FirebaseAuth by lazy { Firebase.auth }
    private val pandyViewModel: PandyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
//            binding = ActivityMainBinding.inflate(layoutInflater)

//
//        binding.btnGoActivityNotes.setOnClickListener{
//            val goActivityNotes = Intent(this, NotesActivity::class.java)
//            startActivity(goActivityNotes)
//        }
//
//        binding.btnGoActivityReader.setOnClickListener{
//            val goActivityReader = Intent(this, ReaderActivity::class.java)
//            startActivity(goActivityReader)
//        }
//        binding.btnGoActivityAtlas.setOnClickListener {
//            val goActivityAtlas = Intent(this, AtlasActivity::class.java)
//            startActivity(goActivityAtlas)
//        }
        setContentView(binding.root)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menuProfileView -> {

                val goProfileActivity = Intent(this, ProfileViewActivity::class.java)
                startActivity(goProfileActivity)
            }

            R.id.menuSignOut -> {

                mAuth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
   }


}