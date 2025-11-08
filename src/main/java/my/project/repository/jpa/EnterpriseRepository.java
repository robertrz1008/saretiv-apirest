package my.project.repository.jpa;

import my.project.dto.sales.RevenuesResponse;
import my.project.entities.abm.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    @Query(value = "SELECT to_char(date_trunc('Month', sal.create_at), 'TMMonth') as \"mes\", sum(sal.total)+ sum(sup.total) as \"ingresos\"\n" +
            "from sales as sal \n" +
            "\tjoin products_detail as pd on pd.sale_id = sal.id\n" +
            "\tjoin supports as sup on pd.support_id = sup.id\n" +
            "WHERE DATE_TRUNC('year', sal.create_at) = DATE_TRUNC('year', CURRENT_DATE)\n" +
            "group by mes ORDER BY mes ASC ", nativeQuery = true)
    public List<RevenuesResponse> revenues();
}
