// LeaderboardAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psrquizapp.R
import com.example.psrquizapp.StudentPerformance

class LeaderboardAdapter(private val leaderboardData: List<StudentPerformance> ) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard_entry, parent, false)
        return LeaderboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val performance = leaderboardData[position]
        holder.nameTextView.text = performance.name
        holder.scoreTextView.text = "Score: ${performance.score}"
        holder.timeTextView.text = "Time Taken: ${performance.timeTaken / 1000} seconds"
    }

    override fun getItemCount(): Int = leaderboardData.size

    class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val scoreTextView: TextView = view.findViewById(R.id.scoreTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
    }
}


