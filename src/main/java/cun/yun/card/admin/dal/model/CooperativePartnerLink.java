package cun.yun.card.admin.dal.model;

import java.util.Date;

public class CooperativePartnerLink {
    private Long id;

    private Long cooperativePartnerId;

    private String url;

    private Integer linkType;

    private Integer isEmploy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCooperativePartnerId() {
        return cooperativePartnerId;
    }

    public void setCooperativePartnerId(Long cooperativePartnerId) {
        this.cooperativePartnerId = cooperativePartnerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
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
        sb.append(", cooperativePartnerId=").append(cooperativePartnerId);
        sb.append(", url=").append(url);
        sb.append(", linkType=").append(linkType);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}