package com.ats.tril.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.report.IssueReport;
import com.ats.tril.model.report.ItemWiseStockValuationReport;

public interface ItemWiseStockValuationReportRepo extends JpaRepository<ItemWiseStockValuationReport, Integer> {

	@Query(value = "SELECT\r\n" + 
			"    c.cat_id,\r\n" + 
			"    c.cat_desc,\r\n" + 
			"    grp.grp_id,\r\n" + 
			"    grp.grp_desc,\r\n" + 
			"    m_item.item_id,\r\n" + 
			"    m_item.item_desc,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_stock_detail.op_stock_qty)\r\n" + 
			"        FROM\r\n" + 
			"            t_stock_detail,\r\n" + 
			"            t_stock_header\r\n" + 
			"        WHERE\r\n" + 
			"            t_stock_header.date = :fromDate AND t_stock_header.stock_header_id = t_stock_detail.stock_header_id AND m_item.item_id = t_stock_detail.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS opening_stock,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_stock_detail.op_stock_value)\r\n" + 
			"        FROM\r\n" + 
			"            t_stock_detail,\r\n" + 
			"            t_stock_header\r\n" + 
			"        WHERE\r\n" + 
			"            t_stock_header.date = :fromDate AND t_stock_header.stock_header_id = t_stock_detail.stock_header_id AND m_item.item_id = t_stock_detail.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS op_stock_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)\r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header\r\n" + 
			"        WHERE\r\n" + 
			"            t_mrn_header.mrn_date BETWEEN :fromDate AND :toDate AND t_mrn_header.mrn_id = t_mrn_detail.mrn_id AND m_item.item_id = t_mrn_detail.item_id AND t_mrn_header.del_status = 1 AND t_mrn_detail.del_status = 1 AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS approve_qty,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(\r\n" + 
			"                po_detail.item_rate * t_mrn_detail.approve_qty\r\n" + 
			"            )\r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail\r\n" + 
			"        WHERE\r\n" + 
			"            t_mrn_header.mrn_date BETWEEN :fromDate AND :toDate AND t_mrn_header.mrn_id = t_mrn_detail.mrn_id AND m_item.item_id = t_mrn_detail.item_id AND t_mrn_header.del_status = 1 AND t_mrn_detail.del_status = 1 AND po_detail.po_detail_id = t_mrn_detail.po_detail_id AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS approved_qty_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(\r\n" + 
			"                (\r\n" + 
			"                    po_detail.landing_cost / po_detail.item_qty\r\n" + 
			"                ) * t_mrn_detail.approve_qty\r\n" + 
			"            )\r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail\r\n" + 
			"        WHERE\r\n" + 
			"            t_mrn_header.mrn_date BETWEEN :fromDate AND :toDate AND t_mrn_header.mrn_id = t_mrn_detail.mrn_id AND m_item.item_id = t_mrn_detail.item_id AND t_mrn_header.del_status = 1 AND t_mrn_detail.del_status = 1 AND po_detail.po_detail_id = t_mrn_detail.po_detail_id AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS approved_landing_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(\r\n" + 
			"                item_issue_detail.item_issue_qty\r\n" + 
			"            )\r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail\r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date BETWEEN :fromDate AND :toDate AND item_issue_header.issue_id = item_issue_detail.issue_id AND m_item.item_id = item_issue_detail.item_id AND item_issue_header.delete_status = 1 AND item_issue_detail.del_status = 1 AND item_issue_detail.status = 2\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS issue_qty,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(\r\n" + 
			"                item_issue_detail.item_issue_qty * po_detail.item_rate\r\n" + 
			"            )\r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail\r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date BETWEEN :fromDate AND :toDate AND item_issue_header.issue_id = item_issue_detail.issue_id AND m_item.item_id = item_issue_detail.item_id AND item_issue_header.delete_status = 1 AND item_issue_detail.del_status = 1 AND item_issue_detail.mrn_detail_id = t_mrn_detail.mrn_detail_id AND po_detail.po_detail_id = t_mrn_detail.po_detail_id AND item_issue_detail.status = 2\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS issue_qty_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(\r\n" + 
			"                (\r\n" + 
			"                    po_detail.landing_cost / po_detail.item_qty\r\n" + 
			"                ) * item_issue_detail.item_issue_qty\r\n" + 
			"            )\r\n" + 
			"        FROM\r\n" + 
			"            item_issue_header,\r\n" + 
			"            item_issue_detail,\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            po_detail\r\n" + 
			"        WHERE\r\n" + 
			"            item_issue_header.issue_date BETWEEN :fromDate AND :toDate AND item_issue_header.issue_id = item_issue_detail.issue_id AND m_item.item_id = item_issue_detail.item_id AND item_issue_header.delete_status = 1 AND item_issue_detail.del_status = 1 AND item_issue_detail.mrn_detail_id = t_mrn_detail.mrn_detail_id AND po_detail.po_detail_id = t_mrn_detail.po_detail_id AND item_issue_detail.status = 2\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS issue_landing_value,\r\n" + 
			"    COALESCE(0) AS return_issue_qty,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_damage.qty)\r\n" + 
			"        FROM\r\n" + 
			"            t_damage\r\n" + 
			"        WHERE\r\n" + 
			"            t_damage.date BETWEEN :fromDate AND :toDate AND t_damage.del_status = 1 AND m_item.item_id = t_damage.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS damage_qty,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_damage.qty * t_damage.value)\r\n" + 
			"        FROM\r\n" + 
			"            t_damage\r\n" + 
			"        WHERE\r\n" + 
			"            t_damage.date BETWEEN :fromDate AND :toDate AND t_damage.del_status = 1 AND m_item.item_id = t_damage.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS damage_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_stock_detail.gatepass_value)\r\n" + 
			"        FROM\r\n" + 
			"            t_stock_detail,\r\n" + 
			"            t_stock_header\r\n" + 
			"        WHERE\r\n" + 
			"            t_stock_header.date = :fromDate AND t_stock_header.stock_header_id = t_stock_detail.stock_header_id AND m_item.item_id = t_stock_detail.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS op_landing_value,\r\n" + 
			"    COALESCE(\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            SUM(t_damage.qty * t_damage.float1)\r\n" + 
			"        FROM\r\n" + 
			"            t_damage\r\n" + 
			"        WHERE\r\n" + 
			"            t_damage.date BETWEEN :fromDate AND :toDate AND t_damage.del_status = 1 AND m_item.item_id = t_damage.item_id\r\n" + 
			"    ),\r\n" + 
			"    0\r\n" + 
			"    ) AS damage_landing_value,\r\n" + 
			"    COALESCE(0) AS gatepass_return_qty,\r\n" + 
			"    COALESCE(0) AS gatepass_qty\r\n" + 
			"FROM\r\n" + 
			"    m_item,\r\n" + 
			"    m_category c,\r\n" + 
			"    m_item_group grp\r\n" + 
			"WHERE\r\n" + 
			"    m_item.is_used = 1 AND m_item.cat_id = c.cat_id AND grp.grp_id = m_item.grp_id\r\n" + 
			"ORDER BY\r\n" + 
			"    c.cat_id,\r\n" + 
			"    grp.grp_id,\r\n" + 
			"    m_item.item_id", nativeQuery = true)
	List<ItemWiseStockValuationReport> getIssueReportList(@Param("fromDate") String fromDate,@Param("toDate") String toDate);

}
