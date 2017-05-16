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

public class Menu3 extends Fragment {
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Issues");

        ArrayList<String> members =  new ArrayList<String>();
        for(int i=0; i<10; i++){
            members.add(i,"Issue_No: "+(i+1));
        }
//        String[] info = {"Team Member 1", "Team Member 2", "Team Member 3", "Team Member 4", "Team Member 5", "Team Member 6",
//                "Team Member 7", "Team Member 8", "Team Member 9", "Team Member 10", "Team Member 11", "Team Member 12" }
//        ListAdapter adap_val = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, members);
        ListAdapter adap_val = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, members);
        ListView theListView = (ListView) getView().findViewById(R.id.tickets_listview);
        theListView.setAdapter(adap_val);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.menu3, container, false);



    }


}
