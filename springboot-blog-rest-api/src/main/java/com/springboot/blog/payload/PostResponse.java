package com.springboot.blog.payload;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Blog Post Response Model Information")
public class PostResponse {
	@Schema(description = "List of Post")
	private List<PostDto> content;
	@Schema(description = "Page Number")
	private int pageNo;
	@Schema(description = "Page Size")
	private int pageSize;
	@Schema(description = "Total Number of Elements")
	private long totalElements;
	@Schema(description = "Total Pages")
	private int totalPages;
	@Schema(description = "Boolean info for Last Page")
	private boolean last;
}