package xyz.ubatv.hub.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import xyz.ubatv.hub.Main;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemAPI {

    private Main main = Main.getInstance();

    public ItemStack item(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack potion(PotionType potionType){
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(potionType, false, false));
        potion.setItemMeta(meta);
        return potion;
    }

    public ItemStack potion(PotionType potionType, String name, String...lore){
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(potionType, false, false));
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        potion.setItemMeta(meta);
        return potion;
    }

    public ItemStack item(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack item(Material material, int quantity, String name) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack itemEnchanted(Material material, int quantity, String name, Enchantment...enchants) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        for (Enchantment enchant : enchants){
            meta.addEnchant(enchant, 1, false);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack itemEnchantedLv4(Material material, int quantity, String name, Enchantment...enchants) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        for (Enchantment enchant : enchants){
            meta.addEnchant(enchant, 4, false);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack item(Material material, int quantity, String name, String...lore) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack skull(Player player, String name, String...lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        skull.setItemMeta(meta);
        return skull;
    }

    public void addPotionToInv(Player player, PotionType potionType){
        Inventory inv = player.getInventory();
        ItemStack potion = potion(potionType);

        if(inv.firstEmpty() != -1){
            player.getInventory().addItem(potion);
            return;
        }

        try{
            for(ItemStack current : inv.getContents()) {
                if(current.getType() != Material.AIR){
                    player.getInventory().addItem(potion);
                    return;
                }
            }
        }catch (NullPointerException e){
            player.sendMessage(main.textUtils.error + "Your inventory is full.");
        }
    }

    public void addItemToInv(Player player, ItemStack item){
        Inventory inv = player.getInventory();

        if(inv.firstEmpty() != -1){
            player.getInventory().addItem(item);
            return;
        }

        try{
            for(ItemStack current : inv.getContents()) {
                if(current.getType() != Material.AIR){
                    if((current.getType().equals(item.getType()) && current.getAmount() < current.getMaxStackSize())) {
                        player.getInventory().addItem(item);
                        return;
                    }
                }
            }
        }catch (NullPointerException e){
            player.getWorld().dropItem(player.getLocation(), item);
            player.sendMessage(main.textUtils.error + "Your inventory is full.");
        }
    }

    public void addItemToInv(Player player, Material mat){
        Inventory inv = player.getInventory();
        ItemStack item = new ItemStack(mat, 1);

        if(inv.firstEmpty() != -1){
            player.getInventory().addItem(item);
            return;
        }

        try{
            for(ItemStack current : inv.getContents()) {
                if(current.getType() != Material.AIR){
                    if((current.getType().equals(mat) && current.getAmount() < current.getMaxStackSize())) {
                        player.getInventory().addItem(item);
                        return;
                    }
                }
            }
        }catch (NullPointerException e){
            player.getWorld().dropItem(player.getLocation(), item);
            player.sendMessage(main.textUtils.error + "Your inventory is full.");
        }
    }
}
