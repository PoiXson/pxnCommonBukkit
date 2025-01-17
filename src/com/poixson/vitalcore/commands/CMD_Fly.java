package com.poixson.vitalcore.commands;

import static com.poixson.utils.BukkitUtils.AllowFlyPlayer;
import static com.poixson.vitalcore.VitalCorePlugin.CHAT_PREFIX;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poixson.tools.commands.pxnCommandRoot;
import com.poixson.vitalcore.VitalCorePlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;


// /fly
public class CMD_Fly extends pxnCommandRoot {



	public CMD_Fly(final VitalCorePlugin plugin) {
		super(
			plugin,
			"pxn", // namespace
			"Toggle allow flying.", // desc
			null, // usage
			"pxn.cmd.fly", // perm
			// labels
			"fly"
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		final int num_args = args.length;
		// self
		if (num_args == 0) {
			if (player == null) {
				sender.sendMessage("Console cannot fly");
				return true;
			}
			if (!sender.hasPermission("pxn.cmd.fly"))
				return false;
			final boolean can_fly = ! player.getAllowFlight();
			AllowFlyPlayer(player, can_fly);
			sender.sendMessage(Component.text(
				can_fly ? "You can fly" : "Flying disabled"
			).color(NamedTextColor.GOLD));
			return true;
		// other players
		} else {
			if (!sender.hasPermission("pxn.cmd.fly.other"))
				return false;
			int count = 0;
			// find fly state
			boolean can_fly = true;
			LOOP_ARGS:
			for (final String arg : args) {
				final Player p = Bukkit.getPlayer(arg);
				if (p == null) {
					sender.sendMessage(CHAT_PREFIX.append(Component.text(
						"Player not found: "+arg).color(NamedTextColor.RED)));
					continue LOOP_ARGS;
				}
				if (!p.getAllowFlight())
					can_fly = true;
			}
			// set fly state
			LOOP_ARGS:
			for (final String arg : args) {
				final Player p = Bukkit.getPlayer(arg);
				if (p == null)
					continue LOOP_ARGS;
				AllowFlyPlayer(p, can_fly);
				p.sendMessage(Component.text(
					can_fly ? "You can fly" : "Flying disabled"
				).color(NamedTextColor.GOLD));
				count++;
			}
			if (count > 0) {
				sender.sendMessage(CHAT_PREFIX.append(Component.text(String.format(
					"Flying %s for %d player%s",
					(can_fly ? "enabled" : "disabled"),
					Integer.valueOf(count),
					(count == 1 ? "" : "s")
				)).color(NamedTextColor.AQUA)));
				return true;
			}
		}
		return false;
	}



	@Override
	public List<String> onTabComplete(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission("pxn.cmd.fly.other"))
			return null;
		return this.onTabComplete_Players(args);
	}



}
