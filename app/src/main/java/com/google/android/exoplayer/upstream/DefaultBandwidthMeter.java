            package com.google.android.exoplayer.upstream;

            import android.os.Handler;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Clock;
            import com.google.android.exoplayer.util.SlidingPercentile;
            import com.google.android.exoplayer.util.SystemClock;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
                private static final int DEFAULT_MAX_WEIGHT = 2000;
                private long bitrateEstimate;
                private long bytesAccumulator;
                private final Clock clock;
                private final Handler eventHandler;
                private final EventListener eventListener;
                private final SlidingPercentile slidingPercentile;
                private long startTimeMs;
                private int streamCount;

                public interface EventListener {
                    void onBandwidthSample(int i, long j, long j2);
                }

/* 28 */        static /* synthetic */ EventListener access$000(DefaultBandwidthMeter x0) {
/* 29 */            return x0.eventListener;
                }

/* 61 */        public DefaultBandwidthMeter() {
/* 62 */            this(null, null);
                }

/* 65 */        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener) {
/* 66 */            this(eventHandler, eventListener, new SystemClock());
                }

/* 69 */        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, Clock clock) {
/* 70 */            this(eventHandler, eventListener, clock, 2000);
                }

/* 73 */        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, int maxWeight) {
/* 74 */            this(eventHandler, eventListener, new SystemClock(), maxWeight);
                }

/* 77 */        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, Clock clock, int maxWeight) {
/* 79 */            this.eventHandler = eventHandler;
/* 80 */            this.eventListener = eventListener;
/* 81 */            this.clock = clock;
/* 82 */            this.slidingPercentile = new SlidingPercentile(maxWeight);
/* 83 */            this.bitrateEstimate = -1L;
                }

                @Override // com.google.android.exoplayer.upstream.BandwidthMeter
/* 87 */        public synchronized long getBitrateEstimate() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.bitrateEstimate;
                }

                @Override // com.google.android.exoplayer.upstream.TransferListener
/* 92 */        public synchronized void onTransferStart() {
                    try {
                        if (this.streamCount == 0) {
/* 94 */                    this.startTimeMs = this.clock.elapsedRealtime();
                        }
/* 96 */                this.streamCount++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.TransferListener
/* 100 */       public synchronized void onBytesTransferred(int bytes) {
                    try {
                        this.bytesAccumulator += (long) bytes;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.TransferListener
/* 105 */       public synchronized void onTransferEnd() {
                    try {
/* 106 */               Assertions.checkState(this.streamCount > 0);
/* 107 */               long nowMs = this.clock.elapsedRealtime();
/* 108 */               int elapsedMs = (int) (nowMs - this.startTimeMs);
/* 109 */               if (elapsedMs > 0) {
/* 110 */                   float bitsPerSecond = (this.bytesAccumulator * 8000) / ((long) elapsedMs);
/* 111 */                   this.slidingPercentile.addSample((int) Math.sqrt(this.bytesAccumulator), bitsPerSecond);
/* 112 */                   float bandwidthEstimateFloat = this.slidingPercentile.getPercentile(0.5f);
/* 113 */                   this.bitrateEstimate = Float.isNaN(bandwidthEstimateFloat) ? -1L : (long) bandwidthEstimateFloat;
/* 115 */                   notifyBandwidthSample(elapsedMs, this.bytesAccumulator, this.bitrateEstimate);
                        }
/* 117 */               this.streamCount--;
/* 118 */               if (this.streamCount > 0) {
/* 119 */                   this.startTimeMs = nowMs;
                        }
/* 121 */               this.bytesAccumulator = 0L;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.upstream.DefaultBandwidthMeter$1 */
/* 124 */       class RunnableC08741 implements Runnable {
                    final /* synthetic */ long val$bitrate;
                    final /* synthetic */ long val$bytes;
                    final /* synthetic */ int val$elapsedMs;

/* 125 */           RunnableC08741(int i, long j, long j2) {
                        this.val$elapsedMs = i;
                        this.val$bytes = j;
                        this.val$bitrate = j2;
                    }

                    @Override // java.lang.Runnable
/* 128 */           public void run() {
/* 129 */               DefaultBandwidthMeter.access$000(DefaultBandwidthMeter.this).onBandwidthSample(this.val$elapsedMs, this.val$bytes, this.val$bitrate);
                    }
                }

/* 124 */       private void notifyBandwidthSample(int elapsedMs, long bytes, long bitrate) {
/* 125 */           if (this.eventHandler != null && this.eventListener != null) {
/* 126 */               this.eventHandler.post(new RunnableC08741(elapsedMs, bytes, bitrate));
                    }
                }
            }
