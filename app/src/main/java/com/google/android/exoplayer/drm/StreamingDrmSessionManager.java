            package com.google.android.exoplayer.drm;

            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import android.media.DeniedByServerException;
            import android.media.MediaCrypto;
            import android.media.MediaDrm;
            import android.media.NotProvisionedException;
            import android.media.UnsupportedSchemeException;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            import android.os.Message;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(18)
/* 39 */    public class StreamingDrmSessionManager implements DrmSessionManager {
                private static final int MSG_KEYS = 1;
                private static final int MSG_PROVISION = 0;
                final MediaDrmCallback callback;
                private final Handler eventHandler;
                private final EventListener eventListener;
                private Exception lastException;
                private MediaCrypto mediaCrypto;
                private final MediaDrm mediaDrm;
                final MediaDrmHandler mediaDrmHandler;
                private String mimeType;
                private int openCount;
                private Handler postRequestHandler;
                final PostResponseHandler postResponseHandler;
                private HandlerThread requestHandlerThread;
                private byte[] schemePsshData;
                private byte[] sessionId;
                private int state;
                final UUID uuid;

                public interface EventListener {
                    void onDrmSessionManagerError(Exception exc);
                }

/* 40 */        static /* synthetic */ Exception access$100(StreamingDrmSessionManager x0) {
/* 41 */            return x0.lastException;
                }

/* 40 */        static /* synthetic */ EventListener access$200(StreamingDrmSessionManager x0) {
/* 41 */            return x0.eventListener;
                }

/* 40 */        static /* synthetic */ int access$300(StreamingDrmSessionManager x0) {
/* 41 */            return x0.openCount;
                }

/* 40 */        static /* synthetic */ int access$400(StreamingDrmSessionManager x0) {
/* 41 */            return x0.state;
                }

/* 40 */        static /* synthetic */ int access$402(StreamingDrmSessionManager x0, int x1) {
/* 41 */            x0.state = x1;
                    return x1;
                }

/* 40 */        static /* synthetic */ void access$500(StreamingDrmSessionManager x0) {
/* 41 */            x0.postKeyRequest();
                }

/* 40 */        static /* synthetic */ void access$600(StreamingDrmSessionManager x0) {
/* 41 */            x0.postProvisionRequest();
                }

/* 40 */        static /* synthetic */ void access$700(StreamingDrmSessionManager x0, Object x1) {
/* 41 */            x0.onProvisionResponse(x1);
                }

/* 40 */        static /* synthetic */ void access$800(StreamingDrmSessionManager x0, Object x1) {
/* 41 */            x0.onKeyResponse(x1);
                }

/* 91 */        public StreamingDrmSessionManager(UUID uuid, Looper playbackLooper, MediaDrmCallback callback, Handler eventHandler, EventListener eventListener) throws UnsupportedSchemeException {
/* 93 */            this.uuid = uuid;
/* 94 */            this.callback = callback;
/* 95 */            this.eventHandler = eventHandler;
/* 96 */            this.eventListener = eventListener;
/* 97 */            this.mediaDrm = new MediaDrm(uuid);
/* 98 */            this.mediaDrm.setOnEventListener(new MediaDrmEventListener(this, null));
/* 99 */            this.mediaDrmHandler = new MediaDrmHandler(playbackLooper);
/* 100 */           this.postResponseHandler = new PostResponseHandler(playbackLooper);
/* 101 */           this.state = 1;
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 105 */       public int getState() {
/* 106 */           return this.state;
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 110 */       public MediaCrypto getMediaCrypto() {
/* 111 */           if (this.state != 3 && this.state != 4) {
/* 112 */               throw new IllegalStateException();
                    }
/* 114 */           return this.mediaCrypto;
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 118 */       public boolean requiresSecureDecoderComponent(String mimeType) {
/* 119 */           if (this.state != 3 && this.state != 4) {
/* 120 */               throw new IllegalStateException();
                    }
/* 122 */           return this.mediaCrypto.requiresSecureDecoderComponent(mimeType);
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 126 */       public Exception getError() {
/* 127 */           if (this.state == 0) {
                        return this.lastException;
                    }
                    return null;
                }

/* 138 */       public final String getPropertyString(String key) {
/* 139 */           return this.mediaDrm.getPropertyString(key);
                }

/* 150 */       public final byte[] getPropertyByteArray(String key) {
/* 151 */           return this.mediaDrm.getPropertyByteArray(key);
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 155 */       public void open(Map<UUID, byte[]> psshData, String mimeType) {
/* 156 */           int i = this.openCount + 1;
                    this.openCount = i;
                    if (i == 1) {
/* 159 */               if (this.postRequestHandler == null) {
/* 160 */                   this.requestHandlerThread = new HandlerThread("DrmRequestHandler");
/* 161 */                   this.requestHandlerThread.start();
/* 162 */                   this.postRequestHandler = new PostRequestHandler(this.requestHandlerThread.getLooper());
                        }
/* 164 */               if (this.schemePsshData == null) {
/* 165 */                   this.mimeType = mimeType;
/* 166 */                   this.schemePsshData = psshData.get(this.uuid);
/* 167 */                   if (this.schemePsshData == null) {
/* 168 */                       onError(new IllegalStateException("Media does not support uuid: " + this.uuid));
/* 174 */                       return;
                            }
                        }
/* 172 */               this.state = 2;
/* 173 */               openInternal(true);
                    }
                }

                @Override // com.google.android.exoplayer.drm.DrmSessionManager
/* 177 */       public void close() {
/* 178 */           int i = this.openCount - 1;
                    this.openCount = i;
                    if (i == 0) {
/* 181 */               this.state = 1;
/* 182 */               this.mediaDrmHandler.removeCallbacksAndMessages(null);
/* 183 */               this.postResponseHandler.removeCallbacksAndMessages(null);
/* 184 */               this.postRequestHandler.removeCallbacksAndMessages(null);
/* 185 */               this.postRequestHandler = null;
/* 186 */               this.requestHandlerThread.quit();
/* 187 */               this.requestHandlerThread = null;
/* 188 */               this.schemePsshData = null;
/* 189 */               this.mediaCrypto = null;
/* 190 */               this.lastException = null;
/* 191 */               if (this.sessionId != null) {
/* 192 */                   this.mediaDrm.closeSession(this.sessionId);
/* 193 */                   this.sessionId = null;
                        }
                    }
                }

/* 198 */       private void openInternal(boolean allowProvisioning) {
                    try {
/* 199 */               this.sessionId = this.mediaDrm.openSession();
/* 200 */               this.mediaCrypto = new MediaCrypto(this.uuid, this.sessionId);
/* 201 */               this.state = 3;
/* 202 */               postKeyRequest();
                    } catch (NotProvisionedException e) {
/* 204 */               if (allowProvisioning) {
/* 205 */                   postProvisionRequest();
                        } else {
/* 207 */                   onError(e);
                        }
                    } catch (Exception e2) {
/* 210 */               onError(e2);
                    }
                }

/* 214 */       private void postProvisionRequest() {
/* 215 */           MediaDrm.ProvisionRequest request = this.mediaDrm.getProvisionRequest();
/* 216 */           this.postRequestHandler.obtainMessage(0, request).sendToTarget();
                }

/* 219 */       private void onProvisionResponse(Object response) {
/* 220 */           if (this.state == 2 || this.state == 3 || this.state == 4) {
/* 225 */               if (response instanceof Exception) {
/* 226 */                   onError((Exception) response);
/* 240 */                   return;
                        }
                        try {
/* 231 */                   this.mediaDrm.provideProvisionResponse((byte[]) response);
/* 232 */                   if (this.state == 2) {
/* 233 */                       openInternal(false);
                            } else {
/* 235 */                       postKeyRequest();
                            }
                        } catch (DeniedByServerException e) {
/* 238 */                   onError(e);
                        }
                    }
                }

/* 244 */       private void postKeyRequest() {
                    try {
/* 245 */               MediaDrm.KeyRequest keyRequest = this.mediaDrm.getKeyRequest(this.sessionId, this.schemePsshData, this.mimeType, 1, null);
/* 247 */               this.postRequestHandler.obtainMessage(1, keyRequest).sendToTarget();
                    } catch (NotProvisionedException e) {
/* 249 */               onKeysError(e);
                    }
                }

/* 253 */       private void onKeyResponse(Object response) {
/* 254 */           if (this.state == 3 || this.state == 4) {
/* 259 */               if (response instanceof Exception) {
/* 260 */                   onKeysError((Exception) response);
/* 270 */                   return;
                        }
                        try {
/* 265 */                   this.mediaDrm.provideKeyResponse(this.sessionId, (byte[]) response);
/* 266 */                   this.state = 4;
                        } catch (Exception e) {
/* 268 */                   onKeysError(e);
                        }
                    }
                }

/* 272 */       private void onKeysError(Exception e) {
/* 273 */           if (e instanceof NotProvisionedException) {
/* 274 */               postProvisionRequest();
                    } else {
/* 276 */               onError(e);
                    }
                }

/* 280 */       private void onError(Exception e) {
/* 281 */           this.lastException = e;
/* 282 */           if (this.eventHandler != null && this.eventListener != null) {
/* 283 */               this.eventHandler.post(new RunnableC08701());
                    }
/* 290 */           if (this.state != 4) {
/* 291 */               this.state = 0;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.drm.StreamingDrmSessionManager$1 */
/* 281 */       class RunnableC08701 implements Runnable {
/* 282 */           RunnableC08701() {
                    }

                    @Override // java.lang.Runnable
/* 285 */           public void run() {
/* 286 */               StreamingDrmSessionManager.access$200(StreamingDrmSessionManager.this).onDrmSessionManagerError(StreamingDrmSessionManager.access$100(StreamingDrmSessionManager.this));
                    }
                }

                @SuppressLint({"HandlerLeak"})
/* 296 */       private class MediaDrmHandler extends Handler {
/* 297 */           public MediaDrmHandler(Looper looper) {
/* 299 */               super(looper);
                    }

                    @Override // android.os.Handler
/* 303 */           public void handleMessage(Message msg) {
/* 304 */               if (StreamingDrmSessionManager.access$300(StreamingDrmSessionManager.this) != 0) {
                            if (StreamingDrmSessionManager.access$400(StreamingDrmSessionManager.this) == 3 || StreamingDrmSessionManager.access$400(StreamingDrmSessionManager.this) == 4) {
                                switch (msg.what) {
                                    case 1:
/* 316 */                               StreamingDrmSessionManager.access$402(StreamingDrmSessionManager.this, 3);
/* 317 */                               StreamingDrmSessionManager.access$600(StreamingDrmSessionManager.this);
                                        break;
                                    case 2:
/* 309 */                               StreamingDrmSessionManager.access$500(StreamingDrmSessionManager.this);
                                        break;
                                    case 3:
/* 312 */                               StreamingDrmSessionManager.access$402(StreamingDrmSessionManager.this, 3);
/* 313 */                               StreamingDrmSessionManager.access$500(StreamingDrmSessionManager.this);
                                        break;
                                }
                            }
                        }
                    }
                }

/* 322 */       private class MediaDrmEventListener implements MediaDrm.OnEventListener {
/* 323 */           private MediaDrmEventListener() {
                    }

/* 323 */           /* synthetic */ MediaDrmEventListener(StreamingDrmSessionManager x0, RunnableC08701 x1) {
/* 324 */               this();
                    }

                    @Override // android.media.MediaDrm.OnEventListener
/* 327 */           public void onEvent(MediaDrm md, byte[] sessionId, int event, int extra, byte[] data) {
/* 328 */               StreamingDrmSessionManager.this.mediaDrmHandler.sendEmptyMessage(event);
                    }
                }

                @SuppressLint({"HandlerLeak"})
/* 334 */       private class PostResponseHandler extends Handler {
/* 335 */           public PostResponseHandler(Looper looper) {
/* 337 */               super(looper);
                    }

                    @Override // android.os.Handler
/* 341 */           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
/* 344 */                       StreamingDrmSessionManager.access$700(StreamingDrmSessionManager.this, msg.obj);
                                break;
                            case 1:
/* 347 */                       StreamingDrmSessionManager.access$800(StreamingDrmSessionManager.this, msg.obj);
                                break;
                        }
                    }
                }

                @SuppressLint({"HandlerLeak"})
/* 355 */       private class PostRequestHandler extends Handler {
/* 356 */           public PostRequestHandler(Looper backgroundLooper) {
/* 358 */               super(backgroundLooper);
                    }

                    @Override // android.os.Handler
/* 364 */           public void handleMessage(Message msg) {
                        Object objExecuteKeyRequest;
                        try {
                            switch (msg.what) {
                                case 0:
/* 367 */                           objExecuteKeyRequest = StreamingDrmSessionManager.this.callback.executeProvisionRequest(StreamingDrmSessionManager.this.uuid, (MediaDrm.ProvisionRequest) msg.obj);
                                    break;
                                case 1:
/* 370 */                           objExecuteKeyRequest = StreamingDrmSessionManager.this.callback.executeKeyRequest(StreamingDrmSessionManager.this.uuid, (MediaDrm.KeyRequest) msg.obj);
                                    break;
                                default:
/* 373 */                           throw new RuntimeException();
                            }
                        } catch (Exception e) {
/* 376 */                   objExecuteKeyRequest = e;
                        }
/* 378 */               StreamingDrmSessionManager.this.postResponseHandler.obtainMessage(msg.what, objExecuteKeyRequest).sendToTarget();
                    }
                }
            }
