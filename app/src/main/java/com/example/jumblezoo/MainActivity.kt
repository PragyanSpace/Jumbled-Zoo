package com.example.jumblezoo

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jumblezoo.Repository.NameRepository
import com.example.jumblezoo.ViewModels.MainViewModel
import com.example.jumblezoo.ViewModels.ViewModelFactory
import com.example.jumblezoo.api.AnimalService
import com.example.jumblezoo.api.RetrofitHelper
import com.example.jumblezoo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    lateinit var binding: ActivityMainBinding

    lateinit var typeface: Typeface

    private var presCounter = 0
    var keys = arrayOf("B")
    private var maxPresCounter = keys.size
    var textAnswer = ""

    var smallbigforth: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        typeface = Typeface.createFromAsset(assets, "fonts/FredokaOne-Regular.ttf")
        binding.textQuestion!!.typeface = typeface
        binding.textScreen!!.typeface = typeface
        binding.textTitle!!.typeface = typeface


        val animalService=RetrofitHelper.getInstance().create(AnimalService::class.java)
        val repository=NameRepository(animalService)
        mainViewModel=ViewModelProvider(this,ViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel.names.observe(this, androidx.lifecycle.Observer {
            Log.d("Animal Name",it.name)
            textAnswer=it.name
            keys=Array(textAnswer.length) {textAnswer[it].toString()}
            keys = shuffleArray(keys)

            maxPresCounter=keys.size
            addViews(keys)
        })

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth)
        maxPresCounter = keys.size
    }

    private fun addViews(keys:Array<String>)
    {
        for (key in keys) {
            addView(
                binding.layoutParent, key,
                binding.editText
            )
        }
    }

    private fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

    private fun addView(viewParent: LinearLayout, text: String, editText: EditText) {
        val linearLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParams.weight = 1f
        linearLayoutParams.rightMargin = 20
        val textView = TextView(this)
        textView.layoutParams = linearLayoutParams
        textView.background = this.resources.getDrawable(R.drawable.bgpink)
        textView.setTextColor(this.resources.getColor(R.color.purple_700))
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.isClickable = true
        textView.isFocusable = true
        textView.textSize = 32f
        editText.typeface = typeface
        textView.typeface = typeface
        textView.setOnClickListener {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0) editText.setText("")
                editText.setText(editText.text.toString() + text)
                textView.startAnimation(smallbigforth)
                textView.animate().alpha(0f).duration = 300
                textView.isClickable=false
                presCounter++
                if (presCounter == maxPresCounter) doValidate()
            }
        }
        viewParent.addView(textView)
    }

    private fun doValidate() {
        presCounter = 0
        val editText = findViewById<EditText>(R.id.editText)
        val linearLayout = findViewById<LinearLayout>(R.id.layoutParent)
        if (editText.text.toString() == textAnswer) {
//            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            val a = Intent(this@MainActivity, BossAct::class.java)
            startActivity(a)
        } else {
            Toast.makeText(this@MainActivity, "Wrong", Toast.LENGTH_SHORT).show()
        }
        editText.setText("")
        keys = shuffleArray(keys)
        linearLayout.removeAllViews()
        for (key in keys) {
            addView(linearLayout, key, editText)
        }
    }
}