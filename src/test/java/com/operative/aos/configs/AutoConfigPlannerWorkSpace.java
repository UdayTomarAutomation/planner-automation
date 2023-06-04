/**
 * 
 */
package com.operative.aos.configs;

/**
 * @author upratap
 *
 */
public class AutoConfigPlannerWorkSpace {
	
	
	
	
	  //Plan Header field
	  public static String planClass="National";
	  // Product Chooser field
	  public static String commType = "commType";
	  public static String lineClass = "lineClass";
	  public static String unitLength = "spotLength";
	  public static String commTypeAttribute = "CML";
	  public static String lineClassAttribute = "PD";
	  public static String spotLengthAttribute = ":30";
	  public static String commTypeAttributeUpdate = "Double Box";
	  public static String lineClassAttributeUpdate = "NG";
	  public static String spotLengthAttributeUpdate = ":45";
	  //Plan workSpace
	  public static String productworkSpace = "Planner_Prd_AllDates";
	  public static String productRc_1="Planner_Prd_01";
	  public static String dayPartDisplay = "Prime";
	  public static String dayPartDisplayWeekend = "Weekend";
	  //cell properties value
	  public static String cellRate="rate";
	  public static String cellImps="imps";
	  public static String grossCpm="cpm";
	  public static String cellrtg="rtg";
	  public static String cellPercentRC="ratecardPercent";
	  
	  
	  //total qtr value
	  public static String ratetotalQtr="rate";
	  public static String netRatetotalQtr="netRate";
	  public static String  impstotalQtr="imps";
	  public static String  imps000totalQtr="impsOOO";
	  public static String  percentImpstotalQtr="percentImps";
	  public static String  percentVartotalQtr="percentVar";
	  public static String  cpmtotalQtr="cpm";
	  public static String  netCpmtotalQtr="netCpm";
	  public static String  unitstotalQtr="units";
	  public static String  unitsPercenttotalQtr="unitsPercent";
	  public static String  grossCpptotalQtr="cpp";
	  public static String  netCpptotalQtr="netCpp";
	  public static String  grptotalQtr="grp";
	  public static String  grpPercenttotalQtr="percentGrp";
	  public static String  percentRCQtr="ratecardPercent";
	  public static String  percent$$Qtr="percentRate";
	  public static String  vpvhQtr="vpvh";
	  public static String  eqUnitsQtr="equiv";
	  public static String  percentEqUnitsQtr="percentEquiv";
	  public static int DBCommission = 0;
	  public static String hundredPercent = "100";
	  public static String  demoValueHH="HH";
	  public static String  demoValueP2134="P21-34";
	  
	  //Unit Metrics col Id
	  public static String  unitRate="unitRate";
	  
	  //line prorate value
	  public static String lineProRate="rate";
	  public static String lineProImps="imps";
	  
	  //Rate Card Name 
	  public static String ratecardName="Planner_RC_DoNotEdit";
	  
	  public static String copyHeader="Copy Header";
	  public static String weeklyTotalDefaultColumns = "Gross $$;Gross CPM;Units";
	  public static String weeklyColumnsGrossRate = "Gross $$";
	  public static String weeklyColumnsNetRate = "Net $$";
	  public static String weeklyColumns000Imps= "(000)";
	  public static String weeklyColumnsPercentImps= "%Imps";
	  public static String weeklyColumnsGrossCPM = "Gross CPM";
	  public static String weeklyColumnsNetCPM = "Net CPM";
	  public static String weeklyColumnsUnits = "Units";
	  public static String weeklyColumnsPercentGRP = "%GRP";
	  public static String weeklyColumnsGrossCPP = "Gross CPP";
	  public static String weeklyColumnsNetCPP = "Net CPP";
	  public static String weeklyColumnsPercentUnits = "%Units";
	  public static String weeklyColumnsGRP = "GRP";
	  public static String weeklyColumnsPercentRC = "%RC";
	  public static String weeklyColumnsPercent$$ = "%$$";
	  public static String weeklyColumnsVPVH = "VPVH";
	  public static String weeklyColumnsEqUnits = "Eq Units";
	  public static String weeklyColumnsPercentEqUnits = "%Eq Units";
	  public static String weeklyColumnsImps = "Imps";
	  
	  
	  //EditLine with Units Warning msg
	  public static String editLineWarningMsg="The units on line(s) you are editing will be deleted. Do you wish to continue?";
	   
	// Date BreakDown Keys
	  public static String quarterlyStartDate = "startDate";
	  public static String quarterlyEndDate = "endDate";
	  public static String monthlyStartDate = "startDate";
	  public static String monthlyEndDate = "endDate";
	  public static String firstDayOfWeekly = "startDate";
	  public static String lastDayOfWeekly = "endDate";
	  
	  //Weekly Filter Metrics Values
	  public static String weeklyTotalRateColumn = "Gross $$";
	  public static String weeklyTotalNetRateColumn = "Net $$";
	  public static String weeklyTotalCPMColumn = "Gross CPM";
	  public static String weeklyTotalNetCPMColumn = "Net CPM";
	  public static String weeklyTotalImps000Column = "(000)";
	  public static String weeklyTotalUnitsColumn = "Units";
	  public static String weeklyTotalGRPColumn = "GRP";
	  public static String weeklyTotalCPPColumn = "Gross CPP";
	  public static String weeklyTotalNetCPPColumn = "Net CPP";
	  public static String weeklyTotalImpsperceColumn = "%Imps";
	  public static String weeklyTotalImpsColumn = "Imps";
	  public static String weeklyTotalGRPperceColumn = "%GRP";
	  public static String weeklyTotalUnitsPerceColumn = "%Units";
	  public static String weeklyTotalVPVHColumn = "VPVH";
	  public static String weeklyTotalRCpercentColumn = "%RC";
	  public static String weeklyTotalPercentRateColumn = "%$$";
	  public static String weeklyTotalEqUnitsColumn = "Eq Units";
	  public static String weeklyTotalPercentEqUnitsColumn = "%Eq Units";
	  
	  //View Metrics Columns
	  public static String quadViewMetrics = "Gross Rate;(000);Gross CPM";
	  
	  //Deal Prorate Metrics value
	  public static String dealProratepercentRC = "PERCENT_RC";
	  
	  //Group By
	  public static String groupByProduct="Product Name";
	  public static String groupByUnitLength="Unit Length";
	  public static String groupByDayPart="Daypart";
	  
	  
	  //Remix Pannel
	  public static String lywRate = "lywRate";
	  public static String lywCpm = "lywCpm";
	  public static String lywImps = "lywImps";
	  public static String cywRate = "cywRate";
	  public static String cywCpm = "cywCpm";
	  public static String cywImps = "cywImps";
	  public static String rtwImps = "rtwImps";
	  public static String rtwDiff = "rtwDiff";
	  public static String rtwRate = "rtwRate";
	  public static String rtwCpm = "rtwCpm";
	  
	  //Refresh Ratecard
	  public static String cpmConstant = "CPM";
	  public static String rateConstant = "Rate";
	  public static String percentRC="%RC";
	  
	  //Line info column value
	  public static String lineClassLineInfo="lineClass";
	  public static String commercialTypeInfo="commercialType";
	  public static String spotLengthInfo="spotLength";
	  
	  //Actioned on Cell Property 3 dots icon
	  public static String copyUnits = "Copy Units";
	  public static String notes = "Notes";
	  public static String removeUnits = "Remove Units";
	  public static String pasteUnits = "Paste Units";
	  
	  public static String commTypeAttributeLocal = "CM";
	  public static String lineClassAttributeLocal = "Cash";
	  public static String commTypeAttributeLocalupdated = "BB";
	  public static String lineClassAttributeLocalupdated = "Trade";
	  public static String bookAug21 = "Aug21 TP L1";
	  public static String bookJul21 = "Jul21 TP L1";
	  public static String bookJun21 = "Jun21 TP L1";
	  public static String bookApr21 = "Apr21 TP L1";
	  public static String bookJan21 = "Jan21 TP L1";
	  public static String bookFeb21 = "Feb21 TP L1";
	  
	  //Quarter filter Metrics
	  public static String qtrGrossRate = "Gross $$";
	  public static String qtrNetRate = "Net $$";
	  public static String qtr000 = "(000)";
	  public static String qtrImps = "Imps";
	  public static String qtrPercentImps = "%Imps";
	  public static String qtrPercentVar = "%Var";
	  public static String qtrGrossCPM = "Gross CPM";
	  public static String qtrNetCPM = "Net CPM";
	  public static String qtrUnits = "Units";
	  public static String qtrPercentUnits = "%Units";
	  public static String qtrGrossCPP = "Gross CPP";
	  public static String qtrNetCPP = "Net CPP";
	  public static String qtrGRP = "GRP";
	  public static String qtrPercentGRP = "%GRP";
	  public static String qtrPercentRC = "%RC";
	  public static String qtrPercent$$ = "%$$";
	  public static String qtrVPVH = "VPVH";
	  public static String qtrEqUnits = "Eq Units";
	  public static String qtrPercentEqUnits = "%Eq Units";
	  public static String defaultWorksapceColumn="(000);Imps;Gross CPM;Units;%RC";
	  
	  //Unit Metrics filter columns
	  public static String unitGrossRate = "Gross Rate (A)";
	  public static String unitRCRate = "RC Rate";
	  public static String unitNetRate = "Net Rate (A)";
	  public static String unit000 = "(000)";
	  public static String unitBook000 = "Book (000)";
	  public static String unitImps = "Imps";
	  public static String unitRTG = "RTG";
	  public static String unitGrossCPM = "Gross CPM";
	  public static String unitNetCPM = "Net CPM";
	  public static String unitBookRTG = "Book RTG";
	  public static String unitShare = "Share";
	  public static String unitPUT = "PUT";
	  public static String unitPercentPUT = "%PUT";
	  public static String unitGrossCPP = "Gross CPP";
	  public static String unitNetCPP = "Net CPP";
	  
	  //Product contraints
	  public static String productHiatus = "Product Hiatus";
	  public static String lineConstraint = "Line Constraint";
	  public static String prodDayColId = "prodDay";
	  public static String prodTimeColId = "prodTime";
	  public static String prodDateColId = "prodDateRange";
	  
	  //unitDetails 
	  public static String unitDetailsPercentageRC="percentageRc";
	  public static String unitDetailsImps="imps";
	  
	  

}
