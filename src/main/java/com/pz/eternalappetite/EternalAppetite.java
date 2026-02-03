package com.pz.eternalappetite;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import com.pz.eternalappetite.command.SaturationCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(EternalAppetite.MODID)
public class EternalAppetite {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "eternalappetite";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public EternalAppetite(IEventBus modEventBus, ModContainer modContainer) {

         modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

         NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("saturation")
                .requires((commandSourceStack -> commandSourceStack.hasPermission(0)))
                .requires((CommandSourceStack::isPlayer))
                .executes(new SaturationCommand())
        );
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class HudClientEvent {
        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
            if (event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) {
                Minecraft mc = Minecraft.getInstance();
                int w = event.getGuiGraphics().guiWidth() / 2 + 100;
                int h = event.getGuiGraphics().guiHeight() - 39;

                int food = (int) (mc.player.getFoodData().getSaturationLevel() / 20);
                boolean isMounted = mc.player.getVehicle() instanceof LivingEntity;
                if (mc.gameMode.canHurtPlayer() && mc.getCameraEntity() instanceof Player && !isMounted) {
                    LOGGER.info("渲染");
                    event.getGuiGraphics().drawString(mc.font,  "x" + food, w, h,Config.RGB.get(),false);
                }
            }
        }
    }
}
