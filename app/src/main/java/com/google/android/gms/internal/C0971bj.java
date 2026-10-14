            package com.google.android.gms.internal;

            import android.content.Context;
            import android.media.MediaPlayer;
            import android.text.TextUtils;
            import android.view.MotionEvent;
            import android.widget.FrameLayout;
            import android.widget.MediaController;
            import android.widget.VideoView;
            import co.vine.android.service.VineService;
            import java.lang.ref.WeakReference;
            import java.util.HashMap;
            import java.util.Map;
            
            
            
            public final class C0971bj extends FrameLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

                
                private final C1005cq f1454fG;

                
                private final MediaController f1455gb;

                
                private final a f1456gc;

                
                private final VideoView f1457gd;

                
                private long f1458ge;

                
                private String f1459gf;

                
                private static final class a {

                    
                    private final Runnable f1460el;

                    
                    private volatile boolean f1461gg = false;

                    
                    class AnonymousClass1 implements Runnable {

                        
                        private final WeakReference<C0971bj> f1462gh;

                        
                        final  C0971bj f1463gi;

                        AnonymousClass1(C0971bj c0971bj) {
                            this.f1463gi = c0971bj;
                            this.f1462gh = new WeakReference<>(this.f1463gi);
                        }

                        @Override 
                        public void run() {
                            C0971bj c0971bj = this.f1462gh.get();
                            if (a.m1334a(a.this) || c0971bj == null) {
                                return;
                            }
                            c0971bj.m1330aa();
                            a.this.m1335ab();
                        }
                    }

                    public a(C0971bj c0971bj) {
                        this.f1460el = new AnonymousClass1(c0971bj);
                    }

                    
                    static  boolean m1334a(a aVar) {
                        return aVar.f1461gg;
                    }

                    
                    public void m1335ab() {
                        C1001cm.f1595hO.postDelayed(this.f1460el, 250L);
                    }

                    public void cancel() {
                        this.f1461gg = true;
                        C1001cm.f1595hO.removeCallbacks(this.f1460el);
                    }
                }

                public C0971bj(Context context, C1005cq c1005cq) {
                    super(context);
                    this.f1454fG = c1005cq;
                    this.f1457gd = new VideoView(context);
                    addView(this.f1457gd, new FrameLayout.LayoutParams(-1, -1, 17));
                    this.f1455gb = new MediaController(context);
                    this.f1456gc = new a(this);
                    this.f1456gc.m1335ab();
                    this.f1457gd.setOnCompletionListener(this);
                    this.f1457gd.setOnPreparedListener(this);
                    this.f1457gd.setOnErrorListener(this);
                }

                
                private static void m1325a(C1005cq c1005cq, String str) {
                    m1328a(c1005cq, str, new HashMap(1));
                }

                
                public static void m1326a(C1005cq c1005cq, String str, String str2) {
                    boolean z = str2 == null;
                    HashMap map = new HashMap(z ? 2 : 3);
                    map.put("what", str);
                    if (!z) {
                        map.put("extra", str2);
                    }
                    m1328a(c1005cq, "error", map);
                }

                
                private static void m1327a(C1005cq c1005cq, String str, String str2, String str3) {
                    HashMap map = new HashMap(2);
                    map.put(str2, str3);
                    m1328a(c1005cq, str, map);
                }

                
                private static void m1328a(C1005cq c1005cq, String str, Map<String, String> map) {
                    map.put("event", str);
                    c1005cq.m1468a("onVideoEvent", map);
                }

                
                public void m1329Z() {
                    if (TextUtils.isEmpty(this.f1459gf)) {
                        m1326a(this.f1454fG, "no_src", (String) null);
                    } else {
                        this.f1457gd.setVideoPath(this.f1459gf);
                    }
                }

                
                public void m1330aa() {
                    long currentPosition = this.f1457gd.getCurrentPosition();
                    if (this.f1458ge != currentPosition) {
                        m1327a(this.f1454fG, "timeupdate", VineService.EXTRA_ANCHOR, String.valueOf(currentPosition / 1000.0f));
                        this.f1458ge = currentPosition;
                    }
                }

                
                public void m1331b(MotionEvent motionEvent) {
                    this.f1457gd.dispatchTouchEvent(motionEvent);
                }

                public void destroy() {
                    this.f1456gc.cancel();
                    this.f1457gd.stopPlayback();
                }

                
                public void m1332f(boolean z) {
                    if (z) {
                        this.f1457gd.setMediaController(this.f1455gb);
                    } else {
                        this.f1455gb.hide();
                        this.f1457gd.setMediaController(null);
                    }
                }

                
                public void m1333i(String str) {
                    this.f1459gf = str;
                }

                @Override 
                public void onCompletion(MediaPlayer mediaPlayer) {
                    m1325a(this.f1454fG, "ended");
                }

                @Override 
                public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                    m1326a(this.f1454fG, String.valueOf(what), String.valueOf(extra));
                    return true;
                }

                @Override 
                public void onPrepared(MediaPlayer mediaPlayer) {
                    m1327a(this.f1454fG, "canplaythrough", "duration", String.valueOf(this.f1457gd.getDuration() / 1000.0f));
                }

                public void pause() {
                    this.f1457gd.pause();
                }

                public void play() {
                    this.f1457gd.start();
                }

                public void seekTo(int timeInMilliseconds) {
                    this.f1457gd.seekTo(timeInMilliseconds);
                }
            }
