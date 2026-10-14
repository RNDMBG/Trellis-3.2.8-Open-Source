            package com.google.android.exoplayer.upstream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Allocation {
                int capacity();

                void ensureCapacity(int i);

                byte[][] getBuffers();

                int getFragmentLength(int i);

                int getFragmentOffset(int i);

                void release();
            }
