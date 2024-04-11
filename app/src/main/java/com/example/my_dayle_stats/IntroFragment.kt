package com.example.my_dayle_stats

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment


class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)

        val enter_api_et = view.requireViewById<EditText>(R.id.enter_api_ET)
        val enter_api_tv = view.requireViewById<TextView>(R.id.your_api_keyTV)
        val submint_api_btn = view.requireViewById<Button>(R.id.submint_api_btn)
        if (sharedPref != null) {
            enter_api_tv.text = sharedPref.getString(
                getString(R.string.api_key),
                "Если вы Видите этот текс, то API-Ключ отсутствует! Введите Ваш АПИ ключ для работы с приложением"
            )
        }
        submint_api_btn.setOnClickListener {
            if (enter_api_et.text.isNotBlank()) {
                val api_key = enter_api_et.text.replace("\\s".toRegex(), "")
                if (sharedPref != null) {
                    sharedPref.edit().putString(getString(R.string.api_key), api_key).apply()
                }
                enter_api_tv.text = api_key
            }

        }


    }

    companion object {

        @JvmStatic
        fun newInstance() = IntroFragment()

    }
}