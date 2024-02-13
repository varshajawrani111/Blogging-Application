package com.codewithvarsha.blogapp2.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse { //this class is used for pagination implementation
	
	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSizeInteger;
	private Long totalElementsInteger;
	private Integer totalPagesInteger;
	private boolean lastPage;

}
