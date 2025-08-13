package service;

import java.util.*;

public class AlertSystem {
    public boolean isAnomalous(List<Integer> usage) {
        if (usage.size() < 3) return false;
        int recent = usage.get(usage.size() - 1);
        double avg = usage.stream().mapToInt(i -> i).average().orElse(0);
        double threshold = 2.0;
        return Math.abs(recent - avg) > threshold * avg;
    }
}
