            package com.google.android.exoplayer;

            import com.google.android.exoplayer.upstream.Allocator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface LoadControl {
                Allocator getAllocator();

                void register(Object obj, int i);

                void trimAllocator();

                void unregister(Object obj);

                boolean update(Object obj, long j, long j2, boolean z, boolean z2);
            }
