package sanchez.sanchez.sergio.brownie.ui.components.preferences

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference

/**
 * List Summary Preference
 */
class SupportListSummaryPreference @JvmOverloads
            constructor(context: Context, attrs: AttributeSet? = null) : ListPreference(context, attrs) {

    /**
     * Get Summary
     */
    override fun getSummary(): CharSequence =
        if(!entry.isNullOrEmpty())
            entry
        else
            super.getSummary()
}