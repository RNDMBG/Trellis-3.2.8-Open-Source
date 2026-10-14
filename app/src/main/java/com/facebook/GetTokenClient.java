            package com.facebook;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    final class GetTokenClient implements ServiceConnection {
                final String applicationId;
                final Context context;
                final Handler handler;
                CompletedListener listener;
                boolean running;
                Messenger sender;

                interface CompletedListener {
                    void completed(Bundle bundle);
                }

/* 25 */        static /* synthetic */ void access$000(GetTokenClient x0, Message x1) {
/* 26 */            x0.handleMessage(x1);
                }

/* 33 */        GetTokenClient(Context context, String applicationId) {
/* 35 */            Context applicationContext = context.getApplicationContext();
/* 37 */            this.context = applicationContext == null ? context : applicationContext;
/* 38 */            this.applicationId = applicationId;
/* 40 */            this.handler = new HandlerC07581();
                }

                /* JADX INFO: renamed from: com.facebook.GetTokenClient$1 */
/* 38 */        class HandlerC07581 extends Handler {
/* 39 */            HandlerC07581() {
                    }

                    @Override // android.os.Handler
/* 42 */            public void handleMessage(Message message) {
/* 43 */                GetTokenClient.access$000(GetTokenClient.this, message);
                    }
                }

/* 48 */        void setCompletedListener(CompletedListener listener) {
/* 49 */            this.listener = listener;
                }

/* 52 */        boolean start() {
/* 53 */            Intent intent = new Intent("com.facebook.platform.PLATFORM_SERVICE");
/* 54 */            intent.addCategory("android.intent.category.DEFAULT");
/* 55 */            Intent intent2 = NativeProtocol.validateKatanaServiceIntent(this.context, intent);
/* 57 */            if (intent2 == null) {
/* 58 */                callback(null);
/* 59 */                return false;
                    }
/* 61 */            this.running = true;
/* 62 */            this.context.bindService(intent2, this, 1);
                    return true;
                }

/* 67 */        void cancel() {
/* 68 */            this.running = false;
                }

                @Override // android.content.ServiceConnection
/* 72 */        public void onServiceConnected(ComponentName name, IBinder service) {
/* 73 */            this.sender = new Messenger(service);
/* 74 */            getToken();
                }

                @Override // android.content.ServiceConnection
/* 78 */        public void onServiceDisconnected(ComponentName name) {
/* 79 */            this.sender = null;
/* 80 */            this.context.unbindService(this);
/* 81 */            callback(null);
                }

/* 84 */        private void getToken() {
/* 85 */            Bundle data = new Bundle();
/* 86 */            data.putString("com.facebook.platform.extra.APPLICATION_ID", this.applicationId);
/* 88 */            Message request = Message.obtain((Handler) null, 65536);
/* 89 */            request.arg1 = 20121101;
/* 90 */            request.setData(data);
/* 91 */            request.replyTo = new Messenger(this.handler);
                    try {
/* 94 */                this.sender.send(request);
                    } catch (RemoteException e) {
/* 96 */                callback(null);
                    }
                }

/* 100 */       private void handleMessage(Message message) {
/* 101 */           if (message.what == 65537) {
/* 102 */               Bundle extras = message.getData();
/* 103 */               String errorType = extras.getString("com.facebook.platform.status.ERROR_TYPE");
/* 104 */               if (errorType != null) {
/* 105 */                   callback(null);
                        } else {
/* 107 */                   callback(extras);
                        }
/* 109 */               this.context.unbindService(this);
                    }
                }

/* 113 */       private void callback(Bundle result) {
/* 114 */           if (this.running) {
/* 117 */               this.running = false;
/* 119 */               CompletedListener callback = this.listener;
/* 120 */               if (callback != null) {
/* 121 */                   callback.completed(result);
                        }
                    }
                }
            }
