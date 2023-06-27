package com.example.criminalintent.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.telecom.Call
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = setupDateListener()
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val (year, month, day) = getDateFromDate(date)
        return DatePickerDialog(requireContext(), dateListener, year, month, day)
    }

    private fun setupDateListener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val resultDate: Date = GregorianCalendar(year, month, dayOfMonth).time
            targetFragment?.let {
                (it as Callbacks).onDateSelected(resultDate)
            }
        }
    }

    private fun getDateFromDate(date: Date): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        var initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return Triple(initialYear, initialMonth, initialDay)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}