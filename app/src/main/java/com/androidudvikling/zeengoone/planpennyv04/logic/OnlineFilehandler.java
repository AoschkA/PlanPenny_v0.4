package com.androidudvikling.zeengoone.planpennyv04.logic;

import com.androidudvikling.zeengoone.planpennyv04.entities.Project;
import com.firebase.client.Firebase;

/**
 * Created by alexandervpedersen on 18/01/16.
 */

// Denne klasse er oprettet med henblik på brug af Firebase.
public class OnlineFilehandler {

    Firebase myFirebaseRef;

    public OnlineFilehandler(){
        myFirebaseRef = new Firebase("https://planpenny.firebaseio.com/");
    }

    public boolean saveProject(Project project){
        return true;
    }

    public Project getProject(String projectName){
        return null;
    }


}
