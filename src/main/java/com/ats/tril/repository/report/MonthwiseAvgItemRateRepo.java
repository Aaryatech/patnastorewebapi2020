package com.ats.tril.repository.report;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import com.ats.tril.model.report.MonthwiseAvgItemRate;

public interface MonthwiseAvgItemRateRepo extends JpaRepository<MonthwiseAvgItemRate, Integer> {
	

	/*@Query(value = " SELECT m_item.item_id, m_item.item_code,m_item.item_desc,m_item.item_uom," + 
			" coalesce((SELECT" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header" + 
			"" + 
			"        WHERE" + 
			"            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                                    " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1                                           " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id                             " + 
			"                  " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"" + 
			"" + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_landing_value," + 
			"                 COALESCE((SELECT SUM(t_mrn_detail.approve_qty) " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header" + 
			"" + 
			"        WHERE" + 
			"            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                                     " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1                                           " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id                             " + 
			"                  " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"" + 
			"" + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_qty," + 
			"        " + 
			"        ROUND(coalesce((Select " + 
			"            AVG(po_detail.landing_cost/t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header" + 
			"" + 
			"        where" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                         " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1                                           " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id                             " + 
			"                  " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"" + 
			"" + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0),2) AS avg_cost_fm,"
			+ ""
			+ "ROUND(coalesce((Select " + 
			"			           AVG(po_detail.landing_cost/t_mrn_detail.approve_qty)                            "+ 
			"			       FROM " + 
			"			           t_mrn_detail," + 
			"			           t_mrn_header, " + 
			"			           po_detail," + 
			"			            po_header " + 
			"		        where " + 
			"			            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                              " + 
			"			           AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                          " + 
			"			            AND m_item.item_id=t_mrn_detail.item_id                                        " + 
			"			           and t_mrn_header.del_status=1                                         " + 
			"			           and t_mrn_detail.del_status=1                                         " + 
			"			           and po_detail.po_detail_id=t_mrn_detail.po_detail_id                             " + 
			"		            and t_mrn_detail.po_id=po_header.po_id               " + 
		
			"			          AND t_mrn_detail.mrn_detail_status = 4)," + 
			"			       0),2) AS avg_cost_lm  " + 
			"        FROM m_item WHERE m_item.is_used=1 GROUP by m_item.item_id ", nativeQuery = true)*/
	
	
	@Query(value=" SELECT " + 
			"        m_item.item_id," + 
			"        m_item.item_code," + 
			"        m_item.item_desc," + 
			"        m_item.item_uom," + 
			"        coalesce((Select"  + 
			"            SUM(t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_header," + 
			"            indent                               " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                                     " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1                " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_qty_cm," + 
			"        coalesce((Select" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header," + 
			"            indent                                " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                                   " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1               " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_qty_value_cm," + 
			"        coalesce((Select" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header," + 
			"            indent            " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1               " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_landing_value_cm," + 
			"" + 
			" coalesce((Select" + 
			"            SUM(t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_header," + 
			"            indent                               " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                                      " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1                " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_qty_pm," + 
			"        coalesce((Select" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header," + 
			"            indent                                " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                                     " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1               " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_qty_value_pm," + 
			"        coalesce((Select" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                               " + 
			"        FROM" + 
			"            t_mrn_detail," + 
			"            t_mrn_header," + 
			"            po_detail," + 
			"            po_header," + 
			"            indent            " + 
			"        where" + 
			"            t_mrn_header.mrn_date between :prevFromDate and :prevToDate                                  " + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                                           " + 
			"            AND m_item.item_id=t_mrn_detail.item_id                                           " + 
			"            and t_mrn_header.del_status=1                                           " + 
			"            and t_mrn_detail.del_status=1               " + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id               " + 
			"            and t_mrn_detail.po_id=po_header.po_id               " + 
			"            and po_header.ind_id=indent.ind_m_id               " + 
			"            and indent.ind_isdev in (0,1) " + 
			"            AND t_mrn_detail.mrn_detail_status = 4)," + 
			"        0) AS approved_landing_value_pm          " + 
			"    FROM" + 
			"        m_item" + 
			"     " + 
			"    where" + 
			"        m_item.is_used=1", nativeQuery = true)
	
	List<MonthwiseAvgItemRate> getMonthWiseAvgItemRate(@Param("fromDate") String fromDate,@Param("toDate") String toDate,
			@Param("prevFromDate") String prevFromDate,@Param("prevToDate") String prevToDate);
	

}
