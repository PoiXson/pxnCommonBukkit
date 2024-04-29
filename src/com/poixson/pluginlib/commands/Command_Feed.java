package com.poixson.pluginlib.commands;

import static com.poixson.utils.BukkitUtils.FeedPlayer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poixson.pluginlib.pxnPluginLib;
import com.poixson.tools.commands.pxnCommandRoot;


// /feed
public class Command_Feed extends pxnCommandRoot {



	public Command_Feed(final pxnPluginLib plugin) {
		super(
			plugin,
			"pxn", // namespace
			"Restore full hunger.", // desc
			null, // usage
			"pxn.cmd.feed", // perm
			new String[] { // labels
				"feed"
			}
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		final int num_args = args.length;
		// other players
		if (num_args > 0) {
			if (!sender.hasPermission("pxn.cmd.feed.other"))
				return false;
			int count = 0;
			ARG_LOOP:
			for (final String arg : args) {
				final Player p = Bukkit.getPlayer(arg);
				if (p == null) {
					sender.sendMessage(String.format("%sPlayer not found: %s", ChatColor.GOLD, arg));
					continue ARG_LOOP;
				}
				FeedPlayer(p);
				p.sendMessage(ChatColor.GOLD+"You are fed");
				count++;
			}
			if (count > 0) {
				sender.sendMessage(String.format(
					"%sFed %d player%s",
					ChatColor.AQUA,
					Integer.valueOf(count),
					(count == 1 ? "" : "s")
				));
				return true;
			}
		// single player
		} else {
			if (!sender.hasPermission("pxn.cmd.feed"))
				return false;
			FeedPlayer(player);
			sender.sendMessage(ChatColor.GOLD+"You are fed");
			return true;
		}
		return false;
	}



	@Override
	public List<String> onTabComplete(final CommandSender sender, final String[] args) {
		final String first = (args.length == 0 ? "" : args[0]);
		return this.onTabComplete_Players(first);
	}



}
