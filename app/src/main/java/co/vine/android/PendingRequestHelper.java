            package co.vine.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
            import android.support.v4.app.FragmentActivity;
            import co.vine.android.client.AppController;
            import java.util.ArrayList;
            
            
    public abstract class PendingRequestHelper {
                public static final int PROGRESS_FETCH_DATA = 3;
                public static final int PROGRESS_FETCH_DATA_NEW_AUTO = 4;
                public static final int PROGRESS_FETCH_NEWER = 2;
                public static final int PROGRESS_FETCH_OLDER = 1;
                public static final int PROGRESS_FIRST_USER = 6;
                public static final int PROGRESS_LOAD_DATA = 5;
                public static final int PROGRESS_LOAD_DATA_OLDER = 7;
                public static final int PROGRESS_NONE = 0;
                private static final String STATE_CURRENT_REQ_IDS = "pending_reqs";
                
                
                
                
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

        public void restorePendingCaptchaRequest(Bundle savedInstanceState) {
            this.mCaptchaRequestHelper.restorePendingCaptchaRequest(savedInstanceState);
                }

        public void handlePendingCaptchaRequest() {
            this.mCaptchaRequestHelper.handlePendingCaptchaRequest();
                }

        public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
            return this.mCaptchaRequestHelper.onActivityResult(requestCode, resultCode, data);
                }

        public void onCaptchaRequired(FragmentActivity activity, String reqId, int actionCode, Bundle bundle, String captchaUrl) {
            this.mCaptchaRequestHelper.onCaptchaRequired(activity, reqId, actionCode, bundle, captchaUrl);
                }

                
        class C02491 extends CaptchaRequestHelper {
            C02491() {
                    }

                    @Override 
            protected void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                PendingRequestHelper.this.handleRetryCaptchaRequest(pendingCaptchaRequest);
                    }

                    @Override 
            protected void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                PendingRequestHelper.this.handleFailedCaptchaRequest(pendingCaptchaRequest);
                    }
                }

        public void onCreate(AppController appController, Bundle savedInstanceState) {
            this.mAppController = appController;
            if (savedInstanceState != null) {
                this.mPendingRequests = savedInstanceState.getParcelableArrayList(STATE_CURRENT_REQ_IDS);
                    }
            if (this.mPendingRequests == null) {
                this.mPendingRequests = new ArrayList<>(5);
                    }
                    scheduleStaleRequestCheck();
                }

        public void onResume() {
                    pruneStaleRequests();
                    for (int i = this.mPendingRequests.size() - 1; i >= 0; i--) {
                PendingRequest req = this.mPendingRequests.get(i);
               if (this.mAppController.isPendingRequest(req.f81id)) {
                   showProgress(req.fetchType);
                        } else {
                   hideProgress(req.fetchType);
                   this.mPendingRequests.remove(req);
                        }
                    }
                }

       public void onSaveInstanceState(Bundle outState) {
           outState.putParcelableArrayList(STATE_CURRENT_REQ_IDS, this.mPendingRequests);
           this.mCaptchaRequestHelper.onSaveInstanceState(outState);
                }

       public String addRequest(String reqId) {
           this.mPendingRequests.add(new PendingRequest(reqId));
                    scheduleStaleRequestCheck();
           return reqId;
                }

       public String addRequest(String reqId, int fetchType) {
           this.mPendingRequests.add(new PendingRequest(reqId, fetchType));
                    scheduleStaleRequestCheck();
           return reqId;
                }

       public PendingRequest removeRequest(String reqId) {
           ArrayList<PendingRequest> reqs = this.mPendingRequests;
           int size = reqs.size();
                    for (int i = 0; i < size; i++) {
               PendingRequest req = reqs.get(i);
               if (req.f81id.equals(reqId)) {
                   return reqs.remove(i);
                        }
                    }
           return null;
                }

                




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

       public boolean hasPendingRequest() {
                    pruneStaleRequests();
                    return !this.mPendingRequests.isEmpty();
                }

       public boolean hasPendingRequest(int fetchType) {
                    pruneStaleRequests();
           if (fetchType != 0) {
                        for (PendingRequest req : this.mPendingRequests) {
                   if (req.fetchType == fetchType) {
                       return true;
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

       public void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
           if (this.mAppController != null) {
               addRequest(this.mAppController.retryRequest(pendingCaptchaRequest.actionCode, pendingCaptchaRequest.bundle));
                    }
                }

       public void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
           if (this.mAppController != null) {
               String newRequestToFail = this.mAppController.generateReqIdForCanceledCaptcha();
               addRequest(newRequestToFail);
               this.mAppController.failRequest(newRequestToFail, pendingCaptchaRequest.actionCode, pendingCaptchaRequest.bundle);
                    }
                }
            }
