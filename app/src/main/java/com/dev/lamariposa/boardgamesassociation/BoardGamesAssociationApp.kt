package com.dev.lamariposa.boardgamesassociation

import android.app.Application
import com.dev.lamariposa.boardgamesassociation.di.appModule
import com.dev.lamariposa.boardgamesassociation.di.authModule
import com.dev.lamariposa.boardgamesassociation.di.dataModule
import com.dev.lamariposa.boardgamesassociation.di.domainModule
import com.dev.lamariposa.boardgamesassociation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BoardGamesAssociationApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin for dependency injection
        startKoin {
            androidContext(this@BoardGamesAssociationApp)
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule,
                authModule
            )
        }
    }
}
