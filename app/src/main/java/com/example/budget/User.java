package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class User extends AppCompatActivity {
    public PieChart pieChart;
    public LineChart mChart;
    public dataBase db ;
    public int shop,services,emi,others,taxes;
    public ArrayList<Entry> values;
    public TextView t1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = findViewById(R.id.UserStatus);
        db = new dataBase(this);
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor cr = sq.rawQuery("SELECT * FROM UserStatus",null);
        cr.moveToFirst();
        do {
            if (cr.getString(6).equals(getIntent().getStringExtra("User"))) {
                shop = cr.getInt(1);
                services = cr.getInt(5);
                emi = cr.getInt(2);
                others = cr.getInt(3);
                taxes = cr.getInt(4);
                t1.setText(getIntent().getStringExtra("User")+"Status" + emi);
                break;
            }
        }while(cr.moveToNext());

        setPieChart();
        setLineChart();
    }

    public void setPieChart() {
        pieChart = findViewById(R.id.Pie);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(0);
        pieChart.setDragDecelerationFrictionCoef(0.1f);
        pieChart.getDescription().setEnabled(false);

        ArrayList<PieEntry> PieEntries = new ArrayList<>();
        PieEntries.add(new PieEntry(shop, "Shopping"));
        PieEntries.add(new PieEntry(services, "Services"));
        PieEntries.add(new PieEntry(others, "Others"));
        PieEntries.add(new PieEntry(emi, "EMI"));
        PieEntries.add(new PieEntry(taxes, "Taxes"));

        PieDataSet dataSet = new PieDataSet(PieEntries,"");
        dataSet.setSelectionShift(10f);
        dataSet.setSliceSpace(2f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        pieChart.setData(pieData);
    }

    public void setLineChart() {
         mChart = findViewById(R.id.LineChart);
         values = new ArrayList<>();
         values.add(new Entry(1,20));
         values.add(new Entry(2,200));
         values.add(new Entry(3,100));
         values.add(new Entry(4,110));
         values.add(new Entry(5,50));
         values.add(new Entry(6,90));

        LineDataSet set1;
        set1 = new LineDataSet(values, "");
        mChart.getDescription().setEnabled(false);
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 2f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.DKGRAY);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(25f);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.style);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.GREEN);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
    }
}
