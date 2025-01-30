package com.jgc.reproducirvideo

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.reproducirvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding : ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.botPlay.setOnClickListener {
      var mediaControls: MediaController = MediaController(this)
      mediaControls.setAnchorView(binding.vv)

      // set the media controller for video view
      binding.vv.setMediaController(mediaControls)

      // set the absolute path of the video file which is going to be played
      binding.vv.setVideoURI(
        Uri.parse("android.resource://"
                + packageName + "/" + R.raw.snow))

      binding.vv.requestFocus()

      // arranca the video
      binding.vv.start()

      // display a toast message after the video is completed
      binding.vv.setOnCompletionListener {
        Toast.makeText(applicationContext, "Video completado",
          Toast.LENGTH_LONG).show()
      }

      // display a toast message if any error occurs while playing the video
      binding.vv.setOnErrorListener { _, _, _ ->
        Toast.makeText(applicationContext, "Ha ocurrido un errror " +
                "mientros se reproduce el video !!!", Toast.LENGTH_LONG).show()
        false
      }
    }

    binding.botPausar.setOnClickListener {

      //pausa la ejecución
      binding.vv.pause()

    }

    binding.botContinuar.setOnClickListener {
      //continua la ejecución por dónde iba si se había pausado
      binding.vv.start()
    }

    binding.botDetener.setOnClickListener {
      //detiene completamente
      binding.vv.stopPlayback()
    }
  }
}