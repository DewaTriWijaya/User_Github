package com.dewatwc.githubuser.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.alarm.Reminder

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener  {

        private lateinit var reminderPreference: SwitchPreferenceCompat
        private lateinit var reminder: String
        private lateinit var alarm: Reminder

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.setting, rootKey)

            alarm = Reminder()

            init()
            setSh()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            if (key == reminder) {
                reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
            }

            val state : Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)

            setAlarm(state)
        }

        private fun init() {
            reminder = resources.getString(R.string.reminder_key)
            reminderPreference = findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
        }

        private fun setAlarm(state: Boolean) {
            if (state) {
                context?.let { cxt -> alarm.setOnAlarm(cxt) }
            } else {
                context?.let { cxt -> alarm.cancelAlarm(cxt) }
            }
        }

        private fun setSh() {
            val sh = preferenceManager.sharedPreferences
            reminderPreference.isChecked = sh.getBoolean(reminder, false)
        }
    }