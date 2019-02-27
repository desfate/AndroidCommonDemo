package com.defate.mac.androidcommondemo.samples.utils

import android.util.Log

class SleepTools{

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    companion object {
        fun sleep(){
            try {
                Thread.sleep(3000, 0)
            } catch (e: InterruptedException) {
                Log.d("SleepTools", e.message)
            }

        }
    }
}