package net.kyuzi.customenchantments.command;

import net.kyuzi.customenchantments.CustomEnchantments;
import net.kyuzi.customenchantments.CustomEnchantmentsAPI;
import net.kyuzi.customenchantments.enchantment.CustomEnchantment;
import net.kyuzi.customenchantments.utility.NumberUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomEnchantmentsCommand implements CommandExecutor {

    private static final String[] HELP_MSG = new String[]{
            ChatColor.GOLD + "CustomEnchantments commands:",
            ChatColor.GOLD + "/customenchantments list " + ChatColor.YELLOW + "Lists all custom enchantments currently loaded",
            ChatColor.GOLD + "/customenchantments disable <enchantment> " + ChatColor.YELLOW + "Temporarily disables the custom enchantment named <enchantment>",
            ChatColor.GOLD + "/customenchantments enable <enchantment> " + ChatColor.YELLOW + "Temporarily enables the custom enchantment named <enchantment>",
            ChatColor.GOLD + "/customenchantments remove <enchantment> " + ChatColor.YELLOW + "Removes the custom enchantment named <enchantment> from the item that you are holding",
            ChatColor.GOLD + "/customenchantments add <enchantment> <level> " + ChatColor.GOLD + "Adds the custom enchantment named <enchantment> with level <level> to the item that you are holding"
    };
    private static final String NO_PERM_MSG = ChatColor.RED + "You do not have permission to use this command!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (!hasPermission(sender, args[0])) {
                    sender.sendMessage(NO_PERM_MSG);
                    return true;
                }

                List<CustomEnchantment> customEnchantments = CustomEnchantments.getInstance().getEnchantments();

                if (!customEnchantments.isEmpty()) {
                    for (CustomEnchantment customEnchantment : customEnchantments) {
                        sender.sendMessage(ChatColor.GOLD + customEnchantment.getName() + ":");
                        sender.sendMessage(ChatColor.YELLOW + "- Display name: " + customEnchantment.getDisplayName());
                        sender.sendMessage(ChatColor.YELLOW + "- Start level: " + customEnchantment.getStartLevel());
                        sender.sendMessage(ChatColor.YELLOW + "- Max level: " + customEnchantment.getMaxLevel());
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "There are no custom enchantments currently stored!");
                }

                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("disable")) {
                if (!hasPermission(sender, args[0])) {
                    sender.sendMessage(NO_PERM_MSG);
                    return true;
                }

                if (CustomEnchantments.getInstance().disableEnchantment(args[1])) {
                    sender.sendMessage(ChatColor.GREEN + "You have disabled the \"" + args[1] + "\" enchantment.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No enchantment exists with the name \"" + args[1] + "\"!");
                }

                return true;
            } else if (args[0].equalsIgnoreCase("enable")) {
                if (!hasPermission(sender, args[0])) {
                    sender.sendMessage(NO_PERM_MSG);
                    return true;
                }

                if (CustomEnchantments.getInstance().enableEnchantment(args[1])) {
                    sender.sendMessage(ChatColor.GREEN + "You have enabled the \"" + args[1] + "\" enchantment.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No enchantment exists with the name \"" + args[1] + "\"!");
                }

                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("You must be a player to use this command!");
                    return true;
                }

                if (!hasPermission(sender, args[0])) {
                    sender.sendMessage(NO_PERM_MSG);
                    return true;
                }

                CustomEnchantment enchantment = CustomEnchantmentsAPI.getEnchantmentByName(args[1]);

                if (enchantment != null) {
                    Player player = (Player) sender;
                    ItemStack itemStack = player.getItemInHand();

                    if (itemStack != null) {
                        if (CustomEnchantmentsAPI.removeEnchantment(itemStack, enchantment)) {
                            sender.sendMessage(ChatColor.GREEN + "You have removed the " + enchantment.getName() + " enchantment from the item you are holding!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "The item you are holding does not have the " + enchantment.getName() + " enchantment!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "The item you are holding does not have the " + enchantment.getName() + " enchantment!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No enchantment exists with the name \"" + args[1] + "\"!");
                }

                return true;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("You must be a player to use this command!");
                    return true;
                }

                if (!hasPermission(sender, args[0])) {
                    sender.sendMessage(NO_PERM_MSG);
                    return true;
                }

                CustomEnchantment enchantment = CustomEnchantmentsAPI.getEnchantmentByName(args[1]);

                if (enchantment != null) {
                    if (NumberUtils.isLong(args[2])) {
                        Player player = (Player) sender;
                        ItemStack itemStack = player.getItemInHand();

                        if (itemStack != null) {
                            long level = Long.parseLong(args[2]);

                            if (CustomEnchantmentsAPI.addEnchantment(itemStack, enchantment, level)) {
                                sender.sendMessage(ChatColor.GREEN + "You have added the " + enchantment.getName() + " enchantment with level " + level + " to the item you are holding!");
                            } else {
                                sender.sendMessage(ChatColor.RED + "The " + enchantment.getName() + " enchantment with level " + level + " cannot be applied to the item you are holding!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "The item you are holding does not have the " + enchantment.getName() + " enchantment!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You have entered an invalid level for the " + enchantment.getName() + " enchantment!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No enchantment exists with the name \"" + args[1] + "\"!");
                }

                return true;
            }
        }

        if (!hasPermission(sender, "help")) {
            sender.sendMessage(NO_PERM_MSG);
            return true;
        }

        for (String helpMessage : HELP_MSG) {
            sender.sendMessage(helpMessage);
        }

        return true;
    }

    private boolean hasPermission(CommandSender sender, String arg) {
        return sender.hasPermission("customenchantments." + arg.toLowerCase());
    }

}
