            package com.squareup.okhttp;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Callback {
                void onFailure(Request request, IOException iOException);

                void onResponse(Response response) throws IOException;
            }
