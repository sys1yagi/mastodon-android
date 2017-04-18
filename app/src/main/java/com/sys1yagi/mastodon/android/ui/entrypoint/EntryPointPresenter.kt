package com.sys1yagi.mastodon.android.ui.entrypoint

import android.app.Activity
import android.content.Context
import co.metalab.asyncawait.async
import com.sys1yagi.mastodon.android.data.database.Credential
import com.sys1yagi.mastodon.android.extensions.await
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EntryPointPresenter
@Inject constructor(
        val activity: Activity,
        val view: EntryPointContract.View,
        val interactor: EntryPointContract.Interactor,
        val router: EntryPointContract.Router
) : EntryPointContract.Presenter, EntryPointContract.InteractorOutput {

    val viewModel = EntryPointViewModel()

    override fun onResume() {
        interactor.startInteraction(this)
        view.showMessage("check instance name...")
        interactor.checkInstanceName()
    }

    override fun onPause() {
        interactor.stoplInteraction(this)
    }

    override fun onError(t: Throwable) {
        view.showError(t.message ?: "error")
    }

    override fun onInstanceNameNotRegistered() {
        async {
            await(Completable.complete().delay(1, TimeUnit.SECONDS))
            router.openSetInstanceNameActivity(activity)
            view.finish()
        }
    }

    override fun onInstanceNameFound(credential: Credential) {
        view.showMessage("Check ${credential.instanceName} credential...")
        interactor.checkRegistration(credential)
    }

    override fun onRegistrationNotRegistered(credential: Credential) {
        view.showMessage("Register ${credential.instanceName} credential...")
        interactor.registerCredential(credential)
    }

    override fun onRegistrationFound(credential: Credential) {
        view.showMessage("Check ${credential.instanceName} authorization...")
        interactor.checkAuthentication(credential)
    }

    override fun onUnAuthorizedOrExpired(credential: Credential) {
        router.openLoginActivity(activity, credential.instanceName)
        view.finish()
    }

    override fun onAuthorized(credential: Credential) {
        async {
            view.showMessage("The ${credential.instanceName} already authorized!")
            await(Completable.complete().delay(1, TimeUnit.SECONDS))
            router.openHomeActivity(activity)
            view.finish()
        }
    }
}
