package com.ats.tril.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.report.POReportDetail;

public interface POReportDetailRepo extends JpaRepository<POReportDetail, Integer> {
	@Query(value = "SELECT po_detail.po_detail_id,po_detail.po_id,po_detail.ind_id,po_detail.vend_id,po_detail.item_id,po_detail.item_desc,po_detail.item_uom,po_detail.item_qty,po_detail.item_rate,po_detail.mrn_qty,po_detail.pending_qty,po_detail.inded_qty,po_detail.disc_per,po_detail.disc_value,po_detail.sch_days,\r\n"
			+ "po_detail.sch_date,po_detail.status,po_detail.basic_value,po_detail.pack_value,po_detail.insu,po_detail.other_charges_befor,po_detail.tax_value,po_detail.freight_value,po_detail.other_charges_after,po_detail.landing_cost,po_detail.cgst,po_detail.sgst,po_detail.igst,t.tax_desc as sch_remark,i.item_code,i.item_desc as item_name, ih.ind_m_date FROM po_detail ,m_item i ,indtrans id,indent ih,m_tax_form t WHERE po_detail.po_id IN(:poIdList) AND i.item_id=po_detail.item_id AND po_detail.ind_id=id.ind_d_id and id.ind_m_id=ih.ind_m_id AND i.item_is_capital=t.tax_id", nativeQuery = true)
	List<POReportDetail> getPOReportDetailList(@Param("poIdList") List<Integer> poIdList);

	@Query(value = "SELECT po_detail.*,i.item_code,i.item_desc as item_name, ih.ind_m_date FROM po_detail ,m_item i ,indtrans id,indent ih WHERE po_detail.po_id=:poId  AND i.item_id=po_detail.item_id AND "
			+ "po_detail.ind_id=id.ind_d_id and id.ind_m_id=ih.ind_m_id", nativeQuery = true)
	List<POReportDetail> getPOReportDetailList(@Param("poId") int poId);

}
