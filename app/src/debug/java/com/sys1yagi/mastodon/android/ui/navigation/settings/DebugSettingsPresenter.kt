package com.sys1yagi.mastodon.android.ui.navigation.settings

import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.Toast
import com.sys1yagi.mastodon.android.R
import com.sys1yagi.mastodon.android.data.database.OrmaDatabaseProvider
import com.sys1yagi.mastodon.android.extensions.async
import com.sys1yagi.mastodon.android.extensions.ui
import javax.inject.Inject

class DebugSettingsPresenter
@Inject
constructor(
        instanceName: String,
        fragment: Fragment,
        view: SettingsContract.View,
        interactor: SettingsContract.Interactor,
        router: SettingsContract.Router,
        databaseProvider: OrmaDatabaseProvider
)
    : SettingsPresenter(instanceName, fragment, view, interactor, router) {

    val database = databaseProvider.database

    override fun initialize(fragment: PreferenceFragmentCompat) {
        super.initialize(fragment)
        val context = fragment.context
        fragment.addPreferencesFromResource(R.xml.preference_debug)

        val disposeTokenAndInstanceName = fragment.findPreference(context.getString(R.string.dispose_token_and_instance_name))
        disposeTokenAndInstanceName.setOnPreferenceClickListener {
            async {
                database.transactionSync {
                    database.deleteAll()
                }
                ui {
                    Toast.makeText(context, "dispose", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}
