            package com.lifevibes.mediacoder;

            import com.google.android.exoplayer.util.MimeTypes;
            import java.nio.ShortBuffer;
            
            
    public class LVMediaCoderSFAudioEncoder {
                private static final String TAG = "LVSF_AudioEncoder";
                static byte[] mAudioDataInput;
                static String mAudioMime;
                private int LVSFAudioContext = 0;
                private static int MAX_DSI_LEN = 128;
                static int mSampleRate = 0;
                static int mAudioBitrate = 0;
                static int mChannels = 0;
                static int MAX_FRAME_LEN = 4096;
                static byte[] mOutEncodedData = new byte[MAX_FRAME_LEN];

                private static native int LVSFAudioEncoderEncode(int i, byte[] bArr, byte[] bArr2, int i2);

                private static native int LVSFAudioEncoderGetDSIInfo(int i, byte[] bArr);

                private static native int LVSFAudioEncoderInit(int i, int i2, int i3);

                private static native int LVSFAudioEncoderStop(int i);

        public static LVMediaCoderSFAudioEncoder create(String mime) {
            mAudioMime = mime;
            if (mime.equalsIgnoreCase(MimeTypes.AUDIO_AAC)) {
                        return new LVMediaCoderSFAudioEncoder();
                    }
            LVMediaCoderFactory.printLog("[LVSF_AudioEncoder] Creation not supported for non AAC mime", true);
            return null;
                }

        public void configure(int bitrate, int sampleRate, int channels) {
            mAudioBitrate = bitrate;
            mSampleRate = sampleRate;
            mChannels = channels;
                }

        public void start() {
            this.LVSFAudioContext = LVSFAudioEncoderInit(mAudioBitrate, mSampleRate, mChannels);
            if (this.LVSFAudioContext == 0) {
                throw new IllegalStateException("LVSF Native ICS Audio Encoder creation failed ..!");
                    }
                }

        public void getDSI() {
            byte[] tempDSI = new byte[MAX_DSI_LEN];
            if (this.LVSFAudioContext != 0) {
                int dsilen = LVSFAudioEncoderGetDSIInfo(this.LVSFAudioContext, tempDSI);
                LVMediaCoderImpl.mAudioDSI = new byte[dsilen];
                        for (int i = 0; i < dsilen; i++) {
                    LVMediaCoderImpl.mAudioDSI[i] = tempDSI[i];
                        }
                    }
                }

       public void stop() {
           int result = LVSFAudioEncoderStop(this.LVSFAudioContext);
           if (result != 0) {
               throw new IllegalStateException("LVSF Native ICS Audio Encoder stop failed ..!");
                    }
                }

       public ShortBuffer encode(byte[] inPCM) {
           int inputLength = mChannels * 2048;
           int size = LVSFAudioEncoderEncode(this.LVSFAudioContext, inPCM, mOutEncodedData, inputLength);
           ShortBuffer shortBuffer = ShortBuffer.allocate(size);
           short[] shortArray2 = new short[size];
                    for (int index = 0; index < size; index++) {
               shortArray2[index] = mOutEncodedData[index];
                    }
           shortBuffer.put(shortArray2);
           return shortBuffer;
                }
            }
