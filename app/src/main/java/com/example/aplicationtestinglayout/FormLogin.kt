package com.example.aplicationtestinglayout

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_task)

        // Gradiente mudando de cor
        val constraintLayout: ConstraintLayout = findViewById(R.id.mainLayout)
        var animationDraw: AnimationDrawable = constraintLayout.background as AnimationDrawable
        animationDraw.setEnterFadeDuration(2500)
        animationDraw.setExitFadeDuration(5000)
        animationDraw.start()
        // Gradiente mudando de cor

    }
}