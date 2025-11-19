package com.softcomp.fuzzy.aggregation;

import java.util.Map;

public interface IAggregator {
    /**
     * Aggregates multiple fuzzy outputs into a single combined fuzzy map.
     * @param fuzzyValues map of fuzzySetName → membershipDegree
     * @return aggregated map (same structure)
     */
    Map<String, Double> aggregate(Map<String, Double> fuzzyValues);
}