/**
 *
 */
package com.operative.base.utils.pojo.PlannerDateTime;

import java.time.LocalDate;
import java.util.List;

/**
 * @author upratap
 *
 */
public class QuarterDTO {

  private String label;
  private String value;
  private LocalDate startDate;
  private LocalDate endDate;
  private List<WeekDTO> weeksOfQuarter;

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return the startDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the weeksOfQuarter
   */
  public List<WeekDTO> getWeeksOfQuarter() {
    return weeksOfQuarter;
  }

  /**
   * @param weeksOfQuarter the weeksOfQuarter to set
   */
  public void setWeeksOfQuarter(List<WeekDTO> weeksOfQuarter) {
    this.weeksOfQuarter = weeksOfQuarter;
  }

}
