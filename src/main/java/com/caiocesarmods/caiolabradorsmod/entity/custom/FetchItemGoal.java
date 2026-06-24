package com.caiocesarmods.caiolabradorsmod.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FetchItemGoal extends Goal {

    private final LabradorEntity dog;

    private ItemEntity targetItem;

    public FetchItemGoal(LabradorEntity dog) {
        this.dog = dog;
    }

    @Override
    public boolean shouldExecute() {

        if (!dog.isTamed())
            return false;

        if (dog.getOwner() == null)
            return false;

        if (dog.getHeldItemMainhand().isEmpty() == false)
            return false;

        List<ItemEntity> items =
                dog.world.getEntitiesWithinAABB(
                        ItemEntity.class,
                        dog.getBoundingBox().grow(12.0D)
                );

        if (items.isEmpty())
            return false;

        targetItem = items.get(0);

        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {

        return targetItem != null
                && targetItem.isAlive();
    }

    @Override
    public void tick() {

        if (targetItem == null)
            return;

        double distance =
                dog.getDistanceSq(targetItem);

        if (dog.getHeldItemMainhand().isEmpty()) {

            dog.getNavigator().tryMoveToEntityLiving(
                    targetItem,
                    1.2D
            );

            if (distance < 2.0D) {

                ItemStack stack =
                        targetItem.getItem().copy();

                dog.setItemStackToSlot(
                        EquipmentSlotType.MAINHAND,
                        stack
                );

                targetItem.remove();
            }

        } else {

            LivingEntity owner =
                    (LivingEntity) dog.getOwner();

            dog.getNavigator().tryMoveToEntityLiving(
                    owner,
                    1.2D
            );

            if (dog.getDistanceSq(owner) < 4.0D) {

                ItemEntity dropped =
                        new ItemEntity(
                                dog.world,
                                owner.getPosX(),
                                owner.getPosY(),
                                owner.getPosZ(),
                                dog.getHeldItemMainhand()
                        );

                dog.world.addEntity(dropped);

                dog.setItemStackToSlot(
                        EquipmentSlotType.MAINHAND,
                        ItemStack.EMPTY
                );

                resetTask();
            }
        }
    }

    @Override
    public void resetTask() {
        targetItem = null;
    }
}
