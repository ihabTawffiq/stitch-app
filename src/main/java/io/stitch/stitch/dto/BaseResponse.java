package io.stitch.stitch.dto;

public class BaseResponse<T> {
    private T payload;
    private Boolean success;

    private String message;

    private Integer totalCount;
    private Integer currentPage;
    private Integer totalPages;
}
