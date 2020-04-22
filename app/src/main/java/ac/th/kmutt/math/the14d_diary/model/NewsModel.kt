package ac.th.kmutt.math.the14d_diary.model

data class NewsModel(
    var source: HashMap<String, String> = HashMap(),
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var urlToImage: String = "",
    var publishedAt: String = ""
)