package com.ovs_corp.bstorm.ui.home.idea_create;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.R;

import java.util.ArrayList;

public class NewIdea extends AppCompatActivity {


    Button Send;
    String[] list_items;
    boolean[] checked_items;
    ArrayList<Integer> userListItems = new ArrayList<>();
    TextView itm_t;


    String[] list_items_addit;
    boolean[] checked_items_addit;
    ArrayList<Integer> userListItems_addit = new ArrayList<>();
    TextView itm_t_addit;
    boolean is_second = false;

    ScrollView first_idea_step, second_idea_step;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_creator);

        first_idea_step = findViewById(R.id.idea_first_step);
        second_idea_step = findViewById(R.id.next_idea_step);

        first_idea_step.setVisibility(View.VISIBLE);
        second_idea_step.setVisibility(View.GONE);

        list_items = getResources().getStringArray(R.array.way_todo);
        checked_items = new boolean[list_items.length];
        itm_t = findViewById(R.id.items);

        list_items_addit = getResources().getStringArray(R.array.predictable_results);
        checked_items_addit = new boolean[list_items_addit.length];
        itm_t_addit = findViewById(R.id.items_addit);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void openVariants(View view) {

            Context context;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewIdea.this);
            mBuilder.setTitle("Выберите тему");
            mBuilder.setMultiChoiceItems(list_items, checked_items, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    if(isChecked){
                        if(!userListItems.contains(which))
                            userListItems.add(which);
                        else
                        {
                            try
                            {
                                userListItems.remove(which);
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }
                }
            });
            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String item = "";
                    for (int i = 0; i < userListItems.size(); i++)
                    {
                        item+=list_items[userListItems.get(i)];
                        if(i != userListItems.size()-1) {

                            item = item + ", ";
                        }
                    }
                    itm_t.setText(item);
                }
            });

            mBuilder.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checked_items.length; i++)
                    {
                        checked_items[i] = false;
                        userListItems.clear();
                        itm_t.setText("");
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
    }


    public void openVariantsAddit(View view) {

        Context context;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewIdea.this);
        mBuilder.setTitle("Выберите тему");
        mBuilder.setMultiChoiceItems(list_items_addit, checked_items_addit, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked){
                    if(!userListItems_addit.contains(which))
                        userListItems_addit.add(which);
                    else
                    {
                        try
                        {
                            userListItems_addit.remove(which);
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < userListItems_addit.size(); i++)
                {
                    item+= list_items_addit[userListItems_addit.get(i)];
                    if(i != userListItems_addit.size()-1) {

                        item = item + ", ";
                    }
                }
                itm_t_addit.setText(item);
            }
        });

        mBuilder.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checked_items_addit.length; i++)
                {
                    checked_items_addit[i] = false;
                    userListItems_addit.clear();
                    itm_t_addit.setText("");
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    public void back_from_idea(View view) {
        super.onBackPressed();
    }

    public void next(View view) {
        first_idea_step.setVisibility(View.GONE);
        second_idea_step.setVisibility(View.VISIBLE);
        is_second = true;
    }


    @Override
    public void onBackPressed() {
        if(is_second)
        {
            first_idea_step.setVisibility(View.VISIBLE);
            second_idea_step.setVisibility(View.GONE);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void setData(View view) {
        super.onBackPressed();
        Toast.makeText(NewIdea.this, "Ваше предложение отправленно на рассмотрение", Toast.LENGTH_LONG).show();
    }
}
