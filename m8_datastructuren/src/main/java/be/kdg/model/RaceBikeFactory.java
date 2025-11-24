package be.kdg.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class RaceBikeFactory {
    private final static Random RANDOM = new Random();

    private RaceBikeFactory() {
    }

    public static RaceBike newEmptyRaceBike() {
        return new RaceBike();
    }

    public static RaceBike newFilledRaceBike(Brand brand, String model, Color color, double weight, int frameSize, LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset, FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType, WheelSize wheelSize) {
        return new RaceBike(brand, model, color, weight, frameSize, releaseDate, price, currency, groupset, frameMaterial, forkMaterial, brakeType, wheelSize);
    }

    public static RaceBike newRandomRaceBike() {
        Brand brand = randomEnum(Brand.class);
        String model = "Model-" + generateString(5, 2, true);
        Color color = randomEnum(Color.class);

        double weight = 5 + RANDOM.nextDouble() * 10;
        int frameSize = 48 + RANDOM.nextInt(15);

        LocalDate releaseDate = LocalDate.now().minusDays(RANDOM.nextInt(3650));
        BigDecimal price = BigDecimal.valueOf(1000 + RANDOM.nextInt(9000));
        Currency currency = randomEnum(Currency.class);

        Groupset groupset = randomEnum(Groupset.class);
        FrameMaterial frameMaterial = randomEnum(FrameMaterial.class);
        ForkMaterial forkMaterial = randomEnum(ForkMaterial.class);
        BrakeType brakeType = randomEnum(BrakeType.class);
        WheelSize wheelSize = randomEnum(WheelSize.class);

        return new RaceBike(brand, model, color, weight, frameSize, releaseDate, price, currency,
                groupset, frameMaterial, forkMaterial, brakeType, wheelSize);
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> c) {
        T[] values = c.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }

    private static String generateString(int maxWordLength, int wordCount, boolean camelCase) {
        StringBuilder sb = new StringBuilder();

        for (int w = 0; w < wordCount; w++) {
            int wordLength = 1 + RANDOM.nextInt(maxWordLength);
            StringBuilder word = new StringBuilder();

            for (int i = 0; i < wordLength; i++) {
                char nextChar;

                if (RANDOM.nextInt(3) == 0) {
                    nextChar = randomVowel();
                } else {
                    nextChar = randomConsonant();
                }

                if (camelCase && i == 0) {
                    nextChar = Character.toUpperCase(nextChar);
                }

                word.append(nextChar);
            }

            sb.append(word);
            if (w < wordCount - 1) sb.append("-");
        }

        return sb.toString();
    }

    private static char randomVowel() {
        return switch (RANDOM.nextInt(5)) {
            case 0 -> 'a';
            case 1 -> 'e';
            case 2 -> 'i';
            case 3 -> 'o';
            default -> 'u';
        };
    }

    private static char randomConsonant() {
        char c;
        do {
            c = (char) ('a' + RANDOM.nextInt(26));
        } while (isVowel(c));
        return c;
    }

    private static boolean isVowel(char c) {
        return "aeiou".indexOf(c) >= 0;
    }
}
