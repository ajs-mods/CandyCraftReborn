package com.ajsmods.candycraftreborn.client.screen;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.menu.SugarFactoryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SugarFactoryScreen extends AbstractContainerScreen<SugarFactoryMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    public SugarFactoryScreen(SugarFactoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        g.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Burn progress (flame)
        if (this.menu.isBurning()) {
            int burnProgress = this.menu.getBurnProgress();
            g.blit(TEXTURE, x + 56, y + 36 + 12 - burnProgress, 176, 12 - burnProgress, 14, burnProgress + 1);
        }

        // Cook progress (arrow)
        int cookProgress = this.menu.getCookProgress();
        g.blit(TEXTURE, x + 79, y + 34, 176, 14, cookProgress + 1, 16);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);
        super.render(g, mouseX, mouseY, partialTick);
        this.renderTooltip(g, mouseX, mouseY);
    }
}
