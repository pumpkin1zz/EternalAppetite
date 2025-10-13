package com.pz.eternalappetite.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FoodData.class})
public abstract class HungerMixin {
    @Shadow
    private int foodLevel;
    @Shadow
    private float saturationLevel;

    @Unique
    private float overflow;
    public HungerMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"add"},
            cancellable = true
    )
    private void add(int hunger, float saturation, CallbackInfo ci) {
        ci.cancel();

        float k = 0.5f;
        int hunger1 = this.foodLevel + hunger;
        if (hunger1 > 20) this.overflow = k * (hunger1 - 20);
        this.foodLevel = Mth.clamp(hunger1, 0, 20);
        this.saturationLevel = Mth.clamp(this.saturationLevel + saturation + this.overflow, 0.0F, Float.MAX_VALUE);

    }
}
