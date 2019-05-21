package sanchez.sanchez.sergio.brownie.ui.components.preferences

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.preference.PreferenceDialogFragmentCompat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Support Date Preference Dialog Fragment
 */
class SupportDatePreferenceDialogFragment: PreferenceDialogFragmentCompat() {

    private var lastYear: Int = 0
    private var lastMonth: Int = 0
    private var lastDay: Int = 0
    private lateinit var datePicker: DatePicker

    /**
     * on Create
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dateValue = getDatePreference().getDate() ?: fun(): String {
            val calendar = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return df.format(calendar.time)
        }.invoke()

        lastYear = getYear(dateValue)
        lastMonth = getMonth(dateValue)
        lastDay = getDay(dateValue)
    }


    /**
     * on Create Dialog View
     */
    override fun onCreateDialogView(context: Context?): View {
        datePicker = DatePicker(getContext())
        // Show spinner dialog for old APIs.
        datePicker.calendarViewShown = false
        return datePicker
    }

    /**
     * on Bind Dialog View
     */
    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)
        datePicker.updateDate(lastYear, lastMonth - 1, lastDay)
    }

    /**
     * on Dialog Closed
     */
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            lastYear = datePicker.year
            lastMonth = datePicker.month + 1
            lastDay = datePicker.dayOfMonth

            val dateVal = (lastYear.toString() + "-"
                    + lastMonth.toString() + "-"
                    + lastDay.toString())

            val preference = getDatePreference()
            if (preference.callChangeListener(dateVal)) {
                preference.setDate(dateVal)
            }
        }
    }

    /**
     * Private Methods
     * =================
     */

    /**
     * Get Date Preference
     */
    private fun getDatePreference(): SupportDatePreference {
        return preference as SupportDatePreference
    }

    /**
     * Get Year
     */
    private fun getYear(dateString: String): Int {
        val datePieces = dateString.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(datePieces[0])
    }

    /**
     * Get Month
     */
    private fun getMonth(dateString: String): Int {
        val datePieces = dateString.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(datePieces[1])
    }

    /**
     * Get Day
     */
    private fun getDay(dateString: String): Int {
        val datePieces = dateString.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(datePieces[2])
    }


    companion object {

        @JvmStatic
        fun newInstance(key: String) =
                SupportDatePreferenceDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_KEY, key)
                    }
                }
    }

}