            package co.vine.android.widgets;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.AlertDialog;
            import android.app.Dialog;
            import android.app.DialogFragment;
            import android.app.FragmentManager;
            import android.content.ComponentCallbacks2;
            import android.content.DialogInterface;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(11)
/* 45 */    public class PromptDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
                private static final String ARG_DIALOG_ICON = "icon";
                private static final String ARG_DIALOG_ID = "id";
                private static final String ARG_DIALOG_ITEMS = "items";
                private static final String ARG_DIALOG_ITEMS_STRING = "items_string";
                private static final String ARG_DIALOG_MESSAGE = "message";
                private static final String ARG_DIALOG_NEGATIVE_BUTTON = "negative_button";
                private static final String ARG_DIALOG_NEUTRAL_BUTTON = "neutral_button";
                private static final String ARG_DIALOG_POSITIVE_BUTTON = "positive_button";
                private static final String ARG_DIALOG_TITLE = "title";
                public static final int DIALOG_CANCELLED = Integer.MIN_VALUE;
                private OnDialogDoneListener mListener;

                public interface OnDialogDoneListener {
                    void onDialogDone(DialogInterface dialogInterface, int i, int i2);
                }

/* 46 */        public static PromptDialogFragment newInstance(int id) {
/* 47 */            PromptDialogFragment f = new PromptDialogFragment();
/* 49 */            Bundle bundle = new Bundle();
/* 50 */            bundle.putInt(ARG_DIALOG_ID, id);
/* 51 */            f.setArguments(bundle);
/* 53 */            return f;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.app.Fragment
/* 57 */        public void onAttach(Activity activity) {
/* 58 */            super.onAttach(activity);
/* 59 */            if (this.mListener == null) {
/* 60 */                ComponentCallbacks2 targetFragment = getTargetFragment();
/* 61 */                if (targetFragment instanceof OnDialogDoneListener) {
/* 62 */                    this.mListener = (OnDialogDoneListener) targetFragment;
                        } else if (activity instanceof OnDialogDoneListener) {
/* 64 */                    this.mListener = (OnDialogDoneListener) activity;
                        }
                    }
                }

                @Override // android.app.DialogFragment
/* 70 */        public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 71 */            super.onCreateDialog(savedInstanceState);
/* 73 */            Bundle args = getArguments();
/* 74 */            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2);
/* 77 */            if (args.containsKey("icon")) {
/* 78 */                builder.setIcon(args.getInt("icon"));
                    }
/* 81 */            if (args.containsKey("title")) {
/* 82 */                builder.setTitle(args.getInt("title"));
                    }
/* 85 */            if (args.containsKey("message")) {
/* 86 */                builder.setMessage(args.getInt("message"));
                    }
/* 89 */            if (args.containsKey(ARG_DIALOG_POSITIVE_BUTTON)) {
/* 90 */                builder.setPositiveButton(args.getInt(ARG_DIALOG_POSITIVE_BUTTON), this);
                    }
/* 93 */            if (args.containsKey(ARG_DIALOG_NEUTRAL_BUTTON)) {
/* 94 */                builder.setNeutralButton(args.getInt(ARG_DIALOG_NEUTRAL_BUTTON), this);
                    }
/* 97 */            if (args.containsKey(ARG_DIALOG_NEGATIVE_BUTTON)) {
/* 98 */                builder.setNegativeButton(args.getInt(ARG_DIALOG_NEGATIVE_BUTTON), this);
                    }
/* 101 */           if (args.containsKey(ARG_DIALOG_ITEMS)) {
/* 102 */               builder.setItems(getResources().getTextArray(args.getInt(ARG_DIALOG_ITEMS)), this);
                    }
/* 105 */           if (args.containsKey(ARG_DIALOG_ITEMS_STRING)) {
/* 106 */               builder.setItems(args.getStringArray(ARG_DIALOG_ITEMS_STRING), this);
                    }
/* 109 */           return builder.create();
                }

                @Override // android.app.DialogFragment, android.content.DialogInterface.OnCancelListener
/* 113 */       public void onCancel(DialogInterface dialog) {
/* 114 */           super.onCancel(dialog);
/* 115 */           if (this.mListener != null) {
/* 116 */               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), Integer.MIN_VALUE);
                    }
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 120 */       public void onClick(DialogInterface dialog, int which) {
/* 121 */           if (this.mListener != null) {
/* 122 */               this.mListener.onDialogDone(dialog, getArguments().getInt(ARG_DIALOG_ID), which);
                    }
                }

/* 126 */       public void setListener(OnDialogDoneListener listener) {
/* 127 */           this.mListener = listener;
                }

/* 130 */       public PromptDialogFragment setIcon(int icon) {
/* 131 */           getArguments().putInt("icon", icon);
/* 132 */           return this;
                }

/* 135 */       public PromptDialogFragment setTitle(int title) {
/* 136 */           getArguments().putInt("title", title);
/* 137 */           return this;
                }

/* 140 */       public PromptDialogFragment setMessage(int message) {
/* 141 */           getArguments().putInt("message", message);
/* 142 */           return this;
                }

/* 145 */       public PromptDialogFragment setItems(int items) {
/* 146 */           getArguments().putInt(ARG_DIALOG_ITEMS, items);
/* 147 */           return this;
                }

/* 150 */       public PromptDialogFragment setItems(String[] items) {
/* 151 */           getArguments().putStringArray(ARG_DIALOG_ITEMS_STRING, items);
/* 152 */           return this;
                }

/* 155 */       public PromptDialogFragment setPositiveButton(int positiveButton) {
/* 156 */           getArguments().putInt(ARG_DIALOG_POSITIVE_BUTTON, positiveButton);
/* 157 */           return this;
                }

/* 160 */       public PromptDialogFragment setNeutralButton(int neutralButton) {
/* 161 */           getArguments().putInt(ARG_DIALOG_NEUTRAL_BUTTON, neutralButton);
/* 162 */           return this;
                }

/* 165 */       public PromptDialogFragment setNegativeButton(int negativeButton) {
/* 166 */           getArguments().putInt(ARG_DIALOG_NEGATIVE_BUTTON, negativeButton);
/* 167 */           return this;
                }

/* 170 */       public void show(FragmentManager fragmentManager) {
/* 171 */           show(fragmentManager, (String) null);
                }
            }
