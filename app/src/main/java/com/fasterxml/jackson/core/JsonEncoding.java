            package com.fasterxml.jackson.core;

            import com.vandalsoftware.p004io.IoUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public enum JsonEncoding {
                UTF8(IoUtils.UTF_8, false, 8),
                UTF16_BE("UTF-16BE", true, 16),
                UTF16_LE("UTF-16LE", false, 16),
                UTF32_BE("UTF-32BE", true, 32),
                UTF32_LE("UTF-32LE", false, 32);

                protected final boolean _bigEndian;
                protected final int _bits;
                protected final String _javaName;

/* 32 */        JsonEncoding(String str, boolean z, int i) {
/* 34 */            this._javaName = str;
/* 35 */            this._bigEndian = z;
/* 36 */            this._bits = i;
                }

/* 43 */        public String getJavaName() {
/* 44 */            return this._javaName;
                }

/* 53 */        public boolean isBigEndian() {
/* 54 */            return this._bigEndian;
                }

/* 55 */        public int bits() {
/* 56 */            return this._bits;
                }
            }
