package edu.andrews.cptr252.ksolomon.bugtracker;

/**
 * Created by solomonjkim on 3/12/18.
 */

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class BugJSONSerializer {
    private Context mContext;
    private String mFilename;

    public BugJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Bug> loadBugs() throws IOException, JSONException{
        ArrayList<Bug> bugs = new ArrayList<>();
        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for(int i = 0; i<array.length(); i++){
                bugs.add(new Bug(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){

        } finally {
            if(reader != null) reader.close();
        }
        return bugs;
    }

    public void saveBugs(ArrayList<Bug> bugs) throws JSONException, IOException{

        JSONArray array = new JSONArray();
        for (Bug bug : bugs) array.put(bug.toJSON());

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null) writer.close();
        }
    }
}
