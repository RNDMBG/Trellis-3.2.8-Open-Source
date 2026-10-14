            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.upstream.BandwidthMeter;
            import java.util.List;
            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 106 */   public interface FormatEvaluator {
                public static final int TRIGGER_ADAPTIVE = 2;
                public static final int TRIGGER_CUSTOM_BASE = 10000;
                public static final int TRIGGER_INITIAL = 0;
                public static final int TRIGGER_MANUAL = 1;

                public static final class Evaluation {
                    public Format format;
                    public int queueSize;
                    public int trigger = 0;
                }

                void disable();

                void enable();

                void evaluate(List<? extends MediaChunk> list, long j, Format[] formatArr, Evaluation evaluation);

/* 107 */       public static class FixedEvaluator implements FormatEvaluator {
                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 108 */           public void enable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 113 */           public void disable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 118 */           public void evaluate(List<? extends MediaChunk> queue, long playbackPositionUs, Format[] formats, Evaluation evaluation) {
/* 119 */               evaluation.format = formats[0];
                    }
                }

/* 136 */       public static class RandomEvaluator implements FormatEvaluator {
                    private final Random random = new Random();

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 137 */           public void enable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 142 */           public void disable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 147 */           public void evaluate(List<? extends MediaChunk> queue, long playbackPositionUs, Format[] formats, Evaluation evaluation) {
/* 148 */               Format newFormat = formats[this.random.nextInt(formats.length)];
/* 149 */               if (evaluation.format != null && !evaluation.format.f875id.equals(newFormat.f875id)) {
/* 150 */                   evaluation.trigger = 2;
                        }
/* 152 */               evaluation.format = newFormat;
                    }
                }

/* 184 */       public static class AdaptiveEvaluator implements FormatEvaluator {
                    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.75f;
                    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
                    public static final int DEFAULT_MAX_INITIAL_BITRATE = 800000;
                    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
                    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
                    private final float bandwidthFraction;
                    private final BandwidthMeter bandwidthMeter;
                    private final long maxDurationForQualityDecreaseUs;
                    private final int maxInitialBitrate;
                    private final long minDurationForQualityIncreaseUs;
                    private final long minDurationToRetainAfterDiscardUs;

/* 185 */           public AdaptiveEvaluator(BandwidthMeter bandwidthMeter) {
/* 186 */               this(bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE, 10000, 25000, 25000, 0.75f);
                    }

/* 212 */           public AdaptiveEvaluator(BandwidthMeter bandwidthMeter, int maxInitialBitrate, int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction) {
/* 214 */               this.bandwidthMeter = bandwidthMeter;
/* 215 */               this.maxInitialBitrate = maxInitialBitrate;
/* 216 */               this.minDurationForQualityIncreaseUs = ((long) minDurationForQualityIncreaseMs) * 1000;
/* 217 */               this.maxDurationForQualityDecreaseUs = ((long) maxDurationForQualityDecreaseMs) * 1000;
/* 218 */               this.minDurationToRetainAfterDiscardUs = ((long) minDurationToRetainAfterDiscardMs) * 1000;
/* 219 */               this.bandwidthFraction = bandwidthFraction;
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 224 */           public void enable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 229 */           public void disable() {
                    }

                    @Override // com.google.android.exoplayer.chunk.FormatEvaluator
/* 234 */           public void evaluate(List<? extends MediaChunk> queue, long playbackPositionUs, Format[] formats, Evaluation evaluation) {
/* 235 */               long bufferedDurationUs = queue.isEmpty() ? 0L : queue.get(queue.size() - 1).endTimeUs - playbackPositionUs;
/* 237 */               Format current = evaluation.format;
/* 238 */               Format ideal = determineIdealFormat(formats, this.bandwidthMeter.getBitrateEstimate());
/* 239 */               boolean isHigher = (ideal == null || current == null || ideal.bitrate <= current.bitrate) ? false : true;
/* 240 */               boolean isLower = (ideal == null || current == null || ideal.bitrate >= current.bitrate) ? false : true;
/* 241 */               if (isHigher) {
                            if (bufferedDurationUs < this.minDurationForQualityIncreaseUs) {
/* 245 */                       ideal = current;
                            } else if (bufferedDurationUs >= this.minDurationToRetainAfterDiscardUs) {
                                for (int i = 1; i < queue.size(); i++) {
/* 251 */                           MediaChunk thisChunk = queue.get(i);
/* 252 */                           long durationBeforeThisSegmentUs = thisChunk.startTimeUs - playbackPositionUs;
                                    if (durationBeforeThisSegmentUs >= this.minDurationToRetainAfterDiscardUs && thisChunk.format.bitrate < ideal.bitrate && thisChunk.format.height < ideal.height && thisChunk.format.height < 720 && thisChunk.format.width < 1280) {
/* 259 */                               evaluation.queueSize = i;
                                        break;
                                    }
                                }
                            }
                        } else if (isLower && current != null && bufferedDurationUs >= this.maxDurationForQualityDecreaseUs) {
/* 268 */                   ideal = current;
                        }
/* 270 */               if (current != null && ideal != current) {
/* 271 */                   evaluation.trigger = 2;
                        }
/* 273 */               evaluation.format = ideal;
                    }

/* 279 */           protected Format determineIdealFormat(Format[] formats, long bitrateEstimate) {
/* 280 */               long effectiveBitrate = computeEffectiveBitrateEstimate(bitrateEstimate);
                        for (Format format : formats) {
                            if (format.bitrate <= effectiveBitrate) {
/* 288 */                       return format;
                            }
                        }
/* 288 */               return formats[formats.length - 1];
                    }

/* 294 */           protected long computeEffectiveBitrateEstimate(long bitrateEstimate) {
                        return bitrateEstimate == -1 ? this.maxInitialBitrate : (long) (bitrateEstimate * this.bandwidthFraction);
                    }
                }
            }
