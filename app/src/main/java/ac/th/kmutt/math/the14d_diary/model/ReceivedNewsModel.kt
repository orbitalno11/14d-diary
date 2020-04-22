package ac.th.kmutt.math.the14d_diary.model

data class ReceivedNewsModel (
    var status: String = "",
    var totalResults: Int = 0,
    var articles: MutableList<NewsModel>
)