package ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.epoxy.KotlinEpoxyHolder
import android.view.View
import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.layout_function_button)
abstract class FunctionButton: EpoxyModelWithHolder<FunctionButton.Holder>() {

    @EpoxyAttribute var chatClick: View.OnClickListener? = null
    @EpoxyAttribute var diaryClick: View.OnClickListener? = null
    @EpoxyAttribute var newsClick: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder){
            this.chatButton.setOnClickListener(chatClick)
            this.diaryButton.setOnClickListener(diaryClick)
            this.newsButton.setOnClickListener(newsClick)
        }
    }

    inner class Holder: KotlinEpoxyHolder(){
        val chatButton by bind<Button>(R.id.chat_button)
        val diaryButton by bind<Button>(R.id.diary_button)
        val newsButton by bind<Button>(R.id.news_button)
    }
}