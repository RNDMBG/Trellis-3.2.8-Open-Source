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
            import com.google.android.exoplayer.parser.Extractor;
            import com.google.android.exoplayer.parser.mp4.FragmentedMp4Extractor;
            import com.google.android.exoplayer.parser.webm.WebmExtractor;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.util.Arrays;
            import java.util.HashMap;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class DashChunkSource implements ChunkSource {
                private final DataSource dataSource;
                private final FormatEvaluator evaluator;
                private final Format[] formats;
                private boolean lastChunkWasInitialization;
                private final int maxHeight;
                private final int maxWidth;
                private final TrackInfo trackInfo;
                private final HashMap<String, Extractor> extractors = new HashMap<>();
                private final HashMap<String, DashSegmentIndex> segmentIndexes = new HashMap<>();
                private final HashMap<String, Representation> representations = new HashMap<>();
                private final FormatEvaluator.Evaluation evaluation = new FormatEvaluator.Evaluation();

/* 51 */        static /* synthetic */ HashMap access$000(DashChunkSource x0) {
/* 52 */            return x0.segmentIndexes;
                }

/* 73 */        public DashChunkSource(DataSource dataSource, FormatEvaluator evaluator, Representation... representations) {
/* 75 */            this.dataSource = dataSource;
/* 76 */            this.evaluator = evaluator;
/* 77 */            this.formats = new Format[representations.length];
/* 81 */            this.trackInfo = new TrackInfo(representations[0].format.mimeType, representations[0].periodDurationMs * 1000);
/* 84 */            int maxWidth = 0;
/* 85 */            int maxHeight = 0;
                    for (int i = 0; i < representations.length; i++) {
/* 87 */                this.formats[i] = representations[i].format;
/* 88 */                maxWidth = Math.max(this.formats[i].width, maxWidth);
/* 89 */                maxHeight = Math.max(this.formats[i].height, maxHeight);
/* 90 */                Extractor extractor = this.formats[i].mimeType.startsWith(MimeTypes.VIDEO_WEBM) ? new WebmExtractor() : new FragmentedMp4Extractor();
/* 92 */                this.extractors.put(this.formats[i].f875id, extractor);
/* 93 */                this.representations.put(this.formats[i].f875id, representations[i]);
/* 94 */                DashSegmentIndex segmentIndex = representations[i].getIndex();
/* 95 */                if (segmentIndex != null) {
/* 96 */                    this.segmentIndexes.put(this.formats[i].f875id, segmentIndex);
                        }
                    }
/* 99 */            this.maxWidth = maxWidth;
/* 100 */           this.maxHeight = maxHeight;
/* 101 */           Arrays.sort(this.formats, new Format.DecreasingBandwidthComparator());
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 105 */       public final void getMaxVideoDimensions(MediaFormat out) {
/* 106 */           if (this.trackInfo.mimeType.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
/* 107 */               out.setMaxVideoDimensions(this.maxWidth, this.maxHeight);
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 112 */       public final TrackInfo getTrackInfo() {
/* 113 */           return this.trackInfo;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 117 */       public void enable() {
/* 118 */           this.evaluator.enable();
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 122 */       public void disable(List<? extends MediaChunk> queue) {
/* 123 */           this.evaluator.disable();
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 128 */       public void continueBuffering(long playbackPositionUs) {
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 133 */       public final void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
                    int nextSegmentNum;
/* 134 */           this.evaluation.queueSize = queue.size();
/* 135 */           if (this.evaluation.format == null || !this.lastChunkWasInitialization) {
/* 136 */               this.evaluator.evaluate(queue, playbackPositionUs, this.formats, this.evaluation);
                    }
/* 138 */           Format selectedFormat = this.evaluation.format;
/* 139 */           out.queueSize = this.evaluation.queueSize;
/* 141 */           if (selectedFormat == null) {
/* 142 */               out.chunk = null;
/* 188 */               return;
                    }
/* 144 */           if (out.queueSize != queue.size() || out.chunk == null || !out.chunk.format.f875id.equals(selectedFormat.f875id)) {
/* 151 */               Representation selectedRepresentation = this.representations.get(selectedFormat.f875id);
/* 152 */               Extractor extractor = this.extractors.get(selectedRepresentation.format.f875id);
/* 154 */               RangedUri pendingInitializationUri = null;
/* 155 */               RangedUri pendingIndexUri = null;
/* 156 */               if (extractor.getFormat() == null) {
/* 157 */                   pendingInitializationUri = selectedRepresentation.getInitializationUri();
                        }
/* 159 */               if (!this.segmentIndexes.containsKey(selectedRepresentation.format.f875id)) {
/* 160 */                   pendingIndexUri = selectedRepresentation.getIndexUri();
                        }
/* 162 */               if (pendingInitializationUri != null || pendingIndexUri != null) {
/* 164 */                   Chunk initializationChunk = newInitializationChunk(pendingInitializationUri, pendingIndexUri, selectedRepresentation, extractor, this.dataSource, this.evaluation.trigger);
/* 166 */                   this.lastChunkWasInitialization = true;
/* 167 */                   out.chunk = initializationChunk;
/* 188 */                   return;
                        }
/* 172 */               DashSegmentIndex segmentIndex = this.segmentIndexes.get(selectedRepresentation.format.f875id);
/* 173 */               if (queue.isEmpty()) {
/* 174 */                   nextSegmentNum = segmentIndex.getSegmentNum(seekPositionUs);
                        } else {
/* 176 */                   nextSegmentNum = queue.get(out.queueSize - 1).nextChunkIndex;
                        }
/* 179 */               if (nextSegmentNum == -1) {
/* 180 */                   out.chunk = null;
/* 188 */                   return;
                        }
/* 184 */               Chunk nextMediaChunk = newMediaChunk(selectedRepresentation, segmentIndex, extractor, this.dataSource, nextSegmentNum, this.evaluation.trigger);
/* 186 */               this.lastChunkWasInitialization = false;
/* 187 */               out.chunk = nextMediaChunk;
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 191 */       public IOException getError() {
/* 192 */           return null;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 197 */       public void onChunkLoadError(Chunk chunk, Exception e) {
                }

/* 202 */       private Chunk newInitializationChunk(RangedUri initializationUri, RangedUri indexUri, Representation representation, Extractor extractor, DataSource dataSource, int trigger) {
                    RangedUri requestUri;
                    int expectedExtractorResult;
/* 204 */           long indexAnchor = 0;
/* 206 */           if (initializationUri != null) {
/* 209 */               expectedExtractorResult = 2 | 8;
/* 210 */               requestUri = initializationUri.attemptMerge(indexUri);
/* 211 */               if (requestUri != null) {
/* 212 */                   expectedExtractorResult |= 16;
/* 213 */                   if (extractor.hasRelativeIndexOffsets()) {
/* 214 */                       indexAnchor = indexUri.start + indexUri.length;
                            }
                        } else {
/* 217 */                   requestUri = initializationUri;
                        }
                    } else {
/* 220 */               requestUri = indexUri;
/* 221 */               if (extractor.hasRelativeIndexOffsets()) {
/* 222 */                   indexAnchor = indexUri.start + indexUri.length;
                        }
/* 224 */               expectedExtractorResult = 2 | 16;
                    }
/* 226 */           DataSpec dataSpec = new DataSpec(requestUri.getUri(), requestUri.start, requestUri.length, representation.getCacheKey());
/* 228 */           return new InitializationLoadable(dataSource, dataSpec, trigger, representation.format, extractor, expectedExtractorResult, indexAnchor);
                }

/* 233 */       private Chunk newMediaChunk(Representation representation, DashSegmentIndex segmentIndex, Extractor extractor, DataSource dataSource, int segmentNum, int trigger) {
/* 234 */           int lastSegmentNum = segmentIndex.getLastSegmentNum();
/* 235 */           int nextSegmentNum = segmentNum == lastSegmentNum ? -1 : segmentNum + 1;
/* 236 */           long startTimeUs = segmentIndex.getTimeUs(segmentNum);
/* 237 */           long endTimeUs = segmentNum < lastSegmentNum ? segmentIndex.getTimeUs(segmentNum + 1) : startTimeUs + segmentIndex.getDurationUs(segmentNum);
/* 239 */           RangedUri segmentUri = segmentIndex.getSegmentUrl(segmentNum);
/* 240 */           DataSpec dataSpec = new DataSpec(segmentUri.getUri(), segmentUri.start, segmentUri.length, representation.getCacheKey());
/* 242 */           return new Mp4MediaChunk(dataSource, dataSpec, representation.format, trigger, startTimeUs, endTimeUs, nextSegmentNum, extractor, false, 0L);
                }

/* 253 */       private class InitializationLoadable extends Chunk {
                    private final int expectedExtractorResult;
                    private final Extractor extractor;
                    private final long indexAnchor;
                    private final Uri uri;

/* 254 */           public InitializationLoadable(DataSource dataSource, DataSpec dataSpec, int trigger, Format format, Extractor extractor, int expectedExtractorResult, long indexAnchor) {
/* 256 */               super(dataSource, dataSpec, format, trigger);
/* 257 */               this.extractor = extractor;
/* 258 */               this.expectedExtractorResult = expectedExtractorResult;
/* 259 */               this.indexAnchor = indexAnchor;
/* 260 */               this.uri = dataSpec.uri;
                    }

                    @Override // com.google.android.exoplayer.chunk.Chunk
/* 264 */           protected void consumeStream(NonBlockingInputStream stream) throws IOException {
/* 265 */               int result = this.extractor.read(stream, null);
/* 266 */               if (result != this.expectedExtractorResult) {
/* 267 */                   throw new ParserException("Invalid extractor result. Expected " + this.expectedExtractorResult + ", got " + result);
                        }
/* 270 */               if ((result & 16) != 0) {
/* 271 */                   DashChunkSource.access$000(DashChunkSource.this).put(this.format.f875id, new DashWrappingSegmentIndex(this.extractor.getIndex(), this.uri, this.indexAnchor));
                        }
                    }
                }
            }
