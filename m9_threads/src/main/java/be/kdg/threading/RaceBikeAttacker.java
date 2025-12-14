package be.kdg.threading;

import be.kdg.model.RaceBike;

import java.util.List;
import java.util.function.Predicate;

/**
 * Attacker class that removes RaceBikes from a shared list based on a predicate
 */
public class RaceBikeAttacker implements Runnable {
    private final List<RaceBike> targetList;
    private final Predicate<RaceBike> attackPredicate;
    private final Object lock;

    /**
     * Constructor for synchronized attacks
     * @param targetList The shared list to attack
     * @param attackPredicate The condition for removal
     * @param lock The shared lock object for synchronization
     */
    public RaceBikeAttacker(List<RaceBike> targetList, Predicate<RaceBike> attackPredicate, Object lock) {
        if (targetList == null) {
            throw new IllegalArgumentException("Target list cannot be null");
        }
        if (attackPredicate == null) {
            throw new IllegalArgumentException("Attack predicate cannot be null");
        }
        if (lock == null) {
            throw new IllegalArgumentException("Lock cannot be null");
        }

        this.targetList = targetList;
        this.attackPredicate = attackPredicate;
        this.lock = lock;
    }

    /**
     * Constructor without synchronization (for demonstration of the problem)
     * @param targetList The shared list to attack
     * @param attackPredicate The condition for removal
     */
    public RaceBikeAttacker(List<RaceBike> targetList, Predicate<RaceBike> attackPredicate) {
        this(targetList, attackPredicate, null);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " started attack...");

        if (lock != null) {
            synchronized (lock) {
                int sizeBefore = targetList.size();
                targetList.removeIf(attackPredicate);
                int sizeAfter = targetList.size();
                int removedCount = sizeBefore - sizeAfter;
                System.out.println(threadName + " removed " + removedCount + " bikes");
            }
        } else {
            int sizeBefore = targetList.size();
            targetList.removeIf(attackPredicate);
            int sizeAfter = targetList.size();
            int removedCount = sizeBefore - sizeAfter;
            System.out.println(threadName + " removed " + removedCount + " bikes");
        }
    }
}