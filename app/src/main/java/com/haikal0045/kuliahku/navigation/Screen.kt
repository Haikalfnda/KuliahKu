package com.haikal0045.kuliahku.navigation

const val KEY_ID_TUGAS = "idTugas"

sealed class Screen(val route: String) {

    data object Home : Screen("mainScreen")

    data object FormBaru : Screen("detailScreen")

    data object RecycleBin : Screen("recycleBinScreen")

    data object Tema : Screen("themeScreen")

    data object FormUbah : Screen("detailScreen/{$KEY_ID_TUGAS}") {
        fun withId(id: Long): String {
            return "detailScreen/$id"
        }
    }
}