            package com.lifevibes.mediacoder;

            import com.google.android.exoplayer.util.MimeTypes;
            import java.nio.ShortBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class LVMediaCoderSFAudioEncoder {
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

/* 55 */        public static LVMediaCoderSFAudioEncoder create(String mime) {
/* 56 */            mAudioMime = mime;
/* 57 */            if (mime.equalsIgnoreCase(MimeTypes.AUDIO_AAC)) {
                        return new LVMediaCoderSFAudioEncoder();
                    }
/* 59 */            LVMediaCoderFactory.printLog("[LVSF_AudioEncoder] Creation not supported for non AAC mime", true);
/* 60 */            return null;
                }

/* 68 */        public void configure(int bitrate, int sampleRate, int channels) {
/* 69 */            mAudioBitrate = bitrate;
/* 70 */            mSampleRate = sampleRate;
/* 71 */            mChannels = channels;
                }

/* 77 */        public void start() {
/* 78 */            this.LVSFAudioContext = LVSFAudioEncoderInit(mAudioBitrate, mSampleRate, mChannels);
/* 79 */            if (this.LVSFAudioContext == 0) {
/* 80 */                throw new IllegalStateException("LVSF Native ICS Audio Encoder creation failed ..!");
                    }
                }

/* 87 */        public void getDSI() {
/* 88 */            byte[] tempDSI = new byte[MAX_DSI_LEN];
/* 90 */            if (this.LVSFAudioContext != 0) {
/* 92 */                int dsilen = LVSFAudioEncoderGetDSIInfo(this.LVSFAudioContext, tempDSI);
/* 93 */                LVMediaCoderImpl.mAudioDSI = new byte[dsilen];
                        for (int i = 0; i < dsilen; i++) {
/* 95 */                    LVMediaCoderImpl.mAudioDSI[i] = tempDSI[i];
                        }
                    }
                }

/* 103 */       public void stop() {
/* 104 */           int result = LVSFAudioEncoderStop(this.LVSFAudioContext);
/* 105 */           if (result != 0) {
/* 106 */               throw new IllegalStateException("LVSF Native ICS Audio Encoder stop failed ..!");
                    }
                }

/* 113 */       public ShortBuffer encode(byte[] inPCM) {
/* 114 */           int inputLength = mChannels * 2048;
/* 115 */           int size = LVSFAudioEncoderEncode(this.LVSFAudioContext, inPCM, mOutEncodedData, inputLength);
/* 117 */           ShortBuffer shortBuffer = ShortBuffer.allocate(size);
/* 118 */           short[] shortArray2 = new short[size];
                    for (int index = 0; index < size; index++) {
/* 121 */               shortArray2[index] = mOutEncodedData[index];
                    }
/* 123 */           shortBuffer.put(shortArray2);
/* 124 */           return shortBuffer;
                }
            }
