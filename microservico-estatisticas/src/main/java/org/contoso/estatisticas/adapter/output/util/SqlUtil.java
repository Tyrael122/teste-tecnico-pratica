package org.contoso.estatisticas.adapter.output.util;

import org.contoso.estatisticas.domain.exception.EstatisticaException;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

public class SqlUtil {
    public static String addSortingToQuery(String baseQuery, Sort sort, List<String> allowedProperties) {
        if (sort.isUnsorted()) {
            return baseQuery;
        }

        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        queryBuilder.append(" ORDER BY ");

        // Process each sort order
        Iterator<Sort.Order> iterator = sort.iterator();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            String property = order.getProperty();
            String direction = order.getDirection().name();

            // Validate property against allowed properties
            if (!allowedProperties.contains(property)) {
                throw new EstatisticaException("Invalid sort property: " + property);
            }

            queryBuilder.append(property).append(" ").append(direction);

            if (iterator.hasNext()) {
                queryBuilder.append(", ");
            }
        }

        return queryBuilder.toString();
    }
}
