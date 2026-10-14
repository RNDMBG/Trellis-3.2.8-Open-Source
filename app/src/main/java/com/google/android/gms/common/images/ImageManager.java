            package com.google.android.gms.common.images;

            import android.app.ActivityManager;
            import android.content.ComponentCallbacks2;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Configuration;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.ParcelFileDescriptor;
            import android.os.ResultReceiver;
            import android.util.Log;
            import android.widget.ImageView;
            import co.vine.android.HomeTabActivity;
            import com.google.android.gms.internal.C1017db;
            import com.google.android.gms.internal.C1032dq;
            import com.google.android.gms.internal.C1053ek;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Map;
            import java.util.concurrent.CountDownLatch;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class ImageManager {

                /* JADX INFO: renamed from: jC */
                private static final Object f975jC = new Object();

                /* JADX INFO: renamed from: jD */
                private static HashSet<Uri> f976jD = new HashSet<>();

                /* JADX INFO: renamed from: jE */
                private static ImageManager f977jE;

                /* JADX INFO: renamed from: jF */
                private static ImageManager f978jF;

                /* JADX INFO: renamed from: jH */
                private final C0895b f980jH;

                /* JADX INFO: renamed from: jI */
                private final Map<C0900a, ImageReceiver> f981jI;

                /* JADX INFO: renamed from: jJ */
                private final Map<Uri, ImageReceiver> f982jJ;
                private final Context mContext;
                private final Handler mHandler = new Handler(Looper.getMainLooper());

                /* JADX INFO: renamed from: jG */
                private final ExecutorService f979jG = Executors.newFixedThreadPool(4);

                private final class ImageReceiver extends ResultReceiver {

                    /* JADX INFO: renamed from: jK */
                    private final ArrayList<C0900a> f983jK;

                    /* JADX INFO: renamed from: jL */
                    boolean f984jL;
                    private final Uri mUri;

                    ImageReceiver(Uri uri) {
                        super(new Handler(Looper.getMainLooper()));
                        this.f984jL = false;
                        this.mUri = uri;
                        this.f983jK = new ArrayList<>();
                    }

                    /* JADX INFO: renamed from: a */
                    static /* synthetic */ ArrayList m1075a(ImageReceiver imageReceiver) {
                        return imageReceiver.f983jK;
                    }

                    /* JADX INFO: renamed from: aR */
                    public void m1076aR() {
                        Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
                        intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
                        intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
                        intent.putExtra("com.google.android.gms.extras.priority", 3);
                        ImageManager.m1060a(ImageManager.this).sendBroadcast(intent);
                    }

                    /* JADX INFO: renamed from: c */
                    public void m1077c(C0900a c0900a) {
                        C1017db.m1523a(!this.f984jL, "Cannot add an ImageRequest when mHandlingRequests is true");
                        C1017db.m1526w("ImageReceiver.addImageRequest() must be called in the main thread");
                        this.f983jK.add(c0900a);
                    }

                    /* JADX INFO: renamed from: d */
                    public void m1078d(C0900a c0900a) {
                        C1017db.m1523a(!this.f984jL, "Cannot remove an ImageRequest when mHandlingRequests is true");
                        C1017db.m1526w("ImageReceiver.removeImageRequest() must be called in the main thread");
                        this.f983jK.remove(c0900a);
                    }

                    @Override // android.os.ResultReceiver
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        ImageManager.m1071d(ImageManager.this).execute(ImageManager.this.new RunnableC0896c(this.mUri, (ParcelFileDescriptor) resultData.getParcelable("com.google.android.gms.extra.fileDescriptor")));
                    }
                }

                public interface OnImageLoadedListener {
                    void onImageLoaded(Uri uri, Drawable drawable);
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$a */
                private static final class C0894a {
                    /* JADX INFO: renamed from: a */
                    static int m1079a(ActivityManager activityManager) {
                        return activityManager.getLargeMemoryClass();
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$b */
                private static final class C0895b extends C1032dq<C0900a.a, Bitmap> {
                    public C0895b(Context context) {
                        super(m1080q(context));
                    }

                    /* JADX INFO: renamed from: q */
                    private static int m1080q(Context context) {
                        ActivityManager activityManager = (ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY);
                        return (int) (((((context.getApplicationInfo().flags & 1048576) != 0) && C1053ek.m1736bJ()) ? C0894a.m1079a(activityManager) : activityManager.getMemoryClass()) * 1048576 * 0.33f);
                    }

                    /* JADX INFO: renamed from: a */
                    protected int m1081a(C0900a.a aVar, Bitmap bitmap) {
                        return bitmap.getHeight() * bitmap.getRowBytes();
                    }

                    /* JADX INFO: renamed from: a */
                    protected void m1082a(boolean z, C0900a.a aVar, Bitmap bitmap, Bitmap bitmap2) {
                        super.entryRemoved(z, aVar, bitmap, bitmap2);
                    }

                    @Override // com.google.android.gms.internal.C1032dq
                    protected /* synthetic */ void entryRemoved(boolean x0, C0900a.a aVar, Bitmap bitmap, Bitmap bitmap2) {
                        m1082a(x0, aVar, bitmap, bitmap2);
                    }

                    @Override // com.google.android.gms.internal.C1032dq
                    protected /* synthetic */ int sizeOf(C0900a.a aVar, Bitmap bitmap) {
                        return m1081a(aVar, bitmap);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$c */
                private final class RunnableC0896c implements Runnable {

                    /* JADX INFO: renamed from: jN */
                    private final ParcelFileDescriptor f987jN;
                    private final Uri mUri;

                    public RunnableC0896c(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
                        this.mUri = uri;
                        this.f987jN = parcelFileDescriptor;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        C1017db.m1527x("LoadBitmapFromDiskRunnable can't be executed in the main thread");
                        boolean z = false;
                        Bitmap bitmapDecodeFileDescriptor = null;
                        if (this.f987jN != null) {
                            try {
                                bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(this.f987jN.getFileDescriptor());
                            } catch (OutOfMemoryError e) {
                                Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                                z = true;
                            }
                            try {
                                this.f987jN.close();
                            } catch (IOException e2) {
                                Log.e("ImageManager", "closed failed", e2);
                            }
                        }
                        CountDownLatch countDownLatch = new CountDownLatch(1);
                        ImageManager.m1072e(ImageManager.this).post(ImageManager.this.new RunnableC0899f(this.mUri, bitmapDecodeFileDescriptor, z, countDownLatch));
                        try {
                            countDownLatch.await();
                        } catch (InterruptedException e3) {
                            Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$d */
                private final class RunnableC0897d implements Runnable {

                    /* JADX INFO: renamed from: jO */
                    private final C0900a f989jO;

                    public RunnableC0897d(C0900a c0900a) {
                        this.f989jO = c0900a;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        C1017db.m1526w("LoadImageRunnable must be executed on the main thread");
                        ImageManager.m1064a(ImageManager.this, this.f989jO);
                        C0900a.a aVar = this.f989jO.f995jS;
                        if (aVar.uri == null) {
                            this.f989jO.m1092b(ImageManager.m1060a(ImageManager.this), true);
                            return;
                        }
                        Bitmap bitmapM1061a = ImageManager.m1061a(ImageManager.this, aVar);
                        if (bitmapM1061a != null) {
                            this.f989jO.m1089a(ImageManager.m1060a(ImageManager.this), bitmapM1061a, true);
                            return;
                        }
                        this.f989jO.m1093r(ImageManager.m1060a(ImageManager.this));
                        ImageReceiver imageReceiver = (ImageReceiver) ImageManager.m1068b(ImageManager.this).get(aVar.uri);
                        if (imageReceiver == null) {
                            imageReceiver = ImageManager.this.new ImageReceiver(aVar.uri);
                            ImageManager.m1068b(ImageManager.this).put(aVar.uri, imageReceiver);
                        }
                        imageReceiver.m1077c(this.f989jO);
                        if (this.f989jO.f998jV != 1) {
                            ImageManager.m1070c(ImageManager.this).put(this.f989jO, imageReceiver);
                        }
                        synchronized (ImageManager.m1066aP()) {
                            try {
                                if (!ImageManager.m1067aQ().contains(aVar.uri)) {
                                    ImageManager.m1067aQ().add(aVar.uri);
                                    imageReceiver.m1076aR();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$e */
                private static final class ComponentCallbacks2C0898e implements ComponentCallbacks2 {

                    /* JADX INFO: renamed from: jH */
                    private final C0895b f990jH;

                    public ComponentCallbacks2C0898e(C0895b c0895b) {
                        this.f990jH = c0895b;
                    }

                    @Override // android.content.ComponentCallbacks
                    public void onConfigurationChanged(Configuration newConfig) {
                    }

                    @Override // android.content.ComponentCallbacks
                    public void onLowMemory() {
                        this.f990jH.evictAll();
                    }

                    @Override // android.content.ComponentCallbacks2
                    public void onTrimMemory(int level) {
                        if (level >= 60) {
                            this.f990jH.evictAll();
                        } else if (level >= 20) {
                            this.f990jH.trimToSize(this.f990jH.size() / 2);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.images.ImageManager$f */
                private final class RunnableC0899f implements Runnable {

                    /* JADX INFO: renamed from: jP */
                    private final Bitmap f992jP;

                    /* JADX INFO: renamed from: jQ */
                    private final CountDownLatch f993jQ;

                    /* JADX INFO: renamed from: jR */
                    private boolean f994jR;
                    private final Uri mUri;

                    public RunnableC0899f(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
                        this.mUri = uri;
                        this.f992jP = bitmap;
                        this.f994jR = z;
                        this.f993jQ = countDownLatch;
                    }

                    /* JADX INFO: renamed from: a */
                    private void m1083a(ImageReceiver imageReceiver, boolean z) {
                        imageReceiver.f984jL = true;
                        ArrayList arrayListM1075a = ImageReceiver.m1075a(imageReceiver);
                        int size = arrayListM1075a.size();
                        for (int i = 0; i < size; i++) {
                            C0900a c0900a = (C0900a) arrayListM1075a.get(i);
                            if (z) {
                                c0900a.m1089a(ImageManager.m1060a(ImageManager.this), this.f992jP, false);
                            } else {
                                c0900a.m1092b(ImageManager.m1060a(ImageManager.this), false);
                            }
                            if (c0900a.f998jV != 1) {
                                ImageManager.m1070c(ImageManager.this).remove(c0900a);
                            }
                        }
                        imageReceiver.f984jL = false;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        C1017db.m1526w("OnBitmapLoadedRunnable must be executed in the main thread");
                        boolean z = this.f992jP != null;
                        if (ImageManager.m1073f(ImageManager.this) != null) {
                            if (this.f994jR) {
                                ImageManager.m1073f(ImageManager.this).evictAll();
                                System.gc();
                                this.f994jR = false;
                                ImageManager.m1072e(ImageManager.this).post(this);
                                return;
                            }
                            if (z) {
                                ImageManager.m1073f(ImageManager.this).put(new C0900a.a(this.mUri), this.f992jP);
                            }
                        }
                        ImageReceiver imageReceiver = (ImageReceiver) ImageManager.m1068b(ImageManager.this).remove(this.mUri);
                        if (imageReceiver != null) {
                            m1083a(imageReceiver, z);
                        }
                        this.f993jQ.countDown();
                        synchronized (ImageManager.m1066aP()) {
                            try {
                                ImageManager.m1067aQ().remove(this.mUri);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                private ImageManager(Context context, boolean withMemoryCache) {
                    this.mContext = context.getApplicationContext();
                    if (withMemoryCache) {
                        this.f980jH = new C0895b(this.mContext);
                        if (C1053ek.m1739bM()) {
                            m1065aO();
                        }
                    } else {
                        this.f980jH = null;
                    }
                    this.f981jI = new HashMap();
                    this.f982jJ = new HashMap();
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Context m1060a(ImageManager imageManager) {
                    return imageManager.mContext;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Bitmap m1061a(ImageManager imageManager, C0900a.a aVar) {
                    return imageManager.m1062a(aVar);
                }

                /* JADX INFO: renamed from: a */
                private Bitmap m1062a(C0900a.a aVar) {
                    if (this.f980jH == null) {
                        return null;
                    }
                    return this.f980jH.get(aVar);
                }

                /* JADX INFO: renamed from: a */
                public static ImageManager m1063a(Context context, boolean z) {
                    if (z) {
                        if (f978jF == null) {
                            f978jF = new ImageManager(context, true);
                        }
                        return f978jF;
                    }
                    if (f977jE == null) {
                        f977jE = new ImageManager(context, false);
                    }
                    return f977jE;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ boolean m1064a(ImageManager imageManager, C0900a c0900a) {
                    return imageManager.m1069b(c0900a);
                }

                /* JADX INFO: renamed from: aO */
                private void m1065aO() {
                    this.mContext.registerComponentCallbacks(new ComponentCallbacks2C0898e(this.f980jH));
                }

                /* JADX INFO: renamed from: aP */
                static /* synthetic */ Object m1066aP() {
                    return f975jC;
                }

                /* JADX INFO: renamed from: aQ */
                static /* synthetic */ HashSet m1067aQ() {
                    return f976jD;
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ Map m1068b(ImageManager imageManager) {
                    return imageManager.f982jJ;
                }

                /* JADX INFO: renamed from: b */
                private boolean m1069b(C0900a c0900a) {
                    ImageReceiver imageReceiver;
                    C1017db.m1526w("ImageManager.cleanupHashMaps() must be called in the main thread");
                    if (c0900a.f998jV != 1 && (imageReceiver = this.f981jI.get(c0900a)) != null) {
                        if (imageReceiver.f984jL) {
                            return false;
                        }
                        this.f981jI.remove(c0900a);
                        imageReceiver.m1078d(c0900a);
                        return true;
                    }
                    return true;
                }

                /* JADX INFO: renamed from: c */
                static /* synthetic */ Map m1070c(ImageManager imageManager) {
                    return imageManager.f981jI;
                }

                public static ImageManager create(Context context) {
                    return m1063a(context, false);
                }

                /* JADX INFO: renamed from: d */
                static /* synthetic */ ExecutorService m1071d(ImageManager imageManager) {
                    return imageManager.f979jG;
                }

                /* JADX INFO: renamed from: e */
                static /* synthetic */ Handler m1072e(ImageManager imageManager) {
                    return imageManager.mHandler;
                }

                /* JADX INFO: renamed from: f */
                static /* synthetic */ C0895b m1073f(ImageManager imageManager) {
                    return imageManager.f980jH;
                }

                /* JADX INFO: renamed from: a */
                public void m1074a(C0900a c0900a) {
                    C1017db.m1526w("ImageManager.loadImage() must be called in the main thread");
                    boolean zM1069b = m1069b(c0900a);
                    RunnableC0897d runnableC0897d = new RunnableC0897d(c0900a);
                    if (zM1069b) {
                        runnableC0897d.run();
                    } else {
                        this.mHandler.post(runnableC0897d);
                    }
                }

                public void loadImage(ImageView imageView, int resId) {
                    C0900a c0900a = new C0900a(resId);
                    c0900a.m1090a(imageView);
                    m1074a(c0900a);
                }

                public void loadImage(ImageView imageView, Uri uri) {
                    C0900a c0900a = new C0900a(uri);
                    c0900a.m1090a(imageView);
                    m1074a(c0900a);
                }

                public void loadImage(ImageView imageView, Uri uri, int defaultResId) {
                    C0900a c0900a = new C0900a(uri);
                    c0900a.m1094v(defaultResId);
                    c0900a.m1090a(imageView);
                    m1074a(c0900a);
                }

                public void loadImage(OnImageLoadedListener listener, Uri uri) {
                    C0900a c0900a = new C0900a(uri);
                    c0900a.m1091a(listener);
                    m1074a(c0900a);
                }

                public void loadImage(OnImageLoadedListener listener, Uri uri, int defaultResId) {
                    C0900a c0900a = new C0900a(uri);
                    c0900a.m1094v(defaultResId);
                    c0900a.m1091a(listener);
                    m1074a(c0900a);
                }
            }
