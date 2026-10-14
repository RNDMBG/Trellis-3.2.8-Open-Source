            package com.google.android.gms.gcm;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Messenger;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import java.io.IOException;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.TimeUnit;
            
            
            public class GoogleCloudMessaging {
                public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
                public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
                public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
                public static final String MESSAGE_TYPE_MESSAGE = "gcm";
                public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";

                
                static GoogleCloudMessaging f1131oo;

                
                private Context f1132ee;

                
                private PendingIntent f1133op;

                
                final BlockingQueue<Intent> f1134oq = new LinkedBlockingQueue();

                
                private Handler f1135or = new HandlerC09331(Looper.getMainLooper());

                
                private Messenger f1136os = new Messenger(this.f1135or);

                
                class HandlerC09331 extends Handler {
                    HandlerC09331(Looper looper) {
                        super(looper);
                    }

                    @Override 
                    public void handleMessage(Message msg) {
                        GoogleCloudMessaging.this.f1134oq.add((Intent) msg.obj);
                    }
                }

                
                private void m1231b(String... strArr) {
                    String strM1233c = m1233c(strArr);
                    Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                    intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                    intent.putExtra("google.messenger", this.f1136os);
                    m1235d(intent);
                    intent.putExtra("sender", strM1233c);
                    this.f1132ee.startService(intent);
                }

                
                private void m1232cj() {
                    Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
                    intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                    this.f1134oq.clear();
                    intent.putExtra("google.messenger", this.f1136os);
                    m1235d(intent);
                    this.f1132ee.startService(intent);
                }

                public static synchronized GoogleCloudMessaging getInstance(Context context) {
                    try {
                        if (f1131oo == null) {
                            f1131oo = new GoogleCloudMessaging();
                            f1131oo.f1132ee = context;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return f1131oo;
                }

                
                String m1233c(String... strArr) {
                    if (strArr == null || strArr.length == 0) {
                        throw new IllegalArgumentException("No senderIds");
                    }
                    StringBuilder sb = new StringBuilder(strArr[0]);
                    for (int i = 1; i < strArr.length; i++) {
                        sb.append(',').append(strArr[i]);
                    }
                    return sb.toString();
                }

                
                synchronized void m1234ck() {
                    try {
                        if (this.f1133op != null) {
                            this.f1133op.cancel();
                            this.f1133op = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                public void close() {
                    m1234ck();
                }

                
                synchronized void m1235d(Intent intent) {
                    try {
                        if (this.f1133op == null) {
                            this.f1133op = PendingIntent.getBroadcast(this.f1132ee, 0, new Intent(), 0);
                        }
                        intent.putExtra("app", this.f1133op);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                public String getMessageType(Intent intent) {
                    if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
                        return null;
                    }
                    String stringExtra = intent.getStringExtra("message_type");
                    return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
                }

                public String register(String... senderIds) throws IOException {
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        throw new IOException(ERROR_MAIN_THREAD);
                    }
                    this.f1134oq.clear();
                    m1231b(senderIds);
                    try {
                        Intent intentPoll = this.f1134oq.poll(5000L, TimeUnit.MILLISECONDS);
                        if (intentPoll == null) {
                            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
                        }
                        String stringExtra = intentPoll.getStringExtra("registration_id");
                        if (stringExtra != null) {
                            return stringExtra;
                        }
                        intentPoll.getStringExtra("error");
                        String stringExtra2 = intentPoll.getStringExtra("error");
                        if (stringExtra2 != null) {
                            throw new IOException(stringExtra2);
                        }
                        throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
                    } catch (InterruptedException e) {
                        throw new IOException(e.getMessage());
                    }
                }

                public void send(String to, String msgId, long timeToLive, Bundle data) throws IOException {
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        throw new IOException(ERROR_MAIN_THREAD);
                    }
                    if (to == null) {
                        throw new IllegalArgumentException("Missing 'to'");
                    }
                    Intent intent = new Intent("com.google.android.gcm.intent.SEND");
                    intent.putExtras(data);
                    m1235d(intent);
                    intent.putExtra("google.to", to);
                    intent.putExtra("google.message_id", msgId);
                    intent.putExtra("google.ttl", Long.toString(timeToLive));
                    this.f1132ee.sendOrderedBroadcast(intent, null);
                }

                public void send(String to, String msgId, Bundle data) throws IOException {
                    send(to, msgId, -1L, data);
                }

                public void unregister() throws IOException {
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        throw new IOException(ERROR_MAIN_THREAD);
                    }
                    m1232cj();
                    try {
                        Intent intentPoll = this.f1134oq.poll(5000L, TimeUnit.MILLISECONDS);
                        if (intentPoll == null) {
                            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
                        }
                        if (intentPoll.getStringExtra("unregistered") != null) {
                            return;
                        }
                        String stringExtra = intentPoll.getStringExtra("error");
                        if (stringExtra == null) {
                            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
                        }
                        throw new IOException(stringExtra);
                    } catch (InterruptedException e) {
                        throw new IOException(e.getMessage());
                    }
                }
            }
