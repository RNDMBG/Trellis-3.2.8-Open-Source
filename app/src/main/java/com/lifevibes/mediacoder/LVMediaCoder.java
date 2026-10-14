            package com.lifevibes.mediacoder;

            import java.nio.ByteBuffer;
            import java.nio.ShortBuffer;
            
            
            public interface LVMediaCoder {

                public interface LVMediaCoderICSEncoderProgressListener {
                    void onComplete();

                    void onError(Exception exc, int i);

                    void onFrameAvailable(ByteBuffer byteBuffer);
                }

                void cancel() throws IllegalStateException;

                ShortBuffer encodeAudio(byte[] bArr, int i, int i2) throws IllegalStateException;

                ByteBuffer encodeVideo(ByteBuffer byteBuffer, int i) throws IllegalStateException;

                void encodeVideoAsync(ByteBuffer byteBuffer, int i, boolean z) throws IllegalStateException;

                LVMediaCoderConfiguration getConfiguration();

                String getOutputFilePath();

                void recordAudio(ShortBuffer shortBuffer) throws IllegalStateException;

                void recordVideo(ByteBuffer byteBuffer, int i) throws IllegalStateException;

                void setEncoderVideoAsyncListener(LVMediaCoderICSEncoderProgressListener lVMediaCoderICSEncoderProgressListener);

                void startEncoding() throws IllegalStateException;

                void startRecording(String str) throws IllegalArgumentException;

                void stop() throws IllegalStateException;
            }
