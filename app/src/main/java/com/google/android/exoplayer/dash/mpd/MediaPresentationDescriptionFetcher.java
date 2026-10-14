            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.util.ManifestFetcher;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public final class MediaPresentationDescriptionFetcher extends ManifestFetcher<MediaPresentationDescription> {
                private final MediaPresentationDescriptionParser parser;


        public MediaPresentationDescriptionFetcher(ManifestFetcher.ManifestCallback<MediaPresentationDescription> callback) {
            super(callback);
            this.parser = new MediaPresentationDescriptionParser();
                }

        public MediaPresentationDescriptionFetcher(ManifestFetcher.ManifestCallback<MediaPresentationDescription> callback, int timeoutMillis) {
            super(callback, timeoutMillis);
            this.parser = new MediaPresentationDescriptionParser();
                }

                
                @Override 
        protected MediaPresentationDescription parse(InputStream stream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
            return this.parser.parseMediaPresentationDescription(stream, inputEncoding, contentId, baseUrl);
                }
            }
