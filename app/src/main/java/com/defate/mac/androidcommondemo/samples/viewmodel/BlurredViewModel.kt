package com.defate.mac.androidcommondemo.samples.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import android.text.TextUtils
import androidx.work.*
import com.defate.mac.androidcommondemo.samples.utils.IMAGE_MANIPULATION_WORK_NAME
import com.defate.mac.androidcommondemo.samples.utils.KEY_IMAGE_URI
import com.defate.mac.androidcommondemo.samples.utils.TAG_OUTPUT
import com.defate.mac.androidcommondemo.samples.utils.works.BlurredImageWorker
import com.defate.mac.androidcommondemo.samples.utils.works.CleanImageWorker
import com.defate.mac.androidcommondemo.samples.utils.works.SaveImageToFileWorker

/**
 * 模糊类viewModel
 */
class BlurredViewModel : ViewModel(){


    private var mImageUri: Uri? = null
    private var mOutputUri: Uri? = null

    private var mWorkManager: WorkManager = WorkManager.getInstance()
    private var mSavedWorkInfo: LiveData<List<WorkInfo>>  = mWorkManager.getWorkInfosByTagLiveData(TAG_OUTPUT);

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {

        // Add WorkRequest to Cleanup temporary images
        var continuation = mWorkManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanImageWorker::class.java)
            )

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequest.Builder(BlurredImageWorker::class.java!!)

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        // Add WorkRequest to save the image to the filesystem
        val save = OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java!!)
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        // Actually start the work
        continuation.enqueue()

    }

    /**
     * Cancel work using the work's unique name
     */
    internal fun cancelWork() {
        mWorkManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        if (mImageUri != null) {
            builder.putString(KEY_IMAGE_URI, mImageUri.toString())
        }
        return builder.build()
    }


    /**
     * Setters
     */
    internal fun setImageUri(uri: String) {
        mImageUri = uriOrNull(uri)
    }

    internal fun setOutputUri(outputImageUri: String?) {
        mOutputUri = uriOrNull(outputImageUri)
    }

    /**
     * Getters
     */
    internal fun getImageUri(): Uri? {
        return mImageUri
    }

    internal fun getOutputUri(): Uri? {
        return mOutputUri
    }

    internal fun getOutputWorkInfo(): LiveData<List<WorkInfo>> {
        return mSavedWorkInfo
    }


    private fun uriOrNull(uriString: String?): Uri? {
        return if (!TextUtils.isEmpty(uriString)) {
            Uri.parse(uriString)
        } else null
    }


}