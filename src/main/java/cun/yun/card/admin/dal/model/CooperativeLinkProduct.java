package cun.yun.card.admin.dal.model;

import java.util.Date;

public class CooperativeLinkProduct {
    private Long id;

    private Long cooperativePartnerLinkId;

    private String type;

    private String linkId;

    private Integer isEmploy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCooperativePartnerLinkId() {
        return cooperativePartnerLinkId;
    }

    public void setCooperativePartnerLinkId(Long cooperativePartnerLinkId) {
        this.cooperativePartnerLinkId = cooperativePartnerLinkId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Integer getIsEmploy() {
        return isEmploy;
    }

    public void setIsEmploy(Integer isEmploy) {
        this.isEmploy = isEmploy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cooperativePartnerLinkId=").append(cooperativePartnerLinkId);
        sb.append(", type=").append(type);
        sb.append(", linkId=").append(linkId);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}