package com.udc.exotest

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.source.dash.DashChunkSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.MimeTypes
import com.udc.exotest.databinding.ActivityMainBinding


class ShitActivity : Activity() {

    private val licenseRequestHeaders:MutableMap<String,String> = mutableMapOf()
    private lateinit var player: ExoPlayer
    private lateinit var binding: ActivityMainBinding
    private val trackSelector:DefaultTrackSelector = DefaultTrackSelector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        licenseRequestHeaders["x-dt-auth-token"] = "eyJhbGciOiJIUzUxMiJ9.eyJjcnQiOiJbe1wiYWNjb3VudGluZ0lkXCI6XCIzN2QzY2Q5ZC1iOGU0LTRhZjctYWViZS1kNGE5MGM2YjhkM2RcIixcImFzc2V0SWRcIjpcIjRfdHY0XCIsXCJ2YXJpYW50SWRcIjpudWxsLFwicHJvZmlsZVwiOntcInJlbnRhbFwiOntcImFic29sdXRlRXhwaXJhdGlvblwiOlwiMjAyMi0wNC0yOVQxMTo0MjoyNS4zNjhaXCIsXCJwbGF5RHVyYXRpb25cIjo4NjQwMDAwMH19LFwib3V0cHV0UHJvdGVjdGlvblwiOntcImRpZ2l0YWxcIjp0cnVlLFwiYW5hbG9ndWVcIjp0cnVlLFwiZW5mb3JjZVwiOmZhbHNlfSxcIm5yT2ZTaW11bHRhbmVvdXNTdHJlYW1zXCI6MCxcInN0b3JlTGljZW5zZVwiOmZhbHNlfV0iLCJvcHREYXRhIjoie1wibWVyY2hhbnRcIjpcInR2bTFcIixcInVzZXJJZFwiOlwiQ01PUkVfMTAxNjM4NzY0XCIsXCJzZXNzaW9uSWRcIjpcIjNlNDk0MmY3MTZhY2Q3MmVmMGM5MTc4MDM0N2ViMDFiYWVhYjJiMWNlOFwifSIsImlhdCI6MTY1MTA1OTc0NSwianRpIjoiMWxwMWowYmpmYzdocGh2cGtqOTE1cXIwNXFmc3ZlOHFjcHVwZ2JtcnUyN2Joa3N1dTk1NCJ9.DC3hThXSeq7x24wRTCQ0A7ZbCrx6rKcGg4JH3-TdfvDjxMiER_eOw5m_HiNx3oUOYbT0jFlHyf5t06YCaUnOVw"

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        initializePlayer()
    }


    private fun initializePlayer() {

        //val hlsUrl = "https://director.streaming.telia.com/tvm-packager-prod/group1/6089bf7347a23d4da85ce79d/cmaf.m3u8"
        //val hlsUrl = /*Yospace*/ "https://csm-e-tv4se2-eb.tls1.yospace.com/csm/live/378400587.m3u8?yo.ac=true&yo.fr=true&yo.me=true&yo.t.jt=750&yo.asd=true&yo.pdt=true&yo.br=false&project=cmore&screenSize=1727x971&os=macOS&deviceType=desktop_mac&geolocation=87.51.7.136&userId=CMORE_101638764"
        //val hlsUrl = "https://lbs-usp-dash-live.cmore.se/tvm-packager-prod/group2/6089bf7347a23d4da85ce79d/cmaf.m3u8"
        //val hlsUrl = "https://lbs-usp-dash-live.cmore.se/tvm-packager-prod/group2/61a4859b79019af4abf1009d/cmaf.m3u8"
        val hlsUrl = "http://192.168.1.4:9494/cmaf.m3u8"

        // val url = "https://director.streaming.telia.com/tvm-packager-prod/group2/60ae4df01522bd3e54695743/cmaf.m3u8"
        // https://director.streaming.telia.com/tvm-packager-prod/group1/6089bf7347a23d4da85ce79d/cmaf.m3u8
        val dashUrl = "https://lbs-usp-dash-live.cmore.se/tvm-packager-prod/group2/61a4859b79019af4abf1009d/manifest.mpd"
        val drmLicenseUrl = "https://lic.drmtoday.com/license-proxy-widevine/cenc/?specConform=true"

        //val drmLicenseUrl = "https://widevine-proxy.appspot.com/proxy"
        val drmSchemeUuid = C.WIDEVINE_UUID // DRM Type

        //#######################################
        // DASH
        //#######################################
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("")
            .setTransferListener(
                DefaultBandwidthMeter.Builder(this)
                    .setResetOnNetworkTypeChange(false)
                    .build()
            )

        val dashChunkSourceFactory: DashChunkSource.Factory = DefaultDashChunkSource.Factory(
            defaultHttpDataSourceFactory
        )
        val manifestDataSourceFactory = DefaultHttpDataSource.Factory().setUserAgent("");
        val dashMediaSource = DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory)
                .createMediaSource(
                    MediaItem.Builder()
                        .setUri(Uri.parse(dashUrl))
                        // DRM Configuration
                        .setDrmConfiguration(
                            MediaItem.DrmConfiguration.Builder(drmSchemeUuid)
                                .setLicenseUri(drmLicenseUrl)
                                .setLicenseRequestHeaders(licenseRequestHeaders)
                                .build()
                        )
                        .setMimeType(MimeTypes.APPLICATION_MPD)
                        .setTag(null)
                        .build()
                )
        //#######################################
        // HLS
        //#######################################

        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource( MediaItem.Builder()
                .setUri(Uri.parse(hlsUrl))
                .setLiveConfiguration(
                    MediaItem.LiveConfiguration.Builder()
                        .setMaxPlaybackSpeed(1.02f)
                        .build())
                .setDrmConfiguration(
                    MediaItem.DrmConfiguration.Builder(drmSchemeUuid)
                        .setLicenseUri(drmLicenseUrl)
                        .setLicenseRequestHeaders(licenseRequestHeaders)
                        .build()
                )
                .setMimeType(MimeTypes.APPLICATION_M3U8)
                .setTag(null)
                .build())

        // Prepare the player.




        /*
        return new DefaultRenderersFactory(context.getApplicationContext())
        .setExtensionRendererMode(extensionRendererMode).setMediaCodecSelector(
            new MediaCodecSelector() {
              @Override
              public List<MediaCodecInfo> getDecoderInfos(String mimeType,
                  boolean requiresSecureDecoder, boolean requiresTunnelingDecoder)
                  throws MediaCodecUtil.DecoderQueryException {
                List<MediaCodecInfo> decoderInfos = MediaCodecSelector.DEFAULT
                    .getDecoderInfos(mimeType, requiresSecureDecoder, requiresTunnelingDecoder);
                if (MimeTypes.VIDEO_H264.equals(mimeType)) {
                  // copy the list because MediaCodecSelector.DEFAULT returns an unmodifiable list
                  decoderInfos = new ArrayList<>(decoderInfos);
                  Collections.reverse(decoderInfos);
                }
                return decoderInfos;
              }
            });
  }
        * */



       /* val renderersFactory = DefaultRenderersFactory(this)
            .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)
            .setMediaCodecSelector(
                MediaCodecSelector() {
                    @Override
                    public List<MediaCodecInfo> getDecoderInfos(String mimeType,
                    boolean requiresSecureDecoder, boolean requiresTunnelingDecoder)
                    throws MediaCodecUtil.DecoderQueryException {
                        List<MediaCodecInfo> decoderInfos = MediaCodecSelector.DEFAULT
                                .getDecoderInfos(mimeType, requiresSecureDecoder, requiresTunnelingDecoder);
                        if (MimeTypes.VIDEO_H264.equals(mimeType)) {
                            // copy the list because MediaCodecSelector.DEFAULT returns an unmodifiable list
                            decoderInfos = new ArrayList<>(decoderInfos);
                            Collections.reverse(decoderInfos);
                        }
                        return decoderInfos;
                    }
                });*/



        player = ExoPlayer.Builder(this, renderersFactory).build()
        /*
        val timer = object: CountDownTimer(20000, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                player.seekTo(0);
            }

            override fun onFinish() {
                player.seekTo(0);
            }
        }
        timer.start()
        */

        val debugListener: EventLogger = EventLogger(null);


        player.addAnalyticsListener(debugListener);

        player.playWhenReady = true
        binding.playerView.player = player


        //player.setMediaSource(dashMediaSource, true)
        player.setMediaSource(hlsMediaSource, true)
        player.prepare()
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }
}