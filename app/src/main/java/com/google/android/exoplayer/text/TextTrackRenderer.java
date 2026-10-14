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
            
            
            @TargetApi(16)
    public class TextTrackRenderer extends TrackRenderer implements Handler.Callback {
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

        public TextTrackRenderer(SampleSource source, SubtitleParser subtitleParser, TextRenderer textRenderer, Looper textRendererLooper) {
            this.source = (SampleSource) Assertions.checkNotNull(source);
            this.subtitleParser = (SubtitleParser) Assertions.checkNotNull(subtitleParser);
            this.textRenderer = (TextRenderer) Assertions.checkNotNull(textRenderer);
            this.textRendererHandler = textRendererLooper == null ? null : new Handler(textRendererLooper, this);
            this.formatHolder = new MediaFormatHolder();
            this.sampleHolder = new SampleHolder(true);
                }

                @Override 
       protected int doPrepare() throws ExoPlaybackException {
                    try {
               boolean sourcePrepared = this.source.prepare();
               if (!sourcePrepared) {
                   return 0;
                        }
                        for (int i = 0; i < this.source.getTrackCount(); i++) {
                   if (this.subtitleParser.canParse(this.source.getTrackInfo(i).mimeType)) {
                       this.trackIndex = i;
                       return 1;
                            }
                        }
               return -1;
                    } catch (IOException e) {
               throw new ExoPlaybackException(e);
                    }
                }

                @Override 
       protected void onEnabled(long timeUs, boolean joining) {
           this.source.enable(this.trackIndex, timeUs);
           seekToInternal(timeUs);
                }

                @Override 
       protected void seekTo(long timeUs) {
           this.source.seekToUs(timeUs);
           seekToInternal(timeUs);
                }

       private void seekToInternal(long timeUs) {
           this.inputStreamEnded = false;
           this.currentPositionUs = timeUs;
           this.source.seekToUs(timeUs);
           if (this.subtitle != null && (timeUs < this.subtitle.getStartTime() || this.subtitle.getLastEventTime() <= timeUs)) {
               this.subtitle = null;
                    }
           resetSampleData();
           clearTextRenderer();
           syncNextEventIndex(timeUs);
           this.textRendererNeedsUpdate = this.subtitle != null;
                }

                @Override 
       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
                    try {
               this.source.continueBuffering(timeUs);
               this.currentPositionUs = timeUs;
               if (this.subtitle != null) {
                   long nextEventTimeUs = getNextEventTime();
                            while (nextEventTimeUs <= timeUs) {
                       this.nextSubtitleEventIndex++;
                       nextEventTimeUs = getNextEventTime();
                       this.textRendererNeedsUpdate = true;
                            }
                            if (nextEventTimeUs == Long.MAX_VALUE) {
                       this.subtitle = null;
                            }
                        }
               if (this.subtitle == null) {
                   boolean resetSampleHolder = false;
                            try {
                                try {
                           int result = this.source.readData(this.trackIndex, timeUs, this.formatHolder, this.sampleHolder, false);
                           if (result == -3) {
                               resetSampleHolder = true;
                               InputStream subtitleInputStream = new ByteArrayInputStream(this.sampleHolder.data.array(), 0, this.sampleHolder.size);
                               this.subtitle = this.subtitleParser.parse(subtitleInputStream, IoUtils.UTF_8, this.sampleHolder.timeUs);
                               syncNextEventIndex(timeUs);
                               this.textRendererNeedsUpdate = true;
                                    } else if (result == -1) {
                               this.inputStreamEnded = true;
                                    }
                           if (resetSampleHolder) {
                               resetSampleData();
                                    }
                                } catch (IOException e) {
                           throw new ExoPlaybackException(e);
                                }
                            } catch (Throwable th) {
                       if (0 != 0) {
                           resetSampleData();
                                }
                                throw th;
                            }
                        }
               if (this.textRendererNeedsUpdate && getState() == 3) {
                   this.textRendererNeedsUpdate = false;
                   if (this.subtitle == null) {
                       clearTextRenderer();
                            } else {
                       updateTextRenderer(timeUs);
                            }
                        }
                    } catch (IOException e2) {
               throw new ExoPlaybackException(e2);
                    }
                }

                @Override 
       protected void onDisabled() {
           this.source.disable(this.trackIndex);
           this.subtitle = null;
           resetSampleData();
           clearTextRenderer();
                }

                @Override 
       protected void onReleased() {
           this.source.release();
                }

                @Override 
       protected long getCurrentPositionUs() {
           return this.currentPositionUs;
                }

                @Override 
       protected long getDurationUs() {
           return this.source.getTrackInfo(this.trackIndex).durationUs;
                }

                @Override 
       protected long getBufferedPositionUs() {
           return -3L;
                }

                @Override 
       protected boolean isEnded() {
                    return this.inputStreamEnded && this.subtitle == null;
                }

                @Override 
       protected boolean isReady() {
           return true;
                }

       private void syncNextEventIndex(long timeUs) {
           this.nextSubtitleEventIndex = this.subtitle == null ? -1 : this.subtitle.getNextEventTimeIndex(timeUs);
                }

       private long getNextEventTime() {
           if (this.nextSubtitleEventIndex == -1 || this.nextSubtitleEventIndex >= this.subtitle.getEventTimeCount()) {
                        return Long.MAX_VALUE;
                    }
                    return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
                }

       private void resetSampleData() {
           if (this.sampleHolder.data != null) {
               this.sampleHolder.data.position(0);
                    }
                }

       private void updateTextRenderer(long timeUs) {
           String text = this.subtitle.getText(timeUs);
           log("updateTextRenderer; text=: " + text);
           if (this.textRendererHandler != null) {
               this.textRendererHandler.obtainMessage(0, text).sendToTarget();
                    } else {
               invokeTextRenderer(text);
                    }
                }

       private void clearTextRenderer() {
           log("clearTextRenderer");
           if (this.textRendererHandler != null) {
               this.textRendererHandler.obtainMessage(0, null).sendToTarget();
                    } else {
               invokeTextRenderer(null);
                    }
                }

                @Override 
       public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                   invokeTextRenderer((String) msg.obj);
                   return true;
                        default:
                   return false;
                    }
                }

       private void invokeTextRenderer(String text) {
           this.textRenderer.onText(text);
                }

       private void log(String logMessage) {
           if (VerboseLogUtil.isTagEnabled(TAG)) {
               Log.v(TAG, "type=2, " + logMessage);
                    }
                }
            }
