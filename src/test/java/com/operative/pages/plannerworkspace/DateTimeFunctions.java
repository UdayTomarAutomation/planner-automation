/**
 * 
 */
package com.operative.pages.plannerworkspace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.Logger;
import com.operative.base.utils.pojo.PlannerDateTime.QuarterDTO;
import com.operative.base.utils.pojo.PlannerDateTime.WeekDTO;

/**
 * @author upratap
 *
 */
public class DateTimeFunctions {
	private final ZoneId defaultZoneId = ZoneId.systemDefault();

	  // Get Start And End Date Based On BreakDown
	  public Map<String, String> getStartEndDateBreakDownBased(String inputStartDate,
	      String inputEndDate) {
	    final Map<String, String> breakDownDates = new HashMap<String, String>();
	    final String[] startDateInp = inputStartDate.split("-");
	    final String[] endDateInp = inputEndDate.split("-");
	    final LocalDate startDate = LocalDate.of(Integer.parseInt(startDateInp[0]),
	        Integer.parseInt(startDateInp[1]), Integer.parseInt(startDateInp[2]));
	    final LocalDate endDate = LocalDate.of(Integer.parseInt(endDateInp[0]),
	        Integer.parseInt(endDateInp[1]), Integer.parseInt(endDateInp[2]));
	    final LocalDate firstDayOfQuarter = startDate.with(startDate.getMonth().firstMonthOfQuarter())
	        .with(TemporalAdjusters.firstDayOfMonth());
	    final int diffFirstDateQuarterly =
	        firstDayOfQuarter.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
	    final LocalDate firstDayQuarterlyResult = firstDayOfQuarter.minusDays(diffFirstDateQuarterly);
	    final LocalDate lastDayOfQuarter = endDate.with(endDate.getMonth().firstMonthOfQuarter())
	        .plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
	    final int diffLastDateQuarterly =
	        7 - (DayOfWeek.SUNDAY.getValue() - lastDayOfQuarter.getDayOfWeek().getValue());
	    final LocalDate lastDayQuarterlyResult = lastDayOfQuarter.minusDays(diffLastDateQuarterly);
	    Logger.log("First Day Of Quarter Is:" + firstDayQuarterlyResult);
	    Logger.log("Last Day Of Quarter Is:" + lastDayQuarterlyResult);
	    breakDownDates.put(AutoConfigPlannerWorkSpace.quarterlyStartDate,
	        firstDayQuarterlyResult.toString());
	    breakDownDates.put(AutoConfigPlannerWorkSpace.quarterlyEndDate,
	        lastDayQuarterlyResult.toString());
	    // Monthly breakdown example
	    final LocalDate firstDayOfMonth =
	        startDate.with(startDate.getMonth()).with(TemporalAdjusters.firstDayOfMonth());
	    final int diffFirstDateMonthly =
	        firstDayOfMonth.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
	    final LocalDate firstDayMonthlyResult = firstDayOfMonth.minusDays(diffFirstDateMonthly);
	    final LocalDate lastDayOfMonth =
	        endDate.with(endDate.getMonth()).with(TemporalAdjusters.lastDayOfMonth());
	    final int diffLastDateMonthly =
	        7 - (DayOfWeek.SUNDAY.getValue() - lastDayOfMonth.getDayOfWeek().getValue());
	    final LocalDate lastDayMonthlyResult = lastDayOfMonth.minusDays(diffLastDateMonthly);
	    Logger.log("First Day Of Month Is:" + firstDayMonthlyResult);
	    Logger.log("Last Day Of Month Is:" + lastDayMonthlyResult);
	    breakDownDates.put(AutoConfigPlannerWorkSpace.monthlyStartDate,
	        firstDayMonthlyResult.toString());
	    breakDownDates.put(AutoConfigPlannerWorkSpace.monthlyEndDate,
	        lastDayMonthlyResult.toString());
	    // Weekly breakdown example
	    final LocalDate firstDayOfWeekly = startDate.with(DayOfWeek.MONDAY);
	    final LocalDate lastDayOfWeekly = endDate.with(DayOfWeek.SUNDAY);
	    Logger.log("First Day Of Week Is:" + firstDayOfWeekly);
	    Logger.log("Last Day Of Week Is:" + lastDayOfWeekly);
	    breakDownDates.put(AutoConfigPlannerWorkSpace.firstDayOfWeekly, firstDayOfWeekly.toString());
	    breakDownDates.put(AutoConfigPlannerWorkSpace.lastDayOfWeekly, lastDayOfWeekly.toString());
	    return breakDownDates;
	  }

	  // get Quarterly Sequence
	  public List<String> generateQuarterlySequence(String startDates, String endDates)
	      throws ParseException {
	    final SimpleDateFormat sdfmt1 = new SimpleDateFormat("mm/dd/yyyy");
	    final SimpleDateFormat sdfmt2 = new SimpleDateFormat("yyyy-mm-dd");
	    final Date sDate = sdfmt1.parse(startDates);
	    final String startDateValue = sdfmt2.format(sDate);
	    final Date enddate = sdfmt1.parse(endDates);
	    final String endDatesValue = sdfmt2.format(enddate);
	    // convert String to LocalDate
	    LocalDate startDate = LocalDate.parse(startDateValue);
	    final LocalDate endDate = LocalDate.parse(endDatesValue);
	    // first truncate startDate to first day of quarter
	    int startMonth = startDate.getMonthValue();
	    startMonth -= (startMonth - 1) % 3;
	    startDate = startDate.withMonth(startMonth).withDayOfMonth(1);
	    final DateTimeFormatter quarterFormatter =
	        DateTimeFormatter.ofPattern("uuuuQQQ", Locale.ENGLISH);
	    final List<String> quarterSequence = new ArrayList<>();
	    // iterate thorough quarters
	    LocalDate currentQuarterStart = startDate;
	    while (!currentQuarterStart.isAfter(endDate)) {
	      quarterSequence.add(currentQuarterStart.format(quarterFormatter));
	      currentQuarterStart = currentQuarterStart.plusMonths(3);
	    }
	    Logger.log("get Quarter value" + quarterSequence);
	    return quarterSequence;
	  }


	  // get Quarterly Sequence SincDev
	  public List<String> generateQuarterlySequenceSinc(String startDates, String endDates)
	      throws ParseException {
	    final SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd/mm/yyyy");
	    final SimpleDateFormat sdfmt2 = new SimpleDateFormat("yyyy-mm-dd");
	    final Date sDate = sdfmt1.parse(startDates);
	    final String startDateValue = sdfmt2.format(sDate);
	    final Date enddate = sdfmt1.parse(endDates);
	    final String endDatesValue = sdfmt2.format(enddate);
	    // convert String to LocalDate
	    LocalDate startDate = LocalDate.parse(startDateValue);
	    final LocalDate endDate = LocalDate.parse(endDatesValue);
	    // first truncate startDate to first day of quarter
	    int startMonth = startDate.getMonthValue();
	    startMonth -= (startMonth - 1) % 3;
	    startDate = startDate.withMonth(startMonth).withDayOfMonth(1);
	    final DateTimeFormatter quarterFormatter =
	        DateTimeFormatter.ofPattern("uuuuQQQ", Locale.ENGLISH);
	    final List<String> quarterSequence = new ArrayList<>();
	    // iterate thorough quarters
	    LocalDate currentQuarterStart = startDate;
	    while (!currentQuarterStart.isAfter(endDate)) {
	      quarterSequence.add(currentQuarterStart.format(quarterFormatter));
	      currentQuarterStart = currentQuarterStart.plusMonths(3);
	    }
	    Logger.log("get Quarter value" + quarterSequence);
	    return quarterSequence;
	  }

	  // get Quarterly value
	  public List<String> generateQuarterlyValue(String startDates, String endDates)
	      throws ParseException {
	    final SimpleDateFormat sdfmt1 = new SimpleDateFormat("mm/dd/yyyy");
	    final SimpleDateFormat sdfmt2 = new SimpleDateFormat("yyyy-mm-dd");
	    final Date sDate = sdfmt1.parse(startDates);
	    final String startDateValue = sdfmt2.format(sDate);
	    final Date enddate = sdfmt1.parse(endDates);
	    final String endDatesValue = sdfmt2.format(enddate);
	    // convert String to LocalDate
	    LocalDate startDate = LocalDate.parse(startDateValue);
	    final LocalDate endDate = LocalDate.parse(endDatesValue);
	    // first truncate startDate to first day of quarter
	    int startMonth = startDate.getMonthValue();
	    startMonth -= (startMonth - 1) % 3;
	    startDate = startDate.withMonth(startMonth).withDayOfMonth(1);
	    final DateTimeFormatter quarterFormatter =
	        DateTimeFormatter.ofPattern("uuuu-QQQ", Locale.ENGLISH);
	    final List<String> quarterSequence = new ArrayList<>();
	    // iterate thorough quarters
	    LocalDate currentQuarterStart = startDate;
	    while (!currentQuarterStart.isAfter(endDate)) {
	      quarterSequence.add(currentQuarterStart.format(quarterFormatter));
	      currentQuarterStart = currentQuarterStart.plusMonths(3);
	    }
	    Logger.log("get Quarter value" + quarterSequence);
	    return quarterSequence;
	  }

	  // get Monday
	  public String[] getMondayDate(String dates) throws java.text.ParseException {
		  final Calendar rcStart = Calendar.getInstance();
		 rcStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    final Set<String> day = new LinkedHashSet<String>();
	    
	    final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    final SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
	    rcStart.setTime(sdf.parse(dates));
	    final int weekday = rcStart.get(Calendar.DAY_OF_WEEK);
		/*
		 * final int days = (Calendar.SATURDAY - weekday + 2) % 7;
		 * rcStart.add(Calendar.DAY_OF_YEAR, days);
		 */
	    rcStart.add(Calendar.DAY_OF_WEEK, -weekday+Calendar.MONDAY);
	    final String currentmonday = sdf2.format(rcStart.getTime());
	    rcStart.add(Calendar.DATE, 7);
	    final String nextmonday = sdf2.format(rcStart.getTime());
	    rcStart.add(Calendar.DATE, 7);
	    day.add(currentmonday);
	    day.add(nextmonday);
	    return day.toArray(new String[0]);
	  }

	  // next Sunday to give any Random Date
	  public String getNextSundayDate(String startDate) throws java.text.ParseException {
	    final Calendar Day = Calendar.getInstance();
	    Day.setFirstDayOfWeek(2);
	    final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    Day.setTime(sdf.parse(startDate));
	    Day.set(Calendar.DAY_OF_WEEK, Day.getFirstDayOfWeek());
	    Day.add(Calendar.DATE, 6);
	    final String sundayDate = sdf.format(Day.getTime());
	    System.out.println("sunDay " + sundayDate);
	    return sundayDate;
	  }

	  // get all weeks in quarter
	  public List<String> getStartEndDateOfquarter(String quarter) throws ParseException {
	    // final String quarterNumber = quarter.split("Q")[0];
	    // final String quarterYear = quarter.split("Q")[1];
	    final String quarterNumber = quarter.split("-Q")[1];
	    final String quarterYear = quarter.split("Q")[0];
	    final SimpleDateFormat sdfmt1 = new SimpleDateFormat("yy");
	    final SimpleDateFormat sdfmt2 = new SimpleDateFormat("YYYY");
	    final Date sDate = sdfmt1.parse(quarterYear);
	    final String quarteryear = sdfmt2.format(sDate);
	    final List<String> dates = new ArrayList<String>();
	    switch (quarterNumber) {
	      case "1":
	        final Month month = Month.JANUARY;
	        // get startDate
	        final LocalDate start = LocalDate.of(Integer.parseInt(quarteryear),
	            month.firstMonthOfQuarter(), Integer.parseInt(quarterNumber));
	        final Month endMonth = start.getMonth().plus(2);
	        // get end Date
	        final LocalDate end = LocalDate.of(Integer.parseInt(quarteryear), endMonth,
	            endMonth.length(start.isLeapYear()));
	        dates.add(start.toString());
	        dates.add(end.toString());
	    }
	    switch (quarterNumber) {
	      case "2":
	        final Month month = Month.APRIL;
	        // get startDate
	        final LocalDate start = LocalDate.of(Integer.parseInt(quarteryear),
	            month.firstMonthOfQuarter(), Integer.parseInt(quarterNumber));
	        final Month endMonth = start.getMonth().plus(2);
	        // get end Date
	        final LocalDate end = LocalDate.of(Integer.parseInt(quarteryear), endMonth,
	            endMonth.length(start.isLeapYear()));
	        dates.add(start.toString());
	        dates.add(end.toString());
	    }
	    switch (quarterNumber) {
	      case "3":
	        final Month month = Month.JULY;
	        // get startDate
	        final LocalDate start = LocalDate.of(Integer.parseInt(quarteryear),
	            month.firstMonthOfQuarter(), Integer.parseInt(quarterNumber));
	        final Month endMonth = start.getMonth().plus(2);
	        // get end Date
	        final LocalDate end = LocalDate.of(Integer.parseInt(quarteryear), endMonth,
	            endMonth.length(start.isLeapYear()));
	        dates.add(start.toString());
	        dates.add(end.toString());
	    }
	    switch (quarterNumber) {
	      case "4":
	        final Month month = Month.OCTOBER;
	        // get startDate
	        final LocalDate start = LocalDate.of(Integer.parseInt(quarteryear),
	            month.firstMonthOfQuarter(), Integer.parseInt(quarterNumber));
	        final Month endMonth = start.getMonth().plus(2);
	        // get end Date
	        final LocalDate end = LocalDate.of(Integer.parseInt(quarteryear), endMonth,
	            endMonth.length(start.isLeapYear()));
	        dates.add(start.toString());
	        dates.add(end.toString());
	    }
	    return dates;
	  }

	  public List<QuarterDTO> getQuartersBetweenDates(String startDates, String endDates)
	      throws ParseException {
	    final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    final Date sDate = sdf1.parse(startDates);
	    final String startDateValue = sdf.format(sDate);
	    final Date eDate = sdf1.parse(endDates);
	    final String endDateValue = sdf.format(eDate);
	    final LocalDate startDate = LocalDate.parse(startDateValue);
	    final LocalDate endDate = LocalDate.parse(endDateValue);
	    Date stDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
	    final LocalDate localStartDate = getFirstDayOfQuarter(stDate);
	    int quarterStartDayOfWeek = localStartDate.getDayOfWeek().getValue() - 1;
	    LocalDate quarterStartDate = localStartDate.minusDays(quarterStartDayOfWeek);
	    final List<QuarterDTO> quarterList = new ArrayList<>();
	    int i = 0;
	    while (quarterStartDate.isBefore(endDate) || quarterStartDate.equals(endDate)) {
	      quarterStartDayOfWeek = getFirstDayOfQuarter(stDate).getDayOfWeek().getValue() - 1;
	      quarterStartDate = quarterStartDayOfWeek == 7
	          ? getFirstDayOfQuarter(stDate).minusDays(quarterStartDayOfWeek - (long) 1)
	          : quarterStartDate;
	      stDate = quarterStartDayOfWeek < 7
	          ? Date.from(quarterStartDate.plusDays(7).atStartOfDay(defaultZoneId).toInstant())
	          : stDate;
	      final QuarterDTO quarter = new QuarterDTO();
	      final int quarterEndDayOfWeek = getLastDayOfQuarter(stDate).getDayOfWeek().getValue();
	      final LocalDate quarterEndDate = quarterEndDayOfWeek == 7 ? getLastDayOfQuarter(stDate)
	          : getLastDayOfQuarter(stDate).minusDays(quarterEndDayOfWeek);
	      quarter.setStartDate(quarterStartDate);
	      quarter.setEndDate(quarterEndDate);
	      final LocalDate median =
	          quarterStartDate.plusDays(ChronoUnit.DAYS.between(quarterStartDate, quarterEndDate));
	      final int midMonth = getQuarter(median);
	      if (midMonth != 4) {
	        quarter
	            .setLabel(String.valueOf(midMonth) + 'Q' + quarterEndDate.toString().substring(2, 4));
	      } else {
	        quarter
	            .setLabel(String.valueOf(midMonth) + 'Q' + quarterStartDate.toString().substring(2, 4));
	      }
	      quarter.setValue(quarterEndDate.toString().substring(0, 4) + 'Q' + midMonth);
	      Map<LocalDate, LocalDate> broadcastWeeks = new TreeMap<>();
	      if (i == 0 && (quarterEndDate.isBefore(endDate) || quarterEndDate.equals(endDate))) {
	        broadcastWeeks = getBroadcastWeeksInDates(startDate, quarterEndDate);
	        i++;
	      } else if (i == 0 && endDate.isBefore(quarterEndDate)) {
	        broadcastWeeks = getBroadcastWeeksInDates(startDate, endDate);
	      } else if (i != 0 && (quarterEndDate.isBefore(endDate) || quarterEndDate.equals(endDate))) {
	        broadcastWeeks = getBroadcastWeeksInDates(quarterStartDate, quarterEndDate);
	      } else {
	        broadcastWeeks = getBroadcastWeeksInDates(quarterStartDate, endDate);
	      }
	      quarter.setWeeksOfQuarter(getWeeksOfQuarter(broadcastWeeks));
	      quarterList.add(quarter);
	      quarterStartDate = quarterEndDate.plusDays(1);
	      stDate = Date.from(quarterStartDate.atStartOfDay(defaultZoneId).toInstant());
	    }
	    return quarterList;
	  }

	  public static Map<LocalDate, LocalDate> getBroadcastWeeksInDates(LocalDate startDate,
	      LocalDate endDate) {
	    final Map<LocalDate, LocalDate> broadcastWeeks = new TreeMap<>();
	    LocalDate localStartDate = startDate;
	    LocalDate localEndDate = endDate;
	    if (!localStartDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
	      localStartDate = localStartDate
	          .minusDays(localStartDate.getDayOfWeek().getValue() - (long) DayOfWeek.MONDAY.getValue());
	    }
	    if (!localEndDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
	      localEndDate = localEndDate
	          .plusDays(DayOfWeek.SUNDAY.getValue() - (long) localEndDate.getDayOfWeek().getValue());
	    }
	    while (!localStartDate.equals(localEndDate) && !localStartDate.isAfter(localEndDate)) {
	      broadcastWeeks.put(localStartDate, localStartDate.plusWeeks(1).minusDays(1));
	      localStartDate = localStartDate.plusWeeks(1);
	    }
	    return broadcastWeeks;
	  }

	  private LocalDate getFirstDayOfQuarter(Date date) {
	    final Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    final LocalDate localDate = date.toInstant().atZone(defaultZoneId).toLocalDate();
	    final int dayOfWeekInt = localDate.getDayOfWeek().getValue();
	    cal.add(Calendar.DATE, 7 - dayOfWeekInt);
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    final Instant instant = cal.getTime().toInstant();
	    return instant.atZone(defaultZoneId).toLocalDate();
	  }

	  private LocalDate getLastDayOfQuarter(Date date) {
	    final Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    final Instant instant = cal.getTime().toInstant();
	    return instant.atZone(defaultZoneId).toLocalDate();
	  }

	  private int getQuarter(LocalDate cal) {
	    final int month = cal.get(ChronoField.MONTH_OF_YEAR);
	    switch (Month.of(month)) {
	      case JANUARY:
	      case FEBRUARY:
	      case MARCH:
	      default:
	        return 1;
	      case APRIL:
	      case MAY:
	      case JUNE:
	        return 2;
	      case JULY:
	      case AUGUST:
	      case SEPTEMBER:
	        return 3;
	      case OCTOBER:
	      case NOVEMBER:
	      case DECEMBER:
	        return 4;
	    }
	  }

	  private List<WeekDTO> getWeeksOfQuarter(Map<LocalDate, LocalDate> broadcastWeeks) {
	    final List<WeekDTO> weekList = new ArrayList<>();
	    for (final Map.Entry<LocalDate, LocalDate> entry : broadcastWeeks.entrySet()) {
	      final WeekDTO week = new WeekDTO();
	      week.setStartDate(entry.getKey());
	      week.setValue(entry.getKey().toString().replaceAll("-", ""));
	      week.setEndDate(entry.getValue());
	      String val = entry.getKey().toString();
	      val = new StringBuffer().append(val, 5, 7).append('/').append(val, 8, 10).toString();
	      week.setLabel(val);
	      weekList.add(week);
	    }
	    return weekList;
	  }

	  // Verify that format of the date is "MM/dd/yyyy hh:mm:ss aa"
	  public static boolean isValidDate(String inDate) {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate);
	    } catch (final ParseException pe) {
	      Logger.log(inDate);
	      return false;
	    }
	    return true;
	  }

	  public static String getBroadcastWeekStartDate(String startDate) {
	    String formattedDate = "";
	    // default, ISO_LOCAL_DATE
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate localDate = LocalDate.parse(startDate, formatter);
	    if (!localDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
	      LocalDate date = localDate
	          .minusDays(localDate.getDayOfWeek().getValue() - (long) DayOfWeek.MONDAY.getValue());
	      formattedDate = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    } else {
	      formattedDate = startDate;
	    }

	    return formattedDate;
	  }
}
