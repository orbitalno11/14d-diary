package ac.th.kmutt.math.the14d_diary.model

data class InflectNewsModel(
    var Confirmed: Int = 0,
    var Deaths: Int = 0,
    var Hospitalized: Int = 0,
    var NewConfirmed: Int = 0,
    var NewDeaths: Int = 0,
    var NewHospitalized: Int = 0,
    var NewRecovered: Int = 0,
    var Recovered: Int = 0,
    var UpdateDate: String = ""
)