            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import co.vine.android.util.AudioUtils;
            import java.util.List;
            
            
    public abstract class SegmentBase {
                final RangedUri initialization;
                final long presentationTimeOffset;
                final long timescale;

        public SegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset) {
            this.initialization = initialization;
            this.timescale = timescale;
            this.presentationTimeOffset = presentationTimeOffset;
                }

        public RangedUri getInitialization(Representation representation) {
            return this.initialization;
                }

        public static class SingleSegmentBase extends SegmentBase {
                    final long indexLength;
                    final long indexStart;
                    public final Uri uri;

            public SingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, Uri uri, long indexStart, long indexLength) {
                super(initialization, timescale, presentationTimeOffset);
                this.uri = uri;
                this.indexStart = indexStart;
                this.indexLength = indexLength;
                    }

            public RangedUri getIndex() {
                return new RangedUri(this.uri, null, this.indexStart, this.indexLength);
                    }
                }

       public static abstract class MultiSegmentBase extends SegmentBase {
                    final long duration;
                    final long periodDurationMs;
                    final List<SegmentTimelineElement> segmentTimeline;
                    final int startNumber;

                    public abstract int getLastSegmentNum();

                    public abstract RangedUri getSegmentUrl(Representation representation, int i);

           public MultiSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline) {
               super(initialization, timescale, presentationTimeOffset);
               this.periodDurationMs = periodDurationMs;
               this.startNumber = startNumber;
               this.duration = duration;
               this.segmentTimeline = segmentTimeline;
                    }

           public final int getSegmentNum(long timeUs) {
               int index = this.startNumber;
               while (index + 1 <= getLastSegmentNum() && getSegmentTimeUs(index + 1) <= timeUs) {
                   index++;
                        }
               return index;
                    }

           public final long getSegmentDurationUs(int sequenceNumber) {
               if (this.segmentTimeline != null) {
                   return (this.segmentTimeline.get(sequenceNumber - this.startNumber).duration * AudioUtils.MICROS_PER_SECOND) / this.timescale;
                        }
                        return sequenceNumber == getLastSegmentNum() ? (this.periodDurationMs * 1000) - getSegmentTimeUs(sequenceNumber) : (this.duration * AudioUtils.MICROS_PER_SECOND) / this.timescale;
                    }

           public final long getSegmentTimeUs(int sequenceNumber) {
                        long unscaledSegmentTime;
               if (this.segmentTimeline != null) {
                   unscaledSegmentTime = this.segmentTimeline.get(sequenceNumber - this.startNumber).startTime - this.presentationTimeOffset;
                        } else {
                   unscaledSegmentTime = ((long) (sequenceNumber - this.startNumber)) * this.duration;
                        }
               return (AudioUtils.MICROS_PER_SECOND * unscaledSegmentTime) / this.timescale;
                    }

           public int getFirstSegmentNum() {
               return this.startNumber;
                    }
                }

       public static class SegmentList extends MultiSegmentBase {
                    final List<RangedUri> mediaSegments;

           public SegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, List<RangedUri> mediaSegments) {
               super(initialization, timescale, presentationTimeOffset, periodDurationMs, startNumber, duration, segmentTimeline);
               this.mediaSegments = mediaSegments;
                    }

                    @Override 
           public RangedUri getSegmentUrl(Representation representation, int sequenceNumber) {
               return this.mediaSegments.get(sequenceNumber - this.startNumber);
                    }

                    @Override 
           public int getLastSegmentNum() {
               return (this.startNumber + this.mediaSegments.size()) - 1;
                    }
                }

       public static class SegmentTemplate extends MultiSegmentBase {
                    private final Uri baseUrl;
                    final UrlTemplate initializationTemplate;
                    final UrlTemplate mediaTemplate;

           public SegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long periodDurationMs, int startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, UrlTemplate initializationTemplate, UrlTemplate mediaTemplate, Uri baseUrl) {
               super(initialization, timescale, presentationTimeOffset, periodDurationMs, startNumber, duration, segmentTimeline);
               this.initializationTemplate = initializationTemplate;
               this.mediaTemplate = mediaTemplate;
               this.baseUrl = baseUrl;
                    }

                    @Override 
           public RangedUri getInitialization(Representation representation) {
               if (this.initializationTemplate == null) {
                            return super.getInitialization(representation);
                        }
               String urlString = this.initializationTemplate.buildUri(representation.format.f875id, 0, representation.format.bitrate, 0L);
               return new RangedUri(this.baseUrl, urlString, 0L, -1L);
                    }

                    @Override 
           public RangedUri getSegmentUrl(Representation representation, int sequenceNumber) {
                        long time;
               if (this.segmentTimeline != null) {
                   time = this.segmentTimeline.get(sequenceNumber - this.startNumber).startTime;
                        } else {
                   time = ((long) (sequenceNumber - this.startNumber)) * this.duration;
                        }
               String uriString = this.mediaTemplate.buildUri(representation.format.f875id, sequenceNumber, representation.format.bitrate, time);
               return new RangedUri(this.baseUrl, uriString, 0L, -1L);
                    }

                    @Override 
           public int getLastSegmentNum() {
               if (this.segmentTimeline != null) {
                   return (this.segmentTimeline.size() + this.startNumber) - 1;
                        }
               long durationMs = (this.duration * 1000) / this.timescale;
               return this.startNumber + ((int) (this.periodDurationMs / durationMs));
                    }
                }

       public static class SegmentTimelineElement {
                    long duration;
                    long startTime;

           public SegmentTimelineElement(long startTime, long duration) {
               this.startTime = startTime;
               this.duration = duration;
                    }
                }
            }
