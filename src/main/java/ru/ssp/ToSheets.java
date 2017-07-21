package ru.ssp;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Created by KrutovBS on 18.07.2017.
 */
@WebServlet("/tosheet")
public class ToSheets extends HttpServlet {

    private String spreadsheetId = "1XUUdErflFkHpzOSkH3QZOTsL45nXBRFQTIn5aOVCH1A";
    public HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public JsonFactory JSON_FACTORY = new JacksonFactory();
    public AppIdentityCredential CREDENTIAL = new AppIdentityCredential(SheetsScopes.all());
    public Sheets service = new Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY,CREDENTIAL).setApplicationName("SSP-Interview").build();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        String request = req.getParameter("request");
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(request);
        JsonObject rootObject = element.getAsJsonObject();

        String team = "";
        switch (rootObject.get("team").getAsInt())
        {
            case 1 : team = "CAS";
                break;
            case 2 : team = "СБУ";
                break;
            case 3 : team = "ТЭК";
                break;
            case 4 : team = "Ланит";
                break;
        }
        List<Integer> priorities = Utils.jsotToListInt(rootObject,"priorities");
        //String otherPriority = rootObject.get("other_priority").getAsString();
        List<Integer> fivepoint = Utils.jsotToListInt(rootObject,"fivepoint");
        List<Integer> yesno = Utils.jsotToListInt(rootObject,"yesno");
        List<Integer> question = Utils.jsotToListInt(rootObject,"question");
        List<String> additional = Utils.jsotToListStr(rootObject,"additional");
        List<String> otherPriority = Utils.jsotToListStr(rootObject,"other_priority");
        List<Integer> loyality = Utils.jsotToListInt(rootObject,"loyality");



        String columnToInsert = getColumnToInsert("Результаты "+team);
        updateSheets("Результаты "+team, "priorities", priorities,columnToInsert);
        updateSheets("Результаты "+team, "fivepoint", fivepoint,columnToInsert);
        updateSheets("Результаты "+team, "yesno", yesno,columnToInsert);
        updateSheets("Результаты "+team, "question", question,columnToInsert);
        updateSheets("Результаты "+team, "additional", additional,columnToInsert);
        updateSheets("Результаты "+team, "other_priority", otherPriority,columnToInsert);
        updateSheets("Результаты "+team, "loyality", loyality,columnToInsert);

        columnToInsert = getColumnToInsert("Результаты общие");
        updateSheets("Результаты общие", "priorities", priorities,columnToInsert);
        updateSheets("Результаты общие", "fivepoint", fivepoint,columnToInsert);
        updateSheets("Результаты общие", "yesno", yesno,columnToInsert);
        updateSheets("Результаты общие", "question", question,columnToInsert);
        updateSheets("Результаты общие", "additional", additional,columnToInsert);
        updateSheets("Результаты общие", "other_priority", otherPriority,columnToInsert);
        updateSheets("Результаты общие", "loyality", loyality,columnToInsert);
    }

    private void updateSheets (String sheetId, String type, List data,String columnToInsert) throws IOException
    {
        ArrayList<String> rows = new ArrayList<>();
        switch (type)
        {
            //case "id" : rows.add("1"); rows.add("1");
            //break;
            case "priorities": rows.add("2"); rows.add("11");
                break;
            case "fivepoint" : rows.add("14"); rows.add("30");
                break;
            case "yesno" : rows.add("33"); rows.add("37");
                break;
            case "question" : rows.add(40+Integer.parseInt(data.get(0)+"")+""); rows.add(40+Integer.parseInt(data.get(0)+"")+"");
                data.set(0,1);
                break;
            case "additional" : rows.add("46"); rows.add("46");
                break;
            case "other_priority" : rows.add("49"); rows.add("49");
                break;
            case "loyality" : rows.add("53"); rows.add("67");
                break;
        }

        String column = columnToInsert;

        String range =  "'" + sheetId + "'!" + column + rows.get(0) + ":" + column + rows.get(1);
        ValueRange resp = new ValueRange();
        resp.setRange(range);
        resp.setMajorDimension("COLUMNS");
        List<List<Object>> values = new ArrayList<>();
        /*List<Object> value = new ArrayList<>();
        for (String s:
             data) {
            value.add(s);

        }
        value.addAll()*/
        values.add(data);
        resp.setValues(values);

        BatchUpdateValuesRequest batchRequest = new BatchUpdateValuesRequest();
        batchRequest.setValueInputOption("RAW");
        List<Request> requests = new ArrayList<>();


        List<ValueRange> updateValueRangeList = new ArrayList<>();
        updateValueRangeList.add(resp);

        batchRequest.setData(updateValueRangeList);

        BatchUpdateValuesResponse updateResponse = service.spreadsheets().
                values().batchUpdate(spreadsheetId, batchRequest).
                execute();
    }

    private void addAditionalQuestion (String sheetId, String data) throws IOException
    {

        if (StringUtils.isBlank(data))
        {
            return;
        }
        String range = "'" + sheetId + "'!B";
        ValueRange resp = service.spreadsheets().values().get(spreadsheetId,range).execute();
        resp.setMajorDimension("ROWS");
        String row = resp.getValues().size()+"";

        range = "'" + sheetId + "'B" + row;
        resp = new ValueRange();
        resp.setRange(range);
        List<List<Object>> values = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        value.add(0,data);
        values.add(0, value);
        resp.setValues(values);

        BatchUpdateValuesRequest batchRequest = new BatchUpdateValuesRequest();
        batchRequest.setValueInputOption("RAW");

        List<ValueRange> updateValueRangeList = new ArrayList<>();
        updateValueRangeList.add(resp);

        batchRequest.setData(updateValueRangeList);

        BatchUpdateValuesResponse updateResponse = service.spreadsheets().
                values().batchUpdate(spreadsheetId, batchRequest).
                execute();
    }

    private String getColumnToInsert(String sheetId) throws IOException
    {
        String range = "'" + sheetId + "'!2:2";
        ValueRange resp = service.spreadsheets().values().get(spreadsheetId,range).setMajorDimension("COLUMNS").execute();
        int fistChar = 0;
        int secondChar = resp.getValues().size()+1;
        while (secondChar>26)
        {
            fistChar++;
            secondChar-=26;
        }
        Request request = new Request();
        request.getAutoResizeDimensions();
        if(fistChar>0)
            return (char)(fistChar+64)+""+(char)(secondChar+64);
        else
            return (char)(secondChar+64)+"";
    }
}
