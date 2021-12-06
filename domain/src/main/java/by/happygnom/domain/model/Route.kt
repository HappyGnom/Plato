package by.happygnom.domain.model

import java.util.*

data class Route(
    val id: String,
    val gradeLevel: Int,
    val holdsColor: String,
    val pictureUrl: String,
    val likesCount: Int,
    val sendsCount: Int,
    val commentsCount: Int,
    val setterName: String,
    val setDate: Date,
    val tags: List<String>,
    val status: Status,
    val visualisationUrl: String?,
) {
    enum class Status { SET, TAKEN_DOWN }

    val isNew: Boolean
        get() {
            val comparisonDate = Calendar.getInstance()
            comparisonDate.add(Calendar.DATE, -7)

            return setDate.after(comparisonDate.time)
        }
}

const val mockRoutePhoto = "https://i.imgur.com/7Yr1NLM.jpeg"

val mockRoutes = listOf(
    Route(
        "1",
        0,
        "Blue",
        mockRoutePhoto,
        10,
        50,
        3,
        "Yura Morozov",
        Calendar.getInstance().apply { set(2021, 8, 4) }.time,
        listOf("Big holds", "Slab"),
        Route.Status.SET,
        "https://raw.githubusercontent.com/intade/plato_gltf/master/test%20wall/WallGLTF-05-38-2021-19-38-16.gltf"
    ),
    Route(
        "2",
        5,
        "Green",
        mockRoutePhoto,
        15,
        20,
        12,
        "Yura Morozov",
        Calendar.getInstance().apply { set(2021, 11, 15) }.time,
        listOf("Dyno", "Sloper"),
        Route.Status.SET,
        "https://raw.githubusercontent.com/intade/plato_gltf/master/test%20wall/WallGLTF-05-38-2021-19-38-16.gltf"
    ),
    Route(
        "3",
        8,
        "Yellow",
        mockRoutePhoto,
        12,
        5,
        0,
        "Nika Malinovskaya",
        Calendar.getInstance().apply { set(2021, 11, 25) }.time,
        listOf("Strength"),
        Route.Status.SET,
        null
    ),
    Route(
        "4",
        15,
        "Black",
        mockRoutePhoto,
        3,
        1,
        0,
        "Igor Morozov",
        Calendar.getInstance().apply { set(2021, 4, 12) }.time,
        listOf("Dyno", "Volumes", "Overhang"),
        Route.Status.TAKEN_DOWN,
        null
    ),
    Route(
        "5",
        9,
        "Red",
        mockRoutePhoto,
        6,
        4,
        3,
        "Igor Morozov",
        Calendar.getInstance().apply { set(2021, 7, 4) }.time,
        listOf("Overhang"),
        Route.Status.SET,
        null
    ),
    Route(
        "6",
        4,
        "Green",
        mockRoutePhoto,
        13,
        25,
        10,
        "Nikita Logunov",
        Calendar.getInstance().apply { set(2021, 12, 1) }.time,
        listOf("Stretch", "Grip", "Overhang", "Long moves"),
        Route.Status.SET,
        null
    ),
    Route(
        "7",
        6,
        "Blue",
        mockRoutePhoto,
        12,
        15,
        5,
        "Nikita Logunov",
        Calendar.getInstance().apply { set(2021, 12, 1) }.time,
        listOf("Comp", "Crimps"),
        Route.Status.SET,
        null
    )
)

