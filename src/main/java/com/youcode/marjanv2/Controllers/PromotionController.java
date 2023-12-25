package com.youcode.marjanv2.Controllers;

import com.youcode.marjanv2.Models.Dto.PromotionDto.PromotionDto;
import com.youcode.marjanv2.Models.Entity.Product;
import com.youcode.marjanv2.Models.Entity.Statitistiques;
import com.youcode.marjanv2.Services.PromotionService;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    @GetMapping
    public Page<PromotionDto> getPromotions(
            @RequestParam int page,
            @RequestParam int size){
        return promotionService.getPromotions(page, size);
    }

    @GetMapping("/{id}")
    public PromotionDto getPromotion(@PathVariable Long id){
        return promotionService.getPromotionById(id);
    }

    @PostMapping("/create")
    public PromotionDto createPromotion(@RequestBody PromotionDto promotion){
        return promotionService.createPromotion(promotion);
    }
    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable Long id){
        promotionService.deletePromotion(id);
    }

    @GetMapping("/statistic")
    public Statitistiques getPromotionStatistic(){
        return promotionService.getStatistic();
    }
    @PatchMapping("/update/{promotionId}")
    public PromotionDto updatePromotion(@PathVariable Long promotionId, @RequestBody PromotionDto promotionDto) {
        return promotionService.updatePromotion(promotionId, promotionDto);
    }
}
