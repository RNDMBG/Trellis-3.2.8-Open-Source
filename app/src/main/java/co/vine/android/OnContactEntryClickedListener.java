            package co.vine.android;

            import android.content.DialogInterface;
            import android.support.v4.app.Fragment;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public abstract class OnContactEntryClickedListener implements PromptDialogSupportFragment.OnDialogDoneListener {
                public static final int DIALOG_SELECT_CONTACT_ITEM = 0;
                protected ContactEntry mEntry;
                protected String[] mValues;

                protected abstract void onContactEntryClicked(ContactEntry contactEntry, VineRecipient vineRecipient, int i, String str);

/* 20 */        public void onClick(Fragment fragment, ContactEntry entry) {
/* 21 */            if (entry != null) {
/* 22 */                Set<String> keySet = entry.valueMimeMap.keySet();
/* 23 */                this.mValues = (String[]) keySet.toArray(new String[keySet.size()]);
/* 24 */                this.mEntry = entry;
/* 25 */                PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
/* 27 */                p.setItems(this.mValues);
/* 28 */                p.setListener(this);
                        try {
/* 30 */                    p.show(fragment.getFragmentManager());
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 40 */        public void onDialogDone(DialogInterface dialog, int id, int which) {
                    Integer typeFlag;
/* 41 */            if (which >= 0 && (typeFlag = this.mEntry.valueMimeMap.get(this.mValues[which])) != null) {
/* 44 */                VineRecipient recipient = null;
/* 45 */                if (typeFlag.intValue() == 1) {
/* 46 */                    recipient = VineRecipient.fromEmail(this.mEntry.displayName, -1L, this.mValues[which], 0L);
                        }
/* 48 */                if (typeFlag.intValue() == 2) {
/* 49 */                    recipient = VineRecipient.fromPhone(this.mEntry.displayName, -1L, this.mValues[which], 0L);
                        }
/* 51 */                if (recipient != null) {
/* 52 */                    recipient.contactId = this.mEntry.contactId;
/* 53 */                    onContactEntryClicked(this.mEntry, recipient, typeFlag.intValue(), this.mValues[which]);
                        }
                    }
                }
            }
