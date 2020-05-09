package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.ui.diaryList.quest.QuestFragment
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
            0 -> QuestFragment.newInstance()
            1 -> QuestFragment.newInstance()
            else -> null!!
        }
    }

}