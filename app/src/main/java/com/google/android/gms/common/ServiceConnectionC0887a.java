            package com.google.android.gms.common;

            import android.content.ComponentName;
            import android.content.ServiceConnection;
            import android.os.IBinder;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.LinkedBlockingQueue;
            
            
            
            public class ServiceConnectionC0887a implements ServiceConnection {

                
                boolean f946iN = false;

                
                private final BlockingQueue<IBinder> f947iO = new LinkedBlockingQueue();

                
                public IBinder m1028aG() throws InterruptedException {
                    if (this.f946iN) {
                        throw new IllegalStateException();
                    }
                    this.f946iN = true;
                    return this.f947iO.take();
                }

                @Override 
                public void onServiceConnected(ComponentName name, IBinder service) {
                    try {
                        this.f947iO.put(service);
                    } catch (InterruptedException e) {
                    }
                }

                @Override 
                public void onServiceDisconnected(ComponentName name) {
                }
            }
