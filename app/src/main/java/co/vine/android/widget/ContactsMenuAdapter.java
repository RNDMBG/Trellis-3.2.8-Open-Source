            package co.vine.android.widget;

            import android.content.Context;
            import android.support.annotation.Nullable;
            import android.util.SparseArray;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.ContactEntry;
            import co.vine.android.util.Util;
            import java.lang.ref.WeakReference;
            import java.util.Collection;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ContactsMenuAdapter extends ArrayAdapter<ContactEntry> {
                private static final int STATE_REGULAR = 2;
                private static final int STATE_SECTIONED = 1;
                private static final int STATE_UNKNOWN = 0;
                private int[] mRowHeaderStates;
                private final SparseArray<WeakReference<ContactsPickerViewHolder>> mViewHolders;

/* 35 */        public ContactsMenuAdapter(Context context) {
/* 36 */            super(context, 0);
/* 33 */            this.mViewHolders = new SparseArray<>();
                }

                @Override // android.widget.ArrayAdapter
/* 40 */        public void addAll(Collection<? extends ContactEntry> collection) {
/* 41 */            super.addAll(collection);
/* 42 */            this.mRowHeaderStates = new int[collection.size()];
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                @Nullable
/* 48 */        public View getView(int position, View convertView, ViewGroup parent) {
                    ContactsPickerViewHolder holder;
                    boolean showSeparator;
                    boolean showDivider;
/* 49 */            if (convertView == null || !(convertView.getTag() instanceof ContactsPickerViewHolder)) {
/* 50 */                LayoutInflater inflater = LayoutInflater.from(getContext());
/* 51 */                convertView = inflater.inflate(C0257R.layout.menu_contacts_row, parent, false);
/* 52 */                holder = new ContactsPickerViewHolder(getContext(), convertView);
/* 53 */                convertView.setTag(holder);
                    } else {
/* 55 */                holder = (ContactsPickerViewHolder) convertView.getTag();
                    }
/* 57 */            this.mViewHolders.put(position, new WeakReference<>(holder));
/* 59 */            ContactEntry entry = getItem(position);
                    switch (this.mRowHeaderStates[position]) {
                        case 1:
/* 63 */                    showSeparator = true;
                            break;
                        case 2:
/* 67 */                    showSeparator = false;
                            break;
                        default:
/* 72 */                    if (position == 0) {
/* 73 */                        showSeparator = true;
                            } else {
/* 75 */                        ContactEntry lastEntry = getItem(position - 1);
/* 76 */                        showSeparator = !entry.isSameSection(lastEntry);
                            }
/* 78 */                    this.mRowHeaderStates[position] = showSeparator ? 1 : 2;
                            break;
                    }
/* 84 */            if (position < getCount() - 1) {
/* 85 */                ContactEntry nextEntry = getItem(position + 1);
/* 86 */                showDivider = entry.isSameSection(nextEntry);
                    } else {
/* 88 */                showDivider = true;
                    }
/* 91 */            holder.divider.setVisibility(showDivider ? 0 : 8);
/* 93 */            boolean showEmail = (entry.typeFlag & 1) != 0;
/* 94 */            boolean showPhone = (entry.typeFlag & 2) != 0;
/* 95 */            holder.username.setText(entry.displayName);
/* 96 */            holder.emailIndicator.setVisibility(showEmail ? 0 : 8);
/* 97 */            holder.phoneIndicator.setVisibility(showPhone ? 0 : 8);
/* 98 */            holder.contactId = entry.contactId;
/* 100 */           if (position == 0) {
/* 101 */               holder.sectionTitle.setText(getContext().getString(C0257R.string.contacts));
/* 102 */               holder.sectionTitle.setVisibility(0);
                    } else {
/* 104 */               holder.sectionTitle.setVisibility(8);
                    }
/* 107 */           if (showSeparator) {
/* 108 */               holder.sectionIndicator.setVisibility(0);
/* 109 */               if (entry.alternateName != null) {
/* 110 */                   holder.sectionSort.setText(new char[]{entry.alternateName.toUpperCase().charAt(0)}, 0, 1);
                        } else if (entry.displayName != null) {
/* 113 */                   holder.sectionSort.setText(new char[]{entry.displayName.toUpperCase().charAt(0)}, 0, 1);
                        } else {
/* 115 */                   holder.sectionSort.setText(new char[]{' '}, 0, 1);
                        }
                    } else {
/* 119 */               holder.sectionIndicator.setVisibility(8);
                    }
/* 122 */           return convertView;
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 126 */       public long getItemId(int position) {
/* 127 */           ContactEntry entry = getItem(position);
/* 128 */           if (entry != null) {
/* 129 */               return entry.contactId;
                    }
                    return 0L;
                }

/* 144 */       public static class ContactsPickerViewHolder {
                    public long contactId;
                    public View divider;
                    public ImageView emailIndicator;
                    public ImageView phoneIndicator;
                    public int position;
                    public View sectionIndicator;
                    public TextView sectionSort;
                    public TextView sectionTitle;
                    public TextView username;

/* 145 */           public ContactsPickerViewHolder(Context context, View v) {
/* 147 */               this.emailIndicator = (ImageView) v.findViewById(C0257R.id.email_indicator);
/* 148 */               this.phoneIndicator = (ImageView) v.findViewById(C0257R.id.phone_indicator);
/* 149 */               this.sectionIndicator = v.findViewById(C0257R.id.section_indicator);
/* 150 */               this.sectionTitle = (TextView) v.findViewById(C0257R.id.section_title);
/* 151 */               this.sectionSort = (TextView) v.findViewById(C0257R.id.section_sort);
/* 152 */               this.username = (TextView) v.findViewById(C0257R.id.contact_name);
/* 153 */               this.divider = v.findViewById(C0257R.id.divider);
/* 154 */               Util.styleSectionHeader(context, this.sectionTitle, this.sectionSort);
                    }
                }
            }
