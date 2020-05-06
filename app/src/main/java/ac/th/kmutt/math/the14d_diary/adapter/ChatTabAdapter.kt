package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.ui.chatHome.historyChat.ChatHistoryListFragment
import ac.th.kmutt.math.the14d_diary.ui.chatHome.userList.ChatListFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChatTabAdapter(
    private val fragment: Fragment
) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ChatListFragment.newInstance()
            1 -> ChatHistoryListFragment.newInstance()
            else -> null!!
        }
    }

}