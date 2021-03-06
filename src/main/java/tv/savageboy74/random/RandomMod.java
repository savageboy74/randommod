package tv.savageboy74.random;

/*
 * RandomMod.java
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

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tv.savageboy74.random.blocks.BlockLinkEraser;
import tv.savageboy74.random.blocks.BlockTeleporter;
import tv.savageboy74.random.client.ModCreativeTab;
import tv.savageboy74.random.itemblocks.ItemBlockLinkEraser;
import tv.savageboy74.random.itemblocks.ItemBlockTeleporter;
import tv.savageboy74.random.items.ItemTeleporterLinker;
import tv.savageboy74.random.proxy.CommonProxy;

@Mod(modid = RandomMod.MODID, version = RandomMod.VERSION, name = RandomMod.MODNAME)
public class RandomMod
{
  @Mod.Instance(RandomMod.MODID)
  public static RandomMod instance;

  @SidedProxy(serverSide = "tv.savageboy74.random.proxy.CommonProxy", clientSide = "tv.savageboy74.random.proxy.ClientProxy")
  public static CommonProxy proxy;

  public static final String MODID = "randommod";
  public static final String MODNAME = "RandomMod";
  public static final String VERSION = "1.7.10-1.0.2";

  public static Item itemRandom = new ItemTeleporterLinker();
  public static Block blockRandom = new BlockTeleporter();
  public static Block blockLinkEraser = new BlockLinkEraser();

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    initBlocks();
    initItems();
    proxy.preInit();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init();
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit();
  }

  @Mod.EventHandler
  public void serverStarting(FMLServerStartingEvent event) {
    proxy.serverStarting();
  }

  private void initItems() {
    GameRegistry.registerItem(itemRandom, "itemRandom");
  }

  private void initBlocks() {
    GameRegistry.registerBlock(blockRandom, ItemBlockTeleporter.class, "blockRandom");
    GameRegistry.registerBlock(blockLinkEraser, ItemBlockLinkEraser.class, "blockLinkEraser");
  }
}
