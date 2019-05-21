package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.XmlRes
import androidx.preference.*
import sanchez.sanchez.sergio.brownie.ui.components.preferences.SupportDatePreference
import java.lang.ClassCastException

/**
 * Support Preference Fragment
 */
abstract class SupportPreferenceFragment<T>: PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    /**
     * Listener
     */
    private var listener: T? = null


    /**
     * On Create Preferences
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(getPreferencesLayout())
        initPreference(preferenceScreen)
    }

    /**
     * On Attach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as T
        } catch (ex : ClassCastException) {}
    }

    /**
     * On Detach
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Get Preferences Layout
     */
    @XmlRes
    abstract fun getPreferencesLayout(): Int

    /**
     * Config Switch Preference Compat Value
     * @param key
     * @param isEnabled
     */
    protected fun configSwitchPreferenceCompatValue(key: String, isEnabled: Boolean): Boolean {
        val switchPreferenceCompatPreference = findPreference<Preference>(key) as SwitchPreferenceCompat?
        switchPreferenceCompatPreference!!.onPreferenceChangeListener = this
        switchPreferenceCompatPreference.isEnabled = isEnabled
        return switchPreferenceCompatPreference.isChecked
    }

    /**
     * Show Switch Preference Compat
     * @param key
     */
    protected fun showSwitchPreferenceCompat(key: String) {
        findPreference<Preference>(key)?.isVisible = true
    }

    /**
     * Hide Switch Preference Compat
     * @param key
     */
    protected fun hideSwitchPreferenceCompat(key: String) {
        findPreference<Preference>(key)?.isVisible = false
    }

    /**
     * Set Switch Preference Compat Checked
     * @param key
     * @param isChecked
     */
    protected fun setSwitchPreferenceCompatChecked(key: String, isChecked: Boolean) {
        val switchPreferenceCompatPreference = findPreference<Preference>(key) as SwitchPreferenceCompat?
        switchPreferenceCompatPreference?.isChecked = isChecked

    }


    /**
     * Get Switch Preference Compat Value
     * @param key
     * @return
     */
    protected fun getSwitchPrefenceCompatValue(key: String): Boolean {
        val switchPreferenceCompatPreference = findPreference<Preference>(key) as SwitchPreferenceCompat?
        switchPreferenceCompatPreference?.onPreferenceChangeListener = this
        return switchPreferenceCompatPreference?.isChecked ?: false
    }


    /**
     * Toogle Switch Preference Compat
     * @param key
     */
    protected fun toggleSwitchPreferenceCompat(key: String, isEnabled: Boolean) {
        val switchPreferenceCompatPreference = findPreference<Preference>(key) as SwitchPreferenceCompat?
        switchPreferenceCompatPreference?.let {
            if (isEnabled){
                it.isChecked = true
                it.isEnabled = false
            } else {
                it.isEnabled = true
            }
        }
    }


    /**
     * Has List Preference this value
     * @param key
     * @param value
     * @return
     */
    protected fun hasListPreferenceThisValue(key: String, value: String): Boolean {
        val listPreference = findPreference<Preference>(key) as ListPreference?
        return listPreference?.value == value
    }

    /**
     * Get List Preference Value
     */
    protected fun getListPreferenceValue(key: String): String? {
        val listPreference = findPreference<Preference>(key) as ListPreference?
        return listPreference?.value
    }
    /**
     * Switch Preference Compat Is It In This State
     * @param key
     * @param state
     * @return
     */
    protected fun switchPreferenceCompatIsItInThisState(key: String, state: Boolean): Boolean {
        val switchPreferenceCompat = findPreference<Preference>(key) as SwitchPreferenceCompat?
        return state == switchPreferenceCompat?.isChecked
    }


    /**
     * Private Methods
     * =======================
     */


    /**
     * Walks through all preferences.
     * @param p The starting preference to search from.
     */
    private fun initPreference(p: Preference) {
        if (p is PreferenceGroup) {
            for (i in 0 until p.preferenceCount) {
                initPreference(p.getPreference(i))
            }
        } else {
            attachPreferenceListeners(p)
            setPreferenceSummary(p)
        }
    }

    /**
     * Sets up summary providers for the preferences.
     * @param p The preference to set up summary provider.
     */
    private fun setPreferenceSummary(p: Preference) {
        // No need to set up preference summaries for checkbox preferences because
        // they can be set up in xml using summaryOff and summary On
        if (p is SupportDatePreference) {
            p.summaryProvider = SupportDatePreference.SimpleSummaryProvider.instance
        } else if (p is EditTextPreference) {
            p.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
        }
    }

    /**
     * Attach Preference Listener
     */
    private fun attachPreferenceListeners(p: Preference){
        p.onPreferenceChangeListener = this
        p.onPreferenceClickListener = this
    }

}