package dku.capstone.foodlog.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageDto<ENTITY, DTO> {

    private List<DTO> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private boolean isLast;

    public PageDto(Page<ENTITY> page, Function<ENTITY,DTO> fn) {
        this.content = page.getContent().stream().map(fn).collect(Collectors.toList());;
        this.pageNumber = page.getNumber();
        this.pageSize = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = ((page.getTotalPages()-1)==page.getNumber());
    }
}
