            package com.google.android.exoplayer.text;

            import android.annotation.TargetApi;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.util.Log;
            import com.google.android.exoplayer.ExoPlaybackException;
            import com.google.android.exoplayer.MediaFormatHolder;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.SampleSource;
            import com.google.android.exoplayer.TrackRenderer;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.VerboseLogUtil;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.ByteArrayInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 88 */    public class TextTrackRenderer extends TrackRenderer implements Handler.Callback {
                private static final int MSG_UPDATE_OVERLAY = 0;
                private static final String TAG = "TextTrackRenderer";
                private long currentPositionUs;
                private final MediaFormatHolder formatHolder;
                private boolean inputStreamEnded;
                private int nextSubtitleEventIndex;
                private final SampleHolder sampleHolder;
                private final SampleSource source;
                private Subtitle subtitle;
                private final SubtitleParser subtitleParser;
                private final TextRenderer textRenderer;
                private final Handler textRendererHandler;
                private boolean textRendererNeedsUpdate;
                private int trackIndex;

                public interface TextRenderer {
                    void onText(String str);
                }

/* 89 */        public TextTrackRenderer(SampleSource source, SubtitleParser subtitleParser, TextRenderer textRenderer, Looper textRendererLooper) {
/* 91 */            this.source = (SampleSource) Assertions.checkNotNull(source);
/* 92 */            this.subtitleParser = (SubtitleParser) Assertions.checkNotNull(subtitleParser);
/* 93 */            this.textRenderer = (TextRenderer) Assertions.checkNotNull(textRenderer);
/* 94 */            this.textRendererHandler = textRendererLooper == null ? null : new Handler(textRendererLooper, this);
/* 96 */            this.formatHolder = new MediaFormatHolder();
/* 97 */            this.sampleHolder = new SampleHolder(true);
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 102 */       protected int doPrepare() throws ExoPlaybackException {
                    try {
/* 103 */               boolean sourcePrepared = this.source.prepare();
/* 104 */               if (!sourcePrepared) {
/* 105 */                   return 0;
                        }
                        for (int i = 0; i < this.source.getTrackCount(); i++) {
/* 111 */                   if (this.subtitleParser.canParse(this.source.getTrackInfo(i).mimeType)) {
/* 112 */                       this.trackIndex = i;
/* 113 */                       return 1;
                            }
                        }
/* 116 */               return -1;
                    } catch (IOException e) {
/* 108 */               throw new ExoPlaybackException(e);
                    }
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 120 */       protected void onEnabled(long timeUs, boolean joining) {
/* 121 */           this.source.enable(this.trackIndex, timeUs);
/* 122 */           seekToInternal(timeUs);
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 126 */       protected void seekTo(long timeUs) {
/* 127 */           this.source.seekToUs(timeUs);
/* 128 */           seekToInternal(timeUs);
                }

/* 131 */       private void seekToInternal(long timeUs) {
/* 132 */           this.inputStreamEnded = false;
/* 133 */           this.currentPositionUs = timeUs;
/* 134 */           this.source.seekToUs(timeUs);
/* 135 */           if (this.subtitle != null && (timeUs < this.subtitle.getStartTime() || this.subtitle.getLastEventTime() <= timeUs)) {
/* 137 */               this.subtitle = null;
                    }
/* 139 */           resetSampleData();
/* 140 */           clearTextRenderer();
/* 141 */           syncNextEventIndex(timeUs);
/* 142 */           this.textRendererNeedsUpdate = this.subtitle != null;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 147 */       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
                    try {
/* 148 */               this.source.continueBuffering(timeUs);
/* 153 */               this.currentPositionUs = timeUs;
/* 157 */               if (this.subtitle != null) {
/* 158 */                   long nextEventTimeUs = getNextEventTime();
                            while (nextEventTimeUs <= timeUs) {
/* 160 */                       this.nextSubtitleEventIndex++;
/* 161 */                       nextEventTimeUs = getNextEventTime();
/* 162 */                       this.textRendererNeedsUpdate = true;
                            }
                            if (nextEventTimeUs == Long.MAX_VALUE) {
/* 166 */                       this.subtitle = null;
                            }
                        }
/* 172 */               if (this.subtitle == null) {
/* 173 */                   boolean resetSampleHolder = false;
                            try {
                                try {
/* 175 */                           int result = this.source.readData(this.trackIndex, timeUs, this.formatHolder, this.sampleHolder, false);
/* 176 */                           if (result == -3) {
/* 177 */                               resetSampleHolder = true;
/* 178 */                               InputStream subtitleInputStream = new ByteArrayInputStream(this.sampleHolder.data.array(), 0, this.sampleHolder.size);
/* 180 */                               this.subtitle = this.subtitleParser.parse(subtitleInputStream, IoUtils.UTF_8, this.sampleHolder.timeUs);
/* 181 */                               syncNextEventIndex(timeUs);
/* 182 */                               this.textRendererNeedsUpdate = true;
                                    } else if (result == -1) {
/* 184 */                               this.inputStreamEnded = true;
                                    }
/* 190 */                           if (resetSampleHolder) {
/* 191 */                               resetSampleData();
                                    }
                                } catch (IOException e) {
/* 188 */                           throw new ExoPlaybackException(e);
                                }
                            } catch (Throwable th) {
/* 173 */                       if (0 != 0) {
/* 191 */                           resetSampleData();
                                }
                                throw th;
                            }
                        }
/* 197 */               if (this.textRendererNeedsUpdate && getState() == 3) {
/* 198 */                   this.textRendererNeedsUpdate = false;
/* 199 */                   if (this.subtitle == null) {
/* 200 */                       clearTextRenderer();
                            } else {
/* 202 */                       updateTextRenderer(timeUs);
                            }
                        }
                    } catch (IOException e2) {
/* 150 */               throw new ExoPlaybackException(e2);
                    }
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 208 */       protected void onDisabled() {
/* 209 */           this.source.disable(this.trackIndex);
/* 210 */           this.subtitle = null;
/* 211 */           resetSampleData();
/* 212 */           clearTextRenderer();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 216 */       protected void onReleased() {
/* 217 */           this.source.release();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 221 */       protected long getCurrentPositionUs() {
/* 222 */           return this.currentPositionUs;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 226 */       protected long getDurationUs() {
/* 227 */           return this.source.getTrackInfo(this.trackIndex).durationUs;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 232 */       protected long getBufferedPositionUs() {
/* 233 */           return -3L;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 237 */       protected boolean isEnded() {
                    return this.inputStreamEnded && this.subtitle == null;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 244 */       protected boolean isReady() {
/* 245 */           return true;
                }

/* 248 */       private void syncNextEventIndex(long timeUs) {
/* 249 */           this.nextSubtitleEventIndex = this.subtitle == null ? -1 : this.subtitle.getNextEventTimeIndex(timeUs);
                }

/* 252 */       private long getNextEventTime() {
/* 253 */           if (this.nextSubtitleEventIndex == -1 || this.nextSubtitleEventIndex >= this.subtitle.getEventTimeCount()) {
                        return Long.MAX_VALUE;
                    }
                    return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
                }

/* 258 */       private void resetSampleData() {
/* 259 */           if (this.sampleHolder.data != null) {
/* 260 */               this.sampleHolder.data.position(0);
                    }
                }

/* 264 */       private void updateTextRenderer(long timeUs) {
/* 265 */           String text = this.subtitle.getText(timeUs);
/* 266 */           log("updateTextRenderer; text=: " + text);
/* 267 */           if (this.textRendererHandler != null) {
/* 268 */               this.textRendererHandler.obtainMessage(0, text).sendToTarget();
                    } else {
/* 270 */               invokeTextRenderer(text);
                    }
                }

/* 274 */       private void clearTextRenderer() {
/* 275 */           log("clearTextRenderer");
/* 276 */           if (this.textRendererHandler != null) {
/* 277 */               this.textRendererHandler.obtainMessage(0, null).sendToTarget();
                    } else {
/* 279 */               invokeTextRenderer(null);
                    }
                }

                @Override // android.os.Handler.Callback
/* 284 */       public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
/* 287 */                   invokeTextRenderer((String) msg.obj);
/* 288 */                   return true;
                        default:
/* 290 */                   return false;
                    }
                }

/* 293 */       private void invokeTextRenderer(String text) {
/* 294 */           this.textRenderer.onText(text);
                }

/* 297 */       private void log(String logMessage) {
/* 298 */           if (VerboseLogUtil.isTagEnabled(TAG)) {
/* 299 */               Log.v(TAG, "type=2, " + logMessage);
                    }
                }
            }
