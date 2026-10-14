            package com.google.android.exoplayer.smoothstreaming;

            import android.net.Uri;
            import com.google.android.exoplayer.util.ManifestFetcher;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public final class SmoothStreamingManifestFetcher extends ManifestFetcher<SmoothStreamingManifest> {
                private final SmoothStreamingManifestParser parser;


/* 42 */        public SmoothStreamingManifestFetcher(ManifestFetcher.ManifestCallback<SmoothStreamingManifest> callback) {
/* 43 */            super(callback);
/* 44 */            this.parser = new SmoothStreamingManifestParser();
                }

/* 52 */        public SmoothStreamingManifestFetcher(ManifestFetcher.ManifestCallback<SmoothStreamingManifest> callback, int timeoutMillis) {
/* 53 */            super(callback, timeoutMillis);
/* 54 */            this.parser = new SmoothStreamingManifestParser();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.exoplayer.util.ManifestFetcher
/* 59 */        protected SmoothStreamingManifest parse(InputStream stream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
/* 60 */            return this.parser.parse(stream, inputEncoding);
                }
            }
