package codes.reason.youaintcool;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class YouAintCoolClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoConfig.register(YouAintCoolConfig.class, Toml4jConfigSerializer::new);

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) ->
            dispatcher.register(ClientCommandManager.literal("youaintcool")
                    .executes(ctx -> {
                        MinecraftClient.getInstance().send(() ->
                            MinecraftClient.getInstance().setScreen(
                                    AutoConfig.getConfigScreen(YouAintCoolConfig.class, null).get()
                            )
                        );
                        return 1;
                    }))
        ));
    }

}