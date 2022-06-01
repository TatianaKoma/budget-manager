package analyzer;

import ua.griddynamics.Product;
import ua.griddynamics.Purchase;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AllProductsAnalyzer implements Analyzer {

    @Override
    public String getSortedResult(Purchase purchase) {
        StringBuilder sb = new StringBuilder();
        if (!purchase.isEmptyAllProductList()) {
            sb.append("\nAll: \n");
            String str = purchase.getAllProducts().stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
            sb.append(str);
            sb.append("\nTotal sum: $").append(String.format("%.2f", purchase.getTotalSumOfAllProducts()));
            return sb.toString();
        } else {
            return "\nThe purchase list is empty!";
        }
    }
}
