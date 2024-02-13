package com.codewithvarsha.blogapp2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryIdInteger;
	
	@NotEmpty
	@Size(min = 3,message = "Category name must be of min 3 chars and max 10 chars")
	private String categoryName;
	
	@NotEmpty
	@Size(min = 4)
	private String categoryDescription;

}
