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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class DashMp4ChunkSource implements ChunkSource {
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

/* 46 */        static /* synthetic */ HashMap access$000(DashMp4ChunkSource x0) {
/* 47 */            return x0.segmentIndexes;
                }

/* 68 */        public DashMp4ChunkSource(DataSource dataSource, FormatEvaluator evaluator, Representation... representations) {
/* 70 */            this.dataSource = dataSource;
/* 71 */            this.evaluator = evaluator;
/* 72 */            this.formats = new Format[representations.length];
/* 76 */            this.trackInfo = new TrackInfo(representations[0].format.mimeType, representations[0].periodDurationMs * 1000);
/* 79 */            int maxWidth = 0;
/* 80 */            int maxHeight = 0;
                    for (int i = 0; i < representations.length; i++) {
/* 82 */                this.formats[i] = representations[i].format;
/* 83 */                maxWidth = Math.max(this.formats[i].width, maxWidth);
/* 84 */                maxHeight = Math.max(this.formats[i].height, maxHeight);
/* 85 */                this.extractors.put(this.formats[i].f875id, new FragmentedMp4Extractor());
/* 86 */                this.representations.put(this.formats[i].f875id, representations[i]);
/* 87 */                DashSegmentIndex segmentIndex = representations[i].getIndex();
/* 88 */                if (segmentIndex != null) {
/* 89 */                    this.segmentIndexes.put(this.formats[i].f875id, segmentIndex);
                        }
                    }
/* 92 */            this.maxWidth = maxWidth;
/* 93 */            this.maxHeight = maxHeight;
/* 94 */            Arrays.sort(this.formats, new Format.DecreasingBandwidthComparator());
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 98 */        public final void getMaxVideoDimensions(MediaFormat out) {
/* 99 */            if (this.trackInfo.mimeType.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
/* 100 */               out.setMaxVideoDimensions(this.maxWidth, this.maxHeight);
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 105 */       public final TrackInfo getTrackInfo() {
/* 106 */           return this.trackInfo;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 110 */       public void enable() {
/* 111 */           this.evaluator.enable();
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 115 */       public void disable(List<? extends MediaChunk> queue) {
/* 116 */           this.evaluator.disable();
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 121 */       public void continueBuffering(long playbackPositionUs) {
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 126 */       public final void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
                    int nextSegmentNum;
/* 127 */           this.evaluation.queueSize = queue.size();
/* 128 */           if (this.evaluation.format == null || !this.lastChunkWasInitialization) {
/* 129 */               this.evaluator.evaluate(queue, playbackPositionUs, this.formats, this.evaluation);
                    }
/* 131 */           Format selectedFormat = this.evaluation.format;
/* 132 */           out.queueSize = this.evaluation.queueSize;
/* 134 */           if (selectedFormat == null) {
/* 135 */               out.chunk = null;
/* 181 */               return;
                    }
/* 137 */           if (out.queueSize != queue.size() || out.chunk == null || !out.chunk.format.f875id.equals(selectedFormat.f875id)) {
/* 144 */               Representation selectedRepresentation = this.representations.get(selectedFormat.f875id);
/* 145 */               FragmentedMp4Extractor extractor = this.extractors.get(selectedRepresentation.format.f875id);
/* 147 */               RangedUri pendingInitializationUri = null;
/* 148 */               RangedUri pendingIndexUri = null;
/* 149 */               if (extractor.getFormat() == null) {
/* 150 */                   pendingInitializationUri = selectedRepresentation.getInitializationUri();
                        }
/* 152 */               if (!this.segmentIndexes.containsKey(selectedRepresentation.format.f875id)) {
/* 153 */                   pendingIndexUri = selectedRepresentation.getIndexUri();
                        }
/* 155 */               if (pendingInitializationUri != null || pendingIndexUri != null) {
/* 157 */                   Chunk initializationChunk = newInitializationChunk(pendingInitializationUri, pendingIndexUri, selectedRepresentation, extractor, this.dataSource, this.evaluation.trigger);
/* 159 */                   this.lastChunkWasInitialization = true;
/* 160 */                   out.chunk = initializationChunk;
/* 181 */                   return;
                        }
/* 165 */               DashSegmentIndex segmentIndex = this.segmentIndexes.get(selectedRepresentation.format.f875id);
/* 166 */               if (queue.isEmpty()) {
/* 167 */                   nextSegmentNum = segmentIndex.getSegmentNum(seekPositionUs);
                        } else {
/* 169 */                   nextSegmentNum = queue.get(out.queueSize - 1).nextChunkIndex;
                        }
/* 172 */               if (nextSegmentNum == -1) {
/* 173 */                   out.chunk = null;
/* 181 */                   return;
                        }
/* 177 */               Chunk nextMediaChunk = newMediaChunk(selectedRepresentation, segmentIndex, extractor, this.dataSource, nextSegmentNum, this.evaluation.trigger);
/* 179 */               this.lastChunkWasInitialization = false;
/* 180 */               out.chunk = nextMediaChunk;
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 184 */       public IOException getError() {
/* 185 */           return null;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 190 */       public void onChunkLoadError(Chunk chunk, Exception e) {
                }

/* 195 */       private Chunk newInitializationChunk(RangedUri initializationUri, RangedUri indexUri, Representation representation, FragmentedMp4Extractor extractor, DataSource dataSource, int trigger) {
                    RangedUri requestUri;
                    int expectedExtractorResult;
/* 197 */           long indexAnchor = 0;
/* 199 */           if (initializationUri != null) {
/* 202 */               expectedExtractorResult = 2 | 8;
/* 203 */               requestUri = initializationUri.attemptMerge(indexUri);
/* 204 */               if (requestUri != null) {
/* 205 */                   expectedExtractorResult |= 16;
/* 206 */                   indexAnchor = indexUri.start + indexUri.length;
                        } else {
/* 208 */                   requestUri = initializationUri;
                        }
                    } else {
/* 211 */               requestUri = indexUri;
/* 212 */               indexAnchor = indexUri.start + indexUri.length;
/* 213 */               expectedExtractorResult = 2 | 16;
                    }
/* 215 */           DataSpec dataSpec = new DataSpec(requestUri.getUri(), requestUri.start, requestUri.length, representation.getCacheKey());
/* 217 */           return new InitializationMp4Loadable(dataSource, dataSpec, trigger, representation.format, extractor, expectedExtractorResult, indexAnchor);
                }

/* 222 */       private Chunk newMediaChunk(Representation representation, DashSegmentIndex segmentIndex, FragmentedMp4Extractor extractor, DataSource dataSource, int segmentNum, int trigger) {
/* 223 */           int lastSegmentNum = segmentIndex.getLastSegmentNum();
/* 224 */           int nextSegmentNum = segmentNum == lastSegmentNum ? -1 : segmentNum + 1;
/* 225 */           long startTimeUs = segmentIndex.getTimeUs(segmentNum);
/* 226 */           long endTimeUs = segmentNum < lastSegmentNum ? segmentIndex.getTimeUs(segmentNum + 1) : startTimeUs + segmentIndex.getDurationUs(segmentNum);
/* 228 */           RangedUri segmentUri = segmentIndex.getSegmentUrl(segmentNum);
/* 229 */           DataSpec dataSpec = new DataSpec(segmentUri.getUri(), segmentUri.start, segmentUri.length, representation.getCacheKey());
/* 231 */           return new Mp4MediaChunk(dataSource, dataSpec, representation.format, trigger, startTimeUs, endTimeUs, nextSegmentNum, extractor, false, 0L);
                }

/* 242 */       private class InitializationMp4Loadable extends Chunk {
                    private final int expectedExtractorResult;
                    private final FragmentedMp4Extractor extractor;
                    private final long indexAnchor;
                    private final Uri uri;

/* 243 */           public InitializationMp4Loadable(DataSource dataSource, DataSpec dataSpec, int trigger, Format format, FragmentedMp4Extractor extractor, int expectedExtractorResult, long indexAnchor) {
/* 245 */               super(dataSource, dataSpec, format, trigger);
/* 246 */               this.extractor = extractor;
/* 247 */               this.expectedExtractorResult = expectedExtractorResult;
/* 248 */               this.indexAnchor = indexAnchor;
/* 249 */               this.uri = dataSpec.uri;
                    }

                    @Override // com.google.android.exoplayer.chunk.Chunk
/* 253 */           protected void consumeStream(NonBlockingInputStream stream) throws IOException {
/* 254 */               int result = this.extractor.read(stream, null);
/* 255 */               if (result != this.expectedExtractorResult) {
/* 256 */                   throw new ParserException("Invalid extractor result. Expected " + this.expectedExtractorResult + ", got " + result);
                        }
/* 259 */               if ((result & 16) != 0) {
/* 260 */                   DashMp4ChunkSource.access$000(DashMp4ChunkSource.this).put(this.format.f875id, new DashWrappingSegmentIndex(this.extractor.getIndex(), this.uri, this.indexAnchor));
                        }
                    }
                }
            }
