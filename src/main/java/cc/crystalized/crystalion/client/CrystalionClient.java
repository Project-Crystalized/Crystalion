package cc.crystalized.crystalion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.net.InetSocketAddress;

public class CrystalionClient implements ClientModInitializer {

    public DiscordRPC RPC;

    @Override
    public void onInitializeClient() {
        DiscordSDK.loadSDK();

        //When player disconnects
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (handler.getConnection().getAddress() instanceof InetSocketAddress) {
                if (((InetSocketAddress) handler.getConnection().getAddress()).getHostName().toLowerCase().matches("^((.*)\\.)?crystalized\\.cc\\.?$")) {
                    System.out.println("Disabling Crystalion Discord RPC");
                    RPC.disableRPC();
                }
            }
        });

        //When player connects
        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, client) -> {
            if (handler.getConnection().getAddress() instanceof InetSocketAddress) {
                if (((InetSocketAddress) handler.getConnection().getAddress()).getHostName().toLowerCase().matches("^((.*)\\.)?crystalized\\.cc\\.?$")) {
                    System.out.println("Attempting to start Crystalion Discord RPC");
                    if (RPC == null) {
                        RPC = new DiscordRPC();
                    } else {
                        System.out.println("todo, update crystalion status method");
                        //TODO method for updating status here
                    }
                }
            }
        });
    }

    public CrystalionClient getInstance() {return this;}
}
