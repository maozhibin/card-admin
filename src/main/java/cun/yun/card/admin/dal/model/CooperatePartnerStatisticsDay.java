package cun.yun.card.admin.dal.model;

import java.math.BigDecimal;
import java.util.Date;

public class CooperatePartnerStatisticsDay {
    private Long id;

    private Integer applayUserNum;

    private Integer applayUserSuccess;

    private Integer applayUserDefeate;

    private Integer type;

    private BigDecimal salesVolume;

    private Integer isEmploy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApplayUserNum() {
        return applayUserNum;
    }

    public void setApplayUserNum(Integer applayUserNum) {
        this.applayUserNum = applayUserNum;
    }

    public Integer getApplayUserSuccess() {
        return applayUserSuccess;
    }

    public void setApplayUserSuccess(Integer applayUserSuccess) {
        this.applayUserSuccess = applayUserSuccess;
    }

    public Integer getApplayUserDefeate() {
        return applayUserDefeate;
    }

    public void setApplayUserDefeate(Integer applayUserDefeate) {
        this.applayUserDefeate = applayUserDefeate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(BigDecimal salesVolume) {
        this.salesVolume = salesVolume;
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
        sb.append(", applayUserNum=").append(applayUserNum);
        sb.append(", applayUserSuccess=").append(applayUserSuccess);
        sb.append(", applayUserDefeate=").append(applayUserDefeate);
        sb.append(", type=").append(type);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}