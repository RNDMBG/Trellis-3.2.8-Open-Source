            package com.google.android.exoplayer.parser.webm;

            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            interface EbmlEventHandler {
                int getElementType(int i);

                boolean onBinaryElement(int i, long j, int i2, int i3, NonBlockingInputStream nonBlockingInputStream);

                void onFloatElement(int i, double d);

                void onIntegerElement(int i, long j);

                void onMasterElementEnd(int i);

                void onMasterElementStart(int i, long j, int i2, long j2);

                void onStringElement(int i, String str);
            }
