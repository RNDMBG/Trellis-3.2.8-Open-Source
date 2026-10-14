            package com.google.android.exoplayer.upstream;
            
            
            public interface Allocation {
                int capacity();

                void ensureCapacity(int i);

                byte[][] getBuffers();

                int getFragmentLength(int i);

                int getFragmentOffset(int i);

                void release();
            }
