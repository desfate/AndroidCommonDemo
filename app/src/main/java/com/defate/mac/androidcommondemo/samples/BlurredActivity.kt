package com.defate.mac.androidcommondemo.samples


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_blurred.*
import com.defate.mac.androidcommondemo.R
import com.defate.mac.androidcommondemo.samples.utils.KEY_IMAGE_URI
import com.defate.mac.androidcommondemo.samples.viewmodel.BlurredViewModel

/**
 * 模糊图片activity
 */
class BlurredActivity: AppCompatActivity(){

    // Get the ViewModel
    lateinit var mViewModel : BlurredViewModel

    var burLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blurred)

        mViewModel = ViewModelProviders.of(this).get(BlurredViewModel::class.java!!)

        val imageUriExtra = intent.getStringExtra(KEY_IMAGE_URI)
        mViewModel.setImageUri(imageUriExtra)

        if (mViewModel.getImageUri() != null) {
            Glide.with(this).load(mViewModel.getImageUri()).into(image_view)
        }

        // Setup blur image file button
        button.setOnClickListener { mViewModel.applyBlur(burLevel)}

        // Setup view output image file button
        see_btn.setOnClickListener {
            val currentUri = mViewModel.getOutputUri()
            if (currentUri != null) {
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                if (actionView.resolveActivity(packageManager) != null) {
                    startActivity(actionView)
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { // 拖动进行时调用该方法
                burLevel = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { // 拖动开始时调用该方法
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { // 拖动结束时调用该方法
            }

        })

        //show work state
        mViewModel.getOutputWorkInfo().observe(this, Observer {
            // Note that these next few lines grab a single WorkInfo if it exists
            // This code could be in a Transformation in the ViewModel; they are included here
            // so that the entire process of displaying a WorkInfo is in one location.

            // If there are no matching work info, do nothing
            if (it == null || it.isEmpty()) {
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = it.get(0)

            val finished = workInfo.getState().isFinished()
            if (!finished) {
                showWorkInProgress()
            } else {
                showWorkFinished()

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.
                val outputData = workInfo.getOutputData()

                val outputImageUri = outputData.getString(KEY_IMAGE_URI)

                // If there is an output file show "See File" button
                if (!TextUtils.isEmpty(outputImageUri)) {
                    mViewModel.setOutputUri(outputImageUri)
                    see_btn.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    /**
     * image blurred finished
     */
    private fun showWorkFinished(){
        progress_bar.visibility = View.GONE
    }

    /**
     * image blurred inProgress
     */
    private fun showWorkInProgress(){
        progress_bar.visibility = View.VISIBLE
    }

}