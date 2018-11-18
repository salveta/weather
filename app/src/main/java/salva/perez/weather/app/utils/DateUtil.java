package salva.perez.weather.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import salva.perez.weather.domain.model.forecast.ForecastList;

public class DateUtil {

    public final static String DATE_FORMAT_ISO8601_COMPLETE_DATETIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public final static String DATE_FORMAT_ISO8601_COMPLETE_DATETIME_WITH_MILISECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static void parseDate(List<ForecastList> mData, int position){
        Date createDate = null;
        try {
            Date time = new Date(Long.valueOf(mData.get(position).getDt())*1000);
            String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String dateFormat = sdf.format(time);
            createDate = fromStringToDatefromUtc(dateFormat, DATE_FORMAT_ISO8601_COMPLETE_DATETIME);

            Calendar c = Calendar.getInstance();
            int difDay = calculateDaysBetween(c.getTime(), createDate);
            mData.get(position).setDifDays(difDay);

        }catch (ParseException p){
            p.printStackTrace();
        }
    }

    public static String parseDateToDayAndMonth(int dateTime){
        Date time = new Date(Long.valueOf(dateTime)*1000);
        String DATE_FORMAT = "dd/MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        return sdf.format(time);
    }

    public static String parseHour(int dateTime){
        Date time = new Date(Long.valueOf(dateTime)*1000);
        String DATE_FORMAT = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        return sdf.format(time);
    }

    public static Date fromStringToDatefromUtc(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return converttoUTC(sdf.parse(dateStr));
    }

    public static Date converttoUTC(Date date1) {
        if ( date1 == null ) return null;
        try {

            String timezoneID           = TimeZone.getDefault().getID();
            SimpleDateFormat formatter  = new SimpleDateFormat(DATE_FORMAT_ISO8601_COMPLETE_DATETIME_WITH_MILISECONDS);
            formatter.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateFormateInUTC     = formatter.format(date1);

            return new SimpleDateFormat(DATE_FORMAT_ISO8601_COMPLETE_DATETIME_WITH_MILISECONDS).parse(dateFormateInUTC);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static int calculateDaysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();

        cal.setTime(date2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time2 = cal.getTimeInMillis();


        final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        int diffInDays = (int) ((time - time2) / DAY_IN_MILLIS);
        return diffInDays;
    }
}
