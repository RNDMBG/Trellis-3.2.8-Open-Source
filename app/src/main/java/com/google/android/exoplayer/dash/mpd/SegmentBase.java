            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import co.vine.android.util.AudioUtils;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public abstract class SegmentBase {
                final RangedUri initialization;
                final long presentationTimeOffset;
                final long timescale;

/* 37 */        public SegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset) {
/* 39 */            this.initialization = initialization;
/* 40 */            this.timescale = timescale;
/* 41 */            this.presentationTimeOffset = presentationTimeOffset;
                }

/* 51 */        public RangedUri getInitialization(Representation representation) {
/* 52 */            return this.initialization;
                }

/* 78 */        public static class SingleSegmentBase extends SegmentBase {
                    final long indexLength;
                    final long indexStart;
                    public final Uri uri;

/* 79 */            public SingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, Uri uri, long indexStart, long indexLength) {
/* 80 */                super(initialization, timescale, presentationTimeOffset);
/* 81 */                this.uri = uri;
/* 82 */                this.indexStart = indexStart;
/* 83 */                this.indexLength = indexLength;
                    }

/* 86 */            public RangedUri getIndex() {
/* 87 */                return new RangedUri(this.uri, null, this.indexStart, this.indexLength);
                    }
                }

/* 118 */       public static abstract class MultiSegmentBase extends SegmentBase {
                    final long duration;
                    final long periodDurationMs;
                    final List<SegmentTimelineElement> segmentTimeline;
                    final int startNumber;

                    public abstract int getLastSegmentNum();

                    public abstract RangedUri getSegmentUrl(Representation representation, int i);

/* 119 */           public MultiSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline) {
/* 120 */               super(initialization, timescale, presentationTimeOffset);
/* 121 */               this.periodDurationMs = periodDurationMs;
/* 122 */               this.startNumber = startNumber;
/* 123 */               this.duration = duration;
/* 124 */               this.segmentTimeline = segmentTimeline;
                    }

/* 128 */           public final int getSegmentNum(long timeUs) {
/* 129 */               int index = this.startNumber;
/* 130 */               while (index + 1 <= getLastSegmentNum() && getSegmentTimeUs(index + 1) <= timeUs) {
/* 132 */                   index++;
                        }
/* 137 */               return index;
                    }

/* 140 */           public final long getSegmentDurationUs(int sequenceNumber) {
/* 141 */               if (this.segmentTimeline != null) {
/* 142 */                   return (this.segmentTimeline.get(sequenceNumber - this.startNumber).duration * AudioUtils.MICROS_PER_SECOND) / this.timescale;
                        }
                        return sequenceNumber == getLastSegmentNum() ? (this.periodDurationMs * 1000) - getSegmentTimeUs(sequenceNumber) : (this.duration * AudioUtils.MICROS_PER_SECOND) / this.timescale;
                    }

/* 151 */           public final long getSegmentTimeUs(int sequenceNumber) {
                        long unscaledSegmentTime;
/* 152 */               if (this.segmentTimeline != null) {
/* 153 */                   unscaledSegmentTime = this.segmentTimeline.get(sequenceNumber - this.startNumber).startTime - this.presentationTimeOffset;
                        } else {
/* 156 */                   unscaledSegmentTime = ((long) (sequenceNumber - this.startNumber)) * this.duration;
                        }
/* 158 */               return (AudioUtils.MICROS_PER_SECOND * unscaledSegmentTime) / this.timescale;
                    }

/* 163 */           public int getFirstSegmentNum() {
/* 164 */               return this.startNumber;
                    }
                }

/* 195 */       public static class SegmentList extends MultiSegmentBase {
                    final List<RangedUri> mediaSegments;

/* 196 */           public SegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, List<RangedUri> mediaSegments) {
/* 197 */               super(initialization, timescale, presentationTimeOffset, periodDurationMs, startNumber, duration, segmentTimeline);
/* 199 */               this.mediaSegments = mediaSegments;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.SegmentBase.MultiSegmentBase
/* 203 */           public RangedUri getSegmentUrl(Representation representation, int sequenceNumber) {
/* 204 */               return this.mediaSegments.get(sequenceNumber - this.startNumber);
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.SegmentBase.MultiSegmentBase
/* 208 */           public int getLastSegmentNum() {
/* 209 */               return (this.startNumber + this.mediaSegments.size()) - 1;
                    }
                }

/* 247 */       public static class SegmentTemplate extends MultiSegmentBase {
                    private final Uri baseUrl;
                    final UrlTemplate initializationTemplate;
                    final UrlTemplate mediaTemplate;

/* 248 */           public SegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, UrlTemplate initializationTemplate, UrlTemplate mediaTemplate, Uri baseUrl) {
/* 249 */               super(initialization, timescale, presentationTimeOffset, periodDurationMs, startNumber, duration, segmentTimeline);
/* 251 */               this.initializationTemplate = initializationTemplate;
/* 252 */               this.mediaTemplate = mediaTemplate;
/* 253 */               this.baseUrl = baseUrl;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.SegmentBase
/* 257 */           public RangedUri getInitialization(Representation representation) {
/* 258 */               if (this.initializationTemplate == null) {
                            return super.getInitialization(representation);
                        }
/* 259 */               String urlString = this.initializationTemplate.buildUri(representation.format.f875id, 0, representation.format.bitrate, 0L);
/* 261 */               return new RangedUri(this.baseUrl, urlString, 0L, -1L);
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.SegmentBase.MultiSegmentBase
/* 268 */           public RangedUri getSegmentUrl(Representation representation, int sequenceNumber) {
                        long time;
/* 270 */               if (this.segmentTimeline != null) {
/* 271 */                   time = this.segmentTimeline.get(sequenceNumber - this.startNumber).startTime;
                        } else {
/* 273 */                   time = ((long) (sequenceNumber - this.startNumber)) * this.duration;
                        }
/* 275 */               String uriString = this.mediaTemplate.buildUri(representation.format.f875id, sequenceNumber, representation.format.bitrate, time);
/* 277 */               return new RangedUri(this.baseUrl, uriString, 0L, -1L);
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.SegmentBase.MultiSegmentBase
/* 281 */           public int getLastSegmentNum() {
/* 282 */               if (this.segmentTimeline != null) {
/* 283 */                   return (this.segmentTimeline.size() + this.startNumber) - 1;
                        }
/* 285 */               long durationMs = (this.duration * 1000) / this.timescale;
/* 286 */               return this.startNumber + ((int) (this.periodDurationMs / durationMs));
                    }
                }

/* 304 */       public static class SegmentTimelineElement {
                    long duration;
                    long startTime;

/* 305 */           public SegmentTimelineElement(long startTime, long duration) {
/* 307 */               this.startTime = startTime;
/* 308 */               this.duration = duration;
                    }
                }
            }
