            package co.vine.android.embed.player;

            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.media.MediaFormat;
            import android.view.Surface;
            import co.vine.android.util.AudioUtils;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class AudioPreparingThread extends Thread {
                private static final float MICROS_PER_SECOND = 1000000.0f;
                private static final int NO_OUTPUT_COUNTER_LIMIT = 50;
                private static final int TIMEOUT_USEC = 10000;
                private boolean mCancelled;
                private Exception mError;
                private final VineSampleSource.VineMediaExtractor mExtractor;
                private ByteBuffer mPreparedSamples;
                private ByteBuffer mUnpreparedSamples;

/* 26 */        public AudioPreparingThread(VineSampleSource.VineMediaExtractor extractor) {
/* 28 */            this.mExtractor = extractor;
                }

                @Override // java.lang.Thread, java.lang.Runnable
/* 33 */        public void run() {
/* 34 */            VineSampleSource.EncodedAudioSamples audioSamples = this.mExtractor.getAudioSamples();
/* 35 */            byte[] data = audioSamples.data;
/* 36 */            AudioSampleTrack track = audioSamples.track;
/* 37 */            AudioFrame[] frames = audioSamples.frames;
/* 38 */            long endMicros = audioSamples.estimatedDuration;
/* 39 */            MediaFormat format = track.format;
/* 40 */            int sampleRate = -1;
/* 41 */            int channelCount = format.getInteger("channel-count");
/* 42 */            if (format.containsKey("sample-rate")) {
/* 43 */                sampleRate = format.getInteger("sample-rate");
                    }
/* 46 */            if (sampleRate <= 0) {
/* 47 */                throw new RuntimeException("no sample rate");
                    }
/* 50 */            if (frames.length != 0) {
/* 54 */                long startMicros = frames[0].startUs;
/* 55 */                MediaCodec decoder = null;
/* 56 */                boolean isStarted = false;
                        try {
                            try {
/* 58 */                        decoder = MediaCodec.createDecoderByType(format.getString("mime"));
/* 59 */                        decoder.configure(format, (Surface) null, (MediaCrypto) null, 0);
/* 60 */                        decoder.start();
/* 61 */                        isStarted = true;
/* 62 */                        ByteBuffer[] input = decoder.getInputBuffers();
/* 63 */                        ByteBuffer[] output = decoder.getOutputBuffers();
/* 64 */                        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
/* 65 */                        int noOutputCounter = 0;
/* 67 */                        Boolean doStop = false;
/* 69 */                        int bufferSize = 0;
/* 71 */                        int frameIndex = 0;
/* 73 */                        byte[] unpreparedArray = new byte[((int) (((((double) sampleRate) * 1.1d) / 1000.0d) * ((endMicros - startMicros) / 1000) * 1.1d * 4.0d)) * channelCount];
/* 76 */                        this.mUnpreparedSamples = ByteBuffer.wrap(unpreparedArray);
/* 77 */                        this.mUnpreparedSamples.limit(0);
/* 79 */                        byte[] chunk = null;
/* 80 */                        long sampleDurationUs = (long) ((((1.0f / sampleRate) / 2.0f) * MICROS_PER_SECOND) / channelCount);
/* 81 */                        while (noOutputCounter < 50 && !this.mCancelled && !doStop.booleanValue()) {
                                    if (frameIndex >= frames.length) {
                                        break;
                                    }
/* 82 */                            AudioFrame frame = frames[frameIndex];
/* 83 */                            noOutputCounter++;
/* 85 */                            int inputBufIndex = decoder.dequeueInputBuffer(10000L);
/* 87 */                            if (inputBufIndex >= 0) {
/* 88 */                                ByteBuffer dstBuf = input[inputBufIndex];
/* 89 */                                dstBuf.position(0);
/* 90 */                                dstBuf.put(data, frame.offset, frame.size);
/* 91 */                                decoder.queueInputBuffer(inputBufIndex, 0, frame.size, frame.presentationTimeUs, 0);
/* 93 */                                frameIndex++;
                                    }
/* 96 */                            int res = decoder.dequeueOutputBuffer(info, 10000L);
/* 98 */                            if (res >= 0) {
/* 99 */                                if (info.size > 0) {
/* 100 */                                   noOutputCounter = 0;
                                        }
/* 103 */                               ByteBuffer buf = output[res];
/* 105 */                               if (chunk == null || chunk.length < info.size) {
/* 106 */                                   chunk = new byte[info.size];
                                        }
/* 109 */                               if (info.size > 0) {
/* 110 */                                   buf.get(chunk, 0, info.size);
                                        }
/* 113 */                               buf.clear();
/* 115 */                               long sampleTimestampUs = AudioUtils.presentationTimeUsToStartTimeUs(info.presentationTimeUs, info.size, sampleRate, channelCount);
/* 118 */                               long durationNeeded = endMicros - sampleTimestampUs;
/* 120 */                               int maxSampleNeeded = (int) (durationNeeded / sampleDurationUs);
/* 122 */                               int sampleNeeded = Math.min(maxSampleNeeded, info.size);
/* 124 */                               if (sampleNeeded < info.size) {
/* 125 */                                   doStop = true;
                                        }
/* 128 */                               if (sampleNeeded > 0) {
/* 129 */                                   System.arraycopy(chunk, 0, unpreparedArray, bufferSize, sampleNeeded);
/* 130 */                                   bufferSize += sampleNeeded;
/* 131 */                                   this.mUnpreparedSamples.limit(bufferSize);
                                        }
/* 134 */                               decoder.releaseOutputBuffer(res, false);
/* 135 */                               if ((info.flags & 4) != 0) {
/* 136 */                                   doStop = true;
                                        }
                                    } else if (res == -3) {
/* 139 */                               output = decoder.getOutputBuffers();
                                    }
                                }
/* 142 */                       int rampCount = Math.min(bufferSize / 2, (int) (((110000.0d * track.decodedTimeUsToIndex(1L, 2)) / 2.0d) * 2.0d));
/* 143 */                       this.mPreparedSamples = ByteBuffer.wrap(AudioUtils.cubicCrossFade(0, rampCount, bufferSize - 1, unpreparedArray));
/* 144 */                       this.mUnpreparedSamples = null;
/* 148 */                       if (decoder != null) {
/* 150 */                           if (1 != 0) {
                                        try {
/* 151 */                                   decoder.stop();
                                        } catch (IllegalStateException e) {
/* 154 */                                   this.mError = e;
                                        }
                                    }
/* 156 */                           decoder.release();
                                }
                            } catch (IOException e2) {
/* 146 */                       this.mError = e2;
/* 148 */                       if (decoder != null) {
/* 150 */                           if (isStarted) {
                                        try {
/* 151 */                                   decoder.stop();
                                        } catch (IllegalStateException e3) {
/* 154 */                                   this.mError = e3;
                                        }
                                    }
/* 156 */                           decoder.release();
                                }
                            }
                        } catch (Throwable th) {
                            if (decoder != null) {
/* 150 */                       if (isStarted) {
                                    try {
/* 151 */                               decoder.stop();
                                    } catch (IllegalStateException e4) {
/* 154 */                               this.mError = e4;
                                    }
                                }
/* 156 */                       decoder.release();
                            }
                            throw th;
                        }
                    }
                }

/* 161 */       public void cancel() {
/* 162 */           this.mCancelled = true;
                }

/* 165 */       public ByteBuffer getPreparedSamples() {
/* 166 */           return this.mPreparedSamples;
                }

/* 169 */       public ByteBuffer getUnpreparedSamples() {
/* 170 */           return this.mUnpreparedSamples;
                }

/* 173 */       public Exception getError() {
/* 174 */           return this.mError;
                }
            }
