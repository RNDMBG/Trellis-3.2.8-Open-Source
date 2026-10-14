            package co.vine.android;

            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.SwitchInterface;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class ContentControlsFragment extends BaseControllerFragment implements CompoundButton.OnCheckedChangeListener {
                private static final int DIALOG_MAKE_EXPLICIT = 3;
                private static final int DIALOG_MAKE_UNEXPLICIT = 4;
                private static final int DIALOG_PROTECT_POSTS = 1;
                private static final int DIALOG_UNPROTECT_POSTS = 2;
                private static final String STATE_EXPLICIT = "state_explicit";
                private static final String STATE_PRIVATE = "state_private";
                private boolean mExplicit;
                private ImageView mExplicitIcon;
                private SwitchInterface mExplicitSwitch;
                private boolean mPrivate;
                private ImageView mPrivateIcon;
                private SwitchInterface mPrivateSwitch;
                private ProgressDialog mProgress;
                private View mView;
                private final PromptDialogSupportFragment.OnDialogDoneListener mProtectPostDialogDoneListner = new C01731();
                private final PromptDialogSupportFragment.OnDialogDoneListener mUnProtectPostDialogDoneListner = new C01742();
                private final PromptDialogSupportFragment.OnDialogDoneListener mExplictOnDoneListner = new C01753();
                private final PromptDialogSupportFragment.OnDialogDoneListener mExplictOffDoneListner = new C01764();

/* 25 */        static /* synthetic */ boolean access$002(ContentControlsFragment x0, boolean x1) {
/* 26 */            x0.mPrivate = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ ProgressDialog access$100(ContentControlsFragment x0) {
/* 26 */            return x0.mProgress;
                }

/* 25 */        static /* synthetic */ ImageView access$200(ContentControlsFragment x0) {
/* 26 */            return x0.mPrivateIcon;
                }

/* 25 */        static /* synthetic */ SwitchInterface access$300(ContentControlsFragment x0) {
/* 26 */            return x0.mPrivateSwitch;
                }

/* 25 */        static /* synthetic */ boolean access$402(ContentControlsFragment x0, boolean x1) {
/* 26 */            x0.mExplicit = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ ImageView access$500(ContentControlsFragment x0) {
/* 26 */            return x0.mExplicitIcon;
                }

/* 25 */        static /* synthetic */ SwitchInterface access$600(ContentControlsFragment x0) {
/* 26 */            return x0.mExplicitSwitch;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 48 */        public void onCreate(Bundle savedInstanceState) {
/* 49 */            super.onCreate(savedInstanceState);
/* 50 */            this.mAppSessionListener = new ContentControlsListener();
/* 52 */            if (savedInstanceState != null) {
/* 53 */                this.mPrivate = savedInstanceState.getBoolean(STATE_PRIVATE, false);
/* 54 */                this.mExplicit = savedInstanceState.getBoolean(STATE_EXPLICIT, false);
                    } else {
/* 56 */                SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
/* 57 */                this.mPrivate = prefs.getBoolean(Settings.PREF_PRIVATE, false);
/* 58 */                this.mExplicit = prefs.getBoolean(Settings.PREF_EXPLICIT, false);
                    }
/* 60 */            ProgressDialog prog = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 61 */            prog.setMessage(getString(C0257R.string.content_controls_updating));
/* 62 */            prog.setProgressStyle(0);
/* 63 */            this.mProgress = prog;
                }

                @Override // android.support.v4.app.Fragment
/* 67 */        public void onSaveInstanceState(Bundle outState) {
/* 68 */            super.onSaveInstanceState(outState);
/* 69 */            outState.putBoolean(STATE_PRIVATE, this.mPrivate);
/* 70 */            outState.putBoolean(STATE_EXPLICIT, this.mExplicit);
                }

                @Override // android.support.v4.app.Fragment
/* 74 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 75 */            super.onActivityCreated(savedInstanceState);
/* 76 */            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(getActivity(), 1.1f, 10.0f, (TextView) this.mView.findViewById(2131427459), (TextView) this.mView.findViewById(2131427465));
/* 80 */            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(getActivity(), 1.1f, 5.0f, (TextView) this.mView.findViewById(C0257R.id.text3), (TextView) this.mView.findViewById(C0257R.id.explicit_description_settings));
                }

                @Override // android.support.v4.app.Fragment
/* 88 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 89 */            this.mView = inflater.inflate(C0257R.layout.content_controls, container, false);
/* 90 */            return this.mView;
                }

                @Override // android.support.v4.app.Fragment
/* 94 */        public void onViewCreated(View view, Bundle savedInstanceState) {
/* 95 */            super.onViewCreated(view, savedInstanceState);
/* 96 */            SwitchInterface pSwitch = (SwitchInterface) view.findViewById(C0257R.id.private_switch);
/* 97 */            SwitchInterface eSwitch = (SwitchInterface) view.findViewById(C0257R.id.explicit_switch);
/* 99 */            this.mPrivateIcon = (ImageView) view.findViewById(C0257R.id.private_icon);
/* 100 */           if (this.mPrivate) {
/* 101 */               this.mPrivateIcon.setImageResource(C0257R.drawable.ic_settings_private_on);
/* 102 */               pSwitch.setChecked(true);
                    } else {
/* 104 */               this.mPrivateIcon.setImageResource(C0257R.drawable.ic_settings_private_off);
                    }
/* 107 */           if (BuildUtil.isExplore()) {
/* 108 */               view.findViewById(C0257R.id.explicit_row_container_settings).setVisibility(8);
/* 109 */               view.findViewById(C0257R.id.explicit_description_settings).setVisibility(8);
                    } else {
/* 111 */               this.mExplicitIcon = (ImageView) view.findViewById(C0257R.id.explicit_icon);
/* 112 */               if (this.mExplicit) {
/* 113 */                   this.mExplicitIcon.setImageResource(C0257R.drawable.ic_settings_explicit_on);
/* 114 */                   eSwitch.setChecked(true);
                        } else {
/* 116 */                   this.mExplicitIcon.setImageResource(C0257R.drawable.ic_settings_explicit_off);
                        }
                    }
/* 120 */           pSwitch.setOnCheckedChangeListener(this);
/* 121 */           eSwitch.setOnCheckedChangeListener(this);
/* 123 */           this.mPrivateSwitch = pSwitch;
/* 124 */           this.mExplicitSwitch = eSwitch;
                }

                /* JADX INFO: renamed from: co.vine.android.ContentControlsFragment$1 */
/* 126 */       class C01731 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 127 */           C01731() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 130 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 133 */                       ContentControlsFragment.access$002(ContentControlsFragment.this, true);
/* 134 */                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
/* 135 */                       ContentControlsFragment.this.mAppController.updatePrivate(ContentControlsFragment.this.mAppController.getActiveSession(), true);
                                break;
                            default:
/* 139 */                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
/* 140 */                       ContentControlsFragment.access$200(ContentControlsFragment.this).setImageResource(C0257R.drawable.ic_settings_private_off);
/* 141 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(null);
/* 142 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setChecked(false);
/* 143 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ContentControlsFragment$2 */
/* 149 */       class C01742 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 150 */           C01742() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 153 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 156 */                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
/* 157 */                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
/* 158 */                       ContentControlsFragment.this.mAppController.updatePrivate(ContentControlsFragment.this.mAppController.getActiveSession(), false);
                                break;
                            default:
/* 162 */                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
/* 163 */                       ContentControlsFragment.access$200(ContentControlsFragment.this).setImageResource(C0257R.drawable.ic_settings_private_on);
/* 164 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(null);
/* 165 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setChecked(true);
/* 166 */                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ContentControlsFragment$3 */
/* 171 */       class C01753 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 172 */           C01753() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 175 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 178 */                       ContentControlsFragment.access$402(ContentControlsFragment.this, true);
/* 179 */                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
/* 180 */                       ContentControlsFragment.this.mAppController.updateExplicit(ContentControlsFragment.this.mAppController.getActiveSession(), true);
                                break;
                            default:
/* 184 */                       ContentControlsFragment.access$402(ContentControlsFragment.this, false);
/* 185 */                       ContentControlsFragment.access$500(ContentControlsFragment.this).setImageResource(C0257R.drawable.ic_settings_explicit_off);
/* 186 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(null);
/* 187 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setChecked(false);
/* 188 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ContentControlsFragment$4 */
/* 195 */       class C01764 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 196 */           C01764() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 199 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 202 */                       ContentControlsFragment.access$402(ContentControlsFragment.this, false);
/* 203 */                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
/* 204 */                       ContentControlsFragment.this.mAppController.updateExplicit(ContentControlsFragment.this.mAppController.getActiveSession(), false);
                                break;
                            default:
/* 208 */                       ContentControlsFragment.access$402(ContentControlsFragment.this, true);
/* 209 */                       ContentControlsFragment.access$500(ContentControlsFragment.this).setImageResource(C0257R.drawable.ic_settings_explicit_on);
/* 210 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(null);
/* 211 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setChecked(true);
/* 212 */                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 220 */       public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
/* 221 */           int id = compoundButton.getId();
/* 222 */           if (id == 2131427460) {
/* 223 */               if (on) {
/* 224 */                   this.mPrivateIcon.setImageResource(C0257R.drawable.ic_settings_private_on);
/* 225 */                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 227 */                   p.setTitle(C0257R.string.content_controls_protect_my_posts);
/* 228 */                   p.setMessage(C0257R.string.content_controls_protect_my_posts_message);
/* 229 */                   p.setNegativeButton(C0257R.string.cancel);
/* 230 */                   p.setPositiveButton(C0257R.string.content_controls_protect);
/* 231 */                   p.setListener(this.mProtectPostDialogDoneListner);
/* 232 */                   p.show(getActivity().getSupportFragmentManager());
/* 267 */                   return;
                        }
/* 234 */               this.mPrivateIcon.setImageResource(C0257R.drawable.ic_settings_private_off);
/* 235 */               PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
/* 237 */               p2.setTitle(C0257R.string.content_controls_unprotect_my_posts);
/* 238 */               p2.setMessage(C0257R.string.content_controls_unprotect_my_posts_message);
/* 239 */               p2.setNegativeButton(C0257R.string.cancel);
/* 240 */               p2.setPositiveButton(C0257R.string.content_controls_unprotect);
/* 241 */               p2.setListener(this.mUnProtectPostDialogDoneListner);
/* 242 */               p2.show(getActivity().getSupportFragmentManager());
/* 267 */               return;
                    }
/* 244 */           if (id == 2131427466) {
/* 245 */               if (on) {
/* 246 */                   this.mExplicitIcon.setImageResource(C0257R.drawable.ic_settings_explicit_on);
/* 247 */                   PromptDialogSupportFragment p3 = PromptDialogSupportFragment.newInstance(3);
/* 249 */                   p3.setTitle(C0257R.string.content_controls_explicit_my_posts);
/* 250 */                   p3.setMessage(C0257R.string.content_controls_explicit_my_posts_message);
/* 251 */                   p3.setNegativeButton(C0257R.string.cancel);
/* 252 */                   p3.setPositiveButton(C0257R.string.content_controls_make_explicit);
/* 253 */                   p3.setListener(this.mExplictOnDoneListner);
/* 254 */                   p3.show(getActivity().getSupportFragmentManager());
/* 267 */                   return;
                        }
/* 256 */               this.mExplicitIcon.setImageResource(C0257R.drawable.ic_settings_explicit_off);
/* 257 */               PromptDialogSupportFragment p4 = PromptDialogSupportFragment.newInstance(4);
/* 259 */               p4.setTitle(C0257R.string.content_controls_unexplicit_my_posts);
/* 260 */               p4.setMessage(C0257R.string.content_controls_unexplicit_my_posts_message);
/* 261 */               p4.setNegativeButton(C0257R.string.cancel);
/* 262 */               p4.setPositiveButton(C0257R.string.content_controls_unmake_explicit);
/* 263 */               p4.setListener(this.mExplictOffDoneListner);
/* 264 */               p4.show(getFragmentManager());
                    }
                }

/* 267 */       class ContentControlsListener extends AppSessionListener {
/* 268 */           ContentControlsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 272 */           public void onUpdatePrivateComplete(String reqId, int statusCode, String reasonPhrase, boolean priv) {
/* 273 */               if (ContentControlsFragment.access$100(ContentControlsFragment.this) != null) {
/* 274 */                   ContentControlsFragment.access$100(ContentControlsFragment.this).dismiss();
                        }
/* 276 */               Activity activity = ContentControlsFragment.this.getActivity();
/* 277 */               if (statusCode == 200) {
/* 278 */                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(activity).edit();
/* 279 */                   editor.putBoolean(Settings.PREF_PRIVATE, priv);
/* 280 */                   editor.apply();
                        } else if (!TextUtils.isEmpty(reasonPhrase)) {
/* 283 */                   Util.showCenteredToast(activity, reasonPhrase);
                        } else {
/* 285 */                   Util.showCenteredToast(activity, C0257R.string.content_controls_update_fail);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 292 */           public void onUpdateExplicitComplete(String reqId, int statusCode, String reasonPhrase, boolean explicit) {
/* 293 */               if (ContentControlsFragment.access$100(ContentControlsFragment.this) != null) {
/* 294 */                   ContentControlsFragment.access$100(ContentControlsFragment.this).dismiss();
                        }
/* 296 */               Activity activity = ContentControlsFragment.this.getActivity();
/* 297 */               if (statusCode == 200) {
/* 298 */                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(ContentControlsFragment.this.getActivity()).edit();
/* 299 */                   editor.putBoolean(Settings.PREF_EXPLICIT, explicit);
/* 300 */                   editor.apply();
                        } else if (!TextUtils.isEmpty(reasonPhrase)) {
/* 303 */                   Util.showCenteredToast(activity, reasonPhrase);
                        } else {
/* 305 */                   Util.showCenteredToast(activity, C0257R.string.content_controls_update_fail);
                        }
                    }
                }
            }
