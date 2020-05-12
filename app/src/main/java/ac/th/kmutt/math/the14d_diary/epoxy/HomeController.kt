package ac.th.kmutt.math.the14d_diary.epoxy

import ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel.EmergencyCall_
import ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel.FunctionButton_
import ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel.NewsItemEpoxy_
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController

class HomeController : EpoxyController() {

    private var newsItems = emptyList<NewsModel>()
    private var emergencyClick: View.OnClickListener? = null
    private var chatClick: View.OnClickListener? = null
    private var diaryClick: View.OnClickListener? = null
    private var newsClick: View.OnClickListener? = null

    init {
        Carousel.setDefaultGlobalSnapHelperFactory(null)
    }

    fun setNews(news: List<NewsModel>?) {
        news?.let {
            this.newsItems = it
        }
        requestModelBuild()
    }

    fun setEmergencyClick(onClickListener: View.OnClickListener) {
        this.emergencyClick = onClickListener
        requestModelBuild()
    }

    fun setChatClick(onClickListener: View.OnClickListener) {
        this.chatClick = onClickListener
        requestModelBuild()
    }

    fun setDiaryClick(onClickListener: View.OnClickListener) {
        this.diaryClick = onClickListener
        requestModelBuild()
    }

    fun setNewsClick(onClickListener: View.OnClickListener) {
        this.newsClick = onClickListener
        requestModelBuild()
    }

    override fun buildModels() {

        EmergencyCall_()
            .id("EMERGENCY_TAB")
            .onClick(emergencyClick)
            .addTo(this)

        FunctionButton_()
            .id("FUNCTION_BUTTON")
            .chatClick(chatClick)
            .diaryClick(diaryClick)
            .newsClick(newsClick)
            .addTo(this)

        CarouselModel_()
            .id("NEWS_LIST")
            .numViewsToShowOnScreen(2.2f)
            .paddingDp(8)
            .models(newsItems.map { news ->
                NewsItemEpoxy_()
                    .id(news.url)
                    .news(news)
                    .newsOnClickListener(View.OnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(news.url)
                        val activity = it.context as AppCompatActivity
                        activity.startActivity(intent)
                    })
            })
            .addTo(this)
    }

}