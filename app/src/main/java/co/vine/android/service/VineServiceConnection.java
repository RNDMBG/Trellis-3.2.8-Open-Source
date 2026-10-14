            package co.vine.android.service;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.ServiceConnection;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Messenger;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class VineServiceConnection implements ServiceConnection {
                private Context mContext;
                private boolean mIgnoreBundlesInResponse;
                private Messenger mResponder;
                private ServiceResponseHandler mResponseHandler;
                private Messenger mService;
                private final HashMap<String, PendingAction> mPendingQueue = new HashMap<>();
                private final HashMap<String, PendingAction> mRequestQueue = new HashMap<>();
                private final int[] mLock = new int[0];

                public interface ServiceResponseHandler {
                    void handleServiceResponse(int i, int i2, String str, Bundle bundle);
                }

/* 22 */        static /* synthetic */ boolean access$000(VineServiceConnection x0) {
/* 23 */            return x0.mIgnoreBundlesInResponse;
                }

/* 22 */        static /* synthetic */ Context access$100(VineServiceConnection x0) {
/* 23 */            return x0.mContext;
                }

/* 22 */        static /* synthetic */ ServiceResponseHandler access$200(VineServiceConnection x0) {
/* 23 */            return x0.mResponseHandler;
                }

/* 35 */        public VineServiceConnection(Context context, ServiceResponseHandler handler) {
/* 37 */            this.mContext = context;
/* 38 */            this.mResponseHandler = handler;
                }

/* 41 */        public void setIgnoreBundlesInResponse(boolean ignoreBundlesInResponse) {
/* 42 */            this.mIgnoreBundlesInResponse = ignoreBundlesInResponse;
                }

/* 45 */        public String queueAndExecute(int actionCode, Bundle bundle) {
/* 46 */            String reqId = generateRequestId();
/* 47 */            PendingAction action = new PendingAction(actionCode, bundle);
/* 48 */            bundle.putString(VineService.EXTRA_REQUEST_ID, reqId);
                    synchronized (this.mLock) {
                        try {
/* 50 */                    Messenger service = this.mService;
/* 51 */                    if (service != null) {
/* 52 */                        this.mRequestQueue.put(reqId, action);
/* 53 */                        sendMessage(service, getMessage(action));
                            } else {
/* 55 */                        this.mPendingQueue.put(reqId, action);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 58 */            return reqId;
                }

/* 61 */        public void cancelAll() {
                    synchronized (this.mLock) {
                        try {
/* 63 */                    this.mRequestQueue.clear();
/* 64 */                    this.mPendingQueue.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 68 */        private Message getMessage(PendingAction action) {
/* 69 */            Message msg = Message.obtain();
/* 70 */            msg.arg1 = action.actionCode;
/* 71 */            msg.setData(action.bundle);
/* 72 */            msg.replyTo = getServiceResponder();
/* 73 */            return msg;
                }

                /* JADX INFO: renamed from: co.vine.android.service.VineServiceConnection$1 */
/* 76 */        class HandlerC05241 extends Handler {
/* 77 */            HandlerC05241(Looper x0) {
                        super(x0);
                    }

                    @Override // android.os.Handler
/* 80 */            public void handleMessage(Message msg) {
/* 81 */                if (!VineServiceConnection.access$000(VineServiceConnection.this)) {
/* 82 */                    Bundle b = msg.getData();
/* 83 */                    b.setClassLoader(VineServiceConnection.access$100(VineServiceConnection.this).getClassLoader());
/* 84 */                    VineServiceConnection.access$200(VineServiceConnection.this).handleServiceResponse(msg.what, msg.arg1, b.getString(VineService.EXTRA_REASON_PHRASE), b);
/* 89 */                    return;
                        }
/* 87 */                VineServiceConnection.access$200(VineServiceConnection.this).handleServiceResponse(msg.what, msg.arg1, null, null);
                    }
                }

/* 76 */        private synchronized Messenger getServiceResponder() {
                    try {
                        if (this.mResponder == null) {
/* 78 */                    this.mResponder = new Messenger(new HandlerC05241(Looper.getMainLooper()));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 92 */            return this.mResponder;
                }

/* 96 */        private void sendMessage(Messenger service, Message msg) {
                    try {
/* 97 */                service.send(msg);
                    } catch (Throwable e) {
/* 99 */                CrashUtil.logException(e, "Unable to send message to service", new Object[0]);
                        Bundle bundle = msg.getData();
                        String reqId = bundle.getString(VineService.EXTRA_REQUEST_ID);
                        if (reqId != null) {
                            synchronized (this.mLock) {
                                this.mRequestQueue.remove(reqId);
                            }
                            bundle.putString(VineService.EXTRA_REASON_PHRASE, "Unable to send request to service");
                            this.mResponseHandler.handleServiceResponse(msg.arg1, 599, "Unable to send request to service", bundle);
                        }
                    }
                }

                @Override // android.content.ServiceConnection
/* 104 */       public void onServiceConnected(ComponentName name, IBinder binder) {
                    synchronized (this.mLock) {
                        try {
/* 106 */                   Messenger service = new Messenger(binder);
/* 107 */                   this.mService = service;
/* 108 */                   this.mRequestQueue.putAll(this.mPendingQueue);
                            for (PendingAction action : this.mPendingQueue.values()) {
/* 110 */                       sendMessage(service, getMessage(action));
                            }
/* 112 */                   this.mPendingQueue.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
/* 117 */       public void onServiceDisconnected(ComponentName name) {
                    synchronized (this.mLock) {
                        try {
/* 119 */                   this.mService = null;
                            PendingAction[] failedActions = this.mRequestQueue.values().toArray(new PendingAction[0]);
                            this.mRequestQueue.clear();
                            for (PendingAction action : failedActions) {
                                Bundle bundle = action.bundle;
                                String reqId = bundle.getString(VineService.EXTRA_REQUEST_ID);
                                if (reqId != null) {
                                    bundle.putString(VineService.EXTRA_REASON_PHRASE, "Service disconnected");
                                    this.mResponseHandler.handleServiceResponse(action.actionCode, 599, "Service disconnected", bundle);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 123 */       public boolean isPending(String reqId) {
                    boolean zContainsKey;
                    synchronized (this.mLock) {
                        try {
/* 125 */                   zContainsKey = this.mRequestQueue.containsKey(reqId);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zContainsKey;
                }

/* 129 */       public PendingAction remove(String reqId) {
                    PendingAction action;
                    synchronized (this.mLock) {
                        try {
/* 131 */                   action = this.mRequestQueue.get(reqId);
/* 132 */                   this.mRequestQueue.remove(reqId);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return action;
                }

/* 137 */       public String generateRequestId() {
/* 138 */           return Util.randomString(6);
                }

/* 145 */       public boolean isBound() {
                    return this.mService != null;
                }
            }
