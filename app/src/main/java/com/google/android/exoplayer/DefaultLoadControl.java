            package com.google.android.exoplayer;

            import android.os.Handler;
            import com.google.android.exoplayer.upstream.Allocator;
            import com.google.android.exoplayer.upstream.NetworkLock;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.List;
            
            
    public class DefaultLoadControl implements LoadControl {
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

        static  EventListener access$000(DefaultLoadControl x0) {
            return x0.eventListener;
                }

        public DefaultLoadControl(Allocator allocator) {
            this(allocator, null, null);
                }

       public DefaultLoadControl(Allocator allocator, Handler eventHandler, EventListener eventListener) {
           this(allocator, eventHandler, eventListener, DEFAULT_LOW_WATERMARK_MS, DEFAULT_HIGH_WATERMARK_MS, 0.2f, 0.8f);
                }

       public DefaultLoadControl(Allocator allocator, Handler eventHandler, EventListener eventListener, int lowWatermarkMs, int highWatermarkMs, float lowPoolLoad, float highPoolLoad) {
           this.allocator = allocator;
           this.eventHandler = eventHandler;
           this.eventListener = eventListener;
           this.loaders = new ArrayList();
           this.loaderStates = new HashMap<>();
           this.lowWatermarkUs = ((long) lowWatermarkMs) * 1000;
           this.highWatermarkUs = ((long) highWatermarkMs) * 1000;
           this.lowPoolLoad = lowPoolLoad;
           this.highPoolLoad = highPoolLoad;
                }

                @Override 
       public void register(Object loader, int bufferSizeContribution) {
           this.loaders.add(loader);
           this.loaderStates.put(loader, new LoaderState(bufferSizeContribution));
           this.targetBufferSize += bufferSizeContribution;
                }

                @Override 
       public void unregister(Object loader) {
           this.loaders.remove(loader);
           LoaderState state = this.loaderStates.remove(loader);
           this.targetBufferSize -= state.bufferSizeContribution;
           updateControlState();
                }

                @Override 
       public void trimAllocator() {
           this.allocator.trim(this.targetBufferSize);
                }

                @Override 
       public Allocator getAllocator() {
           return this.allocator;
                }

                @Override 
       public boolean update(Object loader, long playbackPositionUs, long nextLoadPositionUs, boolean loading, boolean failed) {
           int loaderBufferState = getLoaderBufferState(playbackPositionUs, nextLoadPositionUs);
           LoaderState loaderState = this.loaderStates.get(loader);
           boolean loaderStateChanged = (loaderState.bufferState == loaderBufferState && loaderState.nextLoadPositionUs == nextLoadPositionUs && loaderState.loading == loading && loaderState.failed == failed) ? false : true;
           if (loaderStateChanged) {
               loaderState.bufferState = loaderBufferState;
               loaderState.nextLoadPositionUs = nextLoadPositionUs;
               loaderState.loading = loading;
               loaderState.failed = failed;
                    }
           int allocatedSize = this.allocator.getAllocatedSize();
           int bufferPoolState = getBufferPoolState(allocatedSize);
           boolean bufferPoolStateChanged = this.bufferPoolState != bufferPoolState;
           if (bufferPoolStateChanged) {
               this.bufferPoolState = bufferPoolState;
                    }
           if (loaderStateChanged || bufferPoolStateChanged) {
               updateControlState();
                    }
                    return allocatedSize < this.targetBufferSize && nextLoadPositionUs != -1 && nextLoadPositionUs <= this.maxLoadStartPositionUs;
                }

       private int getLoaderBufferState(long playbackPositionUs, long nextLoadPositionUs) {
                    if (nextLoadPositionUs == -1) {
                        return 0;
                    }
           long timeUntilNextLoadPosition = nextLoadPositionUs - playbackPositionUs;
                    if (timeUntilNextLoadPosition <= this.highWatermarkUs) {
                        return timeUntilNextLoadPosition < this.lowWatermarkUs ? 2 : 1;
                    }
                    return 0;
                }

       private int getBufferPoolState(int allocatedSize) {
           float bufferPoolLoad = allocatedSize / this.targetBufferSize;
                    if (bufferPoolLoad > this.highPoolLoad) {
                        return 0;
                    }
                    return bufferPoolLoad < this.lowPoolLoad ? 2 : 1;
                }

       private void updateControlState() {
           boolean loading = false;
           boolean failed = false;
           boolean finished = true;
           int highestState = this.bufferPoolState;
                    for (int i = 0; i < this.loaders.size(); i++) {
               LoaderState loaderState = this.loaderStates.get(this.loaders.get(i));
               loading |= loaderState.loading;
               failed |= loaderState.failed;
               finished &= loaderState.nextLoadPositionUs == -1;
               highestState = Math.max(highestState, loaderState.bufferState);
                    }
           this.fillingBuffers = (this.loaders.isEmpty() || finished || failed || (highestState != 2 && (highestState != 1 || !this.fillingBuffers))) ? false : true;
           if (this.fillingBuffers && !this.streamingPrioritySet) {
               NetworkLock.instance.add(0);
               this.streamingPrioritySet = true;
               notifyLoadingChanged(true);
                    } else if (!this.fillingBuffers && this.streamingPrioritySet && !loading) {
               NetworkLock.instance.remove(0);
               this.streamingPrioritySet = false;
               notifyLoadingChanged(false);
                    }
           this.maxLoadStartPositionUs = -1L;
           if (this.fillingBuffers) {
                        for (int i2 = 0; i2 < this.loaders.size(); i2++) {
                   Object loader = this.loaders.get(i2);
                   long loaderTime = this.loaderStates.get(loader).nextLoadPositionUs;
                            if (loaderTime != -1 && (this.maxLoadStartPositionUs == -1 || loaderTime < this.maxLoadStartPositionUs)) {
                       this.maxLoadStartPositionUs = loaderTime;
                            }
                        }
                    }
                }

                
       class RunnableC08511 implements Runnable {
                    final  boolean val$loading;

           RunnableC08511(boolean z) {
                        this.val$loading = z;
                    }

                    @Override 
           public void run() {
               DefaultLoadControl.access$000(DefaultLoadControl.this).onLoadingChanged(this.val$loading);
                    }
                }

       private void notifyLoadingChanged(boolean loading) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08511(loading));
                    }
                }

       private static class LoaderState {
                    public final int bufferSizeContribution;
                    public int bufferState = 0;
                    public boolean loading = false;
                    public boolean failed = false;
                    public long nextLoadPositionUs = -1;

           public LoaderState(int bufferSizeContribution) {
               this.bufferSizeContribution = bufferSizeContribution;
                    }
                }
            }
