            package com.google.android.exoplayer.upstream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Allocator {
                Allocation allocate(int i);

                int getAllocatedSize();

                void trim(int i);
            }
