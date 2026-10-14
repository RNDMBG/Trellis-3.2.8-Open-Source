            package com.facebook.internal;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.support.v4.content.LocalBroadcastManager;
            import com.facebook.Session;
            import com.facebook.SessionState;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class SessionTracker {
                private final LocalBroadcastManager broadcastManager;
                private final Session.StatusCallback callback;
                private boolean isTracking;
                private final BroadcastReceiver receiver;
                private Session session;

/* 31 */        static /* synthetic */ Session.StatusCallback access$100(SessionTracker x0) {
/* 32 */            return x0.callback;
                }

/* 31 */        static /* synthetic */ Session access$200(SessionTracker x0) {
/* 32 */            return x0.session;
                }

/* 47 */        public SessionTracker(Context context, Session.StatusCallback callback) {
/* 48 */            this(context, callback, null);
                }

/* 59 */        SessionTracker(Context context, Session.StatusCallback callback, Session session) {
/* 60 */            this(context, callback, session, true);
                }

/* 71 */        public SessionTracker(Context context, Session.StatusCallback callback, Session session, boolean startTracking) {
/* 38 */            this.isTracking = false;
/* 73 */            this.callback = new CallbackWrapper(callback);
/* 74 */            this.session = session;
/* 75 */            this.receiver = new ActiveSessionBroadcastReceiver(this, null);
/* 76 */            this.broadcastManager = LocalBroadcastManager.getInstance(context);
/* 78 */            if (startTracking) {
/* 79 */                startTracking();
                    }
                }

/* 88 */        public Session getSession() {
                    return this.session == null ? Session.getActiveSession() : this.session;
                }

/* 98 */        public Session getOpenSession() {
/* 99 */            Session openSession = getSession();
/* 100 */           if (openSession == null || !openSession.isOpened()) {
                        return null;
                    }
/* 103 */           return openSession;
                }

/* 111 */       public void setSession(Session newSession) {
/* 112 */           if (newSession == null) {
/* 113 */               if (this.session != null) {
/* 116 */                   this.session.removeCallback(this.callback);
/* 117 */                   this.session = null;
/* 118 */                   addBroadcastReceiver();
/* 119 */                   if (getSession() != null) {
/* 120 */                       getSession().addCallback(this.callback);
/* 141 */                       return;
                            }
/* 141 */                   return;
                        }
/* 141 */               return;
                    }
/* 124 */           if (this.session == null) {
/* 127 */               Session activeSession = Session.getActiveSession();
/* 128 */               if (activeSession != null) {
/* 129 */                   activeSession.removeCallback(this.callback);
                        }
/* 131 */               this.broadcastManager.unregisterReceiver(this.receiver);
                    } else {
/* 136 */               this.session.removeCallback(this.callback);
                    }
/* 138 */           this.session = newSession;
/* 139 */           this.session.addCallback(this.callback);
                }

/* 146 */       public void startTracking() {
/* 147 */           if (!this.isTracking) {
/* 150 */               if (this.session == null) {
/* 151 */                   addBroadcastReceiver();
                        }
/* 154 */               if (getSession() != null) {
/* 155 */                   getSession().addCallback(this.callback);
                        }
/* 157 */               this.isTracking = true;
                    }
                }

/* 164 */       public void stopTracking() {
/* 165 */           if (this.isTracking) {
/* 168 */               Session session = getSession();
/* 169 */               if (session != null) {
/* 170 */                   session.removeCallback(this.callback);
                        }
/* 172 */               this.broadcastManager.unregisterReceiver(this.receiver);
/* 173 */               this.isTracking = false;
                    }
                }

/* 181 */       public boolean isTracking() {
/* 182 */           return this.isTracking;
                }

/* 190 */       public boolean isTrackingActiveSession() {
                    return this.session == null;
                }

/* 194 */       private void addBroadcastReceiver() {
/* 195 */           IntentFilter filter = new IntentFilter();
/* 196 */           filter.addAction(Session.ACTION_ACTIVE_SESSION_SET);
/* 197 */           filter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
/* 201 */           this.broadcastManager.registerReceiver(this.receiver, filter);
                }

/* 206 */       private class ActiveSessionBroadcastReceiver extends BroadcastReceiver {
/* 207 */           private ActiveSessionBroadcastReceiver() {
                    }

/* 207 */           /* synthetic */ ActiveSessionBroadcastReceiver(SessionTracker x0, Object x1) {
/* 208 */               this();
                    }

                    @Override // android.content.BroadcastReceiver
/* 210 */           public void onReceive(Context context, Intent intent) {
                        Session session;
/* 211 */               if (Session.ACTION_ACTIVE_SESSION_SET.equals(intent.getAction()) && (session = Session.getActiveSession()) != null) {
/* 214 */                   session.addCallback(SessionTracker.access$100(SessionTracker.this));
                        }
                    }
                }

/* 221 */       private class CallbackWrapper implements Session.StatusCallback {
                    private final Session.StatusCallback wrapped;

/* 222 */           public CallbackWrapper(Session.StatusCallback wrapped) {
/* 224 */               this.wrapped = wrapped;
                    }

                    @Override // com.facebook.Session.StatusCallback
/* 228 */           public void call(Session session, SessionState state, Exception exception) {
/* 229 */               if (this.wrapped != null && SessionTracker.this.isTracking()) {
/* 230 */                   this.wrapped.call(session, state, exception);
                        }
/* 234 */               if (session == SessionTracker.access$200(SessionTracker.this) && state.isClosed()) {
/* 235 */                   SessionTracker.this.setSession(null);
                        }
                    }
                }
            }
