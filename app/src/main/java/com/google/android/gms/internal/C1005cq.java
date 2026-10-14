            package com.google.android.gms.internal;

            import android.content.ActivityNotFoundException;
            import android.content.Context;
            import android.content.Intent;
            import android.content.MutableContextWrapper;
            import android.net.Uri;
            import android.os.Build;
            import android.view.MotionEvent;
            import android.view.View;
            import android.webkit.DownloadListener;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import java.util.HashMap;
            import java.util.Map;
            import org.json.JSONException;
            
            
            
            public final class C1005cq extends WebView implements DownloadListener {

                
                private final Object f1600eJ;

                
                private C1126x f1601fg;

                
                private final C1110h f1602go;

                
                private final C1006cr f1603hT;

                
                private final MutableContextWrapper f1604hU;

                
                private final C1003co f1605hV;

                
                private BinderC0967bf f1606hW;

                
                private boolean f1607hX;

                
                private boolean f1608hY;

                private C1005cq(MutableContextWrapper mutableContextWrapper, C1126x c1126x, boolean z, boolean z2, C1110h c1110h, C1003co c1003co) {
                    super(mutableContextWrapper);
                    this.f1600eJ = new Object();
                    this.f1604hU = mutableContextWrapper;
                    this.f1601fg = c1126x;
                    this.f1607hX = z;
                    this.f1602go = c1110h;
                    this.f1605hV = c1003co;
                    setBackgroundColor(0);
                    WebSettings settings = getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setSavePassword(false);
                    C0997ci.m1414a(mutableContextWrapper, c1003co.f1596hP, settings);
                    if (Build.VERSION.SDK_INT >= 17) {
                        C0999ck.m1441a(getContext(), settings);
                    } else if (Build.VERSION.SDK_INT >= 11) {
                        C0998cj.m1435a(getContext(), settings);
                    }
                    setDownloadListener(this);
                    if (Build.VERSION.SDK_INT >= 11) {
                        this.f1603hT = new C1008ct(this, z2);
                    } else {
                        this.f1603hT = new C1006cr(this, z2);
                    }
                    setWebViewClient(this.f1603hT);
                    if (Build.VERSION.SDK_INT >= 14) {
                        setWebChromeClient(new C1009cu(this));
                    } else if (Build.VERSION.SDK_INT >= 11) {
                        setWebChromeClient(new C1007cs(this));
                    }
                    m1463aA();
                }

                
                public static C1005cq m1462a(Context context, C1126x c1126x, boolean z, boolean z2, C1110h c1110h, C1003co c1003co) {
                    return new C1005cq(new MutableContextWrapper(context), c1126x, z, z2, c1110h, c1003co);
                }

                
                private void m1463aA() {
                    synchronized (this.f1600eJ) {
                        try {
                            if (this.f1607hX || this.f1601fg.f2164ex) {
                                if (Build.VERSION.SDK_INT < 14) {
                                    C1002cn.m1454m("Disabling hardware acceleration on an overlay.");
                                    m1464aB();
                                } else {
                                    C1002cn.m1454m("Enabling hardware acceleration on an overlay.");
                                    m1465aC();
                                }
                            } else if (Build.VERSION.SDK_INT < 18) {
                                C1002cn.m1454m("Disabling hardware acceleration on an AdView.");
                                m1464aB();
                            } else {
                                C1002cn.m1454m("Enabling hardware acceleration on an AdView.");
                                m1465aC();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                private void m1464aB() {
                    synchronized (this.f1600eJ) {
                        try {
                            if (!this.f1608hY && Build.VERSION.SDK_INT >= 11) {
                                C0998cj.m1439c(this);
                            }
                            this.f1608hY = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                private void m1465aC() {
                    synchronized (this.f1600eJ) {
                        try {
                            if (this.f1608hY && Build.VERSION.SDK_INT >= 11) {
                                C0998cj.m1440d(this);
                            }
                            this.f1608hY = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                public void m1466a(Context context, C1126x c1126x) {
                    synchronized (this.f1600eJ) {
                        try {
                            this.f1604hU.setBaseContext(context);
                            this.f1606hW = null;
                            this.f1601fg = c1126x;
                            this.f1607hX = false;
                            C0997ci.m1426b(this);
                            loadUrl("about:blank");
                            this.f1603hT.reset();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                public void m1467a(BinderC0967bf binderC0967bf) {
                    synchronized (this.f1600eJ) {
                        try {
                            this.f1606hW = binderC0967bf;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                public void m1468a(String str, Map<String, ?> map) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("javascript:AFMA_ReceiveMessage('");
                    sb.append(str);
                    sb.append("'");
                    if (map != null) {
                        try {
                            String string = C0997ci.m1434l(map).toString();
                            sb.append(",");
                            sb.append(string);
                        } catch (JSONException e) {
                            C1002cn.m1458q("Could not convert AFMA event parameters to JSON.");
                            return;
                        }
                    }
                    sb.append(");");
                    C1002cn.m1457p("Dispatching AFMA event: " + ((Object) sb));
                    loadUrl(sb.toString());
                }

                
                public void m1469as() {
                    HashMap map = new HashMap(1);
                    map.put("version", this.f1605hV.f1596hP);
                    m1468a("onhide", map);
                }

                
                public void m1470at() {
                    HashMap map = new HashMap(1);
                    map.put("version", this.f1605hV.f1596hP);
                    m1468a("onshow", map);
                }

                
                public BinderC0967bf m1471au() {
                    BinderC0967bf binderC0967bf;
                    synchronized (this.f1600eJ) {
                        try {
                            binderC0967bf = this.f1606hW;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return binderC0967bf;
                }

                
                public C1126x m1472av() {
                    C1126x c1126x;
                    synchronized (this.f1600eJ) {
                        try {
                            c1126x = this.f1601fg;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return c1126x;
                }

                
                public C1006cr m1473aw() {
                    return this.f1603hT;
                }

                
                public C1110h m1474ax() {
                    return this.f1602go;
                }

                
                public C1003co m1475ay() {
                    return this.f1605hV;
                }

                
                public boolean m1476az() {
                    boolean z;
                    synchronized (this.f1600eJ) {
                        try {
                            z = this.f1607hX;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                
                public void m1477i(boolean z) {
                    synchronized (this.f1600eJ) {
                        try {
                            this.f1607hX = z;
                            m1463aA();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse(url), mimeType);
                        getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        C1002cn.m1454m("Couldn't find an Activity to view url/mimetype: " + url + " / " + mimeType);
                    }
                }

                @Override 
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    synchronized (this.f1600eJ) {
                        try {
                            if (isInEditMode() || this.f1607hX) {
                                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                                return;
                            }
                            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                            int size = View.MeasureSpec.getSize(widthMeasureSpec);
                            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
                            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
                            int i = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
                            int i2 = (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) ? size2 : Integer.MAX_VALUE;
                            if (this.f1601fg.widthPixels > i || this.f1601fg.heightPixels > i2) {
                                C1002cn.m1458q("Not enough space to show ad. Needs " + this.f1601fg.widthPixels + "x" + this.f1601fg.heightPixels + ", but only has " + size + "x" + size2);
                                if (getVisibility() != 8) {
                                    setVisibility(4);
                                }
                                setMeasuredDimension(0, 0);
                            } else {
                                if (getVisibility() != 8) {
                                    setVisibility(0);
                                }
                                setMeasuredDimension(this.f1601fg.widthPixels, this.f1601fg.heightPixels);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
                public boolean onTouchEvent(MotionEvent event) {
                    if (this.f1602go != null) {
                        this.f1602go.m2126a(event);
                    }
                    return super.onTouchEvent(event);
                }

                public void setContext(Context context) {
                    this.f1604hU.setBaseContext(context);
                }
            }
