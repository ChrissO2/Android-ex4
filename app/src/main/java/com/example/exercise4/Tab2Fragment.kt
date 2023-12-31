package com.example.exercise4

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Tab2Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: SharedPreferences = requireActivity().getSharedPreferences("data", 0)
        val editor: SharedPreferences.Editor = data.edit()
        val welcome = data.getString("welcome", "Cwiczenie 4")
        val welcomeText = view.findViewById<View>(R.id.welcomeText) as TextView
        welcomeText.text = welcome
        val author = data.getString("author", "Krzysztof Otreba")
        val authorText = view.findViewById<View>(R.id.authorText) as TextView
        authorText.text = author
        welcomeText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                editor.putString("welcome", editable.toString())
                editor.apply()
            }
        })

        authorText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                editor.putString("author", editable.toString())
                editor.apply()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

}