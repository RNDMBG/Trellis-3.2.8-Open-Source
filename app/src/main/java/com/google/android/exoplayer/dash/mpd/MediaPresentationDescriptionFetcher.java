            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.util.ManifestFetcher;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class MediaPresentationDescriptionFetcher extends ManifestFetcher<MediaPresentationDescription> {
                private final MediaPresentationDescriptionParser parser;


/* 43 */        public MediaPresentationDescriptionFetcher(ManifestFetcher.ManifestCallback<MediaPresentationDescription> callback) {
/* 44 */            super(callback);
/* 45 */            this.parser = new MediaPresentationDescriptionParser();
                }

/* 53 */        public MediaPresentationDescriptionFetcher(ManifestFetcher.ManifestCallback<MediaPresentationDescription> callback, int timeoutMillis) {
/* 54 */            super(callback, timeoutMillis);
/* 55 */            this.parser = new MediaPresentationDescriptionParser();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.exoplayer.util.ManifestFetcher
/* 60 */        protected MediaPresentationDescription parse(InputStream stream, String inputEncoding, String contentId, Uri baseUrl) throws IOException {
/* 61 */            return this.parser.parseMediaPresentationDescription(stream, inputEncoding, contentId, baseUrl);
                }
            }
