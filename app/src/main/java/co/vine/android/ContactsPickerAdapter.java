            package co.vine.android;

            import android.util.SparseArray;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ContactsPickerAdapter extends ArrayAdapter<ContactEntry> {
                private static final int STATE_REGULAR = 2;
                private static final int STATE_SECTIONED = 1;
                private static final int STATE_UNKNOWN = 0;
                private final ContactsRecipientPickerFragment mFragment;
                private int[] mRowHeaderStates;
                private SparseArray<WeakReference<ContactsPickerViewHolder>> mViewHolders;

/* 34 */        public ContactsPickerAdapter(ContactsRecipientPickerFragment fragment) {
/* 35 */            super(fragment.getActivity(), 0);
/* 36 */            this.mFragment = fragment;
/* 37 */            this.mViewHolders = new SparseArray<>();
                }

/* 40 */        public boolean showSeparator(int position, ContactEntry entry) {
/* 41 */            boolean showSeparator = false;
                    switch (this.mRowHeaderStates[position]) {
                        case 1:
/* 44 */                    return true;
                        case 2:
/* 48 */                    return false;
                        default:
/* 53 */                    if (position == 0) {
/* 54 */                        showSeparator = true;
                            } else {
/* 56 */                        ContactEntry lastEntry = getItem(position - 1);
/* 57 */                        if (lastEntry.alternateName != null && entry.alternateName != null) {
/* 58 */                            if (lastEntry.alternateName.toLowerCase().charAt(0) != entry.alternateName.toLowerCase().charAt(0)) {
/* 60 */                                showSeparator = true;
                                    }
                                } else if (lastEntry.displayName.toLowerCase().charAt(0) != entry.displayName.toLowerCase().charAt(0)) {
/* 65 */                            showSeparator = true;
                                }
                            }
/* 69 */                    this.mRowHeaderStates[position] = showSeparator ? 1 : 2;
/* 73 */                    return showSeparator;
                    }
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 78 */        public View getView(int position, View convertView, ViewGroup parent) {
                    ContactsPickerViewHolder holder;
/* 79 */            if (convertView == null) {
/* 80 */                LayoutInflater inflater = LayoutInflater.from(getContext());
/* 81 */                convertView = inflater.inflate(C0257R.layout.recipient_picker_contacts_row, parent, false);
/* 82 */                holder = new ContactsPickerViewHolder(convertView);
/* 83 */                convertView.setTag(holder);
                    } else {
/* 85 */                holder = (ContactsPickerViewHolder) convertView.getTag();
                    }
/* 87 */            this.mViewHolders.put(position, new WeakReference<>(holder));
/* 89 */            ContactEntry entry = getItem(position);
/* 90 */            boolean showSeparator = showSeparator(position, entry);
/* 91 */            boolean showEmail = (entry.typeFlag & 1) != 0;
/* 92 */            boolean showPhone = (entry.typeFlag & 2) != 0;
/* 93 */            holder.username.setText(entry.displayName);
/* 94 */            holder.emailIndicator.setVisibility(showEmail ? 0 : 8);
/* 95 */            holder.phoneIndicator.setVisibility(showPhone ? 0 : 8);
/* 96 */            holder.contactId = entry.contactId;
/* 98 */            invalidateHolderSelectors(entry, holder);
/* 100 */           if (showSeparator) {
/* 101 */               holder.separator.setVisibility(0);
/* 102 */               if (entry.alternateName != null) {
/* 103 */                   holder.separator.setText(new char[]{entry.alternateName.toUpperCase().charAt(0)}, 0, 1);
                        } else {
/* 105 */                   holder.separator.setText(new char[]{entry.displayName.toUpperCase().charAt(0)}, 0, 1);
                        }
                    } else {
/* 108 */               holder.separator.setVisibility(8);
                    }
/* 111 */           if (position + 1 < getCount()) {
/* 112 */               holder.divider.setVisibility(showSeparator(position + 1, getItem(position + 1)) ? 8 : 0);
                    }
/* 115 */           return convertView;
                }

/* 118 */       private Integer getSelectionState(ContactEntry entry) {
/* 119 */           Set<String> keySet = entry.valueMimeMap.keySet();
/* 120 */           boolean isEmailPicked = false;
/* 121 */           boolean isPhonePicked = false;
                    for (String value : keySet) {
/* 123 */               int typeFlag = entry.valueMimeMap.get(value).intValue();
/* 124 */               if (!isEmailPicked && typeFlag == 1) {
/* 125 */                   isEmailPicked = this.mFragment.isRecipientSelected(VineRecipient.fromEmail(entry.displayName, -1L, value, 0L));
                        }
/* 127 */               if (!isPhonePicked && typeFlag == 2) {
/* 128 */                   isPhonePicked = this.mFragment.isRecipientSelected(VineRecipient.fromPhone(entry.displayName, -1L, value, 0L));
                        }
/* 130 */               if (isEmailPicked && isPhonePicked) {
                            break;
                        }
                    }
/* 132 */           int state = isEmailPicked ? 1 : 0;
/* 133 */           if (isPhonePicked) {
                        state |= 2;
                    }
                    return Integer.valueOf(state);
                }

                @Override // android.widget.ArrayAdapter
/* 137 */       public void addAll(Collection<? extends ContactEntry> collection) {
/* 138 */           super.addAll(collection);
/* 139 */           this.mRowHeaderStates = new int[collection.size()];
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 143 */       public long getItemId(int position) {
/* 144 */           ContactEntry entry = getItem(position);
/* 145 */           if (entry != null) {
/* 146 */               return entry.contactId;
                    }
                    return 0L;
                }

/* 152 */       public void onRecipientItemRemoved(ContactEntry entry, int typeFlag, String value) {
/* 153 */           ArrayList<Integer> toRemove = new ArrayList<>();
/* 154 */           int size = this.mViewHolders.size();
                    for (int i = 0; i < size; i++) {
/* 156 */               WeakReference<ContactsPickerViewHolder> ref = this.mViewHolders.valueAt(i);
/* 157 */               ContactsPickerViewHolder holder = ref.get();
/* 158 */               if (holder == null) {
/* 159 */                   toRemove.add(Integer.valueOf(this.mViewHolders.keyAt(i)));
                        } else if (holder.contactId == entry.contactId) {
/* 162 */                   invalidateHolderSelectors(entry, holder);
                        }
                    }
/* 166 */           int size2 = toRemove.size();
                    for (int i2 = 0; i2 < size2; i2++) {
/* 168 */               this.mViewHolders.remove(toRemove.get(i2).intValue());
                    }
                }

/* 173 */       private void invalidateHolderSelectors(ContactEntry entry, ContactsPickerViewHolder holder) {
/* 174 */           Integer state = getSelectionState(entry);
/* 175 */           int selectionState = state != null ? state.intValue() : 0;
/* 176 */           if ((selectionState & 1) != 0) {
/* 177 */               holder.emailIndicator.setSelected(true);
                    } else {
/* 179 */               holder.emailIndicator.setSelected(false);
                    }
/* 182 */           if ((selectionState & 2) != 0) {
/* 183 */               holder.phoneIndicator.setSelected(true);
                    } else {
/* 185 */               holder.phoneIndicator.setSelected(false);
                    }
                }

/* 190 */       public void onRecipientItemAdded(ContactEntry entry, int typeFlag, String value) {
/* 191 */           ArrayList<Integer> toRemove = new ArrayList<>();
/* 192 */           int size = this.mViewHolders.size();
                    for (int i = 0; i < size; i++) {
/* 194 */               WeakReference<ContactsPickerViewHolder> ref = this.mViewHolders.valueAt(i);
/* 195 */               ContactsPickerViewHolder holder = ref.get();
/* 196 */               if (holder == null) {
/* 197 */                   toRemove.add(Integer.valueOf(this.mViewHolders.keyAt(i)));
                        } else if (holder.contactId == entry.contactId) {
/* 200 */                   if ((typeFlag & 1) != 0) {
/* 201 */                       holder.emailIndicator.setSelected(true);
                            } else if ((typeFlag & 2) != 0) {
/* 203 */                       holder.phoneIndicator.setSelected(true);
                            }
                        }
                    }
/* 208 */           int size2 = toRemove.size();
                    for (int i2 = 0; i2 < size2; i2++) {
/* 210 */               this.mViewHolders.remove(toRemove.get(i2).intValue());
                    }
                }

/* 221 */       public static class ContactsPickerViewHolder {
                    public long contactId;
                    public View divider;
                    public ImageView emailIndicator;
                    public ImageView phoneIndicator;
                    public int position;
                    public TextView separator;
                    public TextView username;

/* 222 */           public ContactsPickerViewHolder(View v) {
/* 224 */               this.emailIndicator = (ImageView) v.findViewById(C0257R.id.email_indicator);
/* 225 */               this.phoneIndicator = (ImageView) v.findViewById(C0257R.id.phone_indicator);
/* 226 */               this.separator = (TextView) v.findViewById(C0257R.id.separator);
/* 227 */               this.username = (TextView) v.findViewById(C0257R.id.contact_name);
/* 228 */               this.divider = v.findViewById(C0257R.id.divider);
                    }
                }
            }
