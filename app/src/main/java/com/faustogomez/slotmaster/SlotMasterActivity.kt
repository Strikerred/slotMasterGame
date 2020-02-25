package com.faustogomez.slotmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_slotmaster.*

class SlotMasterActivity : AppCompatActivity() {

    private val slot: List<Slot> = listOf(
        Slot(R.drawable.cherries, R.drawable.dollarsign, R.drawable.grapes),
        Slot(R.drawable.dollarsign, R.drawable.cherries, R.drawable.cherries),
        Slot(R.drawable.grapes, R.drawable.grapes, R.drawable.dollarsign),
        Slot(R.drawable.lemon, R.drawable.lemon, R.drawable.lemon),
        Slot(R.drawable.number, R.drawable.number, R.drawable.number)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slotmaster)

        spinSlots()

        spin_button.setOnClickListener(){
            spinSlots()
        }
    }

    private fun spinSlots() {
        leftImage.setImageResource(slot.random().leftImage)
        centerImage.setImageResource(slot.random().centerImage)
        rightImage.setImageResource(slot.random().rightImage)
    }
}

data class Slot(val leftImage: Int, val centerImage: Int, val rightImage: Int)