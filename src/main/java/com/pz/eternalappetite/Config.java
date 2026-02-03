package com.pz.eternalappetite;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    static final ModConfigSpec SPEC;

    public static final ModConfigSpec.DoubleValue K;

    public static final ModConfigSpec.DoubleValue MAX;

    public static final ModConfigSpec.IntValue RGB;

    static {
        K = BUILDER.comment("k值为饥饿值转换为饱和度的比例")
                .translation("eternalappetite.config.k")
                .defineInRange("K", 0.5, 0.0, 1.0);
        MAX = BUILDER.comment("饱和度最大值")
                .translation("eternalappetite.config.max")
                .defineInRange("MAX",20.0,0.0,Float.MAX_VALUE);
        RGB = BUILDER.comment("十进制有符号颜色代码")
                .translation("eternalappetite.config.rgb")
                .defineInRange("RGB",-16777216,Integer.MIN_VALUE, Integer.MAX_VALUE);
        SPEC = BUILDER.build();
    }
}
