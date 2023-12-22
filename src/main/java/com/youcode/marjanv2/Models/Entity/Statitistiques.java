package com.youcode.marjanv2.Models.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statitistiques {
    private int totalPromotions;
    private int refused;
    private int accepted;
    private int totalAdminCenter;
    private int totalResponsableRayon;
    private int totalProducts;
}
