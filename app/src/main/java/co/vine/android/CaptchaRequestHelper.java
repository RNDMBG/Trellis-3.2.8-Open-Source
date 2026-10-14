            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import co.vine.android.service.VineService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public abstract class CaptchaRequestHelper {
                public static final int REQUEST_CODE_CAPTCHA = 11;
                protected static final String STATE_PENDING_CAPTCHA_REQUEST = "state_pending_captcha_request";
                protected PendingCaptchaRequest mPendingCaptchaRequest;

                protected abstract void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest);

                protected abstract void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest);

/* 21 */        public void setPendingCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 22 */            this.mPendingCaptchaRequest = pendingCaptchaRequest;
                }

/* 25 */        public void restorePendingCaptchaRequest(Bundle savedInstanceState) {
/* 26 */            this.mPendingCaptchaRequest = (PendingCaptchaRequest) savedInstanceState.getParcelable(STATE_PENDING_CAPTCHA_REQUEST);
                }

/* 29 */        public void onCaptchaRequired(Activity activity, String reqId, int actionCode, Bundle bundle, String captchaUrl) {
/* 30 */            setPendingCaptchaRequest(new PendingCaptchaRequest(reqId, actionCode, bundle));
/* 31 */            if (activity != null) {
/* 32 */                Intent i = CaptchaActivity.getIntent(activity, captchaUrl, reqId);
/* 33 */                activity.startActivityForResult(i, 11);
                    }
                }

/* 40 */        public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 11:
/* 43 */                    if (this.mPendingCaptchaRequest != null && data != null) {
/* 44 */                        String reqId = data.getStringExtra(VineService.EXTRA_REQUEST_ID);
/* 45 */                        if (resultCode == -1 && reqId.equals(this.mPendingCaptchaRequest.reqId)) {
/* 47 */                            this.mPendingCaptchaRequest.state = 2;
                                } else if (resultCode == 2 && reqId.equals(this.mPendingCaptchaRequest.reqId)) {
/* 51 */                            this.mPendingCaptchaRequest.state = 3;
                                }
                            }
/* 55 */                    return true;
                        default:
/* 58 */                    return false;
                    }
                }

/* 61 */        public void handlePendingCaptchaRequest() {
/* 62 */            if (this.mPendingCaptchaRequest != null) {
/* 63 */                if (this.mPendingCaptchaRequest.state == 3) {
/* 64 */                    handleFailedCaptchaRequest(this.mPendingCaptchaRequest);
                        } else if (this.mPendingCaptchaRequest.state == 2) {
/* 66 */                    handleRetryCaptchaRequest(this.mPendingCaptchaRequest);
                        }
/* 68 */                this.mPendingCaptchaRequest = null;
                    }
                }

/* 72 */        public void onSaveInstanceState(Bundle outState) {
/* 73 */            outState.putParcelable(STATE_PENDING_CAPTCHA_REQUEST, this.mPendingCaptchaRequest);
                }
            }
