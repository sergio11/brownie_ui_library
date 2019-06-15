package sanchez.sanchez.sergio.brownie

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import sanchez.sanchez.sergio.brownie.di.components.ApplicationComponent
import sanchez.sanchez.sergio.brownie.di.components.DaggerApplicationComponent
import sanchez.sanchez.sergio.brownie.di.modules.ApplicationModule


abstract class BrownieApp: Application() {


    override fun onCreate() {
        super.onCreate()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/roboto_regular.ttf")
                            .build())
                )
                .build())

        instance = this

        applicationComponent = onInitializeInjector()


        if (BuildConfig.DEBUG) {
            onDebugConfig()
        } else {
            onReleaseConfig()
        }

    }

    /**
     * On Debug Config
     */
    open fun onDebugConfig() {}

    /**
     * On Release Config
     */
    open fun onReleaseConfig(){}


    /**
     * Initialize Injector
     */
    private fun onInitializeInjector(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()


    companion object {

        @JvmStatic
        lateinit var instance: BrownieApp

        @JvmStatic
        lateinit var applicationComponent: ApplicationComponent

    }
}