            package com.google.android.exoplayer;
            
            
    public final class CodecCounters {
                public int codecInitCount;
                public int codecReleaseCount;
                public int droppedOutputBufferCount;
                public int outputBuffersChangedCount;
                public int outputFormatChangedCount;
                public int renderedOutputBufferCount;
                public int skippedOutputBufferCount;

        public synchronized void ensureUpdated() {
                }

        public String getDebugString() {
            ensureUpdated();
            StringBuilder builder = new StringBuilder();
            builder.append("cic(").append(this.codecInitCount).append(")");
            builder.append("crc(").append(this.codecReleaseCount).append(")");
            builder.append("ofc(").append(this.outputFormatChangedCount).append(")");
            builder.append("obc(").append(this.outputBuffersChangedCount).append(")");
            builder.append("ren(").append(this.renderedOutputBufferCount).append(")");
            builder.append("sob(").append(this.skippedOutputBufferCount).append(")");
            builder.append("dob(").append(this.droppedOutputBufferCount).append(")");
            return builder.toString();
                }
            }
