package com.udc.exotest

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashChunkSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.MimeTypes
import com.udc.exotest.databinding.ActivityMainBinding
class ShitActivity : Activity() {

    private val licenseRequestHeaders:MutableMap<String,String> = mutableMapOf()
    private lateinit var player: ExoPlayer
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        licenseRequestHeaders["x-dt-auth-token"] = "eyJhbGciOiJIUzUxMiJ9.eyJjcnQiOiJbe1wiYWNjb3VudGluZ0lkXCI6XCJhNWU1M2Q4ZC1lZWExLTQxOTQtOGJhNS00ZGI3ZWMxMmNmYWZcIixcImFzc2V0SWRcIjpcIjRfdHY0XCIsXCJ2YXJpYW50SWRcIjpudWxsLFwicHJvZmlsZVwiOntcInJlbnRhbFwiOntcImFic29sdXRlRXhwaXJhdGlvblwiOlwiMjAyMi0wNC0yOFQxMTo1MTowMy4wMDRaXCIsXCJwbGF5RHVyYXRpb25cIjo4NjQwMDAwMH19LFwib3V0cHV0UHJvdGVjdGlvblwiOntcImRpZ2l0YWxcIjp0cnVlLFwiYW5hbG9ndWVcIjp0cnVlLFwiZW5mb3JjZVwiOmZhbHNlfSxcIm5yT2ZTaW11bHRhbmVvdXNTdHJlYW1zXCI6MCxcInN0b3JlTGljZW5zZVwiOmZhbHNlfV0iLCJvcHREYXRhIjoie1wibWVyY2hhbnRcIjpcInR2bTFcIixcInVzZXJJZFwiOlwiQ01PUkVfMTAxNjM4NzY0XCIsXCJzZXNzaW9uSWRcIjpcImFiYWJlNDNhZTYxN2FhMWIwYzg1MjViZDI1MTQzODk4MDY5ZjUzNDFiMlwifSIsImlhdCI6MTY1MDk3Mzg2MywianRpIjoiMXViaXBzaGh0NjF0bWxpYmV2cXZpdnFnNnRpYW1ndDRkZnVpc29hdXNyZWlzZWQ5cnJobyJ9.sPgtVT2JIYMshHh-5j3XFOzKveo3RzoRh_zhfFK6H-LHqSwEk7hSTN8Rrcwi6WPbm3Qjh-0ks6bPnd3ybvNgMA"

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializePlayer()
    }


    private fun initializePlayer() {

        val url = "https://csm-e-cetv4aeun1live102-032437f5c85cf7acd.tls1.yospace.com/csm/live/378400587.m3u8?yo.ac=true&yo.fr=true&yo.me=true&yo.t.jt=750&yo.asd=true&yo.pdt=true&yo.br=false&project=cmore&screenSize=3095x1295&os=macOS&deviceType=desktop_mac&geolocation=87.51.7.136&userId=CMORE_101638764&dcid=d918a1f3-0bcc-438b-a205-b2a87ad0daf6&pid=CMORE_101638764&t=cmore.se%2Cns_st_mv-1.39.4&gdpr_consent=CPQFGEYPQFGEYAcABBENCMCsAP_AAAAAAChQHLwLAAEQAKAAsACAAFQALgAZAA8ACAAGQANAAiQBNAE8ALYAXwAxABuADmAICAQQBBgCFAEYAJgAUoA0QB-gEIAIiARYAjoBOACsgFzAMCAYoA2wB2wEyAKTAWGAvMBlgDdwHCAOXAAAAWEgHAAIAAWABUADIAHgAQAAyABoAEQAJ4AcwBSgLzAcuKgAgLzGQAQF5jgAoACAALgEIAIiApMdATAAWABUADIAIAAZAA0ACIAE8AL4AYgA5gCYAFKANEAiwCZAF5gMsAcuQABgAIAO2ApMhAEAAWABkAL4AYiUAkABAACwAMgBEAC-AGIAUoC8wGWEgAQAFwFJlICAACwAKgAZABAADIAGgARAAngBfADEAHMAUoA0QCLALzAcuUABAAXAO2AA.f_gAAAAAAAAA&gdpr=1&f=noprerolls&yo.oh=Y3NtLWUtdHY0c2UyLWViLnRsczEueW9zcGFjZS5jb20="
        val drmLicenseUrl = "https://lic.drmtoday.com/license-proxy-widevine/cenc/?specConform=true"
        val drmSchemeUuid = C.WIDEVINE_UUID // DRM Type

        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource( MediaItem.Builder()
                .setUri(Uri.parse(url))
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
        player = ExoPlayer.Builder(this)
            .setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .build()

        val debugListener: EventLogger = EventLogger(null);


        player.addAnalyticsListener(debugListener);

        player.playWhenReady = true
        binding.playerView.player = player

        player.setMediaSource(hlsMediaSource, true)
        player.prepare()
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }
}