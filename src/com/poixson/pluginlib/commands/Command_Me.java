package com.poixson.pluginlib.commands;

import org.bukkit.command.CommandSender;

import com.poixson.pluginlib.pxnPluginLib;
import com.poixson.tools.commands.pxnCommandRoot;


public class Command_Me extends pxnCommandRoot {



	public Command_Me(final pxnPluginLib plugin) {
		super(
			plugin,
			null, // desc
			null, // usage
			"pxn.cmd.me", // perm
			"me"
		);
	}



	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
System.out.println("COMMAND:"); for (final String arg : args) System.out.println("  "+arg);
return false;
	}



}