            package com.google.android.gms.internal;
            
            
            
            public final class BinderC0952ar extends InterfaceC0959ay.a {

                
                private final Object f1174eJ = new Object();

                
                private C0954at.a f1175fb;

                
                private InterfaceC0951aq f1176fc;

                
                public void m1261a(InterfaceC0951aq interfaceC0951aq) {
                    synchronized (this.f1174eJ) {
                        try {
                            this.f1176fc = interfaceC0951aq;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                public void m1262a(C0954at.a aVar) {
                    synchronized (this.f1174eJ) {
                        try {
                            this.f1175fb = aVar;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onAdClosed() {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1176fc != null) {
                                this.f1176fc.mo1257E();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onAdFailedToLoad(int error) {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1175fb != null) {
                                this.f1175fb.mo1275d(error == 3 ? 1 : 2);
                                this.f1175fb = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onAdLeftApplication() {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1176fc != null) {
                                this.f1176fc.mo1258F();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onAdLoaded() {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1175fb != null) {
                                this.f1175fb.mo1275d(0);
                                this.f1175fb = null;
                            } else {
                                if (this.f1176fc != null) {
                                    this.f1176fc.mo1260H();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onAdOpened() {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1176fc != null) {
                                this.f1176fc.mo1259G();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                
                public void mo1263y() {
                    synchronized (this.f1174eJ) {
                        try {
                            if (this.f1176fc != null) {
                                this.f1176fc.mo1256D();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
