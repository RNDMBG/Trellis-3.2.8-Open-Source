            package com.google.android.exoplayer;

            import android.os.Handler;
            import com.google.android.exoplayer.upstream.Allocator;
            import com.google.android.exoplayer.upstream.NetworkLock;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class DefaultLoadControl implements LoadControl {
                private static final int ABOVE_HIGH_WATERMARK = 0;
                private static final int BELOW_LOW_WATERMARK = 2;
                private static final int BETWEEN_WATERMARKS = 1;
                public static final float DEFAULT_HIGH_POOL_LOAD = 0.8f;
                public static final int DEFAULT_HIGH_WATERMARK_MS = 30000;
                public static final float DEFAULT_LOW_POOL_LOAD = 0.2f;
                public static final int DEFAULT_LOW_WATERMARK_MS = 15000;
                private final Allocator allocator;
                private int bufferPoolState;
                private final Handler eventHandler;
                private final EventListener eventListener;
                private boolean fillingBuffers;
                private final float highPoolLoad;
                private final long highWatermarkUs;
                private final HashMap<Object, LoaderState> loaderStates;
                private final List<Object> loaders;
                private final float lowPoolLoad;
                private final long lowWatermarkUs;
                private long maxLoadStartPositionUs;
                private boolean streamingPrioritySet;
                private int targetBufferSize;

                public interface EventListener {
                    void onLoadingChanged(boolean z);
                }

/* 41 */        static /* synthetic */ EventListener access$000(DefaultLoadControl x0) {
/* 42 */            return x0.eventListener;
                }

/* 89 */        public DefaultLoadControl(Allocator allocator) {
/* 90 */            this(allocator, null, null);
                }

/* 102 */       public DefaultLoadControl(Allocator allocator, Handler eventHandler, EventListener eventListener) {
/* 103 */           this(allocator, eventHandler, eventListener, DEFAULT_LOW_WATERMARK_MS, DEFAULT_HIGH_WATERMARK_MS, 0.2f, 0.8f);
                }

/* 125 */       public DefaultLoadControl(Allocator allocator, Handler eventHandler, EventListener eventListener, int lowWatermarkMs, int highWatermarkMs, float lowPoolLoad, float highPoolLoad) {
/* 127 */           this.allocator = allocator;
/* 128 */           this.eventHandler = eventHandler;
/* 129 */           this.eventListener = eventListener;
/* 130 */           this.loaders = new ArrayList();
/* 131 */           this.loaderStates = new HashMap<>();
/* 132 */           this.lowWatermarkUs = ((long) lowWatermarkMs) * 1000;
/* 133 */           this.highWatermarkUs = ((long) highWatermarkMs) * 1000;
/* 134 */           this.lowPoolLoad = lowPoolLoad;
/* 135 */           this.highPoolLoad = highPoolLoad;
                }

                @Override // com.google.android.exoplayer.LoadControl
/* 139 */       public void register(Object loader, int bufferSizeContribution) {
/* 140 */           this.loaders.add(loader);
/* 141 */           this.loaderStates.put(loader, new LoaderState(bufferSizeContribution));
/* 142 */           this.targetBufferSize += bufferSizeContribution;
                }

                @Override // com.google.android.exoplayer.LoadControl
/* 146 */       public void unregister(Object loader) {
/* 147 */           this.loaders.remove(loader);
/* 148 */           LoaderState state = this.loaderStates.remove(loader);
/* 149 */           this.targetBufferSize -= state.bufferSizeContribution;
/* 150 */           updateControlState();
                }

                @Override // com.google.android.exoplayer.LoadControl
/* 154 */       public void trimAllocator() {
/* 155 */           this.allocator.trim(this.targetBufferSize);
                }

                @Override // com.google.android.exoplayer.LoadControl
/* 159 */       public Allocator getAllocator() {
/* 160 */           return this.allocator;
                }

                @Override // com.google.android.exoplayer.LoadControl
/* 166 */       public boolean update(Object loader, long playbackPositionUs, long nextLoadPositionUs, boolean loading, boolean failed) {
/* 167 */           int loaderBufferState = getLoaderBufferState(playbackPositionUs, nextLoadPositionUs);
/* 168 */           LoaderState loaderState = this.loaderStates.get(loader);
/* 169 */           boolean loaderStateChanged = (loaderState.bufferState == loaderBufferState && loaderState.nextLoadPositionUs == nextLoadPositionUs && loaderState.loading == loading && loaderState.failed == failed) ? false : true;
/* 172 */           if (loaderStateChanged) {
/* 173 */               loaderState.bufferState = loaderBufferState;
/* 174 */               loaderState.nextLoadPositionUs = nextLoadPositionUs;
/* 175 */               loaderState.loading = loading;
/* 176 */               loaderState.failed = failed;
                    }
/* 180 */           int allocatedSize = this.allocator.getAllocatedSize();
/* 181 */           int bufferPoolState = getBufferPoolState(allocatedSize);
/* 182 */           boolean bufferPoolStateChanged = this.bufferPoolState != bufferPoolState;
/* 183 */           if (bufferPoolStateChanged) {
/* 184 */               this.bufferPoolState = bufferPoolState;
                    }
/* 188 */           if (loaderStateChanged || bufferPoolStateChanged) {
/* 189 */               updateControlState();
                    }
                    return allocatedSize < this.targetBufferSize && nextLoadPositionUs != -1 && nextLoadPositionUs <= this.maxLoadStartPositionUs;
                }

/* 196 */       private int getLoaderBufferState(long playbackPositionUs, long nextLoadPositionUs) {
                    if (nextLoadPositionUs == -1) {
                        return 0;
                    }
/* 200 */           long timeUntilNextLoadPosition = nextLoadPositionUs - playbackPositionUs;
                    if (timeUntilNextLoadPosition <= this.highWatermarkUs) {
                        return timeUntilNextLoadPosition < this.lowWatermarkUs ? 2 : 1;
                    }
                    return 0;
                }

/* 207 */       private int getBufferPoolState(int allocatedSize) {
/* 208 */           float bufferPoolLoad = allocatedSize / this.targetBufferSize;
                    if (bufferPoolLoad > this.highPoolLoad) {
                        return 0;
                    }
                    return bufferPoolLoad < this.lowPoolLoad ? 2 : 1;
                }

/* 214 */       private void updateControlState() {
/* 215 */           boolean loading = false;
/* 216 */           boolean failed = false;
/* 217 */           boolean finished = true;
/* 218 */           int highestState = this.bufferPoolState;
                    for (int i = 0; i < this.loaders.size(); i++) {
/* 220 */               LoaderState loaderState = this.loaderStates.get(this.loaders.get(i));
/* 221 */               loading |= loaderState.loading;
/* 222 */               failed |= loaderState.failed;
/* 223 */               finished &= loaderState.nextLoadPositionUs == -1;
/* 224 */               highestState = Math.max(highestState, loaderState.bufferState);
                    }
/* 227 */           this.fillingBuffers = (this.loaders.isEmpty() || finished || failed || (highestState != 2 && (highestState != 1 || !this.fillingBuffers))) ? false : true;
/* 230 */           if (this.fillingBuffers && !this.streamingPrioritySet) {
/* 231 */               NetworkLock.instance.add(0);
/* 232 */               this.streamingPrioritySet = true;
/* 233 */               notifyLoadingChanged(true);
                    } else if (!this.fillingBuffers && this.streamingPrioritySet && !loading) {
/* 235 */               NetworkLock.instance.remove(0);
/* 236 */               this.streamingPrioritySet = false;
/* 237 */               notifyLoadingChanged(false);
                    }
/* 240 */           this.maxLoadStartPositionUs = -1L;
/* 241 */           if (this.fillingBuffers) {
                        for (int i2 = 0; i2 < this.loaders.size(); i2++) {
/* 243 */                   Object loader = this.loaders.get(i2);
/* 245 */                   long loaderTime = this.loaderStates.get(loader).nextLoadPositionUs;
                            if (loaderTime != -1 && (this.maxLoadStartPositionUs == -1 || loaderTime < this.maxLoadStartPositionUs)) {
/* 248 */                       this.maxLoadStartPositionUs = loaderTime;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.DefaultLoadControl$1 */
/* 254 */       class RunnableC08511 implements Runnable {
                    final /* synthetic */ boolean val$loading;

/* 255 */           RunnableC08511(boolean z) {
                        this.val$loading = z;
                    }

                    @Override // java.lang.Runnable
/* 258 */           public void run() {
/* 259 */               DefaultLoadControl.access$000(DefaultLoadControl.this).onLoadingChanged(this.val$loading);
                    }
                }

/* 254 */       private void notifyLoadingChanged(boolean loading) {
/* 255 */           if (this.eventHandler != null && this.eventListener != null) {
/* 256 */               this.eventHandler.post(new RunnableC08511(loading));
                    }
                }

/* 272 */       private static class LoaderState {
                    public final int bufferSizeContribution;
                    public int bufferState = 0;
                    public boolean loading = false;
                    public boolean failed = false;
                    public long nextLoadPositionUs = -1;

/* 273 */           public LoaderState(int bufferSizeContribution) {
/* 275 */               this.bufferSizeContribution = bufferSizeContribution;
                    }
                }
            }
