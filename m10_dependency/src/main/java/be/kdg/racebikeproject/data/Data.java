package be.kdg.racebikeproject.data;

import be.kdg.racebikeproject.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<RaceBike> getData() {
        List<RaceBike> raceBikes = new ArrayList<>();

        raceBikes.add(new RaceBike(
                Brand.SPECIALIZED, "S-Works Tarmac SL7", Color.RED,
                6.8, 56, LocalDate.of(2021, 3, 15),
                new BigDecimal("12500"), Currency.EUR,
                Groupset.SHIMANO_DURA_ACE_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.PINARELLO, "Dogma F12", Color.BLUE,
                7.2, 54, LocalDate.of(2020, 8, 10),
                new BigDecimal("11800"), Currency.EUR,
                Groupset.CAMPAGNOLO_SUPER_RECORD_EPS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.CERVELO, "S5", Color.BLACK,
                7.5, 58, LocalDate.of(2022, 1, 20),
                new BigDecimal("9500"), Currency.EUR,
                Groupset.SRAM_RED_ETAP_AXS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.CANYON, "Ultimate CF SL 8.0", Color.WHITE,
                8.1, 56, LocalDate.of(2021, 6, 5),
                new BigDecimal("3200"), Currency.EUR,
                Groupset.SHIMANO_ULTEGRA_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.GIANT, "TCR Advanced Pro 1", Color.ORANGE,
                7.8, 54, LocalDate.of(2022, 4, 12),
                new BigDecimal("4500"), Currency.EUR,
                Groupset.SHIMANO_ULTEGRA_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_MECHANICAL, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.TREK, "Emonda SL 6", Color.GREEN,
                8.5, 52, LocalDate.of(2021, 9, 18),
                new BigDecimal("3800"), Currency.EUR,
                Groupset.SHIMANO_105, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.SPECIALIZED, "Crux Expert", Color.BROWN,
                8.9, 56, LocalDate.of(2022, 2, 28),
                new BigDecimal("4200"), Currency.EUR,
                Groupset.SHIMANO_GRX_810, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.CANYON, "Grail CF SL 7.0", Color.PURPLE,
                9.2, 58, LocalDate.of(2021, 11, 8),
                new BigDecimal("2800"), Currency.EUR,
                Groupset.SHIMANO_GRX_600, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.CUBE, "Attain Race", Color.BLUE,
                9.8, 56, LocalDate.of(2021, 7, 22),
                new BigDecimal("1200"), Currency.EUR,
                Groupset.SHIMANO_TIAGRA, FrameMaterial.ALUMINIUM, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.MERIDA, "Scultura 400", Color.RED,
                10.2, 54, LocalDate.of(2020, 12, 3),
                new BigDecimal("950"), Currency.EUR,
                Groupset.SHIMANO_SORA, FrameMaterial.ALUMINIUM, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.CERVELO, "P5", Color.BLACK,
                8.8, 56, LocalDate.of(2021, 5, 14),
                new BigDecimal("8500"), Currency.EUR,
                Groupset.SHIMANO_DURA_ACE_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.TIME_TRIAL_650C
        ));

        raceBikes.add(new RaceBike(
                Brand.COLNAGO, "Master X-Light", Color.WHITE,
                11.5, 58, LocalDate.of(2019, 10, 30),
                new BigDecimal("2800"), Currency.EUR,
                Groupset.CAMPAGNOLO_CHORUS, FrameMaterial.STEEL, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.WILIER, "Zero SLR", Color.YELLOW,
                7.1, 54, LocalDate.of(2022, 6, 8),
                new BigDecimal("6800"), Currency.EUR,
                Groupset.CAMPAGNOLO_SUPER_RECORD_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.SCOTT, "Addict RC Pro", Color.ORANGE,
                7.9, 56, LocalDate.of(2021, 12, 17),
                new BigDecimal("5200"), Currency.EUR,
                Groupset.SHIMANO_ULTEGRA_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C
        ));

        raceBikes.add(new RaceBike(
                Brand.GIANT, "Contend 3", Color.BLUE,
                11.8, 52, LocalDate.of(2020, 4, 25),
                new BigDecimal("650"), Currency.EUR,
                Groupset.SHIMANO_CLARIS, FrameMaterial.ALUMINIUM, ForkMaterial.STEEL,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C
        ));

        return raceBikes;
    }
}