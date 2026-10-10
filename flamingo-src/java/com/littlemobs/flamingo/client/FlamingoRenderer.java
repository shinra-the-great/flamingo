package com.littlemobs.flamingo.client;

import com.littlemobs.flamingo.FlamingoMod;
import com.littlemobs.flamingo.entity.FlamingoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class FlamingoRenderer extends MobRenderer<FlamingoEntity, LivingEntityRenderState, FlamingoModel> {
    private static final Identifier TEXTURE = FlamingoMod.id("textures/entity/flamingo.png");

    public FlamingoRenderer(EntityRendererProvider.Context context) {
        super(context, new FlamingoModel(context.bakeLayer(FlamingoModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
