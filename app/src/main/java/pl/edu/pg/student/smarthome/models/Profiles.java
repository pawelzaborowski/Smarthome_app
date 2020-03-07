package pl.edu.pg.student.smarthome.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profiles {


    public static List<Profile> profiles = new ArrayList<>();

    private static Profile returnProfileFromName(String name){
        for (Profile p : profiles){
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public static void activeProfile(String name){

        Profile profile = returnProfileFromName(name);

        if(Profiles.profiles.contains(profile)) {
            for (Profile p : profiles){
                p.setActive(false);
            }

            if(profile != null) {
                for (Device dev : profile.getDevices()) {
                    if (Constants.devices.contains(dev)) {
                        Constants.devices.get(Constants.devices.indexOf(dev)).setStatus(dev.getStatus());
                    }
                }
            }
            Constants.ifActiveProfile = true;
            Constants.activeProfileName = name;
            Profiles.profiles.get(Profiles.profiles.indexOf(profile)).setActive(true);
        }
    }

    public static void disactiveProfile(String name){

        Profile profile = returnProfileFromName(name);

        if(Profiles.profiles.contains(profile)) {
            Profiles.profiles.get(Profiles.profiles.indexOf(profile)).setActive(false);
            Constants.ifActiveProfile = false;
        }
    }
}