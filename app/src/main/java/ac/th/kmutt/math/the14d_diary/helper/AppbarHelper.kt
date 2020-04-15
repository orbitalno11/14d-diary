package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.MainActivity
import ac.th.kmutt.math.the14d_diary.R
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.TextView

class AppbarHelper(
    private val view: View
) {

    private val title: TextView = view.findViewById(R.id.page_header)
    private val menu: Button = view.findViewById(R.id.menu_button)

    fun setTitle(text: String) {
        title.text = text
    }

    fun setTitleVisible(visible: Int){
        title.visibility = visible
    }

    fun setMunuIcon(resource: Int){
        menu.setBackgroundResource(resource)
    }

    fun setMenuClickListener(listener: View.OnClickListener){
        menu.setOnClickListener(listener)
    }

    fun setMenuNav(activity: MainActivity){
        menu.setOnClickListener {
            activity.openDrawer()
        }
    }
}