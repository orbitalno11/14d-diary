package ac.th.kmutt.math.the14d_diary.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val tag: String = HomeViewModel::class.java.simpleName

    fun getTag(): String{
        return tag
    }
}
