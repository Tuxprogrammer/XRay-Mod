package com.fgtXray.fgtXray.client.gui;

import com.fgtXray.fgtXray.client.OresSearch;
import com.fgtXray.fgtXray.client.gui.helper.HelperBlock;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.io.IOException;
import java.util.Objects;
import org.lwjgl.opengl.GL11;

public class GuiAdd extends GuiContainer {
    private GuiTextField oreName;
    private GuiSlider redSlider;
    private GuiSlider greenSlider;
    private GuiSlider blueSlider;
    private HelperBlock selectBlock;
    private boolean oreNameCleared = false;

    GuiAdd(HelperBlock selectedBlock) {
        this.selectBlock = selectedBlock;
    }

    @Override
    public void initGui() {
        // Called when the gui should be (re)created
        this.buttonList.add(new GuiButton(98, width / 2 - 25, height / 2 + 86, 130, 20, "Add"));

        this.buttonList.add(redSlider = new GuiSlider(3, width / 2 - 97, height / 2 + 7, "Red", 0, 255));
        this.buttonList.add(greenSlider = new GuiSlider(2, width / 2 - 97, height / 2 + 30, "Green", 0, 255));
        this.buttonList.add(blueSlider = new GuiSlider(1, width / 2 - 97, height / 2 + 53, "Blue", 0, 255));

        redSlider.sliderValue = 0.0F;
        greenSlider.sliderValue = 0.654F;
        blueSlider.sliderValue = 1.0F;

        oreName = new GuiTextField(this.fontRendererObj, width / 2 - 97, height / 2 - 63, 202, 20);
        oreName.setText("Gui Name");

        this.buttonList.add(new GuiButton(99, width / 2 - 100, height / 2 + 86, 72, 20, "Cancel")); // Cancel button
    }

    @Override
    public void actionPerformed(GuiButton button) // Called on left click of GuiButton
    {
        switch (button.id) {
            case 98: // Add
                int[] rgb = { (int) (redSlider.sliderValue * 255), (int) (greenSlider.sliderValue * 255),
                        (int) (blueSlider.sliderValue * 255) };

                int tmpId = Block.getIdFromBlock(selectBlock.getBlock());
                OresSearch.add(tmpId, selectBlock.getItemStack().getItemDamage(), oreName.getText(), rgb);

                mc.thePlayer.closeScreen();
                mc.displayGuiScreen(new GuiList());
                break;

            case 99: // Cancel
                mc.thePlayer.closeScreen();
                mc.displayGuiScreen(new GuiList());
                break;

            default:
                break;
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException // par1 is char typed, par2 is ascii hex (tab=15
                                                                    // return=28)
    {
        super.keyTyped(par1, par2);

        if (oreName.isFocused())
            oreName.textboxKeyTyped(par1, par2);
        else {
            switch (par2) {
                case 15: // Change focus to oreName on focus-less tab
                    if (!oreNameCleared)
                        oreName.setText("");
                    oreName.setFocused(true);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void updateScreen() {
        oreName.updateCursorCounter();
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        getFontRender().drawStringWithShadow(selectBlock.getName(), width / 2 - 97, height / 2 - 90, 0xffffff);

        oreName.drawTextBox();

        renderPreview(width, height, redSlider.sliderValue, greenSlider.sliderValue, blueSlider.sliderValue);

        RenderHelper.enableGUIStandardItemLighting();
        this.itemRender.renderItemAndEffectIntoGUI(selectBlock.getItemStack(), width / 2 + 88, height / 2 - 105);
        RenderHelper.disableStandardItemLighting();
    }

    public static void renderPreview(int width, int height, float r, float g, float b) {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.color(r, g, b, 1);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(width / 2 - 97, height / 2 - 40, 0.0D).endVertex();
        vertexbuffer.pos(width / 2 - 97, height / 2 + 4, 0.0D).endVertex();
        vertexbuffer.pos(width / 2 + 105, height / 2 + 4, 0.0D).endVertex();
        vertexbuffer.pos(width / 2 + 105, height / 2 - 40, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @Override
    public void mouseClicked(int x, int y, int mouse) throws IOException {
        super.mouseClicked(x, y, mouse);
        oreName.mouseClicked(x, y, mouse);

        if (oreName.isFocused() && !oreNameCleared) {
            oreName.setText("");
            oreNameCleared = true;
        }

        if (!oreName.isFocused() && oreNameCleared && Objects.equals(oreName.getText(), "")) {
            oreNameCleared = false;
            oreName.setText("Gui Name");
        }
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String title() {
        return "Configure Block";
    }
}