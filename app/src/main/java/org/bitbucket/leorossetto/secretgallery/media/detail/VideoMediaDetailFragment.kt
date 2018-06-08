package org.bitbucket.leorossetto.secretgallery.media.detail

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_video_media_detail.*
import org.bitbucket.leorossetto.secretgallery.R
import java.io.File

class VideoMediaDetailFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_video_media_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = arguments?.getSerializable(EXTRA_FILE) ?: return

        videoPlayer.setVideoURI(Uri.fromFile(file as File))
        videoPlayer.setOnPreparedListener { mp ->
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
        }
        videoPlayer.setOnCompletionListener {
            playButton.isActivated = !playButton.isActivated
            playButton.visibility = View.VISIBLE
        }
        view.setOnClickListener {
            playButton.visibility = View.VISIBLE
        }

        playButton.setOnClickListener { play ->
            play.isActivated = !play.isActivated
            if (play.isActivated) {
                play.visibility = View.GONE
                videoPlayer.start()
            } else {
                videoPlayer.pause()
            }
        }
        //well, I did not liked this but.
        videoPlayer.seekTo(100)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser) {
            if (playButton != null && videoPlayer != null) {
                playButton.isActivated = false
                playButton.visibility = View.VISIBLE
                videoPlayer.pause()
            }
        }
    }

    companion object {
        private const val EXTRA_FILE = "extra-detail-video"

        fun newInstance(file: File): VideoMediaDetailFragment {
            val fragment = VideoMediaDetailFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(EXTRA_FILE, file)
            }
            return fragment
        }
    }
}