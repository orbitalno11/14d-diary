package ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.epoxy.KotlinEpoxyHolder
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide

@EpoxyModelClass(layout = R.layout.layout_news_row)
abstract class NewsItemEpoxy :EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute lateinit var news: NewsModel
    @EpoxyAttribute var newsOnClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder){
            Glide.with(this.newsImage)
                .load(news.urlToImage)
                .centerCrop()
                .placeholder(R.color.greenEmerald)
                .into(this.newsImage)

            this.newsTitle.text = news.title
            this.newsFrame.setOnClickListener(newsOnClickListener)
        }
    }
}

class Holder : KotlinEpoxyHolder(){
    val newsImage by bind<ImageView>(R.id.news_image)
    val newsTitle by bind<TextView>(R.id.news_title)
    val newsFrame by bind<LinearLayout>(R.id.news_frame)
}