            package com.crashlytics.android;

            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface PinningInfoProvider {
                String getKeyStorePassword();

                InputStream getKeyStoreStream();

                String[] getPins();
            }
