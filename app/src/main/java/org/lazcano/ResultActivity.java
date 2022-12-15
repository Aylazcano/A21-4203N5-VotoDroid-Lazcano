package org.lazcano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.lazcano.bd.BD;
import org.lazcano.databinding.ActivityResultBinding;
import org.lazcano.modele.VDQuestion;
import org.lazcano.modele.VDVote;
import org.lazcano.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    BarChart chart;

    //*01-Init BD
    private Service service;
    private BD maBD;

    private ActivityResultBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Résultats");

        //*01-Init BD
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BD")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);

        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String question = getIntent().getStringExtra("questionCourante");
        binding.selectedQuestion.setText(question);

        Long idQuestion = getIntent().getLongExtra("idQuestion",0);

        int etoile0 = 0;
        int etoile1 = 0;
        int etoile2 = 0;
        int etoile3 = 0;
        int etoile4 = 0;
        int etoile5 = 0;
        int etoilesTotal = 0;
        int votesTotal = 0;
        for (VDVote v: maBD.monDao().lesVotesPour(idQuestion)) {
            etoilesTotal += v.valeurVote;
            votesTotal++;

            if(v.valeurVote == 0)
                etoile0++;
            if(v.valeurVote == 1)
                etoile1++;
            if(v.valeurVote == 2)
                etoile2++;
            if(v.valeurVote == 3)
                etoile3++;
            if(v.valeurVote == 4)
                etoile4++;
            if(v.valeurVote == 5)
                etoile5++;

        }
        if(votesTotal > 0) {
            double moyenne = (double)etoilesTotal/(double)votesTotal;

            //binding.moyenne.setText(String.valueOf(moyenne));
            binding.moyenne.setText(String.format("%.2f", moyenne));
        }
        else
            binding.moyenne.setText("0");

        chart = findViewById(R.id.chart);


        /* Settings for the graph - Change me if you want*/
        chart.setMaxVisibleValueCount(6);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(4, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);



        /* Data and function call to bind the data to the graph */
        int finalEtoile0 = etoile0;
        int finalEtoile1 = etoile1;
        int finalEtoile2 = etoile2;
        int finalEtoile3 = etoile3;
        int finalEtoile4 = etoile4;
        int finalEtoile5 = etoile5;
        Map<Integer, Integer> dataGraph = new HashMap<Integer, Integer>() {{
            put(0, finalEtoile0);
            put(1, finalEtoile1);
            put(2, finalEtoile2);
            put(3, finalEtoile3);
            put(4, finalEtoile4);
            put(5, finalEtoile5);
        }};
        setData(dataGraph);
    }

    /**
     * methode fournie par le prof pour séparer
     * - la configuration dans le onCreate
     * - l'ajout des données dans le setDate
     * @param datas
     */
    private void setData(Map<Integer, Integer> datas) {
        List<BarEntry> values = new ArrayList<>();
        /* Every bar entry is a bar in the graphic */
        for (Map.Entry<Integer, Integer> key : datas.entrySet()){
            values.add(new BarEntry(key.getKey() , key.getValue()));
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Points");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(.9f);
            chart.setData(data);
        }
    }
}