            package com.google.android.exoplayer.parser;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import java.util.Map;
            import java.util.UUID;
            
            
            public interface Extractor {
                public static final int RESULT_END_OF_STREAM = 2;
                public static final int RESULT_NEED_MORE_DATA = 1;
                public static final int RESULT_NEED_SAMPLE_HOLDER = 32;
                public static final int RESULT_READ_INDEX = 16;
                public static final int RESULT_READ_INIT = 8;
                public static final int RESULT_READ_SAMPLE = 4;

                MediaFormat getFormat();

                SegmentIndex getIndex();

                Map<UUID, byte[]> getPsshInfo();

                boolean hasRelativeIndexOffsets();

                int read(NonBlockingInputStream nonBlockingInputStream, SampleHolder sampleHolder) throws ParserException;

                boolean seekTo(long j, boolean z);
            }
