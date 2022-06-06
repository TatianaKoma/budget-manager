package ua.griddynamics.analyzer;

import ua.griddynamics.ProductType;
import ua.griddynamics.Purchase;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsByTypeAnalyzer implements Analyzer {
    @Override
    public String getSortedResult(Purchase purchase) {
        Map<ProductType, Double> mapForSorting = new HashMap<>();
        for (ProductType type : ProductType.values()) {
            if (!type.equals(ProductType.UNDEFINED)) {
                mapForSorting.put(type, purchase.getTotalSumByType(type));
            }
        }
        StringBuilder sb = new StringBuilder("\nTypes: \n");
        String str = mapForSorting.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> entry.getKey() + " $" + String.format("%.2f", entry.getValue()))
                .collect(Collectors.joining("\n"));
        sb.append(str).append("\nTotal sum: $").append(String.format("%.2f", purchase.getTotalSumOfAllProducts()));
        return sb.toString();
    }
}
