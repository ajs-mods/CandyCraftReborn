package com.ajsmods.candycraftreborn.client.screen;

import com.ajsmods.candycraftreborn.blockentity.AlchemyTableBlockEntity;
import com.ajsmods.candycraftreborn.menu.AlchemyTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {
    // Slot positions must match AlchemyTableMenu slot layout (relative to imageWidth/imageHeight origin)
    // Slots: 0 = (62,27), 1 = (98,27), 2 = (62,63), 3 = (98,63)

    private static final int SLOT_SIZE = 18;
    private static final int BG_COLOR  = 0xCC2E1E16;
    private static final int INNER_COLOR = 0xAA4A3023;
    private static final int SLOT_COLOR  = 0xCC1A1008;
    private static final int SLOT_BORDER = 0xFF9E7A3C;

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth  = 176;
        this.imageHeight = 186;   // slightly taller to fit player inventory at y=103..179
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top  = this.topPos;

        // Overall panel
        g.fill(left, top, left + imageWidth, top + imageHeight, BG_COLOR);
        // Inner area
        g.fill(left + 6, top + 14, left + imageWidth - 6, top + imageHeight - 6, INNER_COLOR);

        // Draw 4 ingredient slot backgrounds
        renderSlotBg(g, left + 62, top + 27);
        renderSlotBg(g, left + 98, top + 27);
        renderSlotBg(g, left + 62, top + 63);
        renderSlotBg(g, left + 98, top + 63);

        // Player inventory divider
        g.fill(left + 7, top + 99, left + imageWidth - 7, top + 100, SLOT_BORDER);
    }

    private void renderSlotBg(GuiGraphics g, int x, int y) {
        g.fill(x - 1, y - 1, x + SLOT_SIZE - 1, y + SLOT_SIZE - 1, SLOT_BORDER);
        g.fill(x, y, x + SLOT_SIZE - 2, y + SLOT_SIZE - 2, SLOT_COLOR);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(this.font, this.title, 8, 6, 0xFFECDCC0, false);
        // Count filled slots and render progress hint
        int filled = 0;
        for (int i = 0; i < AlchemyTableBlockEntity.SLOT_COUNT; i++) {
            if (!this.menu.getSlot(i).getItem().isEmpty()) filled++;
        }
        String hint = filled == AlchemyTableBlockEntity.SLOT_COUNT
                ? "Ready!" : filled + " / 4 ingredients";
        g.drawString(this.font, Component.literal(hint), 8, imageHeight - 18, 0xFFD2B48C, false);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);
        super.render(g, mouseX, mouseY, partialTick);
        this.renderTooltip(g, mouseX, mouseY);
    }
}
