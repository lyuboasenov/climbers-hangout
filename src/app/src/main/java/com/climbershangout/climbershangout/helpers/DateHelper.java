package com.climbershangout.climbershangout.helpers;

import com.climbershangout.climbershangout.dashboard.DashboardActivitiesFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lyuboslav on 2/18/2017.
 */

public class DateHelper {

    private static final Calendar calendar = GregorianCalendar.getInstance();

    static {
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    public static Period getThisWeekPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        period.setStart(calendar.getTime());
        calendar.add(Calendar.DATE, 7);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Period getPreviousWeekPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        period.setStart(calendar.getTime());
        calendar.add(Calendar.DATE, 7);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Period getPreviousMonthPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, - 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        period.setStart(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, - 1);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Period getThisMonthPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        period.setStart(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, - 1);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Period getPreviousYearPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        period.setStart(calendar.getTime());
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DATE, -1);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Period getThisYearPeriod() {
        DateHelper.Period period = new DateHelper.Period();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        period.setStart(calendar.getTime());
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DATE, -1);
        period.setEnd(calendar.getTime());

        return period;
    }

    public static Date getDate(int year, int month, int day) {
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static class Period {
        private Date start;
        private Date end;

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }
}
