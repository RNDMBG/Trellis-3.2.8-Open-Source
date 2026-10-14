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
            
            
   public class ViewServer implements Runnable {
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

        ViewServer(Object x0) {
           this();
                }

       static  boolean access$200(Socket x0, String x1) {
           return writeValue(x0, x1);
                }

       static  ReentrantReadWriteLock access$300(ViewServer x0) {
           return x0.mWindowsLock;
                }

       static  View access$400(ViewServer x0) {
           return x0.mFocusedWindow;
                }

       static  HashMap<View, String> access$500(ViewServer x0) {
           return x0.mWindows;
                }

       static  ReentrantReadWriteLock access$600(ViewServer x0) {
           return x0.mFocusLock;
                }

       static  void access$700(ViewServer x0, WindowListener x1) {
           x0.addWindowListener(x1);
                }

       static  void access$800(ViewServer x0, WindowListener x1) {
           x0.removeWindowListener(x1);
                }

       public static ViewServer get(Context context) {
           ApplicationInfo info = context.getApplicationInfo();
           if ("user".equals(Build.TYPE) && (info.flags & 2) != 0) {
               if (sServer == null) {
                   sServer = new ViewServer(VIEW_SERVER_DEFAULT_PORT);
                        }
               if (!sServer.isRunning()) {
                            try {
                       sServer.start();
                            } catch (IOException e) {
                       Log.d(LOG_TAG, "Error:", e);
                            }
                        }
                    } else {
               sServer = new NoopViewServer(null);
                    }
           return sServer;
                }

       private ViewServer() {
           this.mListeners = new CopyOnWriteArrayList();
           this.mWindows = new HashMap<>();
           this.mWindowsLock = new ReentrantReadWriteLock();
           this.mFocusLock = new ReentrantReadWriteLock();
           this.mPort = -1;
                }

       private ViewServer(int port) {
           this.mListeners = new CopyOnWriteArrayList();
           this.mWindows = new HashMap<>();
           this.mWindowsLock = new ReentrantReadWriteLock();
           this.mFocusLock = new ReentrantReadWriteLock();
           this.mPort = port;
                }

       public boolean start() throws IOException {
           if (this.mThread != null) {
               return false;
                    }
           this.mThread = new Thread(this, "Local View Server [port=" + this.mPort + "]");
           this.mThreadPool = Executors.newFixedThreadPool(10);
           this.mThread.start();
           return true;
                }

       public boolean stop() {
           if (this.mThread != null) {
               this.mThread.interrupt();
               if (this.mThreadPool != null) {
                            try {
                       this.mThreadPool.shutdownNow();
                            } catch (SecurityException e) {
                       Log.w(LOG_TAG, "Could not stop all view server threads");
                            }
                        }
               this.mThreadPool = null;
               this.mThread = null;
                        try {
                   this.mServer.close();
                   this.mServer = null;
                   return true;
                        } catch (IOException e2) {
                   Log.w(LOG_TAG, "Could not close the view server");
                        }
                    }
           this.mWindowsLock.writeLock().lock();
                    try {
               this.mWindows.clear();
               this.mWindowsLock.writeLock().unlock();
               this.mFocusLock.writeLock().lock();
                        try {
                   this.mFocusedWindow = null;
                   this.mFocusLock.writeLock().unlock();
                   return false;
                        } catch (Throwable th) {
                            this.mFocusLock.writeLock().unlock();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th2;
                    }
                }

       public boolean isRunning() {
                    return this.mThread != null && this.mThread.isAlive();
                }

       public void addWindow(Activity activity) {
                    String name;
           String name2 = activity.getTitle().toString();
           if (TextUtils.isEmpty(name2)) {
               name = activity.getClass().getCanonicalName() + "/0x" + System.identityHashCode(activity);
                    } else {
               name = name2 + "(" + activity.getClass().getCanonicalName() + ")";
                    }
           addWindow(activity.getWindow().getDecorView(), name);
                }

       public void removeWindow(Activity activity) {
           removeWindow(activity.getWindow().getDecorView());
                }

       public void addWindow(View view, String name) {
           this.mWindowsLock.writeLock().lock();
                    try {
               this.mWindows.put(view.getRootView(), name);
               this.mWindowsLock.writeLock().unlock();
               fireWindowsChangedEvent();
                    } catch (Throwable th) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th;
                    }
                }

       public void removeWindow(View view) {
           this.mWindowsLock.writeLock().lock();
                    try {
               this.mWindows.remove(view.getRootView());
               this.mWindowsLock.writeLock().unlock();
               fireWindowsChangedEvent();
                    } catch (Throwable th) {
                        this.mWindowsLock.writeLock().unlock();
                        throw th;
                    }
                }

       public void setFocusedWindow(Activity activity) {
           setFocusedWindow(activity.getWindow().getDecorView());
                }

       public void setFocusedWindow(View view) {
                    View rootView;
           this.mFocusLock.writeLock().lock();
           if (view == null) {
                        rootView = null;
                    } else {
                        try {
                   rootView = view.getRootView();
                        } catch (Throwable th) {
                            this.mFocusLock.writeLock().unlock();
                            throw th;
                        }
                    }
                    this.mFocusedWindow = rootView;
           this.mFocusLock.writeLock().unlock();
           fireFocusChangedEvent();
                }

                @Override 
       public void run() {
                    try {
               this.mServer = new ServerSocket(this.mPort, 10, InetAddress.getLocalHost());
                    } catch (Exception e) {
               Log.w(LOG_TAG, "Starting ServerSocket error: ", e);
                    }
           while (this.mServer != null && Thread.currentThread() == this.mThread) {
                        try {
                   Socket client = this.mServer.accept();
                   if (this.mThreadPool != null) {
                       this.mThreadPool.submit(new ViewServerWorker(client));
                            } else {
                                try {
                           client.close();
                                } catch (IOException e2) {
                           e2.printStackTrace();
                                }
                            }
                        } catch (Exception e3) {
                   Log.w(LOG_TAG, "Connection error: ", e3);
                        }
                    }
                }

       private static boolean writeValue(Socket client, String value) {
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

       private void fireWindowsChangedEvent() {
                    for (WindowListener listener : this.mListeners) {
               listener.windowsChanged();
                    }
                }

       private void fireFocusChangedEvent() {
                    for (WindowListener listener : this.mListeners) {
               listener.focusChanged();
                    }
                }

       private void addWindowListener(WindowListener listener) {
           if (!this.mListeners.contains(listener)) {
               this.mListeners.add(listener);
                    }
                }

       private void removeWindowListener(WindowListener listener) {
           this.mListeners.remove(listener);
                }

       private static class UncloseableOutputStream extends OutputStream {
                    private final OutputStream mStream;

           UncloseableOutputStream(OutputStream stream) {
               this.mStream = stream;
                    }

                    @Override 
           public void close() throws IOException {
                    }

           public boolean equals(Object o) {
               return this.mStream.equals(o);
                    }

                    @Override 
           public void flush() throws IOException {
               this.mStream.flush();
                    }

           public int hashCode() {
               return this.mStream.hashCode();
                    }

           public String toString() {
               return this.mStream.toString();
                    }

                    @Override 
           public void write(byte[] buffer, int offset, int count) throws IOException {
               this.mStream.write(buffer, offset, count);
                    }

                    @Override 
           public void write(byte[] buffer) throws IOException {
               this.mStream.write(buffer);
                    }

                    @Override 
           public void write(int oneByte) throws IOException {
               this.mStream.write(oneByte);
                    }
                }

       private static class NoopViewServer extends ViewServer {
            NoopViewServer(Object x0) {
               this();
                    }

           private NoopViewServer() {
               super((Object) null);
                    }

                    @Override 
           public boolean start() throws IOException {
               return false;
                    }

                    @Override 
           public boolean stop() {
               return false;
                    }

                    @Override 
           public boolean isRunning() {
               return false;
                    }

                    @Override 
           public void addWindow(Activity activity) {
                    }

                    @Override 
           public void removeWindow(Activity activity) {
                    }

                    @Override 
           public void addWindow(View view, String name) {
                    }

                    @Override 
           public void removeWindow(View view) {
                    }

                    @Override 
           public void setFocusedWindow(Activity activity) {
                    }

                    @Override 
           public void setFocusedWindow(View view) {
                    }

                    @Override 
           public void run() {
                    }
                }

       private class ViewServerWorker implements Runnable, WindowListener {
                    private Socket mClient;
                    private final Object[] mLock = new Object[0];
                    private boolean mNeedWindowListUpdate = false;
                    private boolean mNeedFocusedWindowUpdate = false;

           public ViewServerWorker(Socket client) {
               this.mClient = client;
                    }

                    @Override 
           public void run() {
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

                    
           private boolean windowCommand(Socket client, String command, String parameters) {
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

           private View findWindow(int hashCode) {
               if (hashCode == -1) {
                   ViewServer.access$300(ViewServer.this).readLock().lock();
                            try {
                       View window = ViewServer.access$400(ViewServer.this);
                       ViewServer.access$300(ViewServer.this).readLock().unlock();
                       return window;
                            } catch (Throwable th) {
                                ViewServer.access$300(ViewServer.this).readLock().unlock();
                                throw th;
                            }
                        }
               ViewServer.access$300(ViewServer.this).readLock().lock();
                        try {
                            for (Map.Entry<View, String> entry : ViewServer.access$500(ViewServer.this).entrySet()) {
                       if (System.identityHashCode(entry.getKey()) == hashCode) {
                           View window2 = entry.getKey();
                           ViewServer.access$300(ViewServer.this).readLock().unlock();
                                    return window2;
                                }
                            }
                            ViewServer.access$300(ViewServer.this).readLock().unlock();
                   return null;
                        } catch (Throwable th2) {
                            ViewServer.access$300(ViewServer.this).readLock().unlock();
                            throw th2;
                        }
                    }

           private boolean listWindows(Socket client) {
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

           private boolean getFocusedWindow(Socket client) {
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

                    @Override 
           public void windowsChanged() {
                        synchronized (this.mLock) {
                            try {
                       this.mNeedWindowListUpdate = true;
                       this.mLock.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           public void focusChanged() {
                        synchronized (this.mLock) {
                            try {
                       this.mNeedFocusedWindowUpdate = true;
                       this.mLock.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           private boolean windowManagerAutolistLoop() {
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
