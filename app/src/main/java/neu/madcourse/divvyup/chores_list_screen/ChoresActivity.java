package neu.madcourse.divvyup.chores_list_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neu.madcourse.divvyup.R;

public class ChoresActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager toDoLayoutManager;
    private RecyclerView.LayoutManager inProgressLayoutManager;
    private RecyclerView.LayoutManager completedLayoutManager;

    private RecyclerView toDoRView;
    private RecyclerView inProgressRView;
    private RecyclerView completedRView;
    private BarChart chart;

    private ChoreAdapter toDoChoreAdapter;
    private ChoreAdapter inProgressChoreAdapter;
    private ChoreAdapter completedChoreAdapter;


    private List<ChoreCard> toDoChoresList = new ArrayList<>();
    private List<ChoreCard> inProgressChoresList = new ArrayList<>();
    private List<ChoreCard> completedChoresList = new ArrayList<>();

    int[] colorClassArray = new int[]{Color.RED, Color.CYAN, Color.RED};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);

        chart = findViewById(R.id.idBarChart);

        BarDataSet barDataSet = new BarDataSet(dataValues1(), "Bar Set");
        barDataSet.setColor(Color.CYAN);

        BarData barData = new BarData(barDataSet);
        chart.setData(barData);

        toDoChoresList.add(new ChoreCard(3, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, 1, "Trash", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(1, 1, "Farm", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(3, 1, "Vacuum", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, 1, "Living Room", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(5, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(7, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));

        inProgressChoresList.add(new ChoreCard(3, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, 1, "Trash", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(1, 1, "Farm", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(3, 1, "Vacuum", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, 1, "Living Room", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(5, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(7, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));

        completedChoresList.add(new ChoreCard(5, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));
        completedChoresList.add(new ChoreCard(7, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));
        completedChoresList.add(new ChoreCard(2, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));

        toDoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        inProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        toDoRView = findViewById(R.id.idToDoChoresRecycler);
        inProgressRView = findViewById(R.id.idInProgressChoresRecycler);
        completedRView = findViewById(R.id.idCompletedChoresRecycler);


        ChoreCardClickListener choreCardClickListener = new ChoreCardClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: Navigate to chores page
            }
        };


        // To Do Chores
        toDoChoreAdapter = new ChoreAdapter(toDoChoresList);

        toDoChoreAdapter.setOnLinkClickListener(choreCardClickListener);
        toDoRView.setAdapter(toDoChoreAdapter);
        toDoRView.setLayoutManager(toDoLayoutManager);
        toDoRView.setHasFixedSize(true);

        // In Progress Chores

        inProgressChoreAdapter = new ChoreAdapter(inProgressChoresList);

        toDoChoreAdapter.setOnLinkClickListener(choreCardClickListener);

        inProgressRView.setAdapter(inProgressChoreAdapter);
        inProgressRView.setLayoutManager(inProgressLayoutManager);
        inProgressRView.setHasFixedSize(true);

        // Completed Chores

        completedChoreAdapter = new ChoreAdapter(completedChoresList);

        completedChoreAdapter.setOnLinkClickListener(choreCardClickListener);

        completedRView.setAdapter(completedChoreAdapter);
        completedRView.setLayoutManager(completedLayoutManager);
        completedRView.setHasFixedSize(true);

    }

    private ArrayList<BarEntry> dataValues1() {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, new float[]{2, 5.5f, 4}));
        return dataVals;
    }



}