            package com.lifevibes.mediacoder;

            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 69 */    public class LVMediaCoderSFVideoEncoder {
                static int MAX_FRAME_LEN = 0;
                private static final String TAG = "LVSF_VideoEncoder";
                static byte[] mOutEncodedData;
                static String mVideoMime;
                public static int LVMC_STREAM_VIDEO = 1;
                public static int LVMC_STREAM_AUDIO = 2;
                private static int MAX_DSI_LEN = 128;
                private static int LVSFVideoContext = 0;
                static int mFrameWidth = 0;
                static int mFrameHeight = 0;
                static int mVideoBitrate = 0;
                static int mFramerate = 0;

                public static final class LVSF_mediaDSI {
                    public byte[] bufferDSI;
                    public int bufferSize;
                }

                private static native int LVSFVideoEncoderInit(LVSF_mediaDSI lVSF_mediaDSI, int i, int i2, int i3, int i4);

                private static native int LifeVibesICSMediaCoderDestroy(int i);

                private static native int LifeVibesICSMediaCoderEncode(int i, byte[] bArr, byte[] bArr2, boolean z);

                private static native int LifeVibesICSPollEncoderForOutput(int i, byte[] bArr);

/* 70 */        public static LVMediaCoderSFVideoEncoder create(String mime) {
/* 71 */            mVideoMime = mime;
/* 72 */            if (mime.equalsIgnoreCase("video/avc")) {
                        return new LVMediaCoderSFVideoEncoder();
                    }
/* 74 */            LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] Creation not supported for non H264 mime", true);
/* 75 */            return null;
                }

/* 83 */        public void configure(int height, int width, int bitrate, int fps) {
/* 84 */            mFrameWidth = width;
/* 85 */            mFrameHeight = height;
/* 86 */            mVideoBitrate = bitrate;
/* 87 */            mFramerate = fps;
/* 88 */            MAX_FRAME_LEN = (int) (((double) (mFrameHeight * mFrameWidth)) * 0.8d);
/* 89 */            mOutEncodedData = new byte[MAX_FRAME_LEN];
                }

/* 95 */        public void start() {
/* 97 */            LVSF_mediaDSI dsiData = new LVSF_mediaDSI();
/* 98 */            dsiData.bufferDSI = new byte[MAX_DSI_LEN];
/* 99 */            dsiData.bufferSize = 0;
/* 102 */           LVSFVideoContext = LVSFVideoEncoderInit(dsiData, mFrameWidth, mFrameHeight, mVideoBitrate, mFramerate);
/* 103 */           if (LVSFVideoContext == 0) {
/* 104 */               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encoder creation failed", true);
/* 105 */               throw new IllegalStateException("LVSF Native ICS Video Encoder creation failed ..!");
                    }
/* 108 */           LVMediaCoderImpl.mVideoDSI = new byte[dsiData.bufferSize];
                    for (int i = 0; i < dsiData.bufferSize; i++) {
/* 110 */               LVMediaCoderImpl.mVideoDSI[i] = dsiData.bufferDSI[i];
                    }
                }

/* 118 */       public void stop() {
/* 119 */           int result = LifeVibesICSMediaCoderDestroy(LVSFVideoContext);
/* 120 */           if (result != 0) {
/* 121 */               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video stop failed ", true);
/* 122 */               throw new RuntimeException("LVSF Native ICS Video stop failed ..!");
                    }
                }

/* 130 */       public void encode(byte[] inputFrame, boolean isEOS) {
/* 134 */           LifeVibesICSMediaCoderEncode(LVSFVideoContext, inputFrame, mOutEncodedData, isEOS);
                }

/* 138 */       public ByteBuffer pollEncoderForOutput() {
/* 142 */           int EncodedDataLen = LifeVibesICSPollEncoderForOutput(LVSFVideoContext, mOutEncodedData);
/* 144 */           if (EncodedDataLen == 0) {
/* 145 */               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encode poll has DataLen = 0 !", true);
/* 146 */               return null;
                    }
/* 148 */           LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encode poll has DataLen = " + EncodedDataLen, false);
/* 150 */           byte[] mOutEncodedDatatest = new byte[EncodedDataLen];
                    for (int i = 0; i < EncodedDataLen; i++) {
/* 152 */               mOutEncodedDatatest[i] = mOutEncodedData[i];
                    }
/* 154 */           return ByteBuffer.wrap(mOutEncodedDatatest);
                }
            }
