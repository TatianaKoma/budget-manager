package analyzer;

import ua.griddynamics.Product;
import ua.griddynamics.ProductType;
import ua.griddynamics.Purchase;

import java.util.Comparator;
import java.util.stream.Collectors;

public class CertainTypeAnalyzer implements Analyzer {

    private final ProductType type;

    public CertainTypeAnalyzer(ProductType type) {
        this.type = type;
    }

    @Override
    public String getSortedResult(Purchase purchase) {
        StringBuilder sb = new StringBuilder();
        if (!purchase.isEmptyProductListByType(type)) {
            sb.append("\n").append(type).append(":\n");

            String str = purchase.getAllProducts().stream()
                    .filter(p -> p.getType().equals(type))
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
            sb.append(str);
            sb.append("\nTotal sum: $").append(String.format("%.2f", purchase.getTotalSumByType(type)));
            return sb.toString();
        } else {
            return "\nThe purchase list is empty!";
        }
    }
}
