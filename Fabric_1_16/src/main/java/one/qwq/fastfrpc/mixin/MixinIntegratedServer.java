package one.qwq.fastfrpc.mixin;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.GameMode;
import one.qwq.fastfrpc.Config;
import one.qwq.fastfrpc.Run;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IntegratedServer.class)
public abstract class MixinIntegratedServer {
    @Inject(method = "openToLan", at = @At(value = "RETURN", ordinal = 0))
    private void openToLan(GameMode gameMode, boolean cheatsAllowed, int port, CallbackInfoReturnable<Boolean> cir) {
        Config.localPort = port;
        Thread thread = new Thread(new Run());
        thread.setName("Reverse Proxy");
        thread.start();
    }

    @Inject(method = "stop", at = @At(value = "HEAD"))
    private void stop(boolean bl, CallbackInfo ci) {
        if (Run.process != null && Run.process.isAlive()) {
            Run.process.destroy();
        }
    }
}
