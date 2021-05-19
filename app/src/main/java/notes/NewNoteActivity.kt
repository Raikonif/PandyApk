package notes

import Models.Note
import android.content.Intent
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dai.pandyapk.databinding.ActivityNewNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    private val binding: ActivityNewNoteBinding by lazy { ActivityNewNoteBinding.inflate(layoutInflater) }
    lateinit var FirebaseStorage: FirebaseStorage
    private val mAuth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        
        binding.btnSave.setOnClickListener{
            saveNote()
            val goToActivityNotes = Intent(this, NotesActivity::class.java)
                startActivity(goToActivityNotes)
        }
        binding.btnCancel.setOnClickListener{
            val goToActivityNotes = Intent(this, NotesActivity::class.java)
            startActivity(goToActivityNotes)
        }

    }
    private fun saveNote(){
        val uid = mAuth.uid ?: ""
        val userRefDB = Firebase.firestore
        val filename = UUID.randomUUID().toString()
        val notename = UUID.randomUUID().toString()
        val title = binding.etTitle.toString()
        val description = binding.etDescription.toString()
        val note = Note(filename,title,description,"",false)
        var batch = userRefDB.batch()
        val referenceNote = userRefDB.collection("Notes").document("$uid")
        batch.set(referenceNote, note)
    }
}