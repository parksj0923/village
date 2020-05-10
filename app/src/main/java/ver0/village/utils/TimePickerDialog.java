package ver0.village.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ver0.village.R;

public class TimePickerDialog extends DialogFragment {
    OnMyDialogResult mDialogResult;

    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    String selected_time, am_pm, date;

    private DatePickerDialog.OnDateSetListener listener;
    private GregorianCalendar calendar = new GregorianCalendar();

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.date_time_picker, null);


        final NumberPicker datePicker = (NumberPicker) dialog.findViewById(R.id.picker_date);
        final NumberPicker ampmPicker = (NumberPicker) dialog.findViewById(R.id.picker_am_pm);
        final NumberPicker hourPicker = (NumberPicker) dialog.findViewById(R.id.picker_hour);
        final NumberPicker minutePicker = (NumberPicker) dialog.findViewById(R.id.picker_minute);


        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        if(calendar.get(Calendar.MINUTE) > 50)
            hourPicker.setValue(calendar.get(Calendar.HOUR) + 1);
        else
            hourPicker.setValue(calendar.get(Calendar.HOUR));

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(5);
        minutePicker.setValue(calendar.get(Calendar.MINUTE)/10 + 1);
        minutePicker.setDisplayedValues(new String[]{"00", "10", "20", "30", "40", "50"});


        ampmPicker.setMinValue(0);
        ampmPicker.setMaxValue(1);
        ampmPicker.setValue(calendar.get(Calendar.AM_PM));
        ampmPicker.setDisplayedValues(new String[]{"오전","오후"});


        NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {

            @Override
            public String format(int value) {
                // TODO Auto-generated method stub
                switch(value){

                    case 0:
                        return "오늘 ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) +")";

                    case 1:
                        calendar.add(Calendar.DATE, 1);
                        String str =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -1);
                        return str;
                    case 2:
                        calendar.add(Calendar.DATE, 2);
                        String str2 =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -2);
                        return str2;

                    case 3:
                        calendar.add(Calendar.DATE, 3);
                        String str3 =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -3);
                        return str3;

                    case 4:
                        calendar.add(Calendar.DATE, 4);
                        String str4 =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -4);
                        return str4;

                    case 5:
                        calendar.add(Calendar.DATE, 5);
                        String str5 =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -5);
                        return str5;

                    case 6:
                        calendar.add(Calendar.DATE, 6);
                        String str6 =  ""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" ("+functionDay(calendar.get(Calendar.DAY_OF_WEEK)) + ")";
                        calendar.add(Calendar.DATE, -6);
                        return str6;
                }
                return null;
            }
        };


        datePicker.setMinValue(0);
        datePicker.setMaxValue(6);
        datePicker.setWrapSelectorWheel(false);
        datePicker.setFormatter(mFormatter);

        try {
            Method method = datePicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(datePicker, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }





        TextView completebutton = dialog.findViewById(R.id.complete_button);


        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    calendar.add(Calendar.DATE, datePicker.getValue());
                    date = ""+(calendar.get(Calendar.MONTH)+1) +"월 " + calendar.get(Calendar.DAY_OF_MONTH) + "일 (" + functionDay(calendar.get(Calendar.DAY_OF_WEEK))+") ";
                    calendar.add(Calendar.DATE, datePicker.getValue());

                    selected_time = ""+date + am_pm+" " + hourPicker.getValue() + "시 " + minutePicker.getValue()*10 +"분";
                    if(ampmPicker.getValue() == 0){
                        am_pm = "오전";
                    } else {
                        am_pm = "오후";
                    }
                    selected_time = ""+date + am_pm+" " + hourPicker.getValue() + "시 " + minutePicker.getValue()*10 +"분";
                    if (mDialogResult != null) {
                        mDialogResult.finish(selected_time);
                    }
                    dismiss();

                }
            }
        });



        builder.setView(dialog);

        return builder.create();
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }


    public String functionDay(int a){
        switch (a%7){
            case 0 :
                return "일";
            case 1 :
                return "월";

            case 2 :
                return "화";

            case 3 :
                return "수";
            case 4 :
                return "목";

            case 5 :
                return "금";

            case 6 :
                return "토";

            default:
                return "일";


        }
    }
}
