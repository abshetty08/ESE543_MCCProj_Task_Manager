package mcc.mcc_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by abhishekshetty on 5/13/17.
 */

public class SecondScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Intent receive_nameIntent = getIntent();

        String received_memberName = receive_nameIntent.getExtras().getString("selected_info");
        TextView nameValue = (TextView) findViewById(R.id.Member_Display);

        nameValue.append(received_memberName);
    }

}
