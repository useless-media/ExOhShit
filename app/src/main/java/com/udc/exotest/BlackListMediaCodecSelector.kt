/*

import com.google.android.exoplayer2.mediacodec.MediaCodecInfo
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil

import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException


*/
/*
package com.udc.exotest

import com.google.android.exoplayer2.mediacodec.MediaCodecInfo
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException


class BlackListMediaCodecSelector : MediaCodecSelector {
    //final static String[] BLACKLISTEDCODECS = {"OMX.amlogic.avc.decoder.awesome.secure"};

    @Throws(DecoderQueryException::class)
    override fun getDecoderInfos(
        mimeType: String,
        requiresSecureDecoder: Boolean,
        requiresTunnelingDecoder: Boolean
    ): List<MediaCodecInfo> {
        val codecInfos = MediaCodecUtil.getDecoderInfos(
            mimeType, requiresSecureDecoder, requiresTunnelingDecoder
        )
        // filter codecs based on blacklist template
        val filteredCodecInfos: MutableList<MediaCodecInfo> = ArrayList()
        for (codecInfo in codecInfos) {
            var blacklisted = false
            for (blackListedCodec in BLACKLISTEDCODECS) {
                if (codecInfo.name.contains(blackListedCodec)) {
                    blacklisted = true
                    break
                }
            }
            if (!blacklisted) {
                filteredCodecInfos.add(codecInfo)
            }
        }
        return filteredCodecInfos
    }

    *//*

*/
/*@get:Throws(DecoderQueryException::class)
    val passthroughDecoderInfo: MediaCodecInfo?
        get() = MediaCodecUtil.getPassthroughDecoderInfo()
*//*
*/
/*

    companion object {
        // list of strings used in blacklisting codecs
        val BLACKLISTEDCODECS = arrayOf<String>()
    }
}*//*


class BlackListMediaCodecSelector : MediaCodecSelector {
    //final static String[] BLACKLISTEDCODECS = {"OMX.amlogic.avc.decoder.awesome.secure"};
    @Throws(DecoderQueryException::class)
    override fun getDecoderInfos(
        mimeType: String,
        requiresSecureDecoder: Boolean,
        requiresTunnelingDecoder: Boolean
    ): List<MediaCodecInfo> {
        val codecInfos: List<MediaCodecInfo> = MediaCodecUtil.getDecoderInfos(
            mimeType, requiresSecureDecoder, requiresTunnelingDecoder
        )
        // filter codecs based on blacklist template
        val filteredCodecInfos: MutableList<MediaCodecInfo> = ArrayList()
        for (codecInfo in codecInfos) {
            var blacklisted = false
            for (blackListedCodec in BLACKLISTEDCODECS) {
                if (codecInfo.name.contains(blackListedCodec)) {
                    blacklisted = true
                    break
                }
            }
            if (!blacklisted) {
                filteredCodecInfos.add(codecInfo)
            }
        }
        return filteredCodecInfos
    }

    val passthroughDecoderInfo: MediaCodecInfo
        get() = MediaCodecUtil.getPassthroughDecoderInfo()

    companion object {
        // list of strings used in blacklisting codecs
        val BLACKLISTEDCODECS = arrayOf<String>()
    }
}*/
