package edu.andrews.cptr252.ksolomon.bugtracker;

/**
 * Created by solomonjkim on 2/7/18.
 */
import java.util.UUID;
import java.util.Date;

public class Bug {

    private String mDescription;
    private Date mDate;
    private boolean mSolved;
    private UUID mId;
    private String mTitle;


    public Bug(){

        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public String getDescription(){return mDescription;}
    public void setDescription(String description) {mDescription = description;}
    public boolean isSolved() {return mSolved;}
    public void setSolved(boolean solved){mSolved = solved;}
    public Date getDate(){return mDate;}
    public void setDate(Date date){mDate = date;}


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
