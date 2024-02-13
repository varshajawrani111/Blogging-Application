package com.codewithvarsha.blogapp2.entities;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	
	private String imageName;
	
	private Date AddedDate;
	
	@ManyToOne //this means there can be many post of single category
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne //this means there can be many post by single user
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	//this means one post can have many comments
	private Set<Comment> comments = new HashSet<>();
	

}
