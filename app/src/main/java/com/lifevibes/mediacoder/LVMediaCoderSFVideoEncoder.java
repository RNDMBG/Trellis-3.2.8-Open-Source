            package com.lifevibes.mediacoder;

            import java.nio.ByteBuffer;
            
            
    public class LVMediaCoderSFVideoEncoder {
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

        public static LVMediaCoderSFVideoEncoder create(String mime) {
            mVideoMime = mime;
            if (mime.equalsIgnoreCase("video/avc")) {
                        return new LVMediaCoderSFVideoEncoder();
                    }
            LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] Creation not supported for non H264 mime", true);
            return null;
                }

        public void configure(int height, int width, int bitrate, int fps) {
            mFrameWidth = width;
            mFrameHeight = height;
            mVideoBitrate = bitrate;
            mFramerate = fps;
            MAX_FRAME_LEN = (int) (((double) (mFrameHeight * mFrameWidth)) * 0.8d);
            mOutEncodedData = new byte[MAX_FRAME_LEN];
                }

        public void start() {
            LVSF_mediaDSI dsiData = new LVSF_mediaDSI();
            dsiData.bufferDSI = new byte[MAX_DSI_LEN];
            dsiData.bufferSize = 0;
           LVSFVideoContext = LVSFVideoEncoderInit(dsiData, mFrameWidth, mFrameHeight, mVideoBitrate, mFramerate);
           if (LVSFVideoContext == 0) {
               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encoder creation failed", true);
               throw new IllegalStateException("LVSF Native ICS Video Encoder creation failed ..!");
                    }
           LVMediaCoderImpl.mVideoDSI = new byte[dsiData.bufferSize];
                    for (int i = 0; i < dsiData.bufferSize; i++) {
               LVMediaCoderImpl.mVideoDSI[i] = dsiData.bufferDSI[i];
                    }
                }

       public void stop() {
           int result = LifeVibesICSMediaCoderDestroy(LVSFVideoContext);
           if (result != 0) {
               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video stop failed ", true);
               throw new RuntimeException("LVSF Native ICS Video stop failed ..!");
                    }
                }

       public void encode(byte[] inputFrame, boolean isEOS) {
           LifeVibesICSMediaCoderEncode(LVSFVideoContext, inputFrame, mOutEncodedData, isEOS);
                }

       public ByteBuffer pollEncoderForOutput() {
           int EncodedDataLen = LifeVibesICSPollEncoderForOutput(LVSFVideoContext, mOutEncodedData);
           if (EncodedDataLen == 0) {
               LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encode poll has DataLen = 0 !", true);
               return null;
                    }
           LVMediaCoderFactory.printLog("[LVSF_VideoEncoder] LVSF Native ICS Video Encode poll has DataLen = " + EncodedDataLen, false);
           byte[] mOutEncodedDatatest = new byte[EncodedDataLen];
                    for (int i = 0; i < EncodedDataLen; i++) {
               mOutEncodedDatatest[i] = mOutEncodedData[i];
                    }
           return ByteBuffer.wrap(mOutEncodedDatatest);
                }
            }
