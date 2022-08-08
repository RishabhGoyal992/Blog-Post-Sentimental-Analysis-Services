 package com.rishabh.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
	
	@Id
	private Integer roleId;
	
	private String roleName;
}
