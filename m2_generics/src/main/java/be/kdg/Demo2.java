package be.kdg;

import be.kdg.data.Data;
import be.kdg.generics.PriorityQueue;
import be.kdg.model.Brand;
import be.kdg.model.RaceBike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo2 {
    public static void main(String[] args) {
//        PriorityQueue<String> myQueue = new PriorityQueue<>();
//        myQueue.enqueue("Tokio", 2);
//        myQueue.enqueue("Denver", 5);
//        myQueue.enqueue("Rio", 2);
//        myQueue.enqueue("Oslo", 3);
//        System.out.println("Overzicht van de PriorityQueue:");
//        System.out.println(myQueue.toString());
//        System.out.println("aantal: " + myQueue.getSize());
//        System.out.println("positie van Tokio: " + myQueue.search("Tokio"));
//        System.out.println("positie van Nairobi: " + myQueue.search("Nairobi"));
//        for (int i = 0; i < 4; i++) {
//            System.out.println("Dequeue: " + myQueue.dequeue());
//        }
//        System.out.println("Size na dequeue: " + myQueue.getSize());

        var raceBikes = Data.getData();
        var raceBikeQueue = new PriorityQueue<>();

        for (RaceBike raceBike : raceBikes) {
            var random = new Random();
            raceBikeQueue.enqueue(raceBike, random.nextInt(5) + 1);
        }

        System.out.println(raceBikeQueue);
        System.out.println("aantal: " + raceBikeQueue.getSize());
        System.out.println("positie van Trek: " + raceBikeQueue.search(new RaceBike(Brand.TREK, "Emonda SL 6")));
        System.out.println("positie van Bianchi Test: " + raceBikeQueue.search(new RaceBike(Brand.BIANCHI, "Test")));
        for (int i = 0; i < 9; i++) {
            System.out.println("Dequeue: " + raceBikeQueue.dequeue());
        }
        System.out.println("Size na dequeue: " + raceBikeQueue.getSize());
    }
}