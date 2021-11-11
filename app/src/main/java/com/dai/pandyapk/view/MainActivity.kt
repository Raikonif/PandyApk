package com.dai.pandyapk.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
            }

            R.id.menuSignOut -> { mAuth.signOut() }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
   }


}