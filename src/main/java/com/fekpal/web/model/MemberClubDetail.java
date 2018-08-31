package com.fekpal.web.model;

/**
 * 社员或普通成员查看的社团信息
 * @author zhangcanlong
 */
public class MemberClubDetail extends OrgDetail {
    /**
     * 表示该用户是否喜欢该社团，如果喜欢，这isLike=1，如果之前不喜欢则，isLike=0；
     */
    private Integer isLike;

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }
}
