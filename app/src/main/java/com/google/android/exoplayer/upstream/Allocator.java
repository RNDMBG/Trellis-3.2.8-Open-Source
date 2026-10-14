            package com.google.android.exoplayer.upstream;
            
            
            public interface Allocator {
                Allocation allocate(int i);

                int getAllocatedSize();

                void trim(int i);
            }
