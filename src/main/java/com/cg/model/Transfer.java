package com.cg.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created_at;
    private Long created_by;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
    private Date updated_at;
    private Long updated_by;
    @Column(nullable = false)
    private BigDecimal fees;
    @Column(nullable = false)
    private BigDecimal fees_amount;

    @Column(nullable = false)
    private BigDecimal transaction_amount;
    @Column(nullable = false)
    private BigDecimal transfer_amount;
    private Long recipient_id;
    private Long sender_id;

    public Transfer() {
    }

    public Transfer(BigDecimal fees, BigDecimal fees_amount, BigDecimal transaction_amount, BigDecimal transfer_amount, Long recipient_id, Long sender_id) {
        this.fees = fees;
        this.fees_amount = fees_amount;
        this.transaction_amount = transaction_amount;
        this.transfer_amount = transfer_amount;
        this.recipient_id = recipient_id;
        this.sender_id = sender_id;
    }

    public Transfer(Date created_at, long created_by, boolean deleted, Date updated_at, long updated_by, BigDecimal fees, BigDecimal feesAmount, BigDecimal transaction_amount, BigDecimal transferAmount, Long recipientId, Long senderId) {
        this.created_at = created_at;
        this.created_by = created_by;
        this.deleted = deleted;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.fees = fees;
        fees_amount = feesAmount;
        this.transaction_amount = transaction_amount;
        transfer_amount = transferAmount;
        recipient_id = recipientId;
        sender_id = senderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BigDecimal getFees_amount() {
        return fees_amount;
    }

    public void setFees_amount(BigDecimal fees_amount) {
        this.fees_amount = fees_amount;
    }

    public BigDecimal getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(BigDecimal transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Long getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(Long recipient_id) {
        this.recipient_id = recipient_id;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }
}