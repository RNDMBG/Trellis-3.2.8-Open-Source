            package com.google.android.exoplayer.parser.mp4;

            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    final class TrackFragment {
                public boolean definesEncryptionData;
                public int length;
                public int[] sampleCompositionTimeOffsetTable;
                public int[] sampleDecodingTimeTable;
                public int sampleDescriptionIndex;
                public ParsableByteArray sampleEncryptionData;
                public int sampleEncryptionDataLength;
                public boolean sampleEncryptionDataNeedsFill;
                public boolean[] sampleHasSubsampleEncryptionTable;
                public boolean[] sampleIsSyncFrameTable;
                public int[] sampleSizeTable;

/* 22 */        TrackFragment() {
                }

/* 77 */        public void reset() {
/* 78 */            this.length = 0;
/* 79 */            this.definesEncryptionData = false;
/* 80 */            this.sampleEncryptionDataNeedsFill = false;
                }

/* 91 */        public void initTables(int sampleCount) {
/* 92 */            this.length = sampleCount;
/* 93 */            if (this.sampleSizeTable == null || this.sampleSizeTable.length < this.length) {
/* 96 */                int tableSize = (sampleCount * 125) / 100;
/* 97 */                this.sampleSizeTable = new int[tableSize];
/* 98 */                this.sampleDecodingTimeTable = new int[tableSize];
/* 99 */                this.sampleCompositionTimeOffsetTable = new int[tableSize];
/* 100 */               this.sampleIsSyncFrameTable = new boolean[tableSize];
/* 101 */               this.sampleHasSubsampleEncryptionTable = new boolean[tableSize];
                    }
                }

/* 114 */       public void initEncryptionData(int length) {
/* 115 */           if (this.sampleEncryptionData == null || this.sampleEncryptionData.length() < length) {
/* 116 */               this.sampleEncryptionData = new ParsableByteArray(length);
                    }
/* 118 */           this.sampleEncryptionDataLength = length;
/* 119 */           this.definesEncryptionData = true;
/* 120 */           this.sampleEncryptionDataNeedsFill = true;
                }

/* 128 */       public void fillEncryptionData(ParsableByteArray source) {
/* 129 */           source.readBytes(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
/* 130 */           this.sampleEncryptionData.setPosition(0);
/* 131 */           this.sampleEncryptionDataNeedsFill = false;
                }

/* 140 */       public boolean fillEncryptionData(NonBlockingInputStream source) {
                    if (source.getAvailableByteCount() < this.sampleEncryptionDataLength) {
                        return false;
                    }
/* 144 */           source.read(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
/* 145 */           this.sampleEncryptionData.setPosition(0);
/* 146 */           this.sampleEncryptionDataNeedsFill = false;
/* 147 */           return true;
                }

/* 150 */       public int getSamplePresentationTime(int index) {
/* 151 */           return this.sampleDecodingTimeTable[index] + this.sampleCompositionTimeOffsetTable[index];
                }
            }
