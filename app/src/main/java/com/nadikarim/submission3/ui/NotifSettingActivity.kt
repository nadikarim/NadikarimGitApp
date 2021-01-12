package com.nadikarim.submission3.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.nadikarim.submission3.service.AlarmReceiver
import com.nadikarim.submission3.R
import com.nadikarim.submission3.databinding.ActivityNotifSettingBinding
import com.nadikarim.submission3.utils.PREFS_NAME
import com.nadikarim.submission3.utils.TYPE_DAILY

class NotifSettingActivity : AppCompatActivity() {
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    companion object {
        private const val DAILY = "daily"
    }

    private lateinit var binding: ActivityNotifSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Notification Setting"

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setSwitch()
        binding.switchDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                    this,
                        TYPE_DAILY,
                    getString(R.string.notif_message)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun setSwitch() {
        binding.switchDaily.isChecked = mSharedPreferences.getBoolean(DAILY, false)
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}