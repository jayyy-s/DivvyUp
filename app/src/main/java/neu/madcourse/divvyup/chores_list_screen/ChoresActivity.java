package neu.madcourse.divvyup.chores_list_screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neu.madcourse.divvyup.EditChoreActivity;
import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;

public class ChoresActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager toDoLayoutManager;
    private RecyclerView.LayoutManager inProgressLayoutManager;
    private RecyclerView.LayoutManager completedLayoutManager;

    private RecyclerView toDoRView;
    private RecyclerView inProgressRView;
    private RecyclerView completedRView;
    private BarChart chart;

    private FirebaseDatabase choreDatabase;

    private ChoreAdapter toDoChoreAdapter;
    private ChoreAdapter inProgressChoreAdapter;
    private ChoreAdapter completedChoreAdapter;


    private List<ChoreCard> toDoChoresList = new ArrayList<>();
    private List<ChoreCard> inProgressChoresList = new ArrayList<>();
    private List<ChoreCard> completedChoresList = new ArrayList<>();

    int[] colorClassArray = new int[]{Color.GREEN, Color.YELLOW, Color.RED};

    private static final String TODO_CHORE_COUNT = "TODO_CHORE_COUNT";
    private static final String TODO_CHORE_KEY = "TODO_CHORE_KEY";
    private static final String IN_PROGRESS_CHORE_COUNT = "IN_PROGRESS_CHORE_COUNT";
    private static final String IN_PROGRESS_CHORE_KEY = "IN_PROGRESS_CHORE_KEY";
    private static final String COMPLETED_CHORE_COUNT = "COMPLETED_CHORE_COUNT";
    private static final String COMPLETED_CHORE_KEY = "COMPLETED_CHORE_KEY";

    String currentUser;
    String groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);

        choreDatabase = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
////            this.currentUser = extras.getString("userKey");
//            this.groupId = extras.getString("groupId");
//        }
        this.groupId = "IDDD";

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("chores").child(choreID).child("name").getValue();

        Query allChores = choreDatabase.getReference().child("groups").orderByChild("idcode").equalTo(this.groupId);

        allChores.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        addChore(snapshot);
//                        String receiver = snapshot.child("groupId").getValue(String.class);
//
//                        if (currentUser.equals(receiver)) {
//                            System.out.println("SENDING NOTIFICATON");
////                            notificationSender.sendNotification(sender);
//                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        addChore(snapshot);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        toDoChoresList.add(new ChoreCard(DayOfWeek.MONDAY, "Dishes", "Bob", 0));


        toDoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        inProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        toDoRView = findViewById(R.id.idToDoChoresRecycler);
        inProgressRView = findViewById(R.id.idInProgressChoresRecycler);
        completedRView = findViewById(R.id.idCompletedChoresRecycler);

        Activity context = this;

        ChoreCardClickListener choreCardClickListener = new ChoreCardClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: Navigate to chores page
                System.out.println("hi");
                Intent editChoreIntent = new Intent(context, EditChoreActivity.class);
                editChoreIntent.putExtra("groupID", groupId);
                startActivity(editChoreIntent);
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


        chart = findViewById(R.id.idBarChart);

        BarDataSet barDataSet = new BarDataSet(dataValues1(), "Bar Set");
        barDataSet.setColors(colorClassArray);

        BarData barData = new BarData(barDataSet);
        chart.setData(barData);

//        chart.setDrawGridBackground(false);
//        chart.setDrawBarShadow(false);
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setDrawValueAboveBar(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);


        LegendEntry l1 = new LegendEntry("Completed", Legend.LegendForm.DEFAULT,10f,2f,null, Color.GREEN);
        LegendEntry l2 = new LegendEntry("In Progress", Legend.LegendForm.DEFAULT,10f,2f,null, Color.YELLOW);
        LegendEntry l3 = new LegendEntry("To Do", Legend.LegendForm.DEFAULT,10f,2f,null, Color.RED);

        chart.getLegend().setCustom(new LegendEntry[]{l1, l2, l3});
//                colorClassArray, new String[] {"Completed", "In Progress", "To Do"});

    }

//    private void initializeChores(Bundle savedInstanceState) {
//        if (savedInstanceState != null && savedInstanceState.containsKey(TODO_CHORE_COUNT)) {
//            if (toDoChoresList != null || toDoChoresList.size() == 0) {
//                int size = savedInstanceState.getInt(TODO_CHORE_COUNT);
//
//                for (int i = 0; i < size; i++) {
//                    String name = savedInstanceState.getString(TODO_CHORE_KEY + i);
//                    ChoreCard choreCard = new ChoreCard(name);
//                    toDoChoresList.add(choreCard);
//                }
//            }
//        }
//    }

    private ArrayList<BarEntry> dataValues1() {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        int completed = completedChoresList.size();
        int inProgress = inProgressChoresList.size();
        int toDo = toDoChoresList.size();
        dataVals.add(new BarEntry(0, new float[]{completed, inProgress, toDo}));
        return dataVals;
    }

    private void addChore(DataSnapshot snapshot) {
        GroupObject group = snapshot.getValue(GroupObject.class);
        for  (ChoreObject chore : group.getChores()) {
//        ChoreObject chore = snapshot.getValue(ChoreObject.class);

            if (snapshot.getKey() != null) {
                List<Boolean> days = chore.getDays();
                List<Integer> progresses = chore.getProgressMode();
                for (int i = 0; i < days.size(); i++) {
                    if (days.get(i)) {
                        DayOfWeek day = getDayOfWeek(i);
                        String title = chore.getName();
                        String assigned = chore.getUserAssigned();
                        int progress = progresses.get(i);
                        assignList(day, title, assigned, progress);
                    }
                }
            }
        }
    }

    public DayOfWeek getDayOfWeek(int number) {
        switch (number) {
            case 1:
                return DayOfWeek.MONDAY;
            case 2:
                return DayOfWeek.TUESDAY;
            case 3:
                return DayOfWeek.WEDNESDAY;
            case 4:
                return DayOfWeek.THURSDAY;
            case 5:
                return DayOfWeek.FRIDAY;
            case 6:
                return DayOfWeek.SATURDAY;
            case 7:
                return DayOfWeek.SUNDAY;
            default: throw new IllegalArgumentException("Not a valid day number");
        }
    }

    public void assignList(DayOfWeek day, String title, String assigned, int progress) {
        ChoreCard newChoreCard = new ChoreCard(day, title, assigned, progress);
        switch (progress) {
            case 0:
                toDoChoresList.add(newChoreCard);
                toDoChoreAdapter.notifyItemInserted(toDoChoresList.size() - 1);
            case 1:
                inProgressChoresList.add(newChoreCard);
                inProgressChoreAdapter.notifyItemInserted(toDoChoresList.size() - 1);
            case 2:
                completedChoresList.add(newChoreCard);
                completedChoreAdapter.notifyItemInserted(toDoChoresList.size() - 1);
        }
    }
//
//    private int[] getColors() {
//        // have as many colors as stack-values per entry
//        int[] colors = new int[3];
//        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);
//        return colors;
//    }



}