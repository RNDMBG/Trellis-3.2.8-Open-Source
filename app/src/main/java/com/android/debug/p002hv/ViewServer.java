            package com.android.debug.p002hv;

            import android.app.Activity;
            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.os.Build;
            import android.text.TextUtils;
            import android.util.Log;
            import android.view.View;
            import android.view.ViewDebug;
            import java.io.BufferedReader;
            import java.io.BufferedWriter;
            import java.io.IOException;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.lang.reflect.Method;
            import java.net.InetAddress;
            import java.net.ServerSocket;
            import java.net.Socket;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            import java.util.concurrent.CopyOnWriteArrayList;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import java.util.concurrent.locks.ReentrantReadWriteLock;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 107 */   public class ViewServer implements Runnable {
                private static final String BUILD_TYPE_USER = "user";
                private static final String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
                private static final String COMMAND_SERVER_VERSION = "SERVER";
                private static final String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
                private static final String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
                private static final String COMMAND_WINDOW_MANAGER_LIST = "LIST";
                private static final String LOG_TAG = "ViewServer";
                private static final String VALUE_PROTOCOL_VERSION = "4";
                private static final String VALUE_SERVER_VERSION = "4";
                private static final int VIEW_SERVER_DEFAULT_PORT = 4939;
                private static final int VIEW_SERVER_MAX_CONNECTIONS = 10;
                private static ViewServer sServer;
                private final ReentrantReadWriteLock mFocusLock;
                private View mFocusedWindow;
                private final List<WindowListener> mListeners;
                private final int mPort;
                private ServerSocket mServer;
                private Thread mThread;
                private ExecutorService mThreadPool;
                private final HashMap<View, String> mWindows;
                private final ReentrantReadWriteLock mWindowsLock;

                private interface WindowListener {
                    void focusChanged();

                    void windowsChanged();
                }

/* 108 */       /* synthetic */ ViewServer(Object x0) {
/* 109 */           this();
                }

/* 108 */       static /* synthetic */ boolean access$200(Socket x0, String x1) {
/* 109 */           return writeValue(x0, x1);
                }

/* 108 */       static /* synthetic */ ReentrantReadWriteLock access$300(ViewServer x0) {
/* 109 */           return x0.mWindowsLock;
                }

/* 108 */       static /* synthetic */ View access$400(ViewServer x0) {
/* 109 */           return x0.mFocusedWindow;
                }

/* 108 */       static /* synthetic */ HashMap<View, String> access$500(ViewServer x0) {
/* 109 */           return x0.mWindows;
                }

/* 108 */       static /* synthetic */ ReentrantReadWriteLock access$600(ViewServer x0) {
/* 109 */           return x0.mFocusLock;
                }

/* 108 */       static /* synthetic */ void access$700(ViewServer x0, WindowListener x1) {
/* 109 */           x0.addWindowListener(x1);
                }

/* 108 */       static /* synthetic */ void access$800(ViewServer x0, WindowListener x1) {
/* 109 */           x0.removeWindowListener(x1);
                }

/* 165 */       public static ViewServer get(Context context) {
/* 166 */           ApplicationInfo info = context.getApplicationInfo();
/* 167 */           if ("user".equals(Build.TYPE) && (info.flags & 2) != 0) {
/* 169 */               if (sServer == null) {
/* 170 */                   sServer = new ViewServer(VIEW_SERVER_DEFAULT_PORT);
                        }
/* 173 */               if (!sServer.isRunning()) {
                            try {
/* 175 */                       sServer.start();
                            } catch (IOException e) {
/* 177 */                       Log.d(LOG_TAG, "Error:", e);
                            }
                        }
                    } else {
/* 181 */               sServer = new NoopViewServer(null);
                    }
/* 184 */           return sServer;
                }

/* 186 */       private ViewServer() {
/* 141 */           this.mListeners = new CopyOnWriteArrayList();
/* 144 */           this.mWindows = new HashMap<>();
/* 145 */           this.mWindowsLock = new ReentrantReadWriteLock();
/* 148 */           this.mFocusLock = new ReentrantReadWriteLock();
/* 188 */           this.mPort = -1;
                }

/* 198 */       private ViewServer(int port) {
/* 141 */           this.mListeners = new CopyOnWriteArrayList();
/* 144 */           this.mWindows = new HashMap<>();
/* 145 */           this.mWindowsLock = new ReentrantReadWriteLock();
/* 148 */           this.mFocusLock = new ReentrantReadWriteLock();
/* 200 */           this.mPort = port;
                }

/* 213 */       public boolean start() throws IOException {
/* 214 */           if (this.mThread != null) {
/* 215 */               return false;
                    }
/* 218 */           this.mThread = new Thread(this, "Local View Server [port=" + this.mPort + "]");
/* 219 */           this.mThreadPool = Executors.newFixedThreadPool(10);
/* 220 */           this.mThread.start();
/* 222 */           return true;
                }

/* 235 */       public boolean stop() {
/* 236 */           if (this.mThread != null) {
/* 237 */               this.mThread.interrupt();
/* 238 */               if (this.mThreadPool != null) {
                            try {
/* 240 */                       this.mThreadPool.shutdownNow();
                            } catch (SecurityException e) {
/* 242 */                       Log.w(LOG_TAG, "Could not stop all view server threads");
                            }
                        }
/* 246 */               this.mThreadPool = null;
/* 247 */               this.mThread = null;
                        try {
/* 250 */                   this.mServer.close();
/* 251 */                   this.mServer = null;
/* 252 */                   return true;
                        } catch (IOException e2) {
/* 254 */                   Log.w(LOG_TAG, "Could not close the view server");
                        }
                    }
/* 258 */           this.mWindowsLock.writeLock().lock();
                    try {
/* 260 */               this.mWindows.clear();
/* 262 */               this.mWindowsLock.writeLock().unlock();
/* 265 */               this.mFocusLock.writeLock().lock();
                        try {
/* 267 */                   this.mFocusedWindow = null;
/* 269 */                   this.mFocusLock.writeLock().unlock();
/* 272 */                   return false;
                        } catch (Throwable th) {
                            this.mFocusLock.writeLock().unlock();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th2;
                    }
                }

/* 284 */       public boolean isRunning() {
                    return this.mThread != null && this.mThread.isAlive();
                }

/* 296 */       public void addWindow(Activity activity) {
                    String name;
/* 297 */           String name2 = activity.getTitle().toString();
/* 298 */           if (TextUtils.isEmpty(name2)) {
/* 299 */               name = activity.getClass().getCanonicalName() + "/0x" + System.identityHashCode(activity);
                    } else {
/* 302 */               name = name2 + "(" + activity.getClass().getCanonicalName() + ")";
                    }
/* 304 */           addWindow(activity.getWindow().getDecorView(), name);
                }

/* 315 */       public void removeWindow(Activity activity) {
/* 316 */           removeWindow(activity.getWindow().getDecorView());
                }

/* 327 */       public void addWindow(View view, String name) {
/* 328 */           this.mWindowsLock.writeLock().lock();
                    try {
/* 330 */               this.mWindows.put(view.getRootView(), name);
/* 332 */               this.mWindowsLock.writeLock().unlock();
/* 334 */               fireWindowsChangedEvent();
                    } catch (Throwable th) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th;
                    }
                }

/* 344 */       public void removeWindow(View view) {
/* 345 */           this.mWindowsLock.writeLock().lock();
                    try {
/* 347 */               this.mWindows.remove(view.getRootView());
/* 349 */               this.mWindowsLock.writeLock().unlock();
/* 351 */               fireWindowsChangedEvent();
                    } catch (Throwable th) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th;
                    }
                }

/* 360 */       public void setFocusedWindow(Activity activity) {
/* 361 */           setFocusedWindow(activity.getWindow().getDecorView());
                }

/* 370 */       public void setFocusedWindow(View view) {
                    View rootView;
/* 371 */           this.mFocusLock.writeLock().lock();
/* 373 */           if (view == null) {
                        rootView = null;
                    } else {
                        try {
/* 373 */                   rootView = view.getRootView();
                        } catch (Throwable th) {
                            this.mFocusLock.writeLock().unlock();
                            throw th;
                        }
                    }
                    this.mFocusedWindow = rootView;
/* 375 */           this.mFocusLock.writeLock().unlock();
/* 377 */           fireFocusChangedEvent();
                }

                @Override // java.lang.Runnable
/* 384 */       public void run() {
                    try {
/* 385 */               this.mServer = new ServerSocket(this.mPort, 10, InetAddress.getLocalHost());
                    } catch (Exception e) {
/* 387 */               Log.w(LOG_TAG, "Starting ServerSocket error: ", e);
                    }
/* 390 */           while (this.mServer != null && Thread.currentThread() == this.mThread) {
                        try {
/* 393 */                   Socket client = this.mServer.accept();
/* 394 */                   if (this.mThreadPool != null) {
/* 395 */                       this.mThreadPool.submit(new ViewServerWorker(client));
                            } else {
                                try {
/* 398 */                           client.close();
                                } catch (IOException e2) {
/* 400 */                           e2.printStackTrace();
                                }
                            }
                        } catch (Exception e3) {
/* 404 */                   Log.w(LOG_TAG, "Connection error: ", e3);
                        }
                    }
                }

/* 410 */       private static boolean writeValue(Socket client, String value) {
                    try {
                        BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(client.getOutputStream()), 8192);
                        out.write(value);
                        out.write(IOUtils.LINE_SEPARATOR_UNIX);
                        out.flush();
                        out.close();
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                }

/* 433 */       private void fireWindowsChangedEvent() {
                    for (WindowListener listener : this.mListeners) {
/* 435 */               listener.windowsChanged();
                    }
                }

/* 439 */       private void fireFocusChangedEvent() {
                    for (WindowListener listener : this.mListeners) {
/* 441 */               listener.focusChanged();
                    }
                }

/* 445 */       private void addWindowListener(WindowListener listener) {
/* 446 */           if (!this.mListeners.contains(listener)) {
/* 447 */               this.mListeners.add(listener);
                    }
                }

/* 451 */       private void removeWindowListener(WindowListener listener) {
/* 452 */           this.mListeners.remove(listener);
                }

/* 461 */       private static class UncloseableOutputStream extends OutputStream {
                    private final OutputStream mStream;

/* 462 */           UncloseableOutputStream(OutputStream stream) {
/* 464 */               this.mStream = stream;
                    }

                    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 468 */           public void close() throws IOException {
                    }

/* 471 */           public boolean equals(Object o) {
/* 472 */               return this.mStream.equals(o);
                    }

                    @Override // java.io.OutputStream, java.io.Flushable
/* 475 */           public void flush() throws IOException {
/* 476 */               this.mStream.flush();
                    }

/* 479 */           public int hashCode() {
/* 480 */               return this.mStream.hashCode();
                    }

/* 483 */           public String toString() {
/* 484 */               return this.mStream.toString();
                    }

                    @Override // java.io.OutputStream
/* 488 */           public void write(byte[] buffer, int offset, int count) throws IOException {
/* 489 */               this.mStream.write(buffer, offset, count);
                    }

                    @Override // java.io.OutputStream
/* 492 */           public void write(byte[] buffer) throws IOException {
/* 493 */               this.mStream.write(buffer);
                    }

                    @Override // java.io.OutputStream
/* 496 */           public void write(int oneByte) throws IOException {
/* 497 */               this.mStream.write(oneByte);
                    }
                }

/* 499 */       private static class NoopViewServer extends ViewServer {
/* 500 */           /* synthetic */ NoopViewServer(Object x0) {
/* 501 */               this();
                    }

/* 501 */           private NoopViewServer() {
/* 502 */               super((Object) null);
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 506 */           public boolean start() throws IOException {
/* 507 */               return false;
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 511 */           public boolean stop() {
/* 512 */               return false;
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 516 */           public boolean isRunning() {
/* 517 */               return false;
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 521 */           public void addWindow(Activity activity) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 525 */           public void removeWindow(Activity activity) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 529 */           public void addWindow(View view, String name) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 533 */           public void removeWindow(View view) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 537 */           public void setFocusedWindow(Activity activity) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer
/* 541 */           public void setFocusedWindow(View view) {
                    }

                    @Override // com.android.debug.p002hv.ViewServer, java.lang.Runnable
/* 545 */           public void run() {
                    }
                }

/* 554 */       private class ViewServerWorker implements Runnable, WindowListener {
                    private Socket mClient;
                    private final Object[] mLock = new Object[0];
                    private boolean mNeedWindowListUpdate = false;
                    private boolean mNeedFocusedWindowUpdate = false;

/* 555 */           public ViewServerWorker(Socket client) {
/* 557 */               this.mClient = client;
                    }

                    @Override // java.lang.Runnable
/* 562 */           public void run() {
                        BufferedReader in = null;
                        try {
                            in = new BufferedReader(new InputStreamReader(this.mClient.getInputStream()), 1024);
                            String request = in.readLine();
                            if (request == null) {
                                return;
                            }
                            int index = request.indexOf(32);
                            String command = index < 0 ? request : request.substring(0, index);
                            String parameters = index < 0 ? "" : request.substring(index + 1);
                            boolean result;
                            if (ViewServer.COMMAND_PROTOCOL_VERSION.equalsIgnoreCase(command)
                                    || ViewServer.COMMAND_SERVER_VERSION.equalsIgnoreCase(command)) {
                                result = ViewServer.access$200(this.mClient, "4");
                            } else if (ViewServer.COMMAND_WINDOW_MANAGER_LIST.equalsIgnoreCase(command)) {
                                result = listWindows(this.mClient);
                            } else if (ViewServer.COMMAND_WINDOW_MANAGER_GET_FOCUS.equalsIgnoreCase(command)) {
                                result = getFocusedWindow(this.mClient);
                            } else if (ViewServer.COMMAND_WINDOW_MANAGER_AUTOLIST.equalsIgnoreCase(command)) {
                                result = windowManagerAutolistLoop();
                            } else {
                                result = windowCommand(this.mClient, command, parameters);
                            }
                            if (!result) {
                                Log.w(ViewServer.LOG_TAG, "An error occurred with the command: " + command);
                            }
                        } catch (Throwable e) {
                            Log.w(ViewServer.LOG_TAG, "Connection error: ", e);
                        } finally {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException ignored) {
                                }
                            }
                            if (this.mClient != null) {
                                try {
                                    this.mClient.close();
                                } catch (IOException ignored) {
                                }
                            }
                        }
                    }

                    /* JADX WARN: Code duplicated, block: B:48:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
/* 620 */           private boolean windowCommand(Socket client, String command, String parameters) {
                        try {
                            int index = parameters.indexOf(32);
                            if (index < 0) {
                                index = parameters.length();
                            }
                            String code = parameters.substring(0, index);
                            int hashCode = (int) Long.parseLong(code, 16);
                            String commandParameters = index < parameters.length()
                                    ? parameters.substring(index + 1) : "";
                            View window = findWindow(hashCode);
                            if (window == null) {
                                return false;
                            }
                            Method dispatch = ViewDebug.class.getDeclaredMethod(
                                    "dispatchCommand", View.class, String.class, String.class, OutputStream.class);
                            dispatch.setAccessible(true);
                            dispatch.invoke(null, window, command, commandParameters,
                                    new UncloseableOutputStream(client.getOutputStream()));
                            if (!client.isOutputShutdown()) {
                                BufferedWriter out = new BufferedWriter(
                                        new OutputStreamWriter(client.getOutputStream()));
                                out.write("DONE\n");
                                out.flush();
                                out.close();
                            }
                            return true;
                        } catch (Throwable e) {
                            Log.w(ViewServer.LOG_TAG,
                                    "Could not send command " + command + " with parameters " + parameters, e);
                            return false;
                        }
                    }

/* 675 */           private View findWindow(int hashCode) {
/* 676 */               if (hashCode == -1) {
/* 678 */                   ViewServer.access$300(ViewServer.this).readLock().lock();
                            try {
/* 680 */                       View window = ViewServer.access$400(ViewServer.this);
/* 682 */                       ViewServer.access$300(ViewServer.this).readLock().unlock();
/* 699 */                       return window;
                            } catch (Throwable th) {
                                ViewServer.access$300(ViewServer.this).readLock().unlock();
                                throw th;
                            }
                        }
/* 688 */               ViewServer.access$300(ViewServer.this).readLock().lock();
                        try {
                            for (Map.Entry<View, String> entry : ViewServer.access$500(ViewServer.this).entrySet()) {
/* 691 */                       if (System.identityHashCode(entry.getKey()) == hashCode) {
/* 692 */                           View window2 = entry.getKey();
/* 696 */                           ViewServer.access$300(ViewServer.this).readLock().unlock();
                                    return window2;
                                }
                            }
                            ViewServer.access$300(ViewServer.this).readLock().unlock();
/* 699 */                   return null;
                        } catch (Throwable th2) {
                            ViewServer.access$300(ViewServer.this).readLock().unlock();
                            throw th2;
                        }
                    }

/* 702 */           private boolean listWindows(Socket client) {
                        ViewServer.access$300(ViewServer.this).readLock().lock();
                        try {
                            BufferedWriter out = new BufferedWriter(
                                    new OutputStreamWriter(client.getOutputStream()), 8192);
                            try {
                                for (Map.Entry<View, String> entry
                                        : ViewServer.access$500(ViewServer.this).entrySet()) {
                                    out.write(Integer.toHexString(System.identityHashCode(entry.getKey())));
                                    out.write(32);
                                    out.append(entry.getValue());
                                    out.write(10);
                                }
                                out.write("DONE.\n");
                                out.flush();
                                return true;
                            } finally {
                                try {
                                    out.close();
                                } catch (IOException ignored) {
                                }
                            }
                        } catch (Throwable e) {
                            Log.w(ViewServer.LOG_TAG, "Could not list windows", e);
                            return false;
                        } finally {
                            ViewServer.access$300(ViewServer.this).readLock().unlock();
                        }
                    }

/* 738 */           private boolean getFocusedWindow(Socket client) {
                        ViewServer.access$600(ViewServer.this).readLock().lock();
                        View focusedWindow;
                        try {
                            focusedWindow = ViewServer.access$400(ViewServer.this);
                        } finally {
                            ViewServer.access$600(ViewServer.this).readLock().unlock();
                        }
                        String focusName = null;
                        if (focusedWindow != null) {
                            ViewServer.access$300(ViewServer.this).readLock().lock();
                            try {
                                focusName = ViewServer.access$500(ViewServer.this).get(focusedWindow);
                            } finally {
                                ViewServer.access$300(ViewServer.this).readLock().unlock();
                            }
                        }
                        try {
                            BufferedWriter out = new BufferedWriter(
                                    new OutputStreamWriter(client.getOutputStream()), 8192);
                            try {
                                if (focusedWindow != null) {
                                    out.write(Integer.toHexString(System.identityHashCode(focusedWindow)));
                                    out.write(32);
                                    out.append(focusName == null ? "" : focusName);
                                }
                                out.write(10);
                                out.flush();
                                return true;
                            } finally {
                                try {
                                    out.close();
                                } catch (IOException ignored) {
                                }
                            }
                        } catch (Throwable e) {
                            Log.w(ViewServer.LOG_TAG, "Could not get focused window", e);
                            return false;
                        }
                    }

                    @Override // com.android.debug.hv.ViewServer.WindowListener
/* 785 */           public void windowsChanged() {
                        synchronized (this.mLock) {
                            try {
/* 787 */                       this.mNeedWindowListUpdate = true;
/* 788 */                       this.mLock.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // com.android.debug.hv.ViewServer.WindowListener
/* 792 */           public void focusChanged() {
                        synchronized (this.mLock) {
                            try {
/* 794 */                       this.mNeedFocusedWindowUpdate = true;
/* 795 */                       this.mLock.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 799 */           private boolean windowManagerAutolistLoop() {
                        ViewServer.access$700(ViewServer.this, this);
                        try {
                            BufferedWriter out = new BufferedWriter(
                                    new OutputStreamWriter(this.mClient.getOutputStream()));
                            try {
                                while (!Thread.interrupted()) {
                                    boolean needWindowListUpdate = false;
                                    boolean needFocusedWindowUpdate = false;
                                    synchronized (this.mLock) {
                                        while (!this.mNeedWindowListUpdate
                                                && !this.mNeedFocusedWindowUpdate) {
                                            try {
                                                this.mLock.wait();
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                return true;
                                            }
                                        }
                                        if (this.mNeedWindowListUpdate) {
                                            this.mNeedWindowListUpdate = false;
                                            needWindowListUpdate = true;
                                        }
                                        if (this.mNeedFocusedWindowUpdate) {
                                            this.mNeedFocusedWindowUpdate = false;
                                            needFocusedWindowUpdate = true;
                                        }
                                    }
                                    if (needWindowListUpdate) {
                                        out.write("LIST UPDATE\n");
                                    }
                                    if (needFocusedWindowUpdate) {
                                        out.write("FOCUS UPDATE\n");
                                    }
                                    if (needWindowListUpdate || needFocusedWindowUpdate) {
                                        out.flush();
                                    }
                                }
                                return true;
                            } finally {
                                try {
                                    out.close();
                                } catch (IOException ignored) {
                                }
                            }
                        } catch (Throwable e) {
                            Log.w(ViewServer.LOG_TAG, "Connection error: ", e);
                            return false;
                        } finally {
                            ViewServer.access$800(ViewServer.this, this);
                        }
                    }
                }
            }
