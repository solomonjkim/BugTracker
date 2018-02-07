package edu.andrews.cptr252.ksolomon.bugtracker;

/**
 * Created by solomonjkim on 2/7/18.
 */
import java.util.UUID;

public class Bug {
    private UUID mId;
    private String mTitle;


    public Bug(){

        mId = UUID.randomUUID();
    }

    public UUID getID(){
        return mId;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }
}
