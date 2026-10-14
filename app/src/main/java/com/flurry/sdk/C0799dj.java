            package com.flurry.sdk;

            import java.util.Timer;
            import java.util.TimerTask;
            
            
            
    class C0799dj {

                
                private Timer f681a;

                
                private a f682b;

                
                private b f683c;

                
                public interface b {
                    
                    void mo695q();
                }

                
        class a extends TimerTask {

                    
                    private b f685b;

            a(b bVar) {
                this.f685b = bVar;
                    }

                    @Override 
            public void run() {
                C0799dj.this.m717a();
                if (this.f685b != null) {
                    this.f685b.mo695q();
                        }
                    }
                }

        C0799dj(b bVar) {
            this.f683c = bVar;
                }

                
        public synchronized void m718a(long j) {
                    try {
                        if (m719b()) {
                    m717a();
                        }
                this.f681a = new Timer("FlurrySessionTimer");
                this.f682b = new a(this.f683c);
                this.f681a.schedule(this.f682b, j);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m717a() {
                    try {
                        if (this.f681a != null) {
                    this.f681a.cancel();
                    this.f681a = null;
                        }
                this.f682b = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public boolean m719b() {
                    return this.f681a != null;
                }
            }
