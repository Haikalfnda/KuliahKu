package com.haikal0045.kuliahku.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {

    private val showListKey = booleanPreferencesKey("show_list")
    private val themeColorKey = intPreferencesKey("theme_color")

    val layoutFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[showListKey] ?: true
        }

    val themeColorFlow: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[themeColorKey] ?: 0
        }

    suspend fun saveLayout(showList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[showListKey] = showList
        }
    }

    suspend fun saveThemeColor(themeColor: Int) {
        context.dataStore.edit { preferences ->
            preferences[themeColorKey] = themeColor
        }
    }
}