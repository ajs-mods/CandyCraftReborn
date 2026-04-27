package com.ajsmods.candycraftreborn.client.screen;

import com.ajsmods.candycraftreborn.menu.AlchemyTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {
    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        guiGraphics.fill(left, top, left + this.imageWidth, top + this.imageHeight, 0xCC2E1E16);
        guiGraphics.fill(left + 6, top + 18, left + this.imageWidth - 6, top + this.imageHeight - 6, 0xAA4A3023);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 0xFFECDCC0, false);
        guiGraphics.drawString(this.font, Component.translatable("menu.candycraftreborn.alchemy_table.hint"), 8, 24, 0xFFD2B48C, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
