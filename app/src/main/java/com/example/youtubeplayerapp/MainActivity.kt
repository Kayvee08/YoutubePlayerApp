package com.example.youtubeplayerapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val RECORD_REQUEST_CODE = 101
    private var backButtonCount=0

    val apiKey = "AIzaSyCQReBUb6SdsbgTTTbuaYUZK2iMNGV_Rhw"
    var youtubePlayer: YouTubePlayer? = null
    val videoId = listOf<String>(
        "IEF6mw7eK4s",
        "8CEJoCr_9UI",
        "344u6uK9qeQ",
        "3-nM3yNi3wg",
        "RlcY37n5j9M",
        "nB7nGcW9TyE"
    )

    override fun onBackPressed() {

        if (backButtonCount >= 1) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext,"Press the back button once again to close the application.",
                Toast.LENGTH_SHORT
            ).show()
            backButtonCount+=1
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        youtubePlayer = p1
        p1?.cueVideo(videoId.random())
        p1?.play()
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val permissionCam = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (permissionCam != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                RECORD_REQUEST_CODE
            )
        }
        val permissionWrite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE
            )
        }
        val permissionRead = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE
            )
        }

        val shuffleButton: Button = findViewById<Button>(R.id.shuffle)
        val nextButton: Button = findViewById<Button>(R.id.next)
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.youtubePlayer)
        ytPlayer.initialize(apiKey, this)
        shuffleButton.setOnClickListener {
            youtubePlayer?.cueVideo(videoId.random())
        }
        nextButton.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

}