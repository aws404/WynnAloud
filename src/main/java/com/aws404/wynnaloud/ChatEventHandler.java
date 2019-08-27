package com.aws404.wynnaloud;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import paulscode.sound.SoundSystem;



public class ChatEventHandler {
	static EntityPlayerSP player = Minecraft.getMinecraft().player;
	static WorldClient world = Minecraft.getMinecraft().world;
	static WynnAloud mainClass;
	

	@SubscribeEvent
	public static void ChatEvent(ClientChatReceivedEvent event) {
		String msg = event.getMessage().getUnformattedText();
		if (!(Minecraft.getMinecraft().isSingleplayer()) && Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("wynncraft")) {
			//Debug Message
			player.sendMessage(new TextComponentTranslation("WynnAloud Debug> Analysing Chat Message: " + msg));
			
			if (WynnAloud.SoundRegistry.containsKey(msg)) {		
				SoundEvent sound = mainClass.SoundRegistry.get(msg);
				
				//Debug Message
				player.sendMessage(new TextComponentTranslation("WynnAloud Debug> Playing voice Clip: " + sound.getSoundName().toString()));
				
				//Play Voiceline
				world.playSound(player, player.posX, player.posY, player.posZ, sound, SoundCategory.VOICE, 3, 1);
			}
		}
	}
	
}