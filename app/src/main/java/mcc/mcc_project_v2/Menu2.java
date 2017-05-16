package mcc.mcc_project_v2;

import android.content.Intent;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by abhishekshetty on 5/13/17.
 */

public class Menu2 extends Fragment {
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Project Status");


        ArrayList<String> tasks =  new ArrayList<String>();
        for(int i=0; i<3; i++){
            tasks.add(i,"Task No: "+(i+1));
        }

        ListAdapter adap_val_tasks = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, tasks);
        ListView task_ListView = (ListView) getView().findViewById(R.id.task_listView);
        task_ListView.setAdapter(adap_val_tasks);
        task_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                String selected_info = String.valueOf(adapterview.getItemAtPosition(position));

//                Toast.makeText(MainActivity.this, selected_info, Toast.LENGTH_SHORT).show();
                Intent send_nameIntent = new Intent(getContext(),SecondScreen.class);

                final int result = 1;
                send_nameIntent.putExtra("selected_info", selected_info);
                startActivity(send_nameIntent);
            }
        });



        ArrayList<String> tasks_status =  new ArrayList<String>();
//        for(int i=0; i<3; i++){
//            tasks_status.add(i,"Task No: "+(i+1));
//        }
        tasks_status.add(0, "Completed");
        tasks_status.add(1, "Ongoing");
        tasks_status.add(2, "Not started");

        ListAdapter adap_val_tasks_status = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, tasks_status);
        ListView staus_ListView = (ListView) getView().findViewById(R.id.status_listView);
        staus_ListView.setAdapter(adap_val_tasks_status);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.menu2, container, false);



    }


}
