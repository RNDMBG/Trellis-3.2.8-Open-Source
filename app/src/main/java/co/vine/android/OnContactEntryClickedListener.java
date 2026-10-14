            package co.vine.android;

            import android.content.DialogInterface;
            import android.support.v4.app.Fragment;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.Set;
            
            
    public abstract class OnContactEntryClickedListener implements PromptDialogSupportFragment.OnDialogDoneListener {
                public static final int DIALOG_SELECT_CONTACT_ITEM = 0;
                protected ContactEntry mEntry;
                protected String[] mValues;

                protected abstract void onContactEntryClicked(ContactEntry contactEntry, VineRecipient vineRecipient, int i, String str);

        public void onClick(Fragment fragment, ContactEntry entry) {
            if (entry != null) {
                Set<String> keySet = entry.valueMimeMap.keySet();
                this.mValues = (String[]) keySet.toArray(new String[keySet.size()]);
                this.mEntry = entry;
                PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
                p.setItems(this.mValues);
                p.setListener(this);
                        try {
                    p.show(fragment.getFragmentManager());
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override 
        public void onDialogDone(DialogInterface dialog, int id, int which) {
                    Integer typeFlag;
            if (which >= 0 && (typeFlag = this.mEntry.valueMimeMap.get(this.mValues[which])) != null) {
                VineRecipient recipient = null;
                if (typeFlag.intValue() == 1) {
                    recipient = VineRecipient.fromEmail(this.mEntry.displayName, -1L, this.mValues[which], 0L);
                        }
                if (typeFlag.intValue() == 2) {
                    recipient = VineRecipient.fromPhone(this.mEntry.displayName, -1L, this.mValues[which], 0L);
                        }
                if (recipient != null) {
                    recipient.contactId = this.mEntry.contactId;
                    onContactEntryClicked(this.mEntry, recipient, typeFlag.intValue(), this.mValues[which]);
                        }
                    }
                }
            }
