            package com.google.android.gms.internal;

            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.plus.PlusShare;
            import com.google.android.gms.plus.model.people.Person;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gg */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1103gg extends AbstractC0889b implements Person {
                public C1103gg(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                /* JADX INFO: renamed from: dX */
                public ArrayList<Person.Organizations> m2090dX() {
                    return null;
                }

                /* JADX INFO: renamed from: dY */
                public ArrayList<Person.PlacesLived> m2091dY() {
                    return null;
                }

                /* JADX INFO: renamed from: dZ */
                public ArrayList<Person.Urls> m2092dZ() {
                    return null;
                }

                /* JADX INFO: renamed from: ea */
                public Person m2093ea() {
                    return new C1091fv(getDisplayName(), getId(), (C1091fv.c) getImage(), getObjectType(), getUrl());
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ Person freeze() {
                    return m2093ea();
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getAboutMe() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public Person.AgeRange getAgeRange() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getBirthday() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getBraggingRights() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public int getCircledByCount() {
                    return 0;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public Person.Cover getCover() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getCurrentLocation() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getDisplayName() {
                    return getString("displayName");
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public int getGender() {
                    return 0;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getId() {
                    return getString("personId");
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public Person.Image getImage() {
                    return new C1091fv.c(getString("image"));
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getLanguage() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public Person.Name getName() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getNickname() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public int getObjectType() {
                    return C1091fv.e.m2050aa(getString("objectType"));
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public /* synthetic */ List getOrganizations() {
                    return m2090dX();
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public /* synthetic */ List getPlacesLived() {
                    return m2091dY();
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public int getPlusOneCount() {
                    return 0;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public int getRelationshipStatus() {
                    return 0;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getTagline() {
                    return null;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public String getUrl() {
                    return getString(PlusShare.KEY_CALL_TO_ACTION_URL);
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public /* synthetic */ List getUrls() {
                    return m2092dZ();
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasAboutMe() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasAgeRange() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasBirthday() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasBraggingRights() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasCircledByCount() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasCover() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasCurrentLocation() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasDisplayName() {
                    return true;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasGender() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasId() {
                    return true;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasImage() {
                    return true;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasIsPlusUser() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasLanguage() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasName() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasNickname() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasObjectType() {
                    return true;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasOrganizations() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasPlacesLived() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasPlusOneCount() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasRelationshipStatus() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasTagline() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasUrl() {
                    return true;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasUrls() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean hasVerified() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean isPlusUser() {
                    return false;
                }

                @Override // com.google.android.gms.plus.model.people.Person
                public boolean isVerified() {
                    return false;
                }
            }
