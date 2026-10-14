            package co.vine.android.api;

            import co.vine.android.util.FlexibleStringHashMap;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.HashMap;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class VineVenue implements Externalizable {
                public static final int TYPE_ARTS_ENTERTAINMENT = 11;
                public static final int TYPE_BIG_HUMAN = 2;
                public static final int TYPE_EBAY = 3;
                public static final int TYPE_EDUCATION = 18;
                public static final int TYPE_FACEBOOK = 5;
                public static final int TYPE_GILT = 4;
                public static final int TYPE_GOOGLE = 6;
                public static final int TYPE_JETSETTER = 19;
                public static final int TYPE_NATURE = 14;
                public static final int TYPE_NIGHTLIFE = 13;
                public static final int TYPE_OFFICE = 15;
                public static final int TYPE_OTHER = 20;
                public static final int TYPE_RESIDENTIAL = 10;
                public static final int TYPE_RESTAURANT = 12;
                public static final int TYPE_SHOPS = 16;
                public static final int TYPE_SQUARE = 8;
                public static final int TYPE_SQUARESPACE = 7;
                public static final int TYPE_TRAVEL = 17;
                public static final int TYPE_TWITTER = 9;
                public static final int TYPE_VINE = 1;
                private static final long serialVersionUID = 362104582584848336L;
                public String address;
                public String categoryIconUrl;
                public String categoryId;
                public String categoryName;
                public String city;
                public String countryCode;
                public int displayType;
                public String state;
                public String venueName;
                public static final Pattern FOURSQUARE_CATEGORY = Pattern.compile("/([^/]+?)/([^/]+?)_\\d*?.png");
                private static final HashMap<String, Integer> venueMap = new FlexibleStringHashMap();

                static {
/* 53 */            venueMap.put("vine hq", 1);
/* 54 */            venueMap.put("verse hq", 1);
/* 55 */            venueMap.put("twitter nyc", 9);
/* 56 */            venueMap.put("twitter, inc", 9);
/* 57 */            venueMap.put("square, inc", 8);
/* 58 */            venueMap.put("squarespace hq", 7);
/* 59 */            venueMap.put("jetsetter", 19);
/* 60 */            venueMap.put("gilt city", 4);
/* 61 */            venueMap.put("gilt groupe", 4);
/* 62 */            venueMap.put("facebook", 5);
/* 63 */            venueMap.put("ebay", 3);
/* 64 */            venueMap.put("big human", 2);
/* 65 */            venueMap.put("arts_entertainment", 11);
/* 66 */            venueMap.put("education", 18);
/* 67 */            venueMap.put("event", 11);
/* 68 */            venueMap.put("food", 12);
/* 69 */            venueMap.put("home", 10);
/* 70 */            venueMap.put("nightlife", 13);
/* 71 */            venueMap.put("parks_outdoors", 14);
/* 72 */            venueMap.put("travel", 17);
/* 73 */            venueMap.put("shops", 16);
/* 74 */            venueMap.put("nightlife", 13);
/* 75 */            venueMap.put("tech startup", 15);
                }

/* 91 */        public VineVenue() {
                }

/* 95 */        public VineVenue(String categoryIconUrl, String categoryName, String categoryId, String city, String countryCode, String venueName, String state, String address) {
/* 98 */            String venueNameLower = venueName != null ? venueName.toLowerCase() : null;
/* 100 */           String categoryNameLower = categoryName != null ? categoryName.toLowerCase() : null;
/* 102 */           this.categoryIconUrl = categoryIconUrl;
/* 103 */           this.categoryName = categoryName;
/* 104 */           this.categoryId = categoryId;
/* 105 */           this.city = city;
/* 106 */           this.countryCode = countryCode;
/* 107 */           this.venueName = venueName;
/* 108 */           this.state = state;
/* 109 */           this.address = address;
/* 111 */           Integer display = venueMap.get(venueNameLower);
/* 112 */           if ((display == null || display.intValue() <= 0) && (((display = venueMap.get(categoryNameLower)) == null || display.intValue() <= 0) && categoryIconUrl != null)) {
/* 116 */               Matcher m = FOURSQUARE_CATEGORY.matcher(categoryIconUrl);
/* 117 */               if (m.find()) {
/* 118 */                   String urlCategoryName = m.group(2).toLowerCase();
/* 119 */                   display = venueMap.get(urlCategoryName);
/* 120 */                   if (display == null || display.intValue() <= 0) {
/* 121 */                       String urlParentName = m.group(1).toLowerCase();
/* 122 */                       display = venueMap.get(urlParentName);
                            }
                        }
                    }
/* 132 */           this.displayType = display == null || display.intValue() <= 0 ? 20 : display.intValue();
                }

                @Override // java.io.Externalizable
/* 136 */       public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 137 */           this.categoryIconUrl = (String) objectInput.readObject();
/* 138 */           this.categoryName = (String) objectInput.readObject();
/* 139 */           this.categoryId = (String) objectInput.readObject();
/* 140 */           this.city = (String) objectInput.readObject();
/* 141 */           this.countryCode = (String) objectInput.readObject();
/* 142 */           this.venueName = (String) objectInput.readObject();
/* 143 */           this.state = (String) objectInput.readObject();
/* 144 */           this.address = (String) objectInput.readObject();
/* 145 */           this.displayType = objectInput.readInt();
                }

                @Override // java.io.Externalizable
/* 149 */       public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 150 */           objectOutput.writeObject(this.categoryIconUrl);
/* 151 */           objectOutput.writeObject(this.categoryName);
/* 152 */           objectOutput.writeObject(this.categoryId);
/* 153 */           objectOutput.writeObject(this.city);
/* 154 */           objectOutput.writeObject(this.countryCode);
/* 155 */           objectOutput.writeObject(this.venueName);
/* 156 */           objectOutput.writeObject(this.state);
/* 157 */           objectOutput.writeObject(this.address);
/* 158 */           objectOutput.writeInt(this.displayType);
                }

/* 162 */       public boolean equals(Object o) {
/* 163 */           if (this == o) {
                        return true;
                    }
/* 164 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 166 */           VineVenue vineVenue = (VineVenue) o;
/* 168 */           if (this.displayType != vineVenue.displayType) {
                        return false;
                    }
/* 169 */           if (this.address == null ? vineVenue.address != null : !this.address.equals(vineVenue.address)) {
                        return false;
                    }
/* 171 */           if (this.categoryIconUrl == null ? vineVenue.categoryIconUrl != null : !this.categoryIconUrl.equals(vineVenue.categoryIconUrl)) {
                        return false;
                    }
/* 174 */           if (this.categoryId == null ? vineVenue.categoryId != null : !this.categoryId.equals(vineVenue.categoryId)) {
                        return false;
                    }
/* 177 */           if (this.categoryName == null ? vineVenue.categoryName != null : !this.categoryName.equals(vineVenue.categoryName)) {
                        return false;
                    }
/* 180 */           if (this.city == null ? vineVenue.city != null : !this.city.equals(vineVenue.city)) {
                        return false;
                    }
/* 181 */           if (this.countryCode == null ? vineVenue.countryCode != null : !this.countryCode.equals(vineVenue.countryCode)) {
                        return false;
                    }
/* 184 */           if (this.state == null ? vineVenue.state != null : !this.state.equals(vineVenue.state)) {
                        return false;
                    }
/* 185 */           if (this.venueName != null) {
                        if (this.venueName.equals(vineVenue.venueName)) {
                            return true;
                        }
                    } else if (vineVenue.venueName == null) {
                        return true;
                    }
                    return false;
                }

/* 193 */       public int hashCode() {
/* 194 */           int result = this.categoryIconUrl != null ? this.categoryIconUrl.hashCode() : 0;
/* 202 */           return (((((((((((((((result * 31) + (this.categoryName != null ? this.categoryName.hashCode() : 0)) * 31) + (this.categoryId != null ? this.categoryId.hashCode() : 0)) * 31) + (this.city != null ? this.city.hashCode() : 0)) * 31) + (this.countryCode != null ? this.countryCode.hashCode() : 0)) * 31) + (this.venueName != null ? this.venueName.hashCode() : 0)) * 31) + (this.state != null ? this.state.hashCode() : 0)) * 31) + (this.address != null ? this.address.hashCode() : 0)) * 31) + this.displayType;
                }
            }
