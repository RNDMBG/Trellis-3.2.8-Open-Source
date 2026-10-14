            package com.google.android.exoplayer.upstream;

            import android.os.Handler;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Clock;
            import com.google.android.exoplayer.util.SlidingPercentile;
            import com.google.android.exoplayer.util.SystemClock;
            
            
    public class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
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

        static  EventListener access$000(DefaultBandwidthMeter x0) {
            return x0.eventListener;
                }

        public DefaultBandwidthMeter() {
            this(null, null);
                }

        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener) {
            this(eventHandler, eventListener, new SystemClock());
                }

        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, Clock clock) {
            this(eventHandler, eventListener, clock, 2000);
                }

        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, int maxWeight) {
            this(eventHandler, eventListener, new SystemClock(), maxWeight);
                }

        public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, Clock clock, int maxWeight) {
            this.eventHandler = eventHandler;
            this.eventListener = eventListener;
            this.clock = clock;
            this.slidingPercentile = new SlidingPercentile(maxWeight);
            this.bitrateEstimate = -1L;
                }

                @Override 
        public synchronized long getBitrateEstimate() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.bitrateEstimate;
                }

                @Override 
        public synchronized void onTransferStart() {
                    try {
                        if (this.streamCount == 0) {
                    this.startTimeMs = this.clock.elapsedRealtime();
                        }
                this.streamCount++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void onBytesTransferred(int bytes) {
                    try {
                        this.bytesAccumulator += (long) bytes;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void onTransferEnd() {
                    try {
               Assertions.checkState(this.streamCount > 0);
               long nowMs = this.clock.elapsedRealtime();
               int elapsedMs = (int) (nowMs - this.startTimeMs);
               if (elapsedMs > 0) {
                   float bitsPerSecond = (this.bytesAccumulator * 8000) / ((long) elapsedMs);
                   this.slidingPercentile.addSample((int) Math.sqrt(this.bytesAccumulator), bitsPerSecond);
                   float bandwidthEstimateFloat = this.slidingPercentile.getPercentile(0.5f);
                   this.bitrateEstimate = Float.isNaN(bandwidthEstimateFloat) ? -1L : (long) bandwidthEstimateFloat;
                   notifyBandwidthSample(elapsedMs, this.bytesAccumulator, this.bitrateEstimate);
                        }
               this.streamCount--;
               if (this.streamCount > 0) {
                   this.startTimeMs = nowMs;
                        }
               this.bytesAccumulator = 0L;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class RunnableC08741 implements Runnable {
                    final  long val$bitrate;
                    final  long val$bytes;
                    final  int val$elapsedMs;

           RunnableC08741(int i, long j, long j2) {
                        this.val$elapsedMs = i;
                        this.val$bytes = j;
                        this.val$bitrate = j2;
                    }

                    @Override 
           public void run() {
               DefaultBandwidthMeter.access$000(DefaultBandwidthMeter.this).onBandwidthSample(this.val$elapsedMs, this.val$bytes, this.val$bitrate);
                    }
                }

       private void notifyBandwidthSample(int elapsedMs, long bytes, long bitrate) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08741(elapsedMs, bytes, bitrate));
                    }
                }
            }
