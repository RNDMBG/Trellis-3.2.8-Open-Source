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
            
            
    public class VineServiceConnection implements ServiceConnection {
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

        static  boolean access$000(VineServiceConnection x0) {
            return x0.mIgnoreBundlesInResponse;
                }

        static  Context access$100(VineServiceConnection x0) {
            return x0.mContext;
                }

        static  ServiceResponseHandler access$200(VineServiceConnection x0) {
            return x0.mResponseHandler;
                }

        public VineServiceConnection(Context context, ServiceResponseHandler handler) {
            this.mContext = context;
            this.mResponseHandler = handler;
                }

        public void setIgnoreBundlesInResponse(boolean ignoreBundlesInResponse) {
            this.mIgnoreBundlesInResponse = ignoreBundlesInResponse;
                }

        public String queueAndExecute(int actionCode, Bundle bundle) {
            String reqId = generateRequestId();
            PendingAction action = new PendingAction(actionCode, bundle);
            bundle.putString(VineService.EXTRA_REQUEST_ID, reqId);
                    synchronized (this.mLock) {
                        try {
                    Messenger service = this.mService;
                    if (service != null) {
                        this.mRequestQueue.put(reqId, action);
                        sendMessage(service, getMessage(action));
                            } else {
                        this.mPendingQueue.put(reqId, action);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            return reqId;
                }

        public void cancelAll() {
                    synchronized (this.mLock) {
                        try {
                    this.mRequestQueue.clear();
                    this.mPendingQueue.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        private Message getMessage(PendingAction action) {
            Message msg = Message.obtain();
            msg.arg1 = action.actionCode;
            msg.setData(action.bundle);
            msg.replyTo = getServiceResponder();
            return msg;
                }

                
        class HandlerC05241 extends Handler {
            HandlerC05241(Looper x0) {
                        super(x0);
                    }

                    @Override 
            public void handleMessage(Message msg) {
                if (!VineServiceConnection.access$000(VineServiceConnection.this)) {
                    Bundle b = msg.getData();
                    b.setClassLoader(VineServiceConnection.access$100(VineServiceConnection.this).getClassLoader());
                    VineServiceConnection.access$200(VineServiceConnection.this).handleServiceResponse(msg.what, msg.arg1, b.getString(VineService.EXTRA_REASON_PHRASE), b);
                    return;
                        }
                VineServiceConnection.access$200(VineServiceConnection.this).handleServiceResponse(msg.what, msg.arg1, null, null);
                    }
                }

        private synchronized Messenger getServiceResponder() {
                    try {
                        if (this.mResponder == null) {
                    this.mResponder = new Messenger(new HandlerC05241(Looper.getMainLooper()));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return this.mResponder;
                }

        private void sendMessage(Messenger service, Message msg) {
                    try {
                service.send(msg);
                    } catch (Throwable e) {
                CrashUtil.logException(e, "Unable to send message to service", new Object[0]);
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

                @Override 
       public void onServiceConnected(ComponentName name, IBinder binder) {
                    synchronized (this.mLock) {
                        try {
                   Messenger service = new Messenger(binder);
                   this.mService = service;
                   this.mRequestQueue.putAll(this.mPendingQueue);
                            for (PendingAction action : this.mPendingQueue.values()) {
                       sendMessage(service, getMessage(action));
                            }
                   this.mPendingQueue.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
       public void onServiceDisconnected(ComponentName name) {
                    synchronized (this.mLock) {
                        try {
                   this.mService = null;
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

       public boolean isPending(String reqId) {
                    boolean zContainsKey;
                    synchronized (this.mLock) {
                        try {
                   zContainsKey = this.mRequestQueue.containsKey(reqId);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zContainsKey;
                }

       public PendingAction remove(String reqId) {
                    PendingAction action;
                    synchronized (this.mLock) {
                        try {
                   action = this.mRequestQueue.get(reqId);
                   this.mRequestQueue.remove(reqId);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return action;
                }

       public String generateRequestId() {
           return Util.randomString(6);
                }

       public boolean isBound() {
                    return this.mService != null;
                }
            }
