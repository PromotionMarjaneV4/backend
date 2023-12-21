package com.youcode.marjanv2.Models.Dto.PromotionDto;

import com.youcode.marjanv2.Enum.Status;

import com.youcode.marjanv2.Models.Dto.CategoryDto.CategoryPromotionDto;
import com.youcode.marjanv2.Models.Entity.Promotion;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDto {
    private Long id;
    private String name;
    private String description;
    private Date created_at;
    private Date ending_at;
    private Double discountPercentage;
    private Status status;
    private List<CategoryPromotionDto> categories = new ArrayList<CategoryPromotionDto>();}