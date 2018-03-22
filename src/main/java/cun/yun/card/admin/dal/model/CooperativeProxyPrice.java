package cun.yun.card.admin.dal.model;

import java.math.BigDecimal;
import java.util.Date;

public class CooperativeProxyPrice {
    private Long id;

    private Long bankOrLoanId;

    private Integer type;

    private BigDecimal price;

    private String isEmploy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankOrLoanId() {
        return bankOrLoanId;
    }

    public void setBankOrLoanId(Long bankOrLoanId) {
        this.bankOrLoanId = bankOrLoanId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIsEmploy() {
        return isEmploy;
    }

    public void setIsEmploy(String isEmploy) {
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
        sb.append(", bankOrLoanId=").append(bankOrLoanId);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", isEmploy=").append(isEmploy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}