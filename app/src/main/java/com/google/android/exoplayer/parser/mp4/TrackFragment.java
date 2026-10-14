            package com.google.android.exoplayer.parser.mp4;

            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            
            
    final class TrackFragment {
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

        TrackFragment() {
                }

        public void reset() {
            this.length = 0;
            this.definesEncryptionData = false;
            this.sampleEncryptionDataNeedsFill = false;
                }

        public void initTables(int sampleCount) {
            this.length = sampleCount;
            if (this.sampleSizeTable == null || this.sampleSizeTable.length < this.length) {
                int tableSize = (sampleCount * 125) / 100;
                this.sampleSizeTable = new int[tableSize];
                this.sampleDecodingTimeTable = new int[tableSize];
                this.sampleCompositionTimeOffsetTable = new int[tableSize];
               this.sampleIsSyncFrameTable = new boolean[tableSize];
               this.sampleHasSubsampleEncryptionTable = new boolean[tableSize];
                    }
                }

       public void initEncryptionData(int length) {
           if (this.sampleEncryptionData == null || this.sampleEncryptionData.length() < length) {
               this.sampleEncryptionData = new ParsableByteArray(length);
                    }
           this.sampleEncryptionDataLength = length;
           this.definesEncryptionData = true;
           this.sampleEncryptionDataNeedsFill = true;
                }

       public void fillEncryptionData(ParsableByteArray source) {
           source.readBytes(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
           this.sampleEncryptionData.setPosition(0);
           this.sampleEncryptionDataNeedsFill = false;
                }

       public boolean fillEncryptionData(NonBlockingInputStream source) {
                    if (source.getAvailableByteCount() < this.sampleEncryptionDataLength) {
                        return false;
                    }
           source.read(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
           this.sampleEncryptionData.setPosition(0);
           this.sampleEncryptionDataNeedsFill = false;
           return true;
                }

       public int getSamplePresentationTime(int index) {
           return this.sampleDecodingTimeTable[index] + this.sampleCompositionTimeOffsetTable[index];
                }
            }
