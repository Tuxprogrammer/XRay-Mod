package com.fgtXray.fgtXray.client.gui;

import com.fgtXray.fgtXray.reference.Reference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiContainer extends GuiScreen {

    private boolean hasTitle = false;
    private String title = "";

    @Override
    public void initGui() {

    }

    @Override
    protected void keyTyped( char par1, int par2 )
    {
        super.keyTyped( par1, par2 );

        // Close on esc, inventory key or keybind
        if( par2 == 1 )
            mc.thePlayer.closeScreen();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen( int x, int y, float f ) {
        drawDefaultBackground();

        mc.renderEngine.bindTexture(new ResourceLocation(Reference.PREFIX_GUI + "bg.png"));
        //drawTexturedQuadFit(width / 2 - 110, height / 2 - 118);
        drawTexturedModalRect(width / 2 - 110, height / 2 - 118, 0, 0, 256, 256);

        if( hasTitle() ) {
            FontRenderer fr = this.mc.fontRenderer;
            fr.drawStringWithShadow(title(), width / 2 - 97, height / 2 - 105, 0xffff00);
        }

        super.drawScreen(x, y, f);
    }

    @Override
    public void mouseClicked( int x, int y, int mouse )
    {
        super.mouseClicked( x, y, mouse );
    }

    public boolean hasTitle() {
        return false;
    }

    public String title() {
        return "";
    }

    FontRenderer getFontRender() {
        return this.mc.fontRenderer;
    }
}
