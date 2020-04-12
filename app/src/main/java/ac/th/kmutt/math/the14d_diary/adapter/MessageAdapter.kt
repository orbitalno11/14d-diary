package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.MessageModel
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    private val context: Context,
    private val mItems: List<MessageModel>
) : RecyclerView.Adapter<MessageAdapter.Holder>() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val user = firebaseHelper.getCurrentUser()

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view){
        val senderName: TextView = view.findViewById(R.id.sender_name)
        val sendTime: TextView = view.findViewById(R.id.send_time)
        val message: TextView = view.findViewById(R.id.send_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(this.context)
        val view = inflater.inflate(R.layout.layout_message, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = this.mItems[position]

        user?.let {
            holder.senderName.text = item.sender
            holder.sendTime.text = item.sendTime
            holder.message.text = item.message

            if (it.uid == item.senderID){
                holder.sendTime.gravity = Gravity.END
                holder.senderName.gravity = Gravity.END
                holder.message.gravity = Gravity.END
            }else{
                holder.sendTime.gravity = Gravity.START
                holder.senderName.gravity = Gravity.START
                holder.message.gravity = Gravity.START
            }
        }
    }
}