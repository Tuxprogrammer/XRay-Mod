package com.fgtXray.fgtXray.client.gui;

import com.fgtXray.fgtXray.FgtXRay;
import com.fgtXray.fgtXray.client.gui.helper.HelperBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;

import java.io.IOException;
import java.util.ArrayList;

public class GuiBlocks extends GuiContainer {
    private RenderItem render;
    private GuiBlocksList blockList;
    private ArrayList<HelperBlock> blocks = new ArrayList<>();
    private GuiTextField search;
    private String lastSearched = "";
    private int selected = -1;

    GuiBlocks() {
        setBlocks( FgtXRay.blockList );
    }

    boolean blockSelected(int index) {
        return index == this.selected;
    }

    void selectBlock(int index)
    {
        if (index == this.selected)
            return;

        this.selected = index;
        mc.thePlayer.closeScreen();
        mc.displayGuiScreen( new GuiAdd( blocks.get( this.selected ) ) );
    }

    @Override
    public void initGui() {
        this.render = this.itemRender;
        this.blockList = new GuiBlocksList( this, this.blocks );

        search = new GuiTextField(getFontRender(), width / 2 -96, height / 2 + 85, 135, 18);
        search.setFocused(true);
        search.setCanLoseFocus(true);

        this.buttonList.add( new GuiButton( 0, width / 2 +43, height / 2 + 84, 60, 20, "Cancel" ) );
    }

    @Override
    public void actionPerformed( GuiButton button )
    {
        switch(button.id)
        {
            case 0: // Cancel
                mc.thePlayer.closeScreen();
                mc.displayGuiScreen( new GuiList() );
                break;

            default:
                break;
        }
    }

    @Override
    protected void keyTyped( char charTyped, int hex )
    {
        super.keyTyped(charTyped, hex);
        search.textboxKeyTyped(charTyped, hex);
    }

    @Override
    public void updateScreen()
    {
        search.updateCursorCounter();

        if(!search.getText().equals(lastSearched))
            reloadBlocks();
    }

    private void reloadBlocks() {
        blocks = new ArrayList<>();
        ArrayList<HelperBlock> tmpBlocks = new ArrayList<>();
        for( HelperBlock block : FgtXRay.blockList ) {
            if( block.getName().toLowerCase().contains( search.getText().toLowerCase() ) )
                tmpBlocks.add(block);
        }
        blocks = tmpBlocks;
        this.blockList.updateBlockList( blocks );
        lastSearched = search.getText();
    }

    @Override
    public void drawScreen( int x, int y, float f )
    {
        super.drawScreen(x, y, f);
        search.drawTextBox();
        this.blockList.drawScreen( x,  y,  f );
    }

    @Override
    public void mouseClicked( int x, int y, int button )
    {
        super.mouseClicked( x, y, button );
        search.mouseClicked(x, y, button );
        //// TODO: Design a mouse input handler for 1.7.10 GuiScrollingList
        // this.blockList.handleMouseInput(x, y);
    }

    private void setBlocks(ArrayList<HelperBlock> blocks) {
        this.blocks = blocks;
    }

    Minecraft getMinecraftInstance() {
        return this.mc;
    }

    RenderItem getRender() {
        return this.render;
    }
}
