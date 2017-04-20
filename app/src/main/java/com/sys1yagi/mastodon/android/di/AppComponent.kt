package com.sys1yagi.mastodon.android.di

import com.sys1yagi.mastodon.android.MastodonAndroidApplication
import com.sys1yagi.mastodon.android.ui.auth.login.LoginActivityModule
import com.sys1yagi.mastodon.android.ui.auth.setinstancename.SetInstanceNameActivityModule
import com.sys1yagi.mastodon.android.ui.entrypoint.EntryPointActivityModule
import com.sys1yagi.mastodon.android.ui.navigation.NavigationActivityModule
import com.sys1yagi.mastodon.android.ui.navigation.home.localtimeline.LocalTimelineFragmentModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        EntryPointActivityModule::class,
        NavigationActivityModule::class,
        SetInstanceNameActivityModule::class,
        LoginActivityModule::class
))
interface AppComponent {

    fun inject(target: MastodonAndroidApplication)
}
