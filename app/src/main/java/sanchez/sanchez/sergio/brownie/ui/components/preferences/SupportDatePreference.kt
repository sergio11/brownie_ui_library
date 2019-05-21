package sanchez.sanchez.sergio.brownie.ui.components.preferences

import android.content.Context
import android.util.AttributeSet
import android.widget.DatePicker
import androidx.preference.DialogPreference
import android.content.res.TypedArray
import androidx.preference.Preference
import sanchez.sanchez.sergio.brownie.R

/**
 * Support Date Preference
 */
class SupportDatePreference @JvmOverloads
        constructor(context: Context, attrs: AttributeSet? = null): DialogPreference(context, attrs), DatePicker.OnDateChangedListener {


    private var mDateValue: String? = null

    /**
     * on Get Default Value
     */
    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any? {
        return a?.getString(index)
    }

    /**
     * on Set Initial Value
     */
    override fun onSetInitialValue(defaultValue: Any?) {
        setDate(getPersistedString(defaultValue as String?))
    }

    /**
     * Gets the date as a string from the current data storage.
     *
     * @return string representation of the date.
     */
    fun getDate(): String? {
        return mDateValue
    }

    /**
     * Saves the date as a string in the current data storage.
     *
     * @param text string representation of the date to save.
     */
    fun setDate(text: String) {
        val wasBlocking = shouldDisableDependents()
        mDateValue = text
        persistString(text)
        val isBlocking = shouldDisableDependents()
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking)
        }
        notifyChanged()
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        System.out.println("on Date Changed")
    }

    /**
     * A simple [androidx.preference.Preference.SummaryProvider] implementation for an
     * [DatePreference]. If no value has been set, the summary displayed will be 'Not
     * set', otherwise the summary displayed will be the value set for this preference.
     */
    class SimpleSummaryProvider private constructor() : Preference.SummaryProvider<SupportDatePreference> {

        /**
         * Provide Summary
         */
        override fun provideSummary(preference: SupportDatePreference): CharSequence {
            return if (!preference.getDate().isNullOrEmpty()) {
                preference.context.getString(R.string.not_set)
            } else {
                preference.getDate()!!
            }
        }

        companion object {

            private var sSimpleSummaryProvider: SimpleSummaryProvider? = null

            /**
             * Retrieve a singleton instance of this simple
             * [androidx.preference.Preference.SummaryProvider] implementation.
             *
             * @return a singleton instance of this simple
             * [androidx.preference.Preference.SummaryProvider] implementation
             */
            val instance: SimpleSummaryProvider
                get() {
                    if (sSimpleSummaryProvider == null) {
                        sSimpleSummaryProvider = SimpleSummaryProvider()
                    }
                    return sSimpleSummaryProvider as SimpleSummaryProvider
                }
        }
    }

}