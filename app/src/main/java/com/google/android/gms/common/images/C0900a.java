            package com.google.android.gms.common.images;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.widget.ImageView;
            import android.widget.TextView;
            import com.google.android.gms.internal.C1014cz;
            import com.google.android.gms.internal.C1016da;
            import com.google.android.gms.internal.C1017db;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1053ek;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.images.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0900a {

                /* JADX INFO: renamed from: jS */
                final a f995jS;

                /* JADX INFO: renamed from: jT */
                private int f996jT;

                /* JADX INFO: renamed from: jU */
                private int f997jU;

                /* JADX INFO: renamed from: jV */
                int f998jV;

                /* JADX INFO: renamed from: jW */
                private int f999jW;

                /* JADX INFO: renamed from: jX */
                private WeakReference<ImageManager.OnImageLoadedListener> f1000jX;

                /* JADX INFO: renamed from: jY */
                private WeakReference<ImageView> f1001jY;

                /* JADX INFO: renamed from: jZ */
                private WeakReference<TextView> f1002jZ;

                /* JADX INFO: renamed from: ka */
                private int f1003ka;

                /* JADX INFO: renamed from: kb */
                private boolean f1004kb;

                /* JADX INFO: renamed from: kc */
                private boolean f1005kc;

                /* JADX INFO: renamed from: com.google.android.gms.common.images.a$a */
                public static final class a {
                    public final Uri uri;

                    public a(Uri uri) {
                        this.uri = uri;
                    }

                    public boolean equals(Object obj) {
                        if (obj instanceof a) {
                            return this == obj || ((a) obj).hashCode() == hashCode();
                        }
                        return false;
                    }

                    public int hashCode() {
                        return C1027dl.hashCode(this.uri);
                    }
                }

                public C0900a(int i) {
                    this.f996jT = 0;
                    this.f997jU = 0;
                    this.f1003ka = -1;
                    this.f1004kb = true;
                    this.f1005kc = false;
                    this.f995jS = new a(null);
                    this.f997jU = i;
                }

                public C0900a(Uri uri) {
                    this.f996jT = 0;
                    this.f997jU = 0;
                    this.f1003ka = -1;
                    this.f1004kb = true;
                    this.f1005kc = false;
                    this.f995jS = new a(uri);
                    this.f997jU = 0;
                }

                /* JADX INFO: renamed from: a */
                private C1014cz m1084a(Drawable drawable, Drawable drawable2) {
                    if (drawable == null) {
                        drawable = null;
                    } else if (drawable instanceof C1014cz) {
                        drawable = ((C1014cz) drawable).m1514aS();
                    }
                    return new C1014cz(drawable, drawable2);
                }

                /* JADX INFO: renamed from: a */
                private void m1085a(Drawable drawable, boolean z, boolean z2, boolean z3) {
                    ImageManager.OnImageLoadedListener onImageLoadedListener;
                    switch (this.f998jV) {
                        case 1:
                            if (!z2 && (onImageLoadedListener = this.f1000jX.get()) != null) {
                                onImageLoadedListener.onImageLoaded(this.f995jS.uri, drawable);
                                break;
                            }
                            break;
                        case 2:
                            ImageView imageView = this.f1001jY.get();
                            if (imageView != null) {
                                m1086a(imageView, drawable, z, z2, z3);
                            }
                            break;
                        case 3:
                            TextView textView = this.f1002jZ.get();
                            if (textView != null) {
                                m1087a(textView, this.f1003ka, drawable, z, z2);
                            }
                            break;
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1086a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
                    boolean z4 = (z2 || z3) ? false : true;
                    if (z4 && (imageView instanceof C1016da)) {
                        int iM1520aU = ((C1016da) imageView).m1520aU();
                        if (this.f997jU != 0 && iM1520aU == this.f997jU) {
                            return;
                        }
                    }
                    boolean zM1088a = m1088a(z, z2);
                    Drawable drawableM1084a = zM1088a ? m1084a(imageView.getDrawable(), drawable) : drawable;
                    imageView.setImageDrawable(drawableM1084a);
                    if (imageView instanceof C1016da) {
                        C1016da c1016da = (C1016da) imageView;
                        c1016da.m1521d(z3 ? this.f995jS.uri : null);
                        c1016da.m1522w(z4 ? this.f997jU : 0);
                    }
                    if (zM1088a) {
                        ((C1014cz) drawableM1084a).startTransition(250);
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1087a(TextView textView, int i, Drawable drawable, boolean z, boolean z2) {
                    boolean zM1088a = m1088a(z, z2);
                    Drawable[] compoundDrawablesRelative = C1053ek.m1741bO() ? textView.getCompoundDrawablesRelative() : textView.getCompoundDrawables();
                    Drawable drawableM1084a = zM1088a ? m1084a(compoundDrawablesRelative[i], drawable) : drawable;
                    Drawable drawable2 = i == 0 ? drawableM1084a : compoundDrawablesRelative[0];
                    Drawable drawable3 = i == 1 ? drawableM1084a : compoundDrawablesRelative[1];
                    Drawable drawable4 = i == 2 ? drawableM1084a : compoundDrawablesRelative[2];
                    Drawable drawable5 = i == 3 ? drawableM1084a : compoundDrawablesRelative[3];
                    if (C1053ek.m1741bO()) {
                        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
                    } else {
                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
                    }
                    if (zM1088a) {
                        ((C1014cz) drawableM1084a).startTransition(250);
                    }
                }

                /* JADX INFO: renamed from: a */
                private boolean m1088a(boolean z, boolean z2) {
                    return this.f1004kb && !z2 && (!z || this.f1005kc);
                }

                /* JADX INFO: renamed from: a */
                void m1089a(Context context, Bitmap bitmap, boolean z) {
                    C1017db.m1524c(bitmap);
                    m1085a(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
                }

                /* JADX INFO: renamed from: a */
                public void m1090a(ImageView imageView) {
                    C1017db.m1524c(imageView);
                    this.f1000jX = null;
                    this.f1001jY = new WeakReference<>(imageView);
                    this.f1002jZ = null;
                    this.f1003ka = -1;
                    this.f998jV = 2;
                    this.f999jW = imageView.hashCode();
                }

                /* JADX INFO: renamed from: a */
                public void m1091a(ImageManager.OnImageLoadedListener onImageLoadedListener) {
                    C1017db.m1524c(onImageLoadedListener);
                    this.f1000jX = new WeakReference<>(onImageLoadedListener);
                    this.f1001jY = null;
                    this.f1002jZ = null;
                    this.f1003ka = -1;
                    this.f998jV = 1;
                    this.f999jW = C1027dl.hashCode(onImageLoadedListener, this.f995jS);
                }

                /* JADX INFO: renamed from: b */
                void m1092b(Context context, boolean z) {
                    m1085a(this.f997jU != 0 ? context.getResources().getDrawable(this.f997jU) : null, z, false, false);
                }

                public boolean equals(Object obj) {
                    if (obj instanceof C0900a) {
                        return this == obj || ((C0900a) obj).hashCode() == hashCode();
                    }
                    return false;
                }

                public int hashCode() {
                    return this.f999jW;
                }

                /* JADX INFO: renamed from: r */
                void m1093r(Context context) {
                    m1085a(this.f996jT != 0 ? context.getResources().getDrawable(this.f996jT) : null, false, true, false);
                }

                /* JADX INFO: renamed from: v */
                public void m1094v(int i) {
                    this.f997jU = i;
                }
            }
