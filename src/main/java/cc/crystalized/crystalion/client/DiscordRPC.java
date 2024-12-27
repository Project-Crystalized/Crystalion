package cc.crystalized.crystalion.client;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

// https://github.com/JnCrMx/discord-game-sdk4j/blob/master/examples/ActivityExample.java
// Mostly copy-pasted from Tubion

public class DiscordRPC{

    public static final Logger LOGGER = LoggerFactory.getLogger("Crystalion/Discord");
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public Core core;

    public DiscordRPC() {

        //TODO from Tubion; make a config option and check if its enabled before doing these
        initialise();
        updateStatus();
    }

    public void initialise() {
        CreateParams params = new CreateParams();
        params.setClientID(1321848154697764965L);
        params.setFlags(CreateParams.Flags.DEFAULT);

        try {
            core = new Core(params);
            LOGGER.info("Successfully initialized Discord GameSDK!");
        } catch(GameSDKException ex) {
            LOGGER.error("Error initializing Discord GameSDK:");
            ex.printStackTrace();
        }
    }

    public void disableRPC() {
        try {
            Core saved = this.core;
            this.core = null;
            saved.activityManager().clearActivity();
            saved.runCallbacks();
            saved.close();
        } catch(GameSDKException ex) {
            ex.printStackTrace();
        }
    }

    public void updateStatus() {
        try (Activity activity = new Activity()) {
            activity.setDetails("lorem ipsum1");
            activity.setState("lorem ipsum2");

            activity.timestamps().setStart(Instant.now());
            activity.assets().setLargeImage("logo_crystalized");
            activity.assets().setLargeText("Project Crystalized/Crystalion");

            activity.assets().setSmallImage("logo_crystalized");
            activity.assets().setSmallText("lorem ipsum");

            core.activityManager().updateActivity(activity);
            LOGGER.info("RPC Update successful");
        } catch(GameSDKException ex) {
            LOGGER.info("Failed to update RPC");
        }
    }
}
