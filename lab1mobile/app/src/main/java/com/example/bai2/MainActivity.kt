package com.example.bai2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.bai2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)

        // Hàm đổi xúc xắc
        fun rollDice() {
            val diceRoll = (1..6).random()

            val drawableResource = when (diceRoll) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            imageView.setImageResource(drawableResource)
        }

        // Hiển thị lần đầu
        rollDice()

        // 👉 BẤM VÀO HÌNH → ĐỔI
        imageView.setOnClickListener {
            rollDice()
        }
    }
}
