package by.happygnom.domain.model

import java.text.SimpleDateFormat
import java.util.*

data class Route(
    val id: String,
    val gradeLevel: Int,
    val holdsColor: String,
    val pictureBase64: String,
    val likesCount: Int,
    val sendsCount: Int,
    val commentsCount: Int,
    val setterName: String,
    val setDate: Date,
    val tags: List<String>,
    val status: Status,
    val visualisationId: String,
) {
    enum class Status { SET, TAKEN_DOWN }

    val isNew: Boolean
        get() {
            val comparisonDate = Calendar.getInstance()
            comparisonDate.add(Calendar.DATE, -7)

            return setDate.after(comparisonDate.time)
        }

    val setDateString: String
        get() {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(this.setDate)
        }
}

val mockRoutes = listOf(
    Route(
        "1",
        0,
        "Blue",
        "",
        10,
        50,
        3,
        "Yura Morozov",
        Calendar.getInstance().apply { set(2021, 8, 4) }.time,
        listOf("Big holds", "Slab"),
        Route.Status.SET,
        "51351"
    ),
    Route(
        "2",
        5,
        "Green",
        "",
        15,
        20,
        12,
        "Yura Morozov",
        Calendar.getInstance().apply { set(2021, 11, 15) }.time,
        listOf("Dyno", "Sloper"),
        Route.Status.SET,
        "54543"
    ),
    Route(
        "3",
        8,
        "Yellow",
        "",
        12,
        5,
        0,
        "Nika Malinovskaya",
        Calendar.getInstance().apply { set(2021, 11, 25) }.time,
        listOf("Strength"),
        Route.Status.SET,
        "11632"
    ),
    Route(
        "4",
        15,
        "Black",
        "",
        3,
        1,
        0,
        "Igor Morozov",
        Calendar.getInstance().apply { set(2021, 4, 12) }.time,
        listOf("Dyno", "Volumes", "Overhang"),
        Route.Status.TAKEN_DOWN,
        "23426"
    ),
    Route(
        "5",
        9,
        "Red",
        "",
        6,
        4,
        3,
        "Igor Morozov",
        Calendar.getInstance().apply { set(2021, 7, 4) }.time,
        listOf("Overhang"),
        Route.Status.SET,
        "21426"
    ),
    Route(
        "6",
        4,
        "Green",
        "",
        13,
        25,
        10,
        "Nikita Logunov",
        Calendar.getInstance().apply { set(2021, 12, 1) }.time,
        listOf("Stretch", "Grip", "Overhang", "Long moves"),
        Route.Status.SET,
        "654326"
    ),
    Route(
        "7",
        6,
        "Blue",
        "",
        12,
        15,
        5,
        "Nikita Logunov",
        Calendar.getInstance().apply { set(2021, 12, 1) }.time,
        listOf("Comp", "Crimps"),
        Route.Status.SET,
        "324545"
    )
)
