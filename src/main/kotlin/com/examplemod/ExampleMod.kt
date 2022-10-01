import com.examplemod.commands.ExampleCommand
import com.examplemod.config.Config
import com.examplemod.config.PersistentData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.File

@Mod(
    modid = ExampleMod.MOD_ID,
    name = ExampleMod.MOD_NAME,
    version = ExampleMod.MOD_VERSION,
    clientSideOnly = true
)
class ExampleMod {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, MOD_ID)
        directory.mkdirs()
        configDirectory = directory
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        persistentData = PersistentData.load()
        config = Config.apply { this.initialize() }
        ClientCommandHandler.instance.registerCommand(ExampleCommand())

        listOf(
            this
        ).forEach(MinecraftForge.EVENT_BUS::register)
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || currentGui == null) return
        mc.displayGuiScreen(currentGui)
        currentGui = null
    }

    companion object {
        val mc: Minecraft = Minecraft.getMinecraft()
        var currentGui: GuiScreen? = null

        lateinit var configDirectory: File
        lateinit var config: Config
        lateinit var persistentData: PersistentData

        const val MOD_ID = "examplemod"
        const val MOD_NAME = "examplemod"
        const val MOD_VERSION = "1.0"
    }
}