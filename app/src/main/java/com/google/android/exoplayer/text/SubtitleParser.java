            package com.google.android.exoplayer.text;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface SubtitleParser {
                boolean canParse(String str);

                Subtitle parse(InputStream inputStream, String str, long j) throws IOException;
            }
