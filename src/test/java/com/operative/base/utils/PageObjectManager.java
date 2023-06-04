/**
 * 
 */
package com.operative.base.utils;

import com.operative.pages.plannerheader.PlannerHeaderPage;

/**
 * @author upratap
 *
 */
public class PageObjectManager extends BaseTest {
	PlannerHeaderPage plannerHeader;	
	
	
	
	public PlannerHeaderPage getPlannerHeaderPage() {
	    if (plannerHeader == null) {
	    	plannerHeader = new PlannerHeaderPage(getPageBrowser());
	    }
	    return plannerHeader;
	  }

}
