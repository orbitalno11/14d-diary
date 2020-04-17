package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.fragment.ChatHistoryListFragment
import ac.th.kmutt.math.the14d_diary.fragment.ChatListFragment
import ac.th.kmutt.math.the14d_diary.fragment.DiaryAllFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DiaryTabAdapter(
    private val fragment: Fragment
) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DiaryAllFragment.newInstance()
            1 -> DiaryAllFragment.newInstance()
            else -> null!!
        }
    }

}