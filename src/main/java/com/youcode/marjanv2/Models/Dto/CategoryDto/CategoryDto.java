package com.youcode.marjanv2.Models.Dto.CategoryDto;

import com.youcode.marjanv2.Models.Dto.ProductDto;
import com.youcode.marjanv2.Models.Dto.ProductResponseDto;
import com.youcode.marjanv2.Models.Dto.PromotionDto.PromotionDto;
import com.youcode.marjanv2.Models.Dto.PromotionDto.PromotionResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private PromotionResponseDto promotion;
    private List<ProductResponseDto> product;
}
