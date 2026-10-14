            package android.support.v4.app;

            import android.R;
            import android.app.Activity;
            import android.app.Dialog;
            import android.content.DialogInterface;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 100 */   public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
                private static final String SAVED_BACK_STACK_ID = "android:backStackId";
                private static final String SAVED_CANCELABLE = "android:cancelable";
                private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
                private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
                private static final String SAVED_STYLE = "android:style";
                private static final String SAVED_THEME = "android:theme";
                public static final int STYLE_NORMAL = 0;
                public static final int STYLE_NO_FRAME = 2;
                public static final int STYLE_NO_INPUT = 3;
                public static final int STYLE_NO_TITLE = 1;
                Dialog mDialog;
                boolean mDismissed;
                boolean mShownByMe;
                boolean mViewDestroyed;
                int mStyle = 0;
                int mTheme = 0;
                boolean mCancelable = true;
                boolean mShowsDialog = true;
                int mBackStackId = -1;

/* 101 */       public void setStyle(int style, int theme) {
/* 102 */           this.mStyle = style;
/* 103 */           if (this.mStyle == 2 || this.mStyle == 3) {
/* 104 */               this.mTheme = R.style.Theme.Panel;
                    }
/* 106 */           if (theme != 0) {
/* 107 */               this.mTheme = theme;
                    }
                }

/* 122 */       public void show(FragmentManager manager, String tag) {
/* 123 */           this.mDismissed = false;
/* 124 */           this.mShownByMe = true;
/* 125 */           FragmentTransaction ft = manager.beginTransaction();
/* 126 */           ft.add(this, tag);
/* 127 */           ft.commit();
                }

/* 139 */       public int show(FragmentTransaction transaction, String tag) {
/* 140 */           this.mDismissed = false;
/* 141 */           this.mShownByMe = true;
/* 142 */           transaction.add(this, tag);
/* 143 */           this.mViewDestroyed = false;
/* 144 */           this.mBackStackId = transaction.commit();
/* 145 */           return this.mBackStackId;
                }

/* 154 */       public void dismiss() {
/* 155 */           dismissInternal(false);
                }

/* 164 */       public void dismissAllowingStateLoss() {
/* 165 */           dismissInternal(true);
                }

/* 168 */       void dismissInternal(boolean allowStateLoss) {
/* 169 */           if (!this.mDismissed) {
/* 172 */               this.mDismissed = true;
/* 173 */               this.mShownByMe = false;
/* 174 */               if (this.mDialog != null) {
/* 175 */                   this.mDialog.dismiss();
/* 176 */                   this.mDialog = null;
                        }
/* 178 */               this.mViewDestroyed = true;
/* 179 */               if (this.mBackStackId >= 0) {
/* 180 */                   getFragmentManager().popBackStack(this.mBackStackId, 1);
/* 182 */                   this.mBackStackId = -1;
/* 192 */                   return;
                        }
/* 184 */               FragmentTransaction ft = getFragmentManager().beginTransaction();
/* 185 */               ft.remove(this);
/* 186 */               if (allowStateLoss) {
/* 187 */                   ft.commitAllowingStateLoss();
                        } else {
/* 189 */                   ft.commit();
                        }
                    }
                }

/* 194 */       public Dialog getDialog() {
/* 195 */           return this.mDialog;
                }

/* 198 */       public int getTheme() {
/* 199 */           return this.mTheme;
                }

/* 211 */       public void setCancelable(boolean cancelable) {
/* 212 */           this.mCancelable = cancelable;
/* 213 */           if (this.mDialog != null) {
                        this.mDialog.setCancelable(cancelable);
                    }
                }

/* 219 */       public boolean isCancelable() {
/* 220 */           return this.mCancelable;
                }

/* 240 */       public void setShowsDialog(boolean showsDialog) {
/* 241 */           this.mShowsDialog = showsDialog;
                }

/* 247 */       public boolean getShowsDialog() {
/* 248 */           return this.mShowsDialog;
                }

                @Override // android.support.v4.app.Fragment
/* 252 */       public void onAttach(Activity activity) {
/* 253 */           super.onAttach(activity);
/* 254 */           if (!this.mShownByMe) {
/* 257 */               this.mDismissed = false;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 262 */       public void onDetach() {
/* 263 */           super.onDetach();
/* 264 */           if (!this.mShownByMe && !this.mDismissed) {
/* 268 */               this.mDismissed = true;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 273 */       public void onCreate(Bundle savedInstanceState) {
/* 274 */           super.onCreate(savedInstanceState);
/* 276 */           this.mShowsDialog = this.mContainerId == 0;
/* 278 */           if (savedInstanceState != null) {
/* 279 */               this.mStyle = savedInstanceState.getInt(SAVED_STYLE, 0);
/* 280 */               this.mTheme = savedInstanceState.getInt(SAVED_THEME, 0);
/* 281 */               this.mCancelable = savedInstanceState.getBoolean(SAVED_CANCELABLE, true);
/* 282 */               this.mShowsDialog = savedInstanceState.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
/* 283 */               this.mBackStackId = savedInstanceState.getInt(SAVED_BACK_STACK_ID, -1);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 290 */       public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
/* 291 */           if (!this.mShowsDialog) {
/* 292 */               return super.getLayoutInflater(savedInstanceState);
                    }
/* 295 */           this.mDialog = onCreateDialog(savedInstanceState);
                    switch (this.mStyle) {
                        case 3:
/* 298 */                   this.mDialog.getWindow().addFlags(24);
                        case 1:
                        case 2:
/* 304 */                   this.mDialog.requestWindowFeature(1);
                            break;
                    }
/* 306 */           if (this.mDialog != null) {
/* 307 */               return (LayoutInflater) this.mDialog.getContext().getSystemService("layout_inflater");
                    }
/* 310 */           return (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
                }

/* 336 */       public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 337 */           return new Dialog(getActivity(), getTheme());
                }

                @Override // android.content.DialogInterface.OnCancelListener
/* 340 */       public void onCancel(DialogInterface dialog) {
                }

                @Override // android.content.DialogInterface.OnDismissListener
/* 343 */       public void onDismiss(DialogInterface dialog) {
/* 344 */           if (!this.mViewDestroyed) {
/* 349 */               dismissInternal(true);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 354 */       public void onActivityCreated(Bundle savedInstanceState) {
                    Bundle dialogState;
/* 355 */           super.onActivityCreated(savedInstanceState);
/* 357 */           if (this.mShowsDialog) {
/* 361 */               View view = getView();
/* 362 */               if (view != null) {
/* 363 */                   if (view.getParent() != null) {
/* 364 */                       throw new IllegalStateException("DialogFragment can not be attached to a container view");
                            }
/* 366 */                   this.mDialog.setContentView(view);
                        }
/* 368 */               this.mDialog.setOwnerActivity(getActivity());
/* 369 */               this.mDialog.setCancelable(this.mCancelable);
/* 370 */               this.mDialog.setOnCancelListener(this);
/* 371 */               this.mDialog.setOnDismissListener(this);
/* 372 */               if (savedInstanceState != null && (dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG)) != null) {
/* 375 */                   this.mDialog.onRestoreInstanceState(dialogState);
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 381 */       public void onStart() {
/* 382 */           super.onStart();
/* 383 */           if (this.mDialog != null) {
/* 384 */               this.mViewDestroyed = false;
/* 385 */               this.mDialog.show();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 390 */       public void onSaveInstanceState(Bundle outState) {
                    Bundle dialogState;
/* 391 */           super.onSaveInstanceState(outState);
/* 392 */           if (this.mDialog != null && (dialogState = this.mDialog.onSaveInstanceState()) != null) {
/* 395 */               outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState);
                    }
/* 398 */           if (this.mStyle != 0) {
/* 399 */               outState.putInt(SAVED_STYLE, this.mStyle);
                    }
/* 401 */           if (this.mTheme != 0) {
/* 402 */               outState.putInt(SAVED_THEME, this.mTheme);
                    }
/* 404 */           if (!this.mCancelable) {
/* 405 */               outState.putBoolean(SAVED_CANCELABLE, this.mCancelable);
                    }
/* 407 */           if (!this.mShowsDialog) {
/* 408 */               outState.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
                    }
/* 410 */           if (this.mBackStackId != -1) {
/* 411 */               outState.putInt(SAVED_BACK_STACK_ID, this.mBackStackId);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 416 */       public void onStop() {
/* 417 */           super.onStop();
/* 418 */           if (this.mDialog != null) {
/* 419 */               this.mDialog.hide();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 427 */       public void onDestroyView() {
/* 428 */           super.onDestroyView();
/* 429 */           if (this.mDialog != null) {
/* 433 */               this.mViewDestroyed = true;
/* 434 */               this.mDialog.dismiss();
/* 435 */               this.mDialog = null;
                    }
                }
            }
