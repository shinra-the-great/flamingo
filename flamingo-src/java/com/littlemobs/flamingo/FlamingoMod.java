package com.littlemobs.flamingo;

import com.littlemobs.flamingo.client.FlamingoModel;
import com.littlemobs.flamingo.client.FlamingoRenderer;
import com.littlemobs.flamingo.entity.FlamingoEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(FlamingoMod.MODID)
public final class FlamingoMod {
    public static final String MODID = "littlemobsflamingo";

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<EntityType<FlamingoEntity>> FLAMINGO = ENTITIES.register("flamingo",
            () -> EntityType.Builder.of(FlamingoEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 1.7F)
                    .clientTrackingRange(10)
                    .build(ENTITIES.key("flamingo")));

    public static final RegistryObject<Item> FLAMINGO_SPAWN_EGG = ITEMS.register("flamingo_spawn_egg",
            () -> new SpawnEggItem(new Item.Properties()
                    .setId(ITEMS.key("flamingo_spawn_egg"))
                    .spawnEgg(FLAMINGO.get())));

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public FlamingoMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        ENTITIES.register(modBusGroup);
        ITEMS.register(modBusGroup);

        EntityAttributeCreationEvent.BUS.addListener(FlamingoMod::onAttributes);
        SpawnPlacementRegisterEvent.BUS.addListener(FlamingoMod::onSpawnPlacements);
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(FlamingoMod::onLayers);
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(FlamingoMod::onRenderers);
        BuildCreativeModeTabContentsEvent.BUS.addListener(FlamingoMod::addCreative);
    }

    private static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(FLAMINGO.get(), FlamingoEntity.createAttributes().build());
    }

    private static void onSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(FLAMINGO.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR);
    }

    private static void onLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FlamingoModel.LAYER_LOCATION, FlamingoModel::createBodyLayer);
    }

    private static void onRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FLAMINGO.get(), FlamingoRenderer::new);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
            event.accept(FLAMINGO_SPAWN_EGG.get());
    }
}
