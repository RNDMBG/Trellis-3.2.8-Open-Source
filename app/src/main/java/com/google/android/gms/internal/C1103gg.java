            package com.google.android.gms.internal;

            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.plus.PlusShare;
            import com.google.android.gms.plus.model.people.Person;
            import java.util.ArrayList;
            import java.util.List;
            
            
            
            public final class C1103gg extends AbstractC0889b implements Person {
                public C1103gg(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                
                public ArrayList<Person.Organizations> m2090dX() {
                    return null;
                }

                
                public ArrayList<Person.PlacesLived> m2091dY() {
                    return null;
                }

                
                public ArrayList<Person.Urls> m2092dZ() {
                    return null;
                }

                
                public Person m2093ea() {
                    return new C1091fv(getDisplayName(), getId(), (C1091fv.c) getImage(), getObjectType(), getUrl());
                }

                @Override 
                public  Person freeze() {
                    return m2093ea();
                }

                @Override 
                public String getAboutMe() {
                    return null;
                }

                @Override 
                public Person.AgeRange getAgeRange() {
                    return null;
                }

                @Override 
                public String getBirthday() {
                    return null;
                }

                @Override 
                public String getBraggingRights() {
                    return null;
                }

                @Override 
                public int getCircledByCount() {
                    return 0;
                }

                @Override 
                public Person.Cover getCover() {
                    return null;
                }

                @Override 
                public String getCurrentLocation() {
                    return null;
                }

                @Override 
                public String getDisplayName() {
                    return getString("displayName");
                }

                @Override 
                public int getGender() {
                    return 0;
                }

                @Override 
                public String getId() {
                    return getString("personId");
                }

                @Override 
                public Person.Image getImage() {
                    return new C1091fv.c(getString("image"));
                }

                @Override 
                public String getLanguage() {
                    return null;
                }

                @Override 
                public Person.Name getName() {
                    return null;
                }

                @Override 
                public String getNickname() {
                    return null;
                }

                @Override 
                public int getObjectType() {
                    return C1091fv.e.m2050aa(getString("objectType"));
                }

                @Override 
                public  List getOrganizations() {
                    return m2090dX();
                }

                @Override 
                public  List getPlacesLived() {
                    return m2091dY();
                }

                @Override 
                public int getPlusOneCount() {
                    return 0;
                }

                @Override 
                public int getRelationshipStatus() {
                    return 0;
                }

                @Override 
                public String getTagline() {
                    return null;
                }

                @Override 
                public String getUrl() {
                    return getString(PlusShare.KEY_CALL_TO_ACTION_URL);
                }

                @Override 
                public  List getUrls() {
                    return m2092dZ();
                }

                @Override 
                public boolean hasAboutMe() {
                    return false;
                }

                @Override 
                public boolean hasAgeRange() {
                    return false;
                }

                @Override 
                public boolean hasBirthday() {
                    return false;
                }

                @Override 
                public boolean hasBraggingRights() {
                    return false;
                }

                @Override 
                public boolean hasCircledByCount() {
                    return false;
                }

                @Override 
                public boolean hasCover() {
                    return false;
                }

                @Override 
                public boolean hasCurrentLocation() {
                    return false;
                }

                @Override 
                public boolean hasDisplayName() {
                    return true;
                }

                @Override 
                public boolean hasGender() {
                    return false;
                }

                @Override 
                public boolean hasId() {
                    return true;
                }

                @Override 
                public boolean hasImage() {
                    return true;
                }

                @Override 
                public boolean hasIsPlusUser() {
                    return false;
                }

                @Override 
                public boolean hasLanguage() {
                    return false;
                }

                @Override 
                public boolean hasName() {
                    return false;
                }

                @Override 
                public boolean hasNickname() {
                    return false;
                }

                @Override 
                public boolean hasObjectType() {
                    return true;
                }

                @Override 
                public boolean hasOrganizations() {
                    return false;
                }

                @Override 
                public boolean hasPlacesLived() {
                    return false;
                }

                @Override 
                public boolean hasPlusOneCount() {
                    return false;
                }

                @Override 
                public boolean hasRelationshipStatus() {
                    return false;
                }

                @Override 
                public boolean hasTagline() {
                    return false;
                }

                @Override 
                public boolean hasUrl() {
                    return true;
                }

                @Override 
                public boolean hasUrls() {
                    return false;
                }

                @Override 
                public boolean hasVerified() {
                    return false;
                }

                @Override 
                public boolean isPlusUser() {
                    return false;
                }

                @Override 
                public boolean isVerified() {
                    return false;
                }
            }
