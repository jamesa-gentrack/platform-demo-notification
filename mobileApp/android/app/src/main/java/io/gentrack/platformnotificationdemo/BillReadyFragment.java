package io.gentrack.platformnotificationdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BillReadyFragment extends Fragment {

    private final int mMonthCount = 5;
    private BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_ready, container, false);
        mChart = (BarChart) view.findViewById(R.id.billReadyBarChart);
        try {
            Bundle bundle = getActivity().getIntent().getExtras();
            String custom_keys = bundle.get("custom_keys").toString();
            JSONObject payload = new JSONObject(custom_keys);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date balanceDate = df.parse(payload.getString("balanceDate"));

            JSONArray recentConsumptions = payload.getJSONArray("recentConsumptions");

            float range = 0;
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for(int i = 0; i < recentConsumptions.length(); ++i) {
                String item = recentConsumptions.getString(i);
                float val = Float.parseFloat(item);
                if(i == 0) {
                    range = val;
                } if (range < val) {
                    range = val;
                }
                entries.add(new BarEntry(i, val));
            }
            configChart(balanceDate, entries, range);
        } catch(Exception e) {

        }
        return view;
    }

    private void configChart(Date endDate, ArrayList<BarEntry> entries, float range) {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(5);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(entries.size());
        xAxis.setValueFormatter(new XAxisMonthFormatter(endDate, entries.size()));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum((int)(Math.ceil(range * 1.2f/10)) * 10);
        leftAxis.setValueFormatter(new YAxisUsageFormatter());

        mChart.getAxisRight().setEnabled(false);

        BarDataSet dataSet;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(entries, "Your monthly electricity consumption");
            dataSet.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(20f);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }

    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            entries.add(new BarEntry(i, val));
        }

        BarDataSet dataSet;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(entries, "Your monthly electricity consumption");
            dataSet.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(20f);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }
    }

    private class XAxisMonthFormatter implements IAxisValueFormatter {

        final private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        private Date mEndDate;
        private int mNumMonth;

        public XAxisMonthFormatter(Date endDate, int numMonth) {
            mEndDate = new Date(endDate.getTime());
            mNumMonth = numMonth;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value < mNumMonth && value >= 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(mEndDate);
                cal.add(Calendar.MONTH, - mNumMonth + 1 + Math.round(value));
                return simpleDateFormat.format(cal.getTime());
            }
            return "";
        }
    }

    private class YAxisUsageFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat = new DecimalFormat("#0.0");

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " Kwh";
        }
    }
}
