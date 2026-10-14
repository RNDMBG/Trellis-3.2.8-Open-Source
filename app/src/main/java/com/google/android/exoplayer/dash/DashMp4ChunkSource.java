            package com.google.android.exoplayer.dash;

            import android.net.Uri;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.chunk.Chunk;
            import com.google.android.exoplayer.chunk.ChunkOperationHolder;
            import com.google.android.exoplayer.chunk.ChunkSource;
            import com.google.android.exoplayer.chunk.Format;
            import com.google.android.exoplayer.chunk.FormatEvaluator;
            import com.google.android.exoplayer.chunk.MediaChunk;
            import com.google.android.exoplayer.chunk.Mp4MediaChunk;
            import com.google.android.exoplayer.dash.mpd.RangedUri;
            import com.google.android.exoplayer.dash.mpd.Representation;
            import com.google.android.exoplayer.parser.mp4.FragmentedMp4Extractor;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.util.Arrays;
            import java.util.HashMap;
            import java.util.List;
            
            
    public class DashMp4ChunkSource implements ChunkSource {
                private final DataSource dataSource;
                private final FormatEvaluator evaluator;
                private final Format[] formats;
                private boolean lastChunkWasInitialization;
                private final int maxHeight;
                private final int maxWidth;
                private final TrackInfo trackInfo;
                private final HashMap<String, FragmentedMp4Extractor> extractors = new HashMap<>();
                private final HashMap<String, DashSegmentIndex> segmentIndexes = new HashMap<>();
                private final HashMap<String, Representation> representations = new HashMap<>();
                private final FormatEvaluator.Evaluation evaluation = new FormatEvaluator.Evaluation();

        static  HashMap access$000(DashMp4ChunkSource x0) {
            return x0.segmentIndexes;
                }

        public DashMp4ChunkSource(DataSource dataSource, FormatEvaluator evaluator, Representation... representations) {
            this.dataSource = dataSource;
            this.evaluator = evaluator;
            this.formats = new Format[representations.length];
            this.trackInfo = new TrackInfo(representations[0].format.mimeType, representations[0].periodDurationMs * 1000);
            int maxWidth = 0;
            int maxHeight = 0;
                    for (int i = 0; i < representations.length; i++) {
                this.formats[i] = representations[i].format;
                maxWidth = Math.max(this.formats[i].width, maxWidth);
                maxHeight = Math.max(this.formats[i].height, maxHeight);
                this.extractors.put(this.formats[i].f875id, new FragmentedMp4Extractor());
                this.representations.put(this.formats[i].f875id, representations[i]);
                DashSegmentIndex segmentIndex = representations[i].getIndex();
                if (segmentIndex != null) {
                    this.segmentIndexes.put(this.formats[i].f875id, segmentIndex);
                        }
                    }
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            Arrays.sort(this.formats, new Format.DecreasingBandwidthComparator());
                }

                @Override 
        public final void getMaxVideoDimensions(MediaFormat out) {
            if (this.trackInfo.mimeType.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
               out.setMaxVideoDimensions(this.maxWidth, this.maxHeight);
                    }
                }

                @Override 
       public final TrackInfo getTrackInfo() {
           return this.trackInfo;
                }

                @Override 
       public void enable() {
           this.evaluator.enable();
                }

                @Override 
       public void disable(List<? extends MediaChunk> queue) {
           this.evaluator.disable();
                }

                @Override 
       public void continueBuffering(long playbackPositionUs) {
                }

                @Override 
       public final void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
                    int nextSegmentNum;
           this.evaluation.queueSize = queue.size();
           if (this.evaluation.format == null || !this.lastChunkWasInitialization) {
               this.evaluator.evaluate(queue, playbackPositionUs, this.formats, this.evaluation);
                    }
           Format selectedFormat = this.evaluation.format;
           out.queueSize = this.evaluation.queueSize;
           if (selectedFormat == null) {
               out.chunk = null;
               return;
                    }
           if (out.queueSize != queue.size() || out.chunk == null || !out.chunk.format.f875id.equals(selectedFormat.f875id)) {
               Representation selectedRepresentation = this.representations.get(selectedFormat.f875id);
               FragmentedMp4Extractor extractor = this.extractors.get(selectedRepresentation.format.f875id);
               RangedUri pendingInitializationUri = null;
               RangedUri pendingIndexUri = null;
               if (extractor.getFormat() == null) {
                   pendingInitializationUri = selectedRepresentation.getInitializationUri();
                        }
               if (!this.segmentIndexes.containsKey(selectedRepresentation.format.f875id)) {
                   pendingIndexUri = selectedRepresentation.getIndexUri();
                        }
               if (pendingInitializationUri != null || pendingIndexUri != null) {
                   Chunk initializationChunk = newInitializationChunk(pendingInitializationUri, pendingIndexUri, selectedRepresentation, extractor, this.dataSource, this.evaluation.trigger);
                   this.lastChunkWasInitialization = true;
                   out.chunk = initializationChunk;
                   return;
                        }
               DashSegmentIndex segmentIndex = this.segmentIndexes.get(selectedRepresentation.format.f875id);
               if (queue.isEmpty()) {
                   nextSegmentNum = segmentIndex.getSegmentNum(seekPositionUs);
                        } else {
                   nextSegmentNum = queue.get(out.queueSize - 1).nextChunkIndex;
                        }
               if (nextSegmentNum == -1) {
                   out.chunk = null;
                   return;
                        }
               Chunk nextMediaChunk = newMediaChunk(selectedRepresentation, segmentIndex, extractor, this.dataSource, nextSegmentNum, this.evaluation.trigger);
               this.lastChunkWasInitialization = false;
               out.chunk = nextMediaChunk;
                    }
                }

                @Override 
       public IOException getError() {
           return null;
                }

                @Override 
       public void onChunkLoadError(Chunk chunk, Exception e) {
                }

       private Chunk newInitializationChunk(RangedUri initializationUri, RangedUri indexUri, Representation representation, FragmentedMp4Extractor extractor, DataSource dataSource, int trigger) {
                    RangedUri requestUri;
                    int expectedExtractorResult;
           long indexAnchor = 0;
           if (initializationUri != null) {
               expectedExtractorResult = 2 | 8;
               requestUri = initializationUri.attemptMerge(indexUri);
               if (requestUri != null) {
                   expectedExtractorResult |= 16;
                   indexAnchor = indexUri.start + indexUri.length;
                        } else {
                   requestUri = initializationUri;
                        }
                    } else {
               requestUri = indexUri;
               indexAnchor = indexUri.start + indexUri.length;
               expectedExtractorResult = 2 | 16;
                    }
           DataSpec dataSpec = new DataSpec(requestUri.getUri(), requestUri.start, requestUri.length, representation.getCacheKey());
           return new InitializationMp4Loadable(dataSource, dataSpec, trigger, representation.format, extractor, expectedExtractorResult, indexAnchor);
                }

       private Chunk newMediaChunk(Representation representation, DashSegmentIndex segmentIndex, FragmentedMp4Extractor extractor, DataSource dataSource, int segmentNum, int trigger) {
           int lastSegmentNum = segmentIndex.getLastSegmentNum();
           int nextSegmentNum = segmentNum == lastSegmentNum ? -1 : segmentNum + 1;
           long startTimeUs = segmentIndex.getTimeUs(segmentNum);
           long endTimeUs = segmentNum < lastSegmentNum ? segmentIndex.getTimeUs(segmentNum + 1) : startTimeUs + segmentIndex.getDurationUs(segmentNum);
           RangedUri segmentUri = segmentIndex.getSegmentUrl(segmentNum);
           DataSpec dataSpec = new DataSpec(segmentUri.getUri(), segmentUri.start, segmentUri.length, representation.getCacheKey());
           return new Mp4MediaChunk(dataSource, dataSpec, representation.format, trigger, startTimeUs, endTimeUs, nextSegmentNum, extractor, false, 0L);
                }

       private class InitializationMp4Loadable extends Chunk {
                    private final int expectedExtractorResult;
                    private final FragmentedMp4Extractor extractor;
                    private final long indexAnchor;
                    private final Uri uri;

           public InitializationMp4Loadable(DataSource dataSource, DataSpec dataSpec, int trigger, Format format, FragmentedMp4Extractor extractor, int expectedExtractorResult, long indexAnchor) {
               super(dataSource, dataSpec, format, trigger);
               this.extractor = extractor;
               this.expectedExtractorResult = expectedExtractorResult;
               this.indexAnchor = indexAnchor;
               this.uri = dataSpec.uri;
                    }

                    @Override 
           protected void consumeStream(NonBlockingInputStream stream) throws IOException {
               int result = this.extractor.read(stream, null);
               if (result != this.expectedExtractorResult) {
                   throw new ParserException("Invalid extractor result. Expected " + this.expectedExtractorResult + ", got " + result);
                        }
               if ((result & 16) != 0) {
                   DashMp4ChunkSource.access$000(DashMp4ChunkSource.this).put(this.format.f875id, new DashWrappingSegmentIndex(this.extractor.getIndex(), this.uri, this.indexAnchor));
                        }
                    }
                }
            }
