package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory

class ChatItemAdapter(
    private val mContext: Context,
    private val mItems: List<ChatUserModel>
) : RecyclerView.Adapter<ChatItemAdapter.Holder>(){

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view){
        val userName: TextView = view.findViewById(R.id.chat_name)
        val userImg: ImageView = view.findViewById(R.id.chat_picture)

        fun bind(user: ChatUserModel){
            userName.text = user.displayName
            Glide.with(view).load(user.picture).centerCrop().apply(RequestOptions.circleCropTransform()).into(userImg)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("userID", user.userID)
                bundle.putString("userName", user.displayName)
                val navController = Navigation.findNavController(it.context as AppCompatActivity, R.id.fragment_host)
                navController.navigate(R.id.action_nav_chat_to_chatRoom, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(this.mContext)
        val view = inflater.inflate(R.layout.layout_chat_item, parent, false)

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