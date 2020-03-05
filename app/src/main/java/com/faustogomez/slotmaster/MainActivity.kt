package com.faustogomez.slotmaster

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_score.view.*
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var scoresDB: DatabaseReference

    var scores: MutableList<Score> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoresDB = FirebaseDatabase.getInstance().getReference("Scores")

        score_list.layoutManager = LinearLayoutManager(this)

        scoresDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                scores = mutableListOf()
                dataSnapshot.children.forEach{
                    val score = it.getValue(Score::class.java)
                    if(score != null){
                        scores.add(score)
                    }
                }
                update()
            }

            override fun onCancelled(p0: DatabaseError) {
                //Failed to read values: handle error
            }
        })

        play_button.setOnClickListener {
            startActivity(Intent(this, AuthOpsActivity::class.java))
        }

    }

    private fun update(){
        score_list.adapter = ScoresAdapter(scores, this)
    }

    private class ScoresAdapter(private val scores: List<Score>, val context: Context): RecyclerView.Adapter<ScoresViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
            return ScoresViewHolder(LayoutInflater.from(context).inflate(R.layout.user_score, parent, false))
        }

        override fun getItemCount(): Int {
            return scores.count()
        }

        override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
            val score = scores[position]

            holder.itemView.display_user.text = score.user
            holder.itemView.display_score.text = score.score.toString()
        }
    }
}


class ScoresViewHolder(view: View): RecyclerView.ViewHolder(view)

data class Score(var user: String = "", var score: Int = 0)