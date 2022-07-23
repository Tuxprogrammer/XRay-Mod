package com.fgtXray.fgtXray.helper;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemStackHelper {

    public static boolean isEmpty(ItemStack stack) {
        if (stack.getItem() != null && stack.getItem() != Item.getItemFromBlock(Blocks.air)) {
            if (stack.stackSize <= 0)
                return true;
        } else
            return true;

        return false;
    }

}
