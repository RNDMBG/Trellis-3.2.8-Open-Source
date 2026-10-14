            package com.google.android.gms.common;

            import android.content.ComponentName;
            import android.content.ServiceConnection;
            import android.os.IBinder;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.LinkedBlockingQueue;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class ServiceConnectionC0887a implements ServiceConnection {

                /* JADX INFO: renamed from: iN */
                boolean f946iN = false;

                /* JADX INFO: renamed from: iO */
                private final BlockingQueue<IBinder> f947iO = new LinkedBlockingQueue();

                /* JADX INFO: renamed from: aG */
                public IBinder m1028aG() throws InterruptedException {
                    if (this.f946iN) {
                        throw new IllegalStateException();
                    }
                    this.f946iN = true;
                    return this.f947iO.take();
                }

                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName name, IBinder service) {
                    try {
                        this.f947iO.put(service);
                    } catch (InterruptedException e) {
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName name) {
                }
            }
