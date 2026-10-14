            package android.support.v4.app;

            import android.os.Bundle;
            import android.support.v4.content.Loader;
            import android.support.v4.util.DebugUtils;
            import android.support.v4.util.SparseArrayCompat;
            import android.util.Log;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.lang.reflect.Modifier;
            
            /* JADX INFO: compiled from: LoaderManager.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 225 */   class LoaderManagerImpl extends LoaderManager {
                static boolean DEBUG = false;
                static final String TAG = "LoaderManager";
                FragmentActivity mActivity;
                boolean mCreatingLoader;
                boolean mRetaining;
                boolean mRetainingStarted;
                boolean mStarted;
                final String mWho;
                final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat<>();
                final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat<>();

                /* JADX INFO: compiled from: LoaderManager.java */
/* 226 */       final class LoaderInfo implements Loader.OnLoadCompleteListener<Object> {
                    final Bundle mArgs;
                    LoaderManager.LoaderCallbacks<Object> mCallbacks;
                    Object mData;
                    boolean mDeliveredData;
                    boolean mDestroyed;
                    boolean mHaveData;
                    final int mId;
                    boolean mListenerRegistered;
                    Loader<Object> mLoader;
                    LoaderInfo mPendingLoader;
                    boolean mReportNextStart;
                    boolean mRetaining;
                    boolean mRetainingStarted;
                    boolean mStarted;

/* 227 */           public LoaderInfo(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callbacks) {
/* 229 */               this.mId = id;
/* 230 */               this.mArgs = args;
/* 231 */               this.mCallbacks = callbacks;
                    }

/* 234 */           void start() {
/* 235 */               if (this.mRetaining && this.mRetainingStarted) {
/* 239 */                   this.mStarted = true;
/* 267 */                   return;
                        }
/* 243 */               if (!this.mStarted) {
/* 248 */                   this.mStarted = true;
/* 250 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Starting: " + this);
                            }
/* 251 */                   if (this.mLoader == null && this.mCallbacks != null) {
/* 252 */                       this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
                            }
/* 254 */                   if (this.mLoader != null) {
/* 255 */                       if (this.mLoader.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
/* 257 */                           throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
                                }
/* 261 */                       if (!this.mListenerRegistered) {
/* 262 */                           this.mLoader.registerListener(this.mId, this);
/* 263 */                           this.mListenerRegistered = true;
                                }
/* 265 */                       this.mLoader.startLoading();
                            }
                        }
                    }

/* 269 */           void retain() {
/* 270 */               if (LoaderManagerImpl.DEBUG) {
                            Log.v(LoaderManagerImpl.TAG, "  Retaining: " + this);
                        }
/* 271 */               this.mRetaining = true;
/* 272 */               this.mRetainingStarted = this.mStarted;
/* 273 */               this.mStarted = false;
/* 274 */               this.mCallbacks = null;
                    }

/* 277 */           void finishRetain() {
/* 278 */               if (this.mRetaining) {
/* 279 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                            }
/* 280 */                   this.mRetaining = false;
/* 281 */                   if (this.mStarted != this.mRetainingStarted && !this.mStarted) {
/* 286 */                       stop();
                            }
                        }
/* 291 */               if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
/* 298 */                   callOnLoadFinished(this.mLoader, this.mData);
                        }
                    }

/* 302 */           void reportStart() {
/* 303 */               if (this.mStarted && this.mReportNextStart) {
/* 305 */                   this.mReportNextStart = false;
/* 306 */                   if (this.mHaveData) {
/* 307 */                       callOnLoadFinished(this.mLoader, this.mData);
                            }
                        }
                    }

/* 313 */           void stop() {
/* 314 */               if (LoaderManagerImpl.DEBUG) {
                            Log.v(LoaderManagerImpl.TAG, "  Stopping: " + this);
                        }
/* 315 */               this.mStarted = false;
/* 316 */               if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
/* 319 */                   this.mListenerRegistered = false;
/* 320 */                   this.mLoader.unregisterListener(this);
/* 321 */                   this.mLoader.stopLoading();
                        }
                    }

/* 326 */           void destroy() {
/* 327 */               if (LoaderManagerImpl.DEBUG) {
                            Log.v(LoaderManagerImpl.TAG, "  Destroying: " + this);
                        }
/* 328 */               this.mDestroyed = true;
/* 329 */               boolean needReset = this.mDeliveredData;
/* 330 */               this.mDeliveredData = false;
/* 331 */               if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && needReset) {
/* 332 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Reseting: " + this);
                            }
/* 333 */                   String lastBecause = null;
/* 334 */                   if (LoaderManagerImpl.this.mActivity != null) {
/* 335 */                       lastBecause = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
/* 336 */                       LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoaderReset";
                            }
                            try {
/* 339 */                       this.mCallbacks.onLoaderReset(this.mLoader);
/* 341 */                       if (LoaderManagerImpl.this.mActivity != null) {
/* 342 */                           LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = lastBecause;
                                }
                            } catch (Throwable th) {
                                if (LoaderManagerImpl.this.mActivity != null) {
/* 342 */                           LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = lastBecause;
                                }
                                throw th;
                            }
                        }
/* 346 */               this.mCallbacks = null;
/* 347 */               this.mData = null;
/* 348 */               this.mHaveData = false;
/* 349 */               if (this.mLoader != null) {
/* 350 */                   if (this.mListenerRegistered) {
/* 351 */                       this.mListenerRegistered = false;
/* 352 */                       this.mLoader.unregisterListener(this);
                            }
/* 354 */                   this.mLoader.reset();
                        }
/* 356 */               if (this.mPendingLoader != null) {
/* 357 */                   this.mPendingLoader.destroy();
                        }
                    }

                    @Override // android.support.v4.content.Loader.OnLoadCompleteListener
/* 361 */           public void onLoadComplete(Loader<Object> loader, Object data) {
/* 362 */               if (LoaderManagerImpl.DEBUG) {
                            Log.v(LoaderManagerImpl.TAG, "onLoadComplete: " + this);
                        }
/* 364 */               if (this.mDestroyed) {
/* 365 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
/* 415 */                       return;
                            }
/* 415 */                   return;
                        }
/* 369 */               if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
/* 372 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
/* 415 */                       return;
                            }
/* 415 */                   return;
                        }
/* 376 */               LoaderInfo pending = this.mPendingLoader;
/* 377 */               if (pending != null) {
/* 381 */                   if (LoaderManagerImpl.DEBUG) {
                                Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + pending);
                            }
/* 382 */                   this.mPendingLoader = null;
/* 383 */                   LoaderManagerImpl.this.mLoaders.put(this.mId, null);
/* 384 */                   destroy();
/* 385 */                   LoaderManagerImpl.this.installLoader(pending);
/* 415 */                   return;
                        }
/* 391 */               if (this.mData != data || !this.mHaveData) {
/* 392 */                   this.mData = data;
/* 393 */                   this.mHaveData = true;
/* 394 */                   if (this.mStarted) {
/* 395 */                       callOnLoadFinished(loader, data);
                            }
                        }
/* 405 */               LoaderInfo info = LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
/* 406 */               if (info != null && info != this) {
/* 407 */                   info.mDeliveredData = false;
/* 408 */                   info.destroy();
/* 409 */                   LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
                        }
/* 412 */               if (LoaderManagerImpl.this.mActivity != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
/* 413 */                   LoaderManagerImpl.this.mActivity.mFragments.startPendingDeferredFragments();
                        }
                    }

/* 417 */           void callOnLoadFinished(Loader<Object> loader, Object data) {
/* 418 */               if (this.mCallbacks != null) {
/* 419 */                   String lastBecause = null;
/* 420 */                   if (LoaderManagerImpl.this.mActivity != null) {
/* 421 */                       lastBecause = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
/* 422 */                       LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoadFinished";
                            }
                            try {
/* 425 */                       if (LoaderManagerImpl.DEBUG) {
                                    Log.v(LoaderManagerImpl.TAG, "  onLoadFinished in " + loader + ": " + loader.dataToString(data));
                                }
/* 427 */                       this.mCallbacks.onLoadFinished(loader, data);
/* 429 */                       if (LoaderManagerImpl.this.mActivity != null) {
/* 430 */                           LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = lastBecause;
                                }
/* 433 */                       this.mDeliveredData = true;
                            } catch (Throwable th) {
                                if (LoaderManagerImpl.this.mActivity != null) {
/* 430 */                           LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = lastBecause;
                                }
                                throw th;
                            }
                        }
                    }

/* 438 */           public String toString() {
/* 439 */               StringBuilder sb = new StringBuilder(64);
/* 440 */               sb.append("LoaderInfo{");
/* 441 */               sb.append(Integer.toHexString(System.identityHashCode(this)));
/* 442 */               sb.append(" #");
/* 443 */               sb.append(this.mId);
/* 444 */               sb.append(" : ");
/* 445 */               DebugUtils.buildShortClassTag(this.mLoader, sb);
/* 446 */               sb.append("}}");
/* 447 */               return sb.toString();
                    }

/* 450 */           public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 451 */               writer.print(prefix);
                        writer.print("mId=");
                        writer.print(this.mId);
/* 452 */               writer.print(" mArgs=");
                        writer.println(this.mArgs);
/* 453 */               writer.print(prefix);
                        writer.print("mCallbacks=");
                        writer.println(this.mCallbacks);
/* 454 */               writer.print(prefix);
                        writer.print("mLoader=");
                        writer.println(this.mLoader);
/* 455 */               if (this.mLoader != null) {
/* 456 */                   this.mLoader.dump(prefix + "  ", fd, writer, args);
                        }
/* 458 */               if (this.mHaveData || this.mDeliveredData) {
/* 459 */                   writer.print(prefix);
                            writer.print("mHaveData=");
                            writer.print(this.mHaveData);
/* 460 */                   writer.print("  mDeliveredData=");
                            writer.println(this.mDeliveredData);
/* 461 */                   writer.print(prefix);
                            writer.print("mData=");
                            writer.println(this.mData);
                        }
/* 463 */               writer.print(prefix);
                        writer.print("mStarted=");
                        writer.print(this.mStarted);
/* 464 */               writer.print(" mReportNextStart=");
                        writer.print(this.mReportNextStart);
/* 465 */               writer.print(" mDestroyed=");
                        writer.println(this.mDestroyed);
/* 466 */               writer.print(prefix);
                        writer.print("mRetaining=");
                        writer.print(this.mRetaining);
/* 467 */               writer.print(" mRetainingStarted=");
                        writer.print(this.mRetainingStarted);
/* 468 */               writer.print(" mListenerRegistered=");
                        writer.println(this.mListenerRegistered);
/* 469 */               if (this.mPendingLoader != null) {
/* 470 */                   writer.print(prefix);
                            writer.println("Pending Loader ");
/* 471 */                   writer.print(this.mPendingLoader);
                            writer.println(":");
/* 472 */                   this.mPendingLoader.dump(prefix + "  ", fd, writer, args);
                        }
                    }
                }

/* 476 */       LoaderManagerImpl(String who, FragmentActivity activity, boolean started) {
/* 478 */           this.mWho = who;
/* 479 */           this.mActivity = activity;
/* 480 */           this.mStarted = started;
                }

/* 483 */       void updateActivity(FragmentActivity activity) {
/* 484 */           this.mActivity = activity;
                }

/* 488 */       private LoaderInfo createLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callback) {
/* 489 */           LoaderInfo info = new LoaderInfo(id, args, callback);
/* 490 */           Loader<Object> loader = callback.onCreateLoader(id, args);
/* 491 */           info.mLoader = loader;
/* 492 */           return info;
                }

/* 497 */       private LoaderInfo createAndInstallLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<Object> callback) {
                    try {
/* 498 */               this.mCreatingLoader = true;
/* 499 */               LoaderInfo info = createLoader(id, args, callback);
/* 500 */               installLoader(info);
/* 503 */               this.mCreatingLoader = false;
                        return info;
                    } catch (Throwable th) {
                        this.mCreatingLoader = false;
                        throw th;
                    }
                }

/* 507 */       void installLoader(LoaderInfo info) {
/* 508 */           this.mLoaders.put(info.mId, info);
/* 509 */           if (this.mStarted) {
/* 513 */               info.start();
                    }
                }

                @Override // android.support.v4.app.LoaderManager
/* 542 */       public <D> Loader<D> initLoader(int i, Bundle bundle, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
/* 543 */           if (this.mCreatingLoader) {
/* 544 */               throw new IllegalStateException("Called while creating a loader");
                    }
/* 547 */           LoaderInfo loaderInfoCreateAndInstallLoader = this.mLoaders.get(i);
/* 549 */           if (DEBUG) {
                        Log.v(TAG, "initLoader in " + this + ": args=" + bundle);
                    }
/* 551 */           if (loaderInfoCreateAndInstallLoader == null) {
/* 553 */               loaderInfoCreateAndInstallLoader = createAndInstallLoader(i, bundle, loaderCallbacks);
/* 554 */               if (DEBUG) {
                            Log.v(TAG, "  Created new loader " + loaderInfoCreateAndInstallLoader);
                        }
                    } else {
/* 556 */               if (DEBUG) {
                            Log.v(TAG, "  Re-using existing loader " + loaderInfoCreateAndInstallLoader);
                        }
/* 557 */               loaderInfoCreateAndInstallLoader.mCallbacks = loaderCallbacks;
                    }
/* 560 */           if (loaderInfoCreateAndInstallLoader.mHaveData && this.mStarted) {
/* 562 */               loaderInfoCreateAndInstallLoader.callOnLoadFinished(loaderInfoCreateAndInstallLoader.mLoader, loaderInfoCreateAndInstallLoader.mData);
                    }
/* 565 */           return (Loader<D>) loaderInfoCreateAndInstallLoader.mLoader;
                }

                @Override // android.support.v4.app.LoaderManager
/* 592 */       public <D> Loader<D> restartLoader(int i, Bundle bundle, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
/* 593 */           if (this.mCreatingLoader) {
/* 594 */               throw new IllegalStateException("Called while creating a loader");
                    }
/* 597 */           LoaderInfo loaderInfo = this.mLoaders.get(i);
/* 598 */           if (DEBUG) {
                        Log.v(TAG, "restartLoader in " + this + ": args=" + bundle);
                    }
/* 599 */           if (loaderInfo != null) {
/* 600 */               LoaderInfo loaderInfo2 = this.mInactiveLoaders.get(i);
/* 601 */               if (loaderInfo2 != null) {
/* 602 */                   if (loaderInfo.mHaveData) {
/* 607 */                       if (DEBUG) {
                                    Log.v(TAG, "  Removing last inactive loader: " + loaderInfo);
                                }
/* 608 */                       loaderInfo2.mDeliveredData = false;
/* 609 */                       loaderInfo2.destroy();
/* 610 */                       loaderInfo.mLoader.abandon();
/* 611 */                       this.mInactiveLoaders.put(i, loaderInfo);
                            } else if (!loaderInfo.mStarted) {
/* 619 */                       if (DEBUG) {
                                    Log.v(TAG, "  Current loader is stopped; replacing");
                                }
/* 620 */                       this.mLoaders.put(i, null);
/* 621 */                       loaderInfo.destroy();
                            } else {
/* 626 */                       if (loaderInfo.mPendingLoader != null) {
/* 627 */                           if (DEBUG) {
                                        Log.v(TAG, "  Removing pending loader: " + loaderInfo.mPendingLoader);
                                    }
/* 628 */                           loaderInfo.mPendingLoader.destroy();
/* 629 */                           loaderInfo.mPendingLoader = null;
                                }
/* 631 */                       if (DEBUG) {
                                    Log.v(TAG, "  Enqueuing as new pending loader");
                                }
/* 632 */                       loaderInfo.mPendingLoader = createLoader(i, bundle, loaderCallbacks);
/* 634 */                       return (Loader<D>) loaderInfo.mPendingLoader.mLoader;
                            }
                        } else {
/* 640 */                   if (DEBUG) {
                                Log.v(TAG, "  Making last loader inactive: " + loaderInfo);
                            }
/* 641 */                   loaderInfo.mLoader.abandon();
/* 642 */                   this.mInactiveLoaders.put(i, loaderInfo);
                        }
                    }
/* 647 */           return (Loader<D>) createAndInstallLoader(i, bundle, loaderCallbacks).mLoader;
                }

                @Override // android.support.v4.app.LoaderManager
/* 657 */       public void destroyLoader(int id) {
/* 658 */           if (this.mCreatingLoader) {
/* 659 */               throw new IllegalStateException("Called while creating a loader");
                    }
/* 662 */           if (DEBUG) {
                        Log.v(TAG, "destroyLoader in " + this + " of " + id);
                    }
/* 663 */           int idx = this.mLoaders.indexOfKey(id);
/* 664 */           if (idx >= 0) {
/* 665 */               LoaderInfo info = this.mLoaders.valueAt(idx);
/* 666 */               this.mLoaders.removeAt(idx);
/* 667 */               info.destroy();
                    }
/* 669 */           int idx2 = this.mInactiveLoaders.indexOfKey(id);
/* 670 */           if (idx2 >= 0) {
/* 671 */               LoaderInfo info2 = this.mInactiveLoaders.valueAt(idx2);
/* 672 */               this.mInactiveLoaders.removeAt(idx2);
/* 673 */               info2.destroy();
                    }
/* 675 */           if (this.mActivity != null && !hasRunningLoaders()) {
/* 676 */               this.mActivity.mFragments.startPendingDeferredFragments();
                    }
                }

                @Override // android.support.v4.app.LoaderManager
/* 685 */       public <D> Loader<D> getLoader(int i) {
/* 686 */           if (this.mCreatingLoader) {
/* 687 */               throw new IllegalStateException("Called while creating a loader");
                    }
/* 690 */           LoaderInfo loaderInfo = this.mLoaders.get(i);
/* 691 */           if (loaderInfo != null) {
/* 692 */               if (loaderInfo.mPendingLoader != null) {
/* 693 */                   return (Loader<D>) loaderInfo.mPendingLoader.mLoader;
                        }
/* 695 */               return (Loader<D>) loaderInfo.mLoader;
                    }
/* 697 */           return null;
                }

/* 700 */       void doStart() {
/* 701 */           if (DEBUG) {
                        Log.v(TAG, "Starting in " + this);
                    }
/* 702 */           if (this.mStarted) {
/* 703 */               RuntimeException e = new RuntimeException("here");
/* 704 */               e.fillInStackTrace();
/* 705 */               Log.w(TAG, "Called doStart when already started: " + this, e);
                    } else {
/* 709 */               this.mStarted = true;
                        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 714 */                   this.mLoaders.valueAt(i).start();
                        }
                    }
                }

/* 718 */       void doStop() {
/* 719 */           if (DEBUG) {
                        Log.v(TAG, "Stopping in " + this);
                    }
/* 720 */           if (!this.mStarted) {
/* 721 */               RuntimeException e = new RuntimeException("here");
/* 722 */               e.fillInStackTrace();
/* 723 */               Log.w(TAG, "Called doStop when not started: " + this, e);
                    } else {
                        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 728 */                   this.mLoaders.valueAt(i).stop();
                        }
/* 730 */               this.mStarted = false;
                    }
                }

/* 733 */       void doRetain() {
/* 734 */           if (DEBUG) {
                        Log.v(TAG, "Retaining in " + this);
                    }
/* 735 */           if (!this.mStarted) {
/* 736 */               RuntimeException e = new RuntimeException("here");
/* 737 */               e.fillInStackTrace();
/* 738 */               Log.w(TAG, "Called doRetain when not started: " + this, e);
                    } else {
/* 742 */               this.mRetaining = true;
/* 743 */               this.mStarted = false;
                        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 745 */                   this.mLoaders.valueAt(i).retain();
                        }
                    }
                }

/* 749 */       void finishRetain() {
/* 750 */           if (this.mRetaining) {
/* 751 */               if (DEBUG) {
                            Log.v(TAG, "Finished Retaining in " + this);
                        }
/* 753 */               this.mRetaining = false;
                        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 755 */                   this.mLoaders.valueAt(i).finishRetain();
                        }
                    }
                }

/* 760 */       void doReportNextStart() {
                    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 762 */               this.mLoaders.valueAt(i).mReportNextStart = true;
                    }
                }

/* 766 */       void doReportStart() {
                    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 768 */               this.mLoaders.valueAt(i).reportStart();
                    }
                }

/* 772 */       void doDestroy() {
/* 773 */           if (!this.mRetaining) {
/* 774 */               if (DEBUG) {
                            Log.v(TAG, "Destroying Active in " + this);
                        }
                        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
/* 776 */                   this.mLoaders.valueAt(i).destroy();
                        }
/* 778 */               this.mLoaders.clear();
                    }
/* 781 */           if (DEBUG) {
                        Log.v(TAG, "Destroying Inactive in " + this);
                    }
                    for (int i2 = this.mInactiveLoaders.size() - 1; i2 >= 0; i2--) {
/* 783 */               this.mInactiveLoaders.valueAt(i2).destroy();
                    }
/* 785 */           this.mInactiveLoaders.clear();
                }

/* 789 */       public String toString() {
/* 790 */           StringBuilder sb = new StringBuilder(128);
/* 791 */           sb.append("LoaderManager{");
/* 792 */           sb.append(Integer.toHexString(System.identityHashCode(this)));
/* 793 */           sb.append(" in ");
/* 794 */           DebugUtils.buildShortClassTag(this.mActivity, sb);
/* 795 */           sb.append("}}");
/* 796 */           return sb.toString();
                }

                @Override // android.support.v4.app.LoaderManager
/* 800 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 801 */           if (this.mLoaders.size() > 0) {
/* 802 */               writer.print(prefix);
                        writer.println("Active Loaders:");
/* 803 */               String innerPrefix = prefix + "    ";
                        for (int i = 0; i < this.mLoaders.size(); i++) {
/* 805 */                   LoaderInfo li = this.mLoaders.valueAt(i);
/* 806 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(this.mLoaders.keyAt(i));
/* 807 */                   writer.print(": ");
                            writer.println(li.toString());
/* 808 */                   li.dump(innerPrefix, fd, writer, args);
                        }
                    }
/* 811 */           if (this.mInactiveLoaders.size() > 0) {
/* 812 */               writer.print(prefix);
                        writer.println("Inactive Loaders:");
/* 813 */               String innerPrefix2 = prefix + "    ";
                        for (int i2 = 0; i2 < this.mInactiveLoaders.size(); i2++) {
/* 815 */                   LoaderInfo li2 = this.mInactiveLoaders.valueAt(i2);
/* 816 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(this.mInactiveLoaders.keyAt(i2));
/* 817 */                   writer.print(": ");
                            writer.println(li2.toString());
/* 818 */                   li2.dump(innerPrefix2, fd, writer, args);
                        }
                    }
                }

                @Override // android.support.v4.app.LoaderManager
/* 824 */       public boolean hasRunningLoaders() {
/* 825 */           boolean loadersRunning = false;
/* 826 */           int count = this.mLoaders.size();
                    for (int i = 0; i < count; i++) {
/* 828 */               LoaderInfo li = this.mLoaders.valueAt(i);
/* 829 */               loadersRunning |= li.mStarted && !li.mDeliveredData;
                    }
/* 831 */           return loadersRunning;
                }
            }
