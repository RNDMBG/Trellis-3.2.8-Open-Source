            package com.facebook.internal;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.support.v4.content.LocalBroadcastManager;
            import com.facebook.Session;
            import com.facebook.SessionState;
            
            
    public class SessionTracker {
                private final LocalBroadcastManager broadcastManager;
                private final Session.StatusCallback callback;
                private boolean isTracking;
                private final BroadcastReceiver receiver;
                private Session session;

        static  Session.StatusCallback access$100(SessionTracker x0) {
            return x0.callback;
                }

        static  Session access$200(SessionTracker x0) {
            return x0.session;
                }

        public SessionTracker(Context context, Session.StatusCallback callback) {
            this(context, callback, null);
                }

        SessionTracker(Context context, Session.StatusCallback callback, Session session) {
            this(context, callback, session, true);
                }

        public SessionTracker(Context context, Session.StatusCallback callback, Session session, boolean startTracking) {
            this.isTracking = false;
            this.callback = new CallbackWrapper(callback);
            this.session = session;
            this.receiver = new ActiveSessionBroadcastReceiver(this, null);
            this.broadcastManager = LocalBroadcastManager.getInstance(context);
            if (startTracking) {
                startTracking();
                    }
                }

        public Session getSession() {
                    return this.session == null ? Session.getActiveSession() : this.session;
                }

        public Session getOpenSession() {
            Session openSession = getSession();
           if (openSession == null || !openSession.isOpened()) {
                        return null;
                    }
           return openSession;
                }

       public void setSession(Session newSession) {
           if (newSession == null) {
               if (this.session != null) {
                   this.session.removeCallback(this.callback);
                   this.session = null;
                   addBroadcastReceiver();
                   if (getSession() != null) {
                       getSession().addCallback(this.callback);
                       return;
                            }
                   return;
                        }
               return;
                    }
           if (this.session == null) {
               Session activeSession = Session.getActiveSession();
               if (activeSession != null) {
                   activeSession.removeCallback(this.callback);
                        }
               this.broadcastManager.unregisterReceiver(this.receiver);
                    } else {
               this.session.removeCallback(this.callback);
                    }
           this.session = newSession;
           this.session.addCallback(this.callback);
                }

       public void startTracking() {
           if (!this.isTracking) {
               if (this.session == null) {
                   addBroadcastReceiver();
                        }
               if (getSession() != null) {
                   getSession().addCallback(this.callback);
                        }
               this.isTracking = true;
                    }
                }

       public void stopTracking() {
           if (this.isTracking) {
               Session session = getSession();
               if (session != null) {
                   session.removeCallback(this.callback);
                        }
               this.broadcastManager.unregisterReceiver(this.receiver);
               this.isTracking = false;
                    }
                }

       public boolean isTracking() {
           return this.isTracking;
                }

       public boolean isTrackingActiveSession() {
                    return this.session == null;
                }

       private void addBroadcastReceiver() {
           IntentFilter filter = new IntentFilter();
           filter.addAction(Session.ACTION_ACTIVE_SESSION_SET);
           filter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
           this.broadcastManager.registerReceiver(this.receiver, filter);
                }

       private class ActiveSessionBroadcastReceiver extends BroadcastReceiver {
           private ActiveSessionBroadcastReceiver() {
                    }

            ActiveSessionBroadcastReceiver(SessionTracker x0, Object x1) {
               this();
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
                        Session session;
               if (Session.ACTION_ACTIVE_SESSION_SET.equals(intent.getAction()) && (session = Session.getActiveSession()) != null) {
                   session.addCallback(SessionTracker.access$100(SessionTracker.this));
                        }
                    }
                }

       private class CallbackWrapper implements Session.StatusCallback {
                    private final Session.StatusCallback wrapped;

           public CallbackWrapper(Session.StatusCallback wrapped) {
               this.wrapped = wrapped;
                    }

                    @Override 
           public void call(Session session, SessionState state, Exception exception) {
               if (this.wrapped != null && SessionTracker.this.isTracking()) {
                   this.wrapped.call(session, state, exception);
                        }
               if (session == SessionTracker.access$200(SessionTracker.this) && state.isClosed()) {
                   SessionTracker.this.setSession(null);
                        }
                    }
                }
            }
