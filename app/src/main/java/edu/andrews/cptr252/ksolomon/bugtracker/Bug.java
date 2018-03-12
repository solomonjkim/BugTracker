package edu.andrews.cptr252.ksolomon.bugtracker;

/**
 * Created by solomonjkim on 2/7/18.
 */
import java.util.UUID;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class Bug {

    private String mDescription;
    private Date mDate;
    private boolean mSolved;
    private UUID mId;
    private String mTitle;

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_DATE = "date";
    private static final String JSON_SOLVED = "solved";



    public Bug(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.optString(JSON_TITLE);
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        mDescription = json.optString(JSON_DESCRIPTION);
    }

    public Bug(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_DESCRIPTION, mDescription);
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);

        return json;
    }

    public String getDescription(){return mDescription;}
    public void setDescription(String description) {mDescription = description;} //setDescription
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
