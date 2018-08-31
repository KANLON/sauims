package com.fekpal.common.constant;

/**
 * Created by APone on 2018/2/10 21:32.
 * 各类文件存放默认路径
 */
public class FIleDefaultPath {

    /**
     * 文件根目录
     */
    private static final String FILE_ROOT = "WEB-INF/file/";

    /**
     * 图片文件根目录
     */
    private static final String IMAGES_ROOT = "WEB-INF/resources/";

    /**
     * 社团注册审核文件存放路径
     */
    public static final String CLUB_AUDIT_FILE = WebAppPath.WEB_APP_ROOT + FILE_ROOT + "club_audit/";

    /**
     * 社团注册审核在线浏览文件存放路径
     */
    public static final String CLUB_AUDIT_OVERVIEW_FILE = WebAppPath.WEB_APP_ROOT + FILE_ROOT + "club_audit_ol/";

    /**
     * 社团年度审核文件存放路径
     */
    public static final String CLUB_ANN_AUDIT_FILE = WebAppPath.WEB_APP_ROOT + FILE_ROOT + "ann/";

    /**
     * 社团年度审核在线浏览文件存放路径
     */
    public static final String CLUB_ANN_AUDIT_OVERVIEW_FILE = WebAppPath.WEB_APP_ROOT + FILE_ROOT + "ann_ol/";

    /**
     * 信息附件文件存放路径
     */
    public static final String MESSAGE_ANNEX_FILE = WebAppPath.WEB_APP_ROOT + FILE_ROOT + "msg_annex/";

    /**
     * 社团用户头像文件存放路径
     */
    public static final String CLUB_LOGO_FILE = WebAppPath.WEB_APP_ROOT + IMAGES_ROOT + "logo/";

    /**
     * 社团预览图文件存放路径
     */
    public static final String CLUB_VIEW_FILE = WebAppPath.WEB_APP_ROOT + IMAGES_ROOT + "club_view/";

    /**
     * 普通用户头像文件存放路径
     */
    public static final String PERSON_LOGO_FILE = WebAppPath.WEB_APP_ROOT + IMAGES_ROOT + "logo/";

    /**
     * 校社联用户头像文件存放路径
     */
    public static final String SAU_LOGO_FILE = WebAppPath.WEB_APP_ROOT + IMAGES_ROOT + "logo/";

    /**
     * 校社联预览图文件存放路径
     */
    public static final String SAU_VIEW_FILE = WebAppPath.WEB_APP_ROOT + IMAGES_ROOT + "sau_view/";
}
