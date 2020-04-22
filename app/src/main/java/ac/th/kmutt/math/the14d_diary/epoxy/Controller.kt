package ac.th.kmutt.math.the14d_diary.epoxy

import ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel.NewsItemEpoxy_
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import kotlin.properties.Delegates

class Controller: EpoxyController(){

    init {
        Carousel.setDefaultGlobalSnapHelperFactory(null)
    }

    var newsItems by Delegates.observable(emptyList<NewsModel>()) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        // news
        CarouselModel_()
            .id("NEWS_LIST")
            .numViewsToShowOnScreen(2.2f)
            .paddingDp(8)
            .models(newsItems.map { news ->
                NewsItemEpoxy_()
                    .id(news.url)
                    .news(news)
            })
            .addTo(this)
    }

}