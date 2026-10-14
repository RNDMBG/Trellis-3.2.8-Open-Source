            package co.vine.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
            import android.support.v4.app.FragmentActivity;
            import co.vine.android.client.AppController;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public abstract class PendingRequestHelper {
                public static final int PROGRESS_FETCH_DATA = 3;
                public static final int PROGRESS_FETCH_DATA_NEW_AUTO = 4;
                public static final int PROGRESS_FETCH_NEWER = 2;
                public static final int PROGRESS_FETCH_OLDER = 1;
                public static final int PROGRESS_FIRST_USER = 6;
                public static final int PROGRESS_LOAD_DATA = 5;
                public static final int PROGRESS_LOAD_DATA_OLDER = 7;
                public static final int PROGRESS_NONE = 0;
                private static final String STATE_CURRENT_REQ_IDS = "pending_reqs";
                // Watchdog for requests whose service callback never arrives (for
                // example a refresh issued right after deleting a post). Healthy
                // responses return in a couple of seconds; 30s bounds the worst
                // case so a pull-to-refresh spinner cannot spin indefinitely.
                private static final long STALE_REQUEST_TIMEOUT_MS = 30000L;
                private AppController mAppController;
                private final CaptchaRequestHelper mCaptchaRequestHelper = new C02491();
                private final Handler mStaleRequestHandler = new Handler(Looper.getMainLooper());
                private final Runnable mStaleRequestChecker = new Runnable() {
                    @Override
                    public void run() {
                        pruneStaleRequests();
                        if (mPendingRequests != null && !mPendingRequests.isEmpty()) {
                            mStaleRequestHandler.postDelayed(this, STALE_REQUEST_TIMEOUT_MS);
                        }
                    }
                };
                private ArrayList<PendingRequest> mPendingRequests;

                public abstract void hideProgress(int i);

                public abstract void showProgress(int i);

/* 51 */        public void restorePendingCaptchaRequest(Bundle savedInstanceState) {
/* 52 */            this.mCaptchaRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                }

/* 55 */        public void handlePendingCaptchaRequest() {
/* 56 */            this.mCaptchaRequestHelper.handlePendingCaptchaRequest();
                }

/* 59 */        public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
/* 60 */            return this.mCaptchaRequestHelper.onActivityResult(requestCode, resultCode, data);
                }

/* 63 */        public void onCaptchaRequired(FragmentActivity activity, String reqId, int actionCode, Bundle bundle, String captchaUrl) {
/* 64 */            this.mCaptchaRequestHelper.onCaptchaRequired(activity, reqId, actionCode, bundle, captchaUrl);
                }

                /* JADX INFO: renamed from: co.vine.android.PendingRequestHelper$1 */
/* 69 */        class C02491 extends CaptchaRequestHelper {
/* 70 */            C02491() {
                    }

                    @Override // co.vine.android.CaptchaRequestHelper
/* 73 */            protected void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 74 */                PendingRequestHelper.this.handleRetryCaptchaRequest(pendingCaptchaRequest);
                    }

                    @Override // co.vine.android.CaptchaRequestHelper
/* 78 */            protected void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 79 */                PendingRequestHelper.this.handleFailedCaptchaRequest(pendingCaptchaRequest);
                    }
                }

/* 83 */        public void onCreate(AppController appController, Bundle savedInstanceState) {
/* 84 */            this.mAppController = appController;
/* 85 */            if (savedInstanceState != null) {
/* 86 */                this.mPendingRequests = savedInstanceState.getParcelableArrayList(STATE_CURRENT_REQ_IDS);
                    }
/* 88 */            if (this.mPendingRequests == null) {
/* 89 */                this.mPendingRequests = new ArrayList<>(5);
                    }
                    scheduleStaleRequestCheck();
                }

/* 97 */        public void onResume() {
                    pruneStaleRequests();
                    for (int i = this.mPendingRequests.size() - 1; i >= 0; i--) {
/* 99 */                PendingRequest req = this.mPendingRequests.get(i);
/* 100 */               if (this.mAppController.isPendingRequest(req.f81id)) {
/* 101 */                   showProgress(req.fetchType);
                        } else {
/* 103 */                   hideProgress(req.fetchType);
/* 104 */                   this.mPendingRequests.remove(req);
                        }
                    }
                }

/* 113 */       public void onSaveInstanceState(Bundle outState) {
/* 114 */           outState.putParcelableArrayList(STATE_CURRENT_REQ_IDS, this.mPendingRequests);
/* 115 */           this.mCaptchaRequestHelper.onSaveInstanceState(outState);
                }

/* 121 */       public String addRequest(String reqId) {
/* 122 */           this.mPendingRequests.add(new PendingRequest(reqId));
                    scheduleStaleRequestCheck();
/* 123 */           return reqId;
                }

/* 129 */       public String addRequest(String reqId, int fetchType) {
/* 130 */           this.mPendingRequests.add(new PendingRequest(reqId, fetchType));
                    scheduleStaleRequestCheck();
/* 131 */           return reqId;
                }

/* 138 */       public PendingRequest removeRequest(String reqId) {
/* 139 */           ArrayList<PendingRequest> reqs = this.mPendingRequests;
/* 140 */           int size = reqs.size();
                    for (int i = 0; i < size; i++) {
/* 142 */               PendingRequest req = reqs.get(i);
/* 143 */               if (req.f81id.equals(reqId)) {
/* 144 */                   return reqs.remove(i);
                        }
                    }
/* 147 */           return null;
                }

                /**
                 * Drops locally tracked requests for a refresh type. The network callback may
                 * still arrive later, but it will no longer leave the UI stuck in a loading state
                 * or prevent a new refresh from being issued.
                 */
                public void clearRequests(int fetchType) {
                    if (this.mPendingRequests == null) {
                        return;
                    }
                    for (int i = this.mPendingRequests.size() - 1; i >= 0; i--) {
                        PendingRequest req = this.mPendingRequests.get(i);
                        if (fetchType == 0 || req.fetchType == fetchType) {
                            hideProgress(req.fetchType);
                            this.mPendingRequests.remove(i);
                        }
                    }
                    scheduleStaleRequestCheck();
                }

/* 153 */       public boolean hasPendingRequest() {
                    pruneStaleRequests();
                    return !this.mPendingRequests.isEmpty();
                }

/* 164 */       public boolean hasPendingRequest(int fetchType) {
                    pruneStaleRequests();
/* 165 */           if (fetchType != 0) {
                        for (PendingRequest req : this.mPendingRequests) {
/* 167 */                   if (req.fetchType == fetchType) {
/* 168 */                       return true;
                            }
                        }
                    }
                    return false;
                }

                private void pruneStaleRequests() {
                    if (this.mPendingRequests == null) {
                        return;
                    }
                    long now = System.currentTimeMillis();
                    for (int i = this.mPendingRequests.size() - 1; i >= 0; i--) {
                        PendingRequest req = this.mPendingRequests.get(i);
                        if (now - req.createdAt >= STALE_REQUEST_TIMEOUT_MS) {
                            hideProgress(req.fetchType);
                            this.mPendingRequests.remove(i);
                        }
                    }
                    scheduleStaleRequestCheck();
                }

                private void scheduleStaleRequestCheck() {
                    mStaleRequestHandler.removeCallbacks(mStaleRequestChecker);
                    if (mPendingRequests != null && !mPendingRequests.isEmpty()) {
                        mStaleRequestHandler.postDelayed(mStaleRequestChecker, STALE_REQUEST_TIMEOUT_MS);
                    }
                }

/* 175 */       public void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 176 */           if (this.mAppController != null) {
/* 177 */               addRequest(this.mAppController.retryRequest(pendingCaptchaRequest.actionCode, pendingCaptchaRequest.bundle));
                    }
                }

/* 182 */       public void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 183 */           if (this.mAppController != null) {
/* 184 */               String newRequestToFail = this.mAppController.generateReqIdForCanceledCaptcha();
/* 185 */               addRequest(newRequestToFail);
/* 186 */               this.mAppController.failRequest(newRequestToFail, pendingCaptchaRequest.actionCode, pendingCaptchaRequest.bundle);
                    }
                }
            }
