package com.appcloud.vm.common;

public class SessionConstants {
	//登录用户相关session参数
	public static final String UserEmail = "email";
	public static final String UserID = "userId";
	public static final String AccessToken = "accessToken";
	public static final String AccToken = "accToken";
	public static final String LoginUserID = "loginUserId";
	public static final String KEY_USER_ID = "uid";
	public static final String KEY_SCREEN_NAME = "keyScreenName";
	public static final String KEY_PROFILE_IMAGE_URL = "keyProfileImageUrl";

	public static final String IsEnterpriseAdmin = "is_Enterprise"; 
	public static final String NORMAL_USER = "0";  //0-> 普通用户
	public static final String ENTERPRISE_ADMIN = "1";  //1->企业管理员，非拥有者
	public static final String ENTERPRISE_OWNER = "2";  //2->企业拥有者
	//快照备份的个数限制
	public static final String SnapshotNum = "snapshotNum";
	public static final String BackupNum = "backupNum";
	public static String GroupId = "groupId";
	public static String IsActived = "is_Actived";
	public static String GroupInfo = "groupInfo";
}
