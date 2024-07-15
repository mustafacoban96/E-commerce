package com.shepherd.E_commerce.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class ProductListResponseV2 {
	private List<ProductListResponse> content;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean last;

}
