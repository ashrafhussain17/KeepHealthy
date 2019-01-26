package edu.unidhaka.cse.cse2216.keephealthy;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unidhaka.cse.cse2216.keephealthy.model.SuccessTransaction;
import edu.unidhaka.cse.cse2216.keephealthy.model.StepTransaction;
import edu.unidhaka.cse.cse2216.keephealthy.model.StepModel;
import edu.unidhaka.cse.cse2216.keephealthy.step.StepService;
import edu.unidhaka.cse.cse2216.keephealthy.MyApplication;
import io.realm.Realm;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;



public class Step_counter extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView showSteps;
    private View mLayout;
    Switch on_off, foreground_model;
    SharedPreferences sharedPreferences;
    EventBus bus;
    long numSteps;
    boolean isServiceRun;
    boolean isforeground_model;
    TextView btn;
    TextView about;
    LineChartView lineChart;
    List<PointValue> mPointValues = new ArrayList<>();
    List<AxisValue> mAxisXValues = new ArrayList<>();

    public void mybt(View v) {
        showPopupWindow(v);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_count);
        btn = (TextView) findViewById(R.id.bt);
        btn.setText(getResources().getText(R.string.settingss));

        Log.d("eee", "on create()");
        showSteps = (TextView) findViewById(R.id.showSteps);
        mLayout = findViewById(R.id.mylayout);
        on_off = (Switch) findViewById(R.id.on_off);
        foreground_model = (Switch) findViewById(R.id.foreground_model);


        sharedPreferences = getSharedPreferences("conf", MODE_PRIVATE);

        detectService();

        bus = EventBus.getDefault();
        bus.register(this);

        Realm realm = Realm.getDefaultInstance();
        StepModel result = realm.where(StepModel.class)
                .equalTo("date", DateTimeHelper.getToday())
                .findFirst();
        numSteps = result == null ? 0 : result.getNumSteps();
        bus.post(true);
        updateShowSteps();
        realm.close();

        drawChart();


    }

    public void drawChart() {

        Date[] days = DateTimeHelper.get6days();

        Realm realm = Realm.getDefaultInstance();

        int[] data = new int[]{0, 0, 0, 0, 0, 0};
        int i = 0;
        for (Date d : days) {
            Log.d("eee","date "+d);
            if (i == 5) {
                data[i] = Integer.parseInt(String.valueOf(numSteps));
            }
            else {
                StepModel result = realm.where(StepModel.class)
                        .equalTo("date", d)
                        .findFirst();
                if (result != null) {
                    Log.d("eee","r !null  ");
                    data[i] = Integer.parseInt(String.valueOf(result.getNumSteps()));
                }
            }
            i++;
        }

        realm.close();

        String[] xValues = DateTimeHelper.get6days(true);


        lineChart = (LineChartView) findViewById(R.id.line_chart);
        for (i = 0; i < xValues.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(xValues[i]));
        }

        for (i = 0; i < data.length; i++) {
            mPointValues.add(new PointValue(i, data[i]));
        }
        initLineChart();

    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFFAFA"));
        List<Line> lines = new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.WHITE);
        //axisX.setName("date");
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);

        Axis axisY = new Axis();

        axisY.setName("");
        // axisY.setTextSize(10);
        axisY.setTextColor(Color.parseColor("#ffffff"));
        data.setAxisYLeft(axisY);


        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateSteps(Long num) {
        numSteps = num;
        updateShowSteps();
    }

    public void updateShowSteps() {
        String text = "" + numSteps;

        if (numSteps >= 10000000)
            showSteps.setTextSize(45);

        else if (numSteps >= 1000000)
            showSteps.setTextSize(50);
        else if (numSteps >= 100000)
            showSteps.setTextSize(55);
        else if (numSteps >= 10000) {
            notifyIsUpToStandard( "Great, you have more than 10,000 steps today");
            showSteps.setTextSize(60);
        }

        else {
            showSteps.setTextSize(66);
            if (numSteps>=5000) notifyIsUpToStandard("Come on, you have already walked away and you have reached 10,000 steps.");
            else notifyIsUpToStandard("You didn’t walk very much today, go out and go out");
        }
        showSteps.setText(text);

    }

    private void notifyIsUpToStandard(String msg)
    {
        MyApplication app = (MyApplication) getApplication();
        if(!app.isShowToast()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            app.setShowToast(true);
        }

    }

    private void showPopupWindow(View view) {

        MyApplication app = (MyApplication) getApplication();
        isServiceRun=app.getServiceRun();

        isforeground_model=sharedPreferences.getBoolean("foreground_model",false);

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.setting_layout, null);
        PopupWindow popupWindow = new PopupWindow(contentView,
                370, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        on_off = (Switch) contentView.findViewById(R.id.on_off);
        foreground_model = (Switch) contentView.findViewById(R.id.foreground_model);
        on_off.setChecked(isServiceRun);
        foreground_model.setChecked(isforeground_model);

        on_off.setOnCheckedChangeListener(this);
        foreground_model.setOnCheckedChangeListener(this);

        popupWindow.showAsDropDown(view);

    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("eee", "activity stop()");
        bus.post(false);
        if (bus.isRegistered(this))
            bus.unregister(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.on_off) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("switch_on", isChecked);
            editor.apply();
            Intent intent = new Intent(this, StepService.class);

            if (isChecked) {
                intent.putExtra("isActivity", true);
                if (!bus.isRegistered(this))
                    bus.register(this);
                startService(intent);
                bus.post(true);
            } else {
                editor.putBoolean("foreground_model", isChecked);
                editor.apply();
                foreground_model.setChecked(false);
                if (bus.isRegistered(this))
                    bus.unregister(this);
                stopService(intent);
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new StepTransaction(DateTimeHelper.getToday(), numSteps));
                realm.close();
            }
        } else if (buttonView.getId() == R.id.foreground_model) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("foreground_model", isChecked);
            editor.apply();

            Intent intent = new Intent(this, StepService.class);
            if (isChecked) {
                editor.putBoolean("switch_on", isChecked);
                editor.apply();
                on_off.setChecked(true);
                intent.putExtra("foreground_model", "on");
                intent.putExtra("isActivity", true);
                if (!bus.isRegistered(this))
                    bus.register(this);
                bus.post(true);
            } else {
                intent.putExtra("foreground_model", "off");
            }
            startService(intent);
        }

    }

    public void detectService() {
        MyApplication application = (MyApplication) getApplication();
        isServiceRun = application.getServiceRun();
        boolean temp = sharedPreferences.getBoolean("switch_on", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isServiceRun != temp) {
            if (!isServiceRun) {
                Toast.makeText(getApplicationContext(), "The step counter service terminated unexpectedly",
                        Toast.LENGTH_LONG).show();
            }
            editor.putBoolean("switch_on", isServiceRun);
            editor.apply();
        }

        temp = sharedPreferences.getBoolean("foreground_model", false);
        if (temp && !isServiceRun) {
            editor.putBoolean("foreground_model", false);
            editor.apply();
            isforeground_model = false;
        } else isforeground_model = temp;
    }



}

