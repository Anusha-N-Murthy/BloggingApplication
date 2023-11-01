package com.codewithsaurabh.blog_app_apis.payloads;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	private List<PostDto> content;
	private int pageNumber;
	private boolean firstPage;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}