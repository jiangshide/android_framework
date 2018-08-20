package com.zd112.read.user.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;

public class UserData extends BaseData {
    @SerializedName("body")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("sessionId")
        public String sessionId;

        @SerializedName("notReadMsg")
        public int notReadMsg;

        @SerializedName("sftUserMdl")
        public SftUserMdl sftUserMdl;

        public class SftUserMdl implements Serializable {
            @SerializedName("findPassTime")
            public long findPassTime;
            @SerializedName("source")
            public String source;
            @SerializedName("friendId")
            public String friendId;
            @SerializedName("sftUserPkMdl")
            public String sftUserPkMdl;
            @SerializedName("useAddrContact")
            public String useAddrContact;
            @SerializedName("useAddrDomicile")
            public String useAddrDomicile;
            @SerializedName("useAddrNow")
            public String useAddrNow;
            @SerializedName("useApp")
            public String useApp;
            @SerializedName("useAppName")
            public String useAppName;
            @SerializedName("useAppFdName")
            public String useAppFdName;
            @SerializedName("useAuthEmail")
            public String useAuthEmail;
            @SerializedName("useAuthEmailName")
            public String useAuthEmailName;
            @SerializedName("useAuthEmailFdName")
            public String useAuthEmailFdName;
            @SerializedName("useAuthMp")
            public String useAuthMp;
            @SerializedName("useAuthMpName")
            public String useAuthMpName;
            @SerializedName("useAuthMpFdName")
            public String useAuthMpFdName;
            @SerializedName("useAuthRealName")
            public String useAuthRealName;
            @SerializedName("useAuthRealNameName")
            public String useAuthRealNameName;
            @SerializedName("useAuthRealNameFdName")
            public String useAuthRealNameFdName;

            @SerializedName("useBirthday")
            public String useBirthday;
            @SerializedName("useCpType")
            public String useCpType;
            @SerializedName("useCpTypeName")
            public String useCpTypeName;
            @SerializedName("useCpTypeFdName")
            public String useCpTypeFdName;
            @SerializedName("useCreatePerson")
            public String useCreatePerson;
            @SerializedName("useCreatePersonName")
            public String useCreatePersonName;
            @SerializedName("useCreateTime")
            public String useCreateTime;
            @SerializedName("useCreateType")
            public String useCreateType;
            @SerializedName("useCreateTypeName")
            public String useCreateTypeName;
            @SerializedName("useCreateTypeFdName")
            public String useCreateTypeFdName;
            @SerializedName("useEmail")
            public String useEmail;
            @SerializedName("useFixedPhone")
            public String useFixedPhone;
            @SerializedName("useIcon")
            public String useIcon;
            @SerializedName("useId")
            public String useId;

            @SerializedName("useIdName")
            public String useIdName;
            @SerializedName("useIdentityNum")
            public String useIdentityNum;
            @SerializedName("useIdentityType")
            public String useIdentityType;
            @SerializedName("useIdentityTypeName")
            public String useIdentityTypeName;
            @SerializedName("useIdentityTypeFdName")
            public String useIdentityTypeFdName;
            @SerializedName("useIerrorCount")
            public int useIerrorCount;
            @SerializedName("useLevel")
            public String useLevel;
            @SerializedName("useLevelName")
            public String useLevelName;
            @SerializedName("useLevelFdName")
            public String useLevelFdName;
            @SerializedName("useLoginName")
            public String useLoginName;
            @SerializedName("useLoginPswd")
            public String useLoginPswd;
            @SerializedName("useMarriageState")
            public String useMarriageState;
            @SerializedName("useMarriageStateName")
            public String useMarriageStateName;
            @SerializedName("useMarriageStateFdName")
            public String useMarriageStateFdName;
            @SerializedName("useMobilePhones")
            public String useMobilePhones;
            @SerializedName("useModifyPerson")
            public String useModifyPerson;
            @SerializedName("useModifyPersonName")
            public String useModifyPersonName;
            @SerializedName("useModifyTime")
            public long useModifyTime;
            @SerializedName("useMpType")
            public String useMpType;
            @SerializedName("useMpTypeName")
            public String useMpTypeName;
            @SerializedName("useMpTypeFdName")
            public String useMpTypeFdName;
            @SerializedName("useMsn")
            public String useMsn;
            @SerializedName("useName")
            public String useName;
            @SerializedName("useNation")
            public String useNation;
            @SerializedName("useNick")
            public String useNick;
            @SerializedName("useNo")
            public String useNo;
            @SerializedName("useNoName")
            public String useNoName;
            @SerializedName("useQq")
            public String useQq;
            @SerializedName("useSex")
            public String useSex;
            @SerializedName("useSexName")
            public String useSexName;
            @SerializedName("useSexFdName")
            public String useSexFdName;
            @SerializedName("useState")
            public String useState;
            @SerializedName("useStateName")
            public String useStateName;
            @SerializedName("useStateFdName")
            public String useStateFdName;
            @SerializedName("useStatus")
            public int useStatus;
            @SerializedName("useWw")
            public String useWw;
            @SerializedName("useZipCodeContact")
            public String useZipCodeContact;
            @SerializedName("useZipCodeDomicile")
            public String useZipCodeDomicile;
            @SerializedName("useZipCodeNow")
            public String useZipCodeNow;
            @SerializedName("useUscc")
            public String useUscc;

            public String getUseAuthRealName() {
                return useAuthRealName;
            }

            public void setUseAuthRealName(String useAuthRealName) {
                this.useAuthRealName = useAuthRealName;
            }

            public String getUseIdentityNum() {
                return useIdentityNum;
            }

            public void setUseIdentityNum(String useIdentityNum) {
                this.useIdentityNum = useIdentityNum;
            }

            public String getUseName() {
                return useName;
            }

            public void setUseName(String useName) {
                this.useName = useName;
            }
        }

        @SerializedName("dueIn")
        public DueIn dueIn;

        public class DueIn implements Serializable {
            @SerializedName("rescPlanPrin")
            public String rescPlanPrin;
            @SerializedName("closeTime")
            public String closeTime;
            @SerializedName("rescPlanInte")
            public String rescPlanInte;
            @SerializedName("closeSum")
            public String closeSum;

        }

        @SerializedName("firstOfMonth")
        public boolean firstOfMonth;
        @SerializedName("hasRepayOfMonth")
        public boolean hasRepayOfMonth;
        @SerializedName("authBackCard")
        public String authBackCard;
        @SerializedName("useAuthRealName")
        public String useAuthRealName;
        @SerializedName("changeDealPwd")
        public boolean changeDealPwd;

        @SerializedName("token")
        public String token;
    }
}
