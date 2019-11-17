package com.jhomlala.frame

import android.app.Application
import com.jhomlala.common.repository.OmdbService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application() {

    val baseNetworkKoinModule =
        module {
            single { OmdbService.create() }
        }
    val applicationKoinModule =
        module {
            single { applicationContext }
        }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(baseNetworkKoinModule, applicationKoinModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}