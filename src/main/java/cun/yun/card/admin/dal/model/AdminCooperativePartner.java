package cun.yun.card.admin.dal.model;

public class AdminCooperativePartner {
    private Long id;

    private Long cooperativePartnerId;

    private Long adminId;

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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cooperativePartnerId=").append(cooperativePartnerId);
        sb.append(", adminId=").append(adminId);
        sb.append("]");
        return sb.toString();
    }
}