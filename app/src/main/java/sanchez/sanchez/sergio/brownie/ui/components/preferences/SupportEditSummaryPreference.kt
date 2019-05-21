package sanchez.sanchez.sergio.brownie.ui.components.preferences

import android.content.Context
import android.util.AttributeSet
import androidx.preference.EditTextPreference

/**
 * Edit Summary Preference
 */
class SupportEditSummaryPreference @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : EditTextPreference(context, attrs) {

    /**
     * Get Summary
     */
    override fun getSummary(): CharSequence =
            if(!text.isNullOrEmpty())
                text
            else
                super.getSummary()
}