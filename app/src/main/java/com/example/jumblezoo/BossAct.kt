package com.example.jumblezoo

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jumblezoo.databinding.ActivityBossBinding


class BossAct : AppCompatActivity() {
    lateinit var binding:ActivityBossBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_boss)

        val mMediaPlayer = MediaPlayer.create(this, R.raw.win)
        mMediaPlayer!!.isLooping=false
        mMediaPlayer!!.start()

        binding.playAgain.setOnClickListener {
            val intent= Intent(this@BossAct,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}