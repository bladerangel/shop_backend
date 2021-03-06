package com.shop_backend.models;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
  private Cart cart;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private Date dateTime;

  @ManyToOne(optional = false)
  @JoinColumn(name = "authUser_id", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private AuthUser authUser;

}