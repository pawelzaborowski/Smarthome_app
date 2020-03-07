package pl.edu.pg.student.smarthome.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Profile;

public class DeviceAdapter extends ArrayAdapter<Device> {

    private int layoutResourceId;
    private List<Device> _devices;
    private static LayoutInflater inflater = null;

    public DeviceAdapter(Activity _activity, int resourceId, List<Device> _ldevices) {
        super(_activity, resourceId, _ldevices);

        this._devices = _ldevices;
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return _devices.size();
    }

    private static class ViewHolder{
        public TextView display_name;
        public TextView display_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ProfileAdapter.ViewHolder viewHolder;

        try {
            if(convertView == null){

                vi = inflater.inflate(R.layout.profile_list_item, null);
                viewHolder = new ProfileAdapter.ViewHolder();

                viewHolder.display_name = vi.findViewById(R.id.display_name);
                viewHolder.display_status = vi.findViewById(R.id.display_status);

                if(_devices.get(position).getRoomId() == null){
                    vi.setBackgroundColor(0xFFF7AEAE);
                }

                vi.setTag(viewHolder);
            } else {
                viewHolder = (ProfileAdapter.ViewHolder) vi.getTag();
            }

            viewHolder.display_name.setText(_devices.get(position).getName());

            if(_devices.get(position).getStatus()) {
                viewHolder.display_status.setText("ON");
            }else
                viewHolder.display_status.setText("OFF");

        } catch (Exception ex) {
            return null;
        }
        return vi;
    }
}
