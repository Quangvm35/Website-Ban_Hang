package com.poly.SOF3021.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String address;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	private Date create_date;
	
	private Double total;
	
	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="username")
	private Account account;
	

}