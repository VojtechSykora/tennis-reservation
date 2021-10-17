package com.example.tennisreservation.reservation;


import com.example.tennisreservation.court.Surface;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ReservationManager {

    private static final HashMap<Surface, Double> SURFACE_RATES;
    private static final HashMap<GameType, Double> GAME_RATES;

    static {
        SURFACE_RATES = new HashMap<>();
        SURFACE_RATES.put(Surface.CLAY, 5.0);
        SURFACE_RATES.put(Surface.ARTIFICIAL, 6.5);
        SURFACE_RATES.put(Surface.GRASS, 8.0);

        GAME_RATES = new HashMap<>();
        GAME_RATES.put(GameType.SINGLE, 1.0);
        GAME_RATES.put(GameType.DOUBLE, 1.5);
    }

    public static double getPrice(Reservation reservation) {
        GameType gameType = reservation.getGameType();
        Surface surface = reservation.getCourt().getSurface();
        return GAME_RATES.get(gameType) * SURFACE_RATES.get(surface) * reservation.getMinutesDuration();
    }

    public static boolean reservationsOverlap(Reservation reservationOne, Reservation reservationTwo) {
        if (! reservationOne.getCourt().equals(reservationTwo.getCourt())) {
            return false;
        }

        LocalDateTime firstStart = reservationOne.getStartTime();
        LocalDateTime firstEnd = reservationOne.getEndTime();
        LocalDateTime secondStart = reservationTwo.getStartTime();
        LocalDateTime secondEnd = reservationTwo.getEndTime();

        return (firstStart.isAfter(secondStart) && firstEnd.isBefore(secondStart) ||
                firstStart.isAfter(secondEnd) && firstEnd.isBefore(secondEnd) ||
                firstStart.isEqual(secondStart));
    }


}
