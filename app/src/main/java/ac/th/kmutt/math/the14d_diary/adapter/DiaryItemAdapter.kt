package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class DiaryItemAdapter(
    private val mContext: Context,
    private val mItems: List<DiaryModel>
) : RecyclerView.Adapter<DiaryItemAdapter.Holder>(){

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view){
        val diaryName: TextView = view.findViewById(R.id.diary_name)
        val diaryImg: ImageView = view.findViewById(R.id.diary_img_preview)
        val diaryDay: TextView = view.findViewById(R.id.diary_day)
        val diaryStatus: ImageView = view.findViewById(R.id.diary_status)

        fun bind(diary: DiaryModel){
            diaryName.text = diary.diaryName

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("userName", diary.diaryName)
                val navController = Navigation.findNavController(it.context as AppCompatActivity, R.id.fragment_host)
                navController.navigate(R.id.action_nav_diary_to_diaryFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(this.mContext)
        val view = inflater.inflate(R.layout.layout_diary_item, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = this.mItems[position]
        holder.bind(item)
    }
}