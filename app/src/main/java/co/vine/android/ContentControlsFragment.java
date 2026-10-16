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
            
            
    public class ContentControlsFragment extends BaseControllerFragment implements CompoundButton.OnCheckedChangeListener {
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

        static  boolean access$002(ContentControlsFragment x0, boolean x1) {
            x0.mPrivate = x1;
                    return x1;
                }

        static  ProgressDialog access$100(ContentControlsFragment x0) {
            return x0.mProgress;
                }

        static  ImageView access$200(ContentControlsFragment x0) {
            return x0.mPrivateIcon;
                }

        static  SwitchInterface access$300(ContentControlsFragment x0) {
            return x0.mPrivateSwitch;
                }

        static  boolean access$402(ContentControlsFragment x0, boolean x1) {
            x0.mExplicit = x1;
                    return x1;
                }

        static  ImageView access$500(ContentControlsFragment x0) {
            return x0.mExplicitIcon;
                }

        static  SwitchInterface access$600(ContentControlsFragment x0) {
            return x0.mExplicitSwitch;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ContentControlsListener();
            if (savedInstanceState != null) {
                this.mPrivate = savedInstanceState.getBoolean(STATE_PRIVATE, false);
                this.mExplicit = savedInstanceState.getBoolean(STATE_EXPLICIT, false);
                    } else {
                SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
                this.mPrivate = prefs.getBoolean(Settings.PREF_PRIVATE, false);
                this.mExplicit = prefs.getBoolean(Settings.PREF_EXPLICIT, false);
                    }
            ProgressDialog prog = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
            prog.setMessage(getString(R.string.content_controls_updating));
            prog.setProgressStyle(0);
            this.mProgress = prog;
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putBoolean(STATE_PRIVATE, this.mPrivate);
            outState.putBoolean(STATE_EXPLICIT, this.mExplicit);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(getActivity(), 1.1f, 10.0f, (TextView) this.mView.findViewById(R.id.text1), (TextView) this.mView.findViewById(R.id.text2));
            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(getActivity(), 1.1f, 5.0f, (TextView) this.mView.findViewById(R.id.text3), (TextView) this.mView.findViewById(R.id.explicit_description_settings));
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            this.mView = inflater.inflate(R.layout.content_controls, container, false);
            return this.mView;
                }

                @Override 
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            SwitchInterface pSwitch = (SwitchInterface) view.findViewById(R.id.private_switch);
            SwitchInterface eSwitch = (SwitchInterface) view.findViewById(R.id.explicit_switch);
            this.mPrivateIcon = (ImageView) view.findViewById(R.id.private_icon);
           if (this.mPrivate) {
               this.mPrivateIcon.setImageResource(R.drawable.ic_settings_private_on);
               pSwitch.setChecked(true);
                    } else {
               this.mPrivateIcon.setImageResource(R.drawable.ic_settings_private_off);
                    }
           if (BuildUtil.isExplore()) {
               view.findViewById(R.id.explicit_row_container_settings).setVisibility(8);
               view.findViewById(R.id.explicit_description_settings).setVisibility(8);
                    } else {
               this.mExplicitIcon = (ImageView) view.findViewById(R.id.explicit_icon);
               if (this.mExplicit) {
                   this.mExplicitIcon.setImageResource(R.drawable.ic_settings_explicit_on);
                   eSwitch.setChecked(true);
                        } else {
                   this.mExplicitIcon.setImageResource(R.drawable.ic_settings_explicit_off);
                        }
                    }
           pSwitch.setOnCheckedChangeListener(this);
           eSwitch.setOnCheckedChangeListener(this);
           this.mPrivateSwitch = pSwitch;
           this.mExplicitSwitch = eSwitch;
                }

                
       class C01731 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01731() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       ContentControlsFragment.access$002(ContentControlsFragment.this, true);
                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
                       ContentControlsFragment.this.mAppController.updatePrivate(ContentControlsFragment.this.mAppController.getActiveSession(), true);
                                break;
                            default:
                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
                       ContentControlsFragment.access$200(ContentControlsFragment.this).setImageResource(R.drawable.ic_settings_private_off);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(null);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setChecked(false);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                
       class C01742 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01742() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
                       ContentControlsFragment.this.mAppController.updatePrivate(ContentControlsFragment.this.mAppController.getActiveSession(), false);
                                break;
                            default:
                       ContentControlsFragment.access$002(ContentControlsFragment.this, false);
                       ContentControlsFragment.access$200(ContentControlsFragment.this).setImageResource(R.drawable.ic_settings_private_on);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(null);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setChecked(true);
                       ContentControlsFragment.access$300(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                
       class C01753 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01753() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       ContentControlsFragment.access$402(ContentControlsFragment.this, true);
                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
                       ContentControlsFragment.this.mAppController.updateExplicit(ContentControlsFragment.this.mAppController.getActiveSession(), true);
                                break;
                            default:
                       ContentControlsFragment.access$402(ContentControlsFragment.this, false);
                       ContentControlsFragment.access$500(ContentControlsFragment.this).setImageResource(R.drawable.ic_settings_explicit_off);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(null);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setChecked(false);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                
       class C01764 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01764() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       ContentControlsFragment.access$402(ContentControlsFragment.this, false);
                       ContentControlsFragment.access$100(ContentControlsFragment.this).show();
                       ContentControlsFragment.this.mAppController.updateExplicit(ContentControlsFragment.this.mAppController.getActiveSession(), false);
                                break;
                            default:
                       ContentControlsFragment.access$402(ContentControlsFragment.this, true);
                       ContentControlsFragment.access$500(ContentControlsFragment.this).setImageResource(R.drawable.ic_settings_explicit_on);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(null);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setChecked(true);
                       ContentControlsFragment.access$600(ContentControlsFragment.this).setOnCheckedChangeListener(ContentControlsFragment.this);
                                break;
                        }
                    }
                }

                @Override 
       public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
           int id = compoundButton.getId();
           if (id == R.id.private_switch) {
               if (on) {
                   this.mPrivateIcon.setImageResource(R.drawable.ic_settings_private_on);
                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
                   p.setTitle(R.string.content_controls_protect_my_posts);
                   p.setMessage(R.string.content_controls_protect_my_posts_message);
                   p.setNegativeButton(R.string.cancel);
                   p.setPositiveButton(R.string.content_controls_protect);
                   p.setListener(this.mProtectPostDialogDoneListner);
                   p.show(getActivity().getSupportFragmentManager());
                   return;
                        }
               this.mPrivateIcon.setImageResource(R.drawable.ic_settings_private_off);
               PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
               p2.setTitle(R.string.content_controls_unprotect_my_posts);
               p2.setMessage(R.string.content_controls_unprotect_my_posts_message);
               p2.setNegativeButton(R.string.cancel);
               p2.setPositiveButton(R.string.content_controls_unprotect);
               p2.setListener(this.mUnProtectPostDialogDoneListner);
               p2.show(getActivity().getSupportFragmentManager());
               return;
                    }
           if (id == R.id.explicit_switch) {
               if (on) {
                   this.mExplicitIcon.setImageResource(R.drawable.ic_settings_explicit_on);
                   PromptDialogSupportFragment p3 = PromptDialogSupportFragment.newInstance(3);
                   p3.setTitle(R.string.content_controls_explicit_my_posts);
                   p3.setMessage(R.string.content_controls_explicit_my_posts_message);
                   p3.setNegativeButton(R.string.cancel);
                   p3.setPositiveButton(R.string.content_controls_make_explicit);
                   p3.setListener(this.mExplictOnDoneListner);
                   p3.show(getActivity().getSupportFragmentManager());
                   return;
                        }
               this.mExplicitIcon.setImageResource(R.drawable.ic_settings_explicit_off);
               PromptDialogSupportFragment p4 = PromptDialogSupportFragment.newInstance(4);
               p4.setTitle(R.string.content_controls_unexplicit_my_posts);
               p4.setMessage(R.string.content_controls_unexplicit_my_posts_message);
               p4.setNegativeButton(R.string.cancel);
               p4.setPositiveButton(R.string.content_controls_unmake_explicit);
               p4.setListener(this.mExplictOffDoneListner);
               p4.show(getFragmentManager());
                    }
                }

       class ContentControlsListener extends AppSessionListener {
           ContentControlsListener() {
                    }

                    @Override 
           public void onUpdatePrivateComplete(String reqId, int statusCode, String reasonPhrase, boolean priv) {
               if (ContentControlsFragment.access$100(ContentControlsFragment.this) != null) {
                   ContentControlsFragment.access$100(ContentControlsFragment.this).dismiss();
                        }
               Activity activity = ContentControlsFragment.this.getActivity();
               if (statusCode == 200) {
                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(activity).edit();
                   editor.putBoolean(Settings.PREF_PRIVATE, priv);
                   editor.apply();
                        } else if (!TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(activity, reasonPhrase);
                        } else {
                   Util.showCenteredToast(activity, R.string.content_controls_update_fail);
                        }
                    }

                    @Override 
           public void onUpdateExplicitComplete(String reqId, int statusCode, String reasonPhrase, boolean explicit) {
               if (ContentControlsFragment.access$100(ContentControlsFragment.this) != null) {
                   ContentControlsFragment.access$100(ContentControlsFragment.this).dismiss();
                        }
               Activity activity = ContentControlsFragment.this.getActivity();
               if (statusCode == 200) {
                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(ContentControlsFragment.this.getActivity()).edit();
                   editor.putBoolean(Settings.PREF_EXPLICIT, explicit);
                   editor.apply();
                        } else if (!TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(activity, reasonPhrase);
                        } else {
                   Util.showCenteredToast(activity, R.string.content_controls_update_fail);
                        }
                    }
                }
            }
