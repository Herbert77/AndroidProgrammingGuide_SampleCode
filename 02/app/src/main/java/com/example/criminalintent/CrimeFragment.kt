package com.example.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

private const val TAG = "CrimeFragment"

class CrimeFragment : Fragment() {

    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        Log.d(TAG, "$view")

        findTitleFieldInView(view)
        findDateButtonInView(view)
        findSolvedCheckBoxInView(view)

        configDateButton()
        return view
    }

    private fun findTitleFieldInView(view: View) {
        titleField = view.findViewById(R.id.crime_title) as EditText
    }

    private fun findDateButtonInView(view: View) {
        dateButton = view.findViewById(R.id.crime_date) as Button
    }

    private fun findSolvedCheckBoxInView(view: View) {
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
    }

    private fun configDateButton() {
        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        addTextChangedListenerForTitleField()
        setOnCheckedChangeListenerForSolvedCheckBox()
    }

    private fun addTextChangedListenerForTitleField() {
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        titleField.addTextChangedListener(titleWatcher)
    }

    private fun setOnCheckedChangeListenerForSolvedCheckBox() {
        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
    }
}