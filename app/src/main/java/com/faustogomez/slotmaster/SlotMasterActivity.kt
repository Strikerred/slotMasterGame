package com.faustogomez.slotmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_slotmaster.*

class SlotMasterActivity : AppCompatActivity() {

    private lateinit var scoresDB: DatabaseReference
    private val slot: List<Int> = listOf(R.drawable.cherries, R.drawable.dollarsign, R.drawable.grapes, R.drawable.lemon, R.drawable.number)
    var score: Int = 0
    var spin_time = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slotmaster)

        scoresDB = FirebaseDatabase.getInstance().getReference("Scores")

        spin_times_text.text = spin_time.toString()
        display_score_text.text = score.toString()

        spin_button.setOnClickListener(){
            spinSlots()
        }
    }

    private fun spinSlots() {
        val leftSlot = slot.random()
        leftImage.setImageResource(leftSlot)

        val centerSlot = slot.random()
        centerImage.setImageResource(centerSlot)

        val rightSlot = slot.random()
        rightImage.setImageResource(rightSlot)

        spin_time--

        spin_times_text.setText(spin_time.toString())

        if(spin_time > 0){
            if(leftSlot.equals(centerSlot) && leftSlot.equals(rightSlot)){
                score += 5
                display_score_text.setText(score.toString())
            }else if(leftSlot.equals(centerSlot) || leftSlot.equals(rightSlot) || centerSlot.equals(rightSlot)){
                score += 2
                display_score_text.setText(score.toString())
            }
        }else{
            val userEmail = FirebaseAuth.getInstance().currentUser?.email

            if(userEmail != null && spin_time == 0){
                savePlayerScore(userEmail, score)
            }
            spin_time = 5
            score = 0
            spin_times_text.text = "5 -> Try Again"
            display_score_text.setText(score.toString())
        }
    }

    private fun savePlayerScore(user: String, score: Int){
        val key = scoresDB.push().key
        key ?: return

        val player = Score(user, score)

        scoresDB.child(key).setValue(player)
    }
}



