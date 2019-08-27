package com.aws404.wynnaloud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.actors.threadpool.Arrays;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.jline.utils.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Mod(modid = WynnAloud.MODID, name = WynnAloud.NAME, version = WynnAloud.VERSION)
public class WynnAloud {
    public static final String MODID = "wynnaloud";
    public static final String NAME = "WynnAloud";
    public static final String VERSION = "1.0";

    private static Logger logger;
    private JsonParser parser = new JsonParser();
    
    public boolean debug = true;
    
    public static HashMap<String, SoundEvent> SoundRegistry = new HashMap<String, SoundEvent>();
    public File soundpackFolder; //needs to be asetss file (ensure this works from resourcepacks) 

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
		try {
			applyRp();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException e) {
			e.printStackTrace();
		}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ChatEventHandler.class);
    	checkSoundpackVersion();
    }
    	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	String jsonString = "";
		try {
			ResourceLocation resource = new ResourceLocation("wynnaloud", "sounds.json");
			BufferedReader jsonStringLines = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream(), "UTF-8"));
			for(Object s : jsonStringLines.lines().toArray()) {
				jsonString = jsonString + s.toString();
			}
			jsonStringLines.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonObject json = (JsonObject) new JsonParser().parse(jsonString); 
		for (java.util.Map.Entry<String, JsonElement> o : json.entrySet()) {
			JsonObject soundJson = (JsonObject) o.getValue();
			String trigger = soundJson.get("trigger").getAsString();
			String name = o.getKey();
			ResourceLocation soundResource = new ResourceLocation("wynnaloud", name);
			SoundEvent sound = new SoundEvent(soundResource);
			SoundRegistry.put(trigger, sound);
		}
    }
    
    public void checkSoundpackVersion() {
    	logger.info("Checking for Soundpack update...");
		try {
	    	String versionInputLine;
	    	ResourceLocation resource = new ResourceLocation("wynnaloud", "version.txt");
	    	StringBuffer localVersion= new StringBuffer();;
	    	BufferedReader versionFile = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream(), "UTF-8"));
			while ((versionInputLine = versionFile.readLine()) != null) {
				localVersion.append(versionInputLine);
			}
			versionFile.close();
			
			
			URL obj = new URL("https://aws404.github.io/WynnAloud/soundpacks/current_version.txt");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				logger.info("Error Checking Soundpack Version, Response code from server: " + responseCode);
				return;
			}
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer currentVersion = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				currentVersion.append(inputLine);
			}
			in.close();
			
			logger.info("Current Online Version: " + currentVersion.toString());
			logger.info("Local Version: " + localVersion.toString());
			
			if (!currentVersion.toString().contentEquals(localVersion.toString())) {
				logger.info("Old Soundpack Version Found, Updating!");
				if (updateSoundPack(currentVersion.toString())) {
					logger.info("Success!");
				} else {
					logger.error("Soundpack Update Failed!");
				}
				
			}
			applyRp();
		} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
    }
    
    private boolean updateSoundPack(String version) {
    	try {
			ReadableByteChannel readableByteChannel = Channels.newChannel(new URL("https://aws404.github.io/WynnAloud/soundpacks/" + version + ".zip").openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("resourcepacks/WynnAloudSoundpack.zip");
			FileChannel fileChannel = fileOutputStream.getChannel();
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			fileOutputStream.close();
			
			return true;
    	} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    private void applyRp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
    	File rpFile = new File(Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks(), "WynnAloudSoundpack.zip");
		if (!rpFile.exists()) return;
    	ResourcePackRepository rpr = Minecraft.getMinecraft().getResourcePackRepository();
		Class cls2 = Entry.class;
		Constructor md2 = cls2.getDeclaredConstructor(ResourcePackRepository.class, File.class);
		md2.setAccessible(true);
		Entry entry = (Entry) md2.newInstance(rpr, rpFile);
		entry.updateResourcePack();
		List<Entry> newrpr = new ArrayList<Entry>(rpr.getRepositoryEntries());
		newrpr.add(entry);
		rpr.setRepositories(newrpr);		
		rpr.updateRepositoryEntriesAll();
		Minecraft.getMinecraft().refreshResources();
    }

}
