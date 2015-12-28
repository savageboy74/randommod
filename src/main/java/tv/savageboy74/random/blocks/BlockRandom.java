package tv.savageboy74.random.blocks;

/*
 * BlockRandom.java
 * Copyright (C) 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tv.savageboy74.random.RandomMod;
import tv.savageboy74.random.tileentity.TileEntityRandom;
import tv.savageboy74.random.util.*;

import java.util.Random;

public class BlockRandom extends BlockBase
{
  public BlockRandom() {
    super(Material.rock);
    this.setHardness(0.9F);
    this.setResistance(1.1F);
    this.setUnlocalizedName("blockRandom");
    this.setTileEntity(TileEntityRandom.class);
    this.setCreativeTab(CreativeTabs.tabBlock);
  }

  @Override
  public TileEntity createTileEntity(World world, int metadata) {
    return new TileEntityRandom();
  }

  @Override
  public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
    TileEntityRandom tileEntityRandom = TileHelper.getTileEntity(worldIn, x, y, z, TileEntityRandom.class);

    if(player.getHeldItem() != null && player.getHeldItem().getItem() == RandomMod.itemRandom)
      return false;

    if(tileEntityRandom == null)
      return false;

    if(!tileEntityRandom.isActivated())
      return false;

    player.setPositionAndRotation(tileEntityRandom.getTeleportPosX(), tileEntityRandom.getTeleportPosY(), tileEntityRandom.getTeleportPosZ(), tileEntityRandom.getTeleportPosYaw(), tileEntityRandom.getTeleportPosPitch());

    return true;
  }

  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
    super.harvestBlock(worldIn, player, x, y, z, meta);
  }

  @Override
  public void breakBlock(World worldIn, int x, int y, int z, Block block, int meta) {
    if(!worldIn.isRemote) {
      TileEntity te = worldIn.getTileEntity(x, y, z);
      ItemStack stack = new ItemStack(this);


      if(te instanceof TileEntityRandom) {
        TileEntityRandom tileEntityRandom = (TileEntityRandom) te;

        if(tileEntityRandom.isActivated()) {

          double posX = tileEntityRandom.getTeleportPosX();
          double posY = tileEntityRandom.getTeleportPosY();
          double posZ = tileEntityRandom.getTeleportPosZ();
          float  posYaw = tileEntityRandom.getTeleportPosYaw();
          float  posPitch = tileEntityRandom.getTeleportPosPitch();

          if(stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
          }

          NBTHelper.setDouble(stack, "TeleportPosX", posX);
          NBTHelper.setDouble(stack, "TeleportPosY", posY);
          NBTHelper.setDouble(stack, "TeleportPosZ", posZ);
          NBTHelper.setFloat(stack, "TeleportPosYaw", posYaw);
          NBTHelper.setFloat(stack, "TeleportPosPitch", posPitch);
        }

        float f = 0.7F;
        double d0 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d1 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d2 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(worldIn, x + d0, y + d1, z + d2, stack);
        entityitem.delayBeforeCanPickup = 10;
        worldIn.spawnEntityInWorld(entityitem);
      }
    }

    worldIn.removeTileEntity(x, y, z);
  }

  @Override
  public int quantityDropped(int meta, int fortune, Random random) {
    return 0;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
    if(itemIn.stackTagCompound != null) {
      TileEntityRandom tileEntityRandom = (TileEntityRandom) worldIn.getTileEntity(x, y, z);

      double posX = NBTHelper.getDouble(itemIn, "TeleportPosX");
      double posY = NBTHelper.getDouble(itemIn, "TeleportPosY");
      double posZ = NBTHelper.getDouble(itemIn, "TeleportPosZ");
      float  posYaw = NBTHelper.getFloat(itemIn, "TeleportPosYaw");
      float  posPitch = NBTHelper.getFloat(itemIn, "TeleportPosPitch");

      tileEntityRandom.setTeleportPos(posX, posY, posZ, posYaw, posPitch);
    }

    super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);
  }

  @Override
  public boolean onBlockEventReceived(World world, int x, int y, int z, int i, int j)
  {
    super.onBlockEventReceived(world, x, y, z, i, j);

    TileEntity tileEntity = world.getTileEntity(x, y, z);

    if (tileEntity != null) {
      return tileEntity.receiveClientEvent(i, j);
    }

    return false;
  }
}
