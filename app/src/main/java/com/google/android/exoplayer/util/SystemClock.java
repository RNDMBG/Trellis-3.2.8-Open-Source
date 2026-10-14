            package com.google.android.exoplayer.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public final class SystemClock implements Clock {
                @Override // com.google.android.exoplayer.util.Clock
/* 24 */        public long elapsedRealtime() {
/* 25 */            return android.os.SystemClock.elapsedRealtime();
                }
            }
