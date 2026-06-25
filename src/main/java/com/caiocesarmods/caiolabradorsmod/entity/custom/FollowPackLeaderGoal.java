package com.caiocesarmods.caiolabradorsmod.entity.custom;

import net.minecraft.entity.ai.goal.Goal;

import java.util.Comparator;
import java.util.List;

public class FollowPackLeaderGoal extends Goal {

    private final LabradorEntity dog;
    private LabradorEntity leader;

    private final double followDistance;

    public FollowPackLeaderGoal(LabradorEntity dog) {
        this.dog = dog;
        this.followDistance = 9.0D + dog.getRNG().nextDouble() * 16.0D;
    }

    @Override
    public boolean shouldExecute() {

        // MY DOG never follows another leader.
        if (dog.isMyDog())
            return false;

        // Sitting dogs don't move.
        if (dog.isQueuedToSit())
            return false;

        List<LabradorEntity> leaders = dog.world.getEntitiesWithinAABB(
                LabradorEntity.class,
                dog.getBoundingBox().grow(32.0D),
                LabradorEntity::isMyDog
        );

        if (leaders.isEmpty())
            return false;

        // Find the closest leader.
        leader = null;
        double closest = Double.MAX_VALUE;

        for (LabradorEntity labrador : leaders) {

            double distance = dog.getDistanceSq(labrador);

            if (distance < closest) {
                closest = distance;
                leader = labrador;
            }
        }

        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {

        return leader != null
                && leader.isAlive()
                && leader.isMyDog()
                && leader.world == dog.world
                && !dog.isQueuedToSit();
    }

    @Override
    public void startExecuting() {
        moveToLeader();
    }

    @Override
    public void tick() {

        if (leader == null)
            return;

        // Keep about 4 blocks away.
        if (dog.getDistanceSq(leader) > followDistance) {

            moveToLeader();

        } else {

            dog.getNavigator().clearPath();

            dog.getLookController().setLookPositionWithEntity(
                    leader,
                    10.0F,
                    dog.getVerticalFaceSpeed()
            );
        }
    }

    @Override
    public void resetTask() {
        leader = null;
        dog.getNavigator().clearPath();
    }

    private void moveToLeader() {
        dog.getNavigator().tryMoveToEntityLiving(leader, 1.1D);
    }
}