package ac.th.kmutt.math.the14d_diary.adapter

import ac.th.kmutt.math.the14d_diary.ui.news.globalNews.GlobalNewsFragment
import ac.th.kmutt.math.the14d_diary.ui.news.thaiNews.ThaiNewsFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NewsTabAdapter(
    private val fragment: Fragment
) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ThaiNewsFragment.newInstance()
            1 -> GlobalNewsFragment.newInstance()
            else -> null!!
        }
    }
}