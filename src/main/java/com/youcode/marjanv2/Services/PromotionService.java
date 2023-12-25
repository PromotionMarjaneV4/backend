package com.youcode.marjanv2.Services;

import com.youcode.marjanv2.Enum.Status;
import com.youcode.marjanv2.Models.Dto.CategoryDto.CategoryPromotionDto;
import com.youcode.marjanv2.Models.Dto.PromotionDto.PromotionDto;
import com.youcode.marjanv2.Models.Entity.Category;
import com.youcode.marjanv2.Models.Entity.Product;
import com.youcode.marjanv2.Models.Entity.Promotion;
import com.youcode.marjanv2.Observer.Observer;
import com.youcode.marjanv2.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PromotionService {
    private List<Observer> observers = new ArrayList<>();
    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final ResponsableCenterRepository responsableCenterRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository, CategoryRepository categoryRepository, CategoryService categoryService, AdminRepository adminRepository, ResponsableCenterService responsableCenterService, ResponsableCenterRepository responsableCenterRepository, ModelMapper modelMapper,
                            ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;
        this.adminRepository = adminRepository;
        this.responsableCenterRepository = responsableCenterRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public Page<PromotionDto> getPromotions(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Promotion> promotionSlice = promotionRepository.findAll(pageRequest);

        List<PromotionDto> promotionDtoList = promotionSlice.getContent().stream()
                .map(promotion -> modelMapper.map(promotion, PromotionDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(promotionDtoList, pageRequest, promotionSlice.getTotalElements());
    }

    public PromotionDto getPromotionById(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.map(p -> modelMapper.map(p, PromotionDto.class)).orElse(null);
    }

    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion promotion = modelMapper.map(promotionDto, Promotion.class);

        // Create a new list to store the managed Category entities
        List<Category> managedCategories = new ArrayList<>();

        for (CategoryPromotionDto categoryDto : promotionDto.getCategories()) {
            Category category = categoryRepository.findById(categoryDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Category with ID " + categoryDto.getId() + " not found"));

            category.setPromotion(promotion);

            // Add the managed category to the list
            managedCategories.add(category);
        }

        // Set the categories for the promotion
        promotion.setCategories(managedCategories);

        // Save the promotion and let cascade settings handle the associated categories
        Promotion savedPromotion = promotionRepository.save(promotion);

        return modelMapper.map(savedPromotion, PromotionDto.class);
    }



    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    public Map<String, Integer> getStatistic() {
        Map<String, Integer> statistic = new HashMap<>();
        statistic.put("total_promotions", promotionRepository.countAllBy());
        statistic.put("refused", promotionRepository.countAllByStatus(Status.REFUSED));
        statistic.put("accepted", promotionRepository.countAllByStatus(Status.ACCEPTED));
        statistic.put("total_admin_center", adminRepository.countAllBy());
        statistic.put("total_responsable_rayon", responsableCenterRepository.countAllBy());
        statistic.put("total_products",productRepository.countAllBy());
        return statistic;
    }
    public PromotionDto updatePromotion(PromotionDto promotionDto) {
        Promotion existingPromotion = promotionRepository.findById(promotionDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));
        existingPromotion.setId(promotionDto.getId());
        existingPromotion.setStatus(promotionDto.getStatus());
        return modelMapper.map(promotionRepository.save(existingPromotion), PromotionDto.class);
    }
}
