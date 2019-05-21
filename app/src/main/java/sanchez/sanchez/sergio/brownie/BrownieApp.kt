package sanchez.sanchez.sergio.brownie

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

/**
 Brownie App
 **/
class BrownieApp: Application() {

    /**
     * On Create
     */
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

        if (BuildConfig.DEBUG) {
            onDebugConfig()
        } else {
            onReleaseConfig()
        }
    }

    /**
     * On Debug Config
     */
    private fun onDebugConfig() {}

    /**
     * On Release Config
     */
    private fun onReleaseConfig(){}

}