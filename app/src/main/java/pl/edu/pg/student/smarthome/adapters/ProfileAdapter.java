package pl.edu.pg.student.smarthome.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Profile;

public class ProfileAdapter extends ArrayAdapter<Profile> {

    private static final String LOG_TAG = "MemoListAdapter";
    private List<Profile> _profiles;
    private static LayoutInflater inflater = null;

    public ProfileAdapter(Activity _activity, int resourceId, List<Profile> _lprofiles) {
        super(_activity, resourceId, _lprofiles);

        this._profiles = _lprofiles;
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return _profiles.size();
    }

    public static class ViewHolder{
        public TextView display_name;
        public TextView display_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder viewHolder;

        try {
            if(convertView == null){

                vi = inflater.inflate(R.layout.profile_list_item, null);
                viewHolder = new ViewHolder();

                viewHolder.display_name = vi.findViewById(R.id.display_name);
                viewHolder.display_status = vi.findViewById(R.id.display_status);

                vi.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) vi.getTag();
            }

            viewHolder.display_name.setText(_profiles.get(position).getName());

            if(_profiles.get(position).getActive())
                viewHolder.display_status.setText("ON");
            else
                viewHolder.display_status.setText("OFF");


        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
        return vi;
    }
}
