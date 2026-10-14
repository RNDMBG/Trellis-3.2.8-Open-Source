            package com.google.android.exoplayer.smoothstreaming;

            import android.net.Uri;
            import com.google.android.exoplayer.util.ManifestFetcher;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public final class SmoothStreamingManifestFetcher extends ManifestFetcher<SmoothStreamingManifest> {
                private final SmoothStreamingManifestParser parser;


        public SmoothStreamingManifestFetcher(ManifestFetcher.ManifestCallback<SmoothStreamingManifest> callback) {
            super(callback);
            this.parser = new SmoothStreamingManifestParser();
                }

        public SmoothStreamingManifestFetcher(ManifestFetcher.ManifestCallback<SmoothStreamingManifest> callback, int timeoutMillis) {
            super(callback, timeoutMillis);
            this.parser = new SmoothStreamingManifestParser();
                }

                
                @Override 
        protected SmoothStreamingManifest parse(InputStream stream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
            return this.parser.parse(stream, inputEncoding);
                }
            }
