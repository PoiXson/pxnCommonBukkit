package com.poixson.pluginlib.commands;

import org.bukkit.command.CommandSender;

import com.poixson.pluginlib.pxnPluginLib;
import com.poixson.tools.commands.pxnCommandRoot;


public class Command_Home_List extends pxnCommandRoot {



	public Command_Home_List(final pxnPluginLib plugin) {
		super(
			plugin,
			"List your saved homes.", // desc
			null, // usage
			"pxn.cmd.home", // perm
			"homes",
			"listhomes", "list-homes",
			"homelist",  "home-list",
			"homeslist", "homes-list"
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
System.out.println("COMMAND:"); for (final String arg : args) System.out.println("  "+arg);
return false;
	}



}