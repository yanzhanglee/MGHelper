package com.example.mghelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mghelper.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class MainActivity : AppCompatActivity() {
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: ConstraintLayout
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var adapter: RecordHistoryAdapter

    companion object {
        private const val TAG = "MainActivity"
        const val RECORDS_CHILD = "records"
        const val ANONYMOUS = "anonymous"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseAuth = FirebaseAuth.getInstance()

        if (mFirebaseAuth!!.getCurrentUser() == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        signInClient = GoogleSignIn.getClient(this, gso)

        mDatabase = FirebaseDatabase.getInstance()
        val recordsRef = mDatabase!!.getReference().child(RECORDS_CHILD)

        val options = FirebaseRecyclerOptions.Builder<Record>()
            .setQuery(recordsRef, Record::class.java)
            .build()

        setupListOfDataIntoRecyclerView(options)
        Log.d("debug","111")
    }

    private fun setupListOfDataIntoRecyclerView(options: FirebaseRecyclerOptions<Record>){
        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecordHistoryAdapter(this, options, getUserName())
        binding.recordsRecyclerView.adapter = adapter
        Log.d("debug","111")
    }

    private fun getUserPhotoUrl(): String? {
        val user = mFirebaseAuth!!.currentUser
        return if (user != null && user.photoUrl != null) {
            user.photoUrl.toString()
        } else null
    }

    private fun getUserName(): String? {
        val user = mFirebaseAuth!!.currentUser
        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    private fun signOut() {
        mFirebaseAuth!!.signOut()
        signInClient.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.feeds -> {
//                feeds()
                true
            }
            R.id.records -> {
//                records()
                true
            }
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        if (mFirebaseAuth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }


}