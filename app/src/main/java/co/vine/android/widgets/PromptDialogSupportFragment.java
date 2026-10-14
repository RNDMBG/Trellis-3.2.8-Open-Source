            package co.vine.android.widgets;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.AlertDialog;
            import android.app.Dialog;
            import android.content.ComponentCallbacks;
            import android.content.DialogInterface;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.app.DialogFragment;
            import android.support.v4.app.FragmentManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class PromptDialogSupportFragment extends DialogFragment implements DialogInterface.OnClickListener {
                private static final String ARG_DIALOG_ICON = "icon";
                private static final String ARG_DIALOG_ID = "id";
                private static final String ARG_DIALOG_ITEMS = "items";
                private static final String ARG_DIALOG_ITEMS_STRING = "items_string";
                private static final String ARG_DIALOG_MESSAGE = "message";
                private static final String ARG_DIALOG_MESSAGE_STRING = "message_string";
                private static final String ARG_DIALOG_NEGATIVE_BUTTON = "negative_button";
                private static final String ARG_DIALOG_NEUTRAL_BUTTON = "neutral_button";
                private static final String ARG_DIALOG_POSITIVE_BUTTON = "positive_button";
                private static final String ARG_DIALOG_THEME = "theme";
                private static final String ARG_DIALOG_TITLE = "title";
                private OnDialogDoneListener mListener;

                public interface OnDialogDoneListener extends PromptDialogFragment.OnDialogDoneListener {
                }

/* 43 */        public static PromptDialogSupportFragment newInstance(int id, int theme) {
/* 44 */            PromptDialogSupportFragment f = new PromptDialogSupportFragment();
/* 46 */            Bundle bundle = new Bundle();
/* 47 */            bundle.putInt(ARG_DIALOG_ID, id);
/* 48 */            bundle.putInt(ARG_DIALOG_THEME, theme);
/* 49 */            f.setArguments(bundle);
/* 51 */            return f;
                }

/* 54 */        public static PromptDialogSupportFragment newInstance(int id) {
/* 55 */            return newInstance(id, 0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.support.v4.app.DialogFragment, android.support.v4.app.Fragment
/* 59 */        public void onAttach(Activity activity) {
/* 60 */            super.onAttach(activity);
/* 61 */            if (this.mListener == null) {
/* 62 */                ComponentCallbacks targetFragment = getTargetFragment();
/* 63 */                if (targetFragment instanceof OnDialogDoneListener) {
/* 64 */                    this.mListener = (OnDialogDoneListener) targetFragment;
                        } else if (activity instanceof OnDialogDoneListener) {
/* 66 */                    this.mListener = (OnDialogDoneListener) activity;
                        }
                    }
                }

                @Override // android.support.v4.app.DialogFragment
                @TargetApi(11)
/* 73 */        public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 74 */            super.onCreateDialog(savedInstanceState);
/* 76 */            Bundle args = getArguments();
/* 77 */            int theme = args.getInt(ARG_DIALOG_THEME, 2);
/* 78 */            AlertDialog.Builder builder = Build.VERSION.SDK_INT >= 11 ? new AlertDialog.Builder(getActivity(), theme) : new AlertDialog.Builder(getActivity());
/* 84 */            if (args.containsKey("icon")) {
/* 85 */                builder.setIcon(args.getInt("icon"));
                    }
/* 88 */            if (args.containsKey("title")) {
/* 89 */                builder.setTitle(args.getInt("title"));
                    }
/* 92 */            if (args.containsKey("message")) {
/* 93 */                builder.setMessage(args.getInt("message"));
                    }
/* 96 */            if (args.containsKey(ARG_DIALOG_MESSAGE_STRING)) {
/* 97 */                builder.setMessage(args.getString(ARG_DIALOG_MESSAGE_STRING));
                    }
/* 100 */           if (args.containsKey(ARG_DIALOG_POSITIVE_BUTTON)) {
/* 101 */               builder.setPositiveButton(args.getInt(ARG_DIALOG_POSITIVE_BUTTON), this);
                    }
/* 104 */           if (args.containsKey(ARG_DIALOG_NEUTRAL_BUTTON)) {
/* 105 */               builder.setNeutralButton(args.getInt(ARG_DIALOG_NEUTRAL_BUTTON), this);
                    }
/* 108 */           if (args.containsKey(ARG_DIALOG_NEGATIVE_BUTTON)) {
/* 109 */               builder.setNegativeButton(args.getInt(ARG_DIALOG_NEGATIVE_BUTTON), this);
                    }
/* 112 */           if (args.containsKey(ARG_DIALOG_ITEMS)) {
/* 113 */               builder.setItems(getResources().getTextArray(args.getInt(ARG_DIALOG_ITEMS)), this);
                    }
/* 116 */           if (args.containsKey(ARG_DIALOG_ITEMS_STRING)) {
/* 117 */               builder.setItems(args.getStringArray(ARG_DIALOG_ITEMS_STRING), this);
                    }
/* 120 */           return builder.create();
                }

                @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnCancelListener
/* 124 */       public void onCancel(DialogInterface dialog) {
/* 125 */           super.onCancel(dialog);
/* 126 */           if (this.mListener != null) {
/* 127 */               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), Integer.MIN_VALUE);
                    }
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 131 */       public void onClick(DialogInterface dialog, int which) {
/* 132 */           if (this.mListener != null) {
/* 133 */               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), which);
                    }
                }

/* 137 */       public void setListener(OnDialogDoneListener listener) {
/* 138 */           this.mListener = listener;
                }

/* 141 */       public PromptDialogSupportFragment setIcon(int icon) {
/* 142 */           getArguments().putInt("icon", icon);
/* 143 */           return this;
                }

/* 146 */       public PromptDialogSupportFragment setTitle(int title) {
/* 147 */           getArguments().putInt("title", title);
/* 148 */           return this;
                }

/* 151 */       public PromptDialogSupportFragment setMessage(int message) {
/* 152 */           getArguments().putInt("message", message);
/* 153 */           return this;
                }

/* 156 */       public PromptDialogSupportFragment setMessage(String message) {
/* 157 */           getArguments().putString(ARG_DIALOG_MESSAGE_STRING, message);
/* 158 */           return this;
                }

/* 161 */       public PromptDialogSupportFragment setItems(int items) {
/* 162 */           getArguments().putInt(ARG_DIALOG_ITEMS, items);
/* 163 */           return this;
                }

/* 166 */       public PromptDialogSupportFragment setItems(String[] items) {
/* 167 */           getArguments().putStringArray(ARG_DIALOG_ITEMS_STRING, items);
/* 168 */           return this;
                }

/* 171 */       public PromptDialogSupportFragment setPositiveButton(int positiveButton) {
/* 172 */           getArguments().putInt(ARG_DIALOG_POSITIVE_BUTTON, positiveButton);
/* 173 */           return this;
                }

/* 176 */       public PromptDialogSupportFragment setNeutralButton(int neutralButton) {
/* 177 */           getArguments().putInt(ARG_DIALOG_NEUTRAL_BUTTON, neutralButton);
/* 178 */           return this;
                }

/* 181 */       public PromptDialogSupportFragment setNegativeButton(int negativeButton) {
/* 182 */           getArguments().putInt(ARG_DIALOG_NEGATIVE_BUTTON, negativeButton);
/* 183 */           return this;
                }

/* 186 */       public void show(FragmentManager fragmentManager) {
/* 187 */           show(fragmentManager, (String) null);
                }
            }
