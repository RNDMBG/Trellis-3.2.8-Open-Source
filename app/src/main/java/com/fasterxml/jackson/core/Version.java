            package com.fasterxml.jackson.core;

            import java.io.Serializable;
            import org.apache.commons.io.FilenameUtils;
            
            
    public class Version implements Comparable<Version>, Serializable {
                private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
                private static final long serialVersionUID = 1;
                protected final String _artifactId;
                protected final String _groupId;
                protected final int _majorVersion;
                protected final int _minorVersion;
                protected final int _patchLevel;
                protected final String _snapshotInfo;


                @Deprecated
        public Version(int i, int i2, int i3, String str) {
            this(i, i2, i3, str, null, null);
                }

        public Version(int i, int i2, int i3, String str, String str2, String str3) {
            this._majorVersion = i;
            this._minorVersion = i2;
            this._patchLevel = i3;
            this._snapshotInfo = str;
            this._groupId = str2 == null ? "" : str2;
            this._artifactId = str3 == null ? "" : str3;
                }

        public static Version unknownVersion() {
            return UNKNOWN_VERSION;
                }

        public boolean isUknownVersion() {
                    return this == UNKNOWN_VERSION;
                }

        public boolean isSnapshot() {
                    return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
                }

        public int getMajorVersion() {
            return this._majorVersion;
                }

        public int getMinorVersion() {
            return this._minorVersion;
                }

        public int getPatchLevel() {
            return this._patchLevel;
                }

        public String getGroupId() {
            return this._groupId;
                }

        public String getArtifactId() {
            return this._artifactId;
                }

        public String toFullString() {
            return this._groupId + '/' + this._artifactId + '/' + toString();
                }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this._majorVersion).append(FilenameUtils.EXTENSION_SEPARATOR);
            sb.append(this._minorVersion).append(FilenameUtils.EXTENSION_SEPARATOR);
            sb.append(this._patchLevel);
            if (isSnapshot()) {
                sb.append('-').append(this._snapshotInfo);
                    }
            return sb.toString();
                }

       public int hashCode() {
           return this._artifactId.hashCode() ^ (((this._groupId.hashCode() + this._majorVersion) - this._minorVersion) + this._patchLevel);
                }

       public boolean equals(Object obj) {
           if (obj == this) {
                        return true;
                    }
           if (obj != null && obj.getClass() == getClass()) {
               Version version = (Version) obj;
                        return version._majorVersion == this._majorVersion && version._minorVersion == this._minorVersion && version._patchLevel == this._patchLevel && version._artifactId.equals(this._artifactId) && version._groupId.equals(this._groupId);
                    }
                    return false;
                }

                
       public int compareTo(Version version) {
           if (version == this) {
                        return 0;
                    }
           int iCompareTo = this._groupId.compareTo(version._groupId);
           if (iCompareTo == 0) {
               int iCompareTo2 = this._artifactId.compareTo(version._artifactId);
               if (iCompareTo2 == 0) {
                   int i = this._majorVersion - version._majorVersion;
                   if (i == 0) {
                       int i2 = this._minorVersion - version._minorVersion;
                       if (i2 == 0) {
                           return this._patchLevel - version._patchLevel;
                                }
                       return i2;
                            }
                   return i;
                        }
               return iCompareTo2;
                    }
           return iCompareTo;
                }
            }
