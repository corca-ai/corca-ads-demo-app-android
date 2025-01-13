package ai.corca.corcaads_android_plugins.corca_ads_demo.user

import java.time.LocalDate

enum class Gender { male, female }

data class User(
    val id: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender
)
