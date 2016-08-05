package com.hubin.android.p2pvoice.utils;

import android.media.AudioFormat;

/**
 * Created by tester on 16-7-3.
 */
public class UiConstants {
    public static final String FILE_NAME_SEND_AUDIO = "sendrecording";
    public static final String FILE_NAME_RECEIVE_AUDIO = "recrecording";

    public static final String DEFAULT_REMOTE_POINTER_IP = "127.0.0.1";
    public static final int DEFAULT_AUDIO_PORT = 9005;
    public static final int DEFAULT_AUDIO_SAMPLE_RATE = 16000;
    public static final int DEFAULT_AUDIO_BUFFER_SIZE = 320;
    public static final int AUDIO_CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;


    public static final String AUDIO_SAMPLE_RATE = "AUDIO_SAMPLE_RATE";
    public static final String REMOTE_POINTER_IP="REMOTE_POINTER_IP";
    public static final String REMOTE_POINTER_PORT="REMOTE_POINTER_PORT";
    public static final String AUDIO_BUFFER_SIZE ="AUDIO_BUFFER_SIZE";
    public static final String IS_SAVE_RECEIVED_AUDIO ="IS_SAVE_RECEIVED_AUDIO";
    public static final String IS_SAVE_SEND_AUDIO ="IS_SAVE_SEND_AUDIO";
}
