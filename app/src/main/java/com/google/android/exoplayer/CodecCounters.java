            package com.google.android.exoplayer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public final class CodecCounters {
                public int codecInitCount;
                public int codecReleaseCount;
                public int droppedOutputBufferCount;
                public int outputBuffersChangedCount;
                public int outputFormatChangedCount;
                public int renderedOutputBufferCount;
                public int skippedOutputBufferCount;

/* 42 */        public synchronized void ensureUpdated() {
                }

/* 45 */        public String getDebugString() {
/* 46 */            ensureUpdated();
/* 47 */            StringBuilder builder = new StringBuilder();
/* 48 */            builder.append("cic(").append(this.codecInitCount).append(")");
/* 49 */            builder.append("crc(").append(this.codecReleaseCount).append(")");
/* 50 */            builder.append("ofc(").append(this.outputFormatChangedCount).append(")");
/* 51 */            builder.append("obc(").append(this.outputBuffersChangedCount).append(")");
/* 52 */            builder.append("ren(").append(this.renderedOutputBufferCount).append(")");
/* 53 */            builder.append("sob(").append(this.skippedOutputBufferCount).append(")");
/* 54 */            builder.append("dob(").append(this.droppedOutputBufferCount).append(")");
/* 55 */            return builder.toString();
                }
            }
