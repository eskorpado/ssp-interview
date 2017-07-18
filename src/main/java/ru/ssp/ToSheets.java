package ru.ssp;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by KrutovBS on 18.07.2017.
 */
@WebServlet("/tosheet")
public class ToSheets extends HttpServlet {

    private String spreadsheetId = "";
    private Sheets service;
    private String refreshToken = "";
    private String clientId = "";
    private String clientSecret = "";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        /*try {
            buildSheet(getAccessToken());
        } catch (Exception ignored) {}*/


        String request = req.getParameter("req");
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(request);
        JsonObject rootObject = element.getAsJsonObject();
        JsonObject priorities = rootObject.get("priorities").getAsJsonObject();
        Map<String,String> mymap = new HashMap<>();
        for (String key:
             priorities.keySet()) {
            out.println(priorities.get(key).getAsString());
        }
        for (String key:
                priorities.keySet()) {
            out.println(key);
        }
        for (String key:
                priorities.keySet()) {
            mymap.put(priorities.get(key).getAsString(),key);
        }
        mymap = MapUtil.sortByValue(mymap);
        for (String key: mymap.keySet())
        {
            out.println(key + " : " + mymap.get(key));
        }
        List<String> myList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            myList.add(mymap.get(i+""));
        }

        for (String string : myList)
        {
            out.println(string);
        }

        /*JsonArray fivepoint = rootObject.get("fivepoint").getAsJsonArray();
        JsonArray yesno = rootObject.get("yesno").getAsJsonArray();
        JsonArray loyality = rootObject.get("loyality").getAsJsonArray();*/
    }

    private String getAccessToken () throws IOException
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", clientId)); //Новый
        nvps.add(new BasicNameValuePair("client_secret", clientSecret));
        nvps.add(new BasicNameValuePair("refresh_token", refreshToken));
        nvps.add(new BasicNameValuePair("grant_type", "refresh_token"));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));


        CloseableHttpResponse response = httpClient.execute(httpPost);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line;
        String answer = "";
        while ((line = in.readLine()) != null)
        {
            answer+=line;
        }

        response.close();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(answer);
        JsonObject rootObject = element.getAsJsonObject();
        return rootObject.get("access_token").getAsString();
    }

    private void buildSheet (String access_token) throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential().setAccessToken(access_token);
        HttpTransport httpTransport = null;
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        service =  new Sheets.Builder(httpTransport, JacksonFactory.getDefaultInstance(),credential).setApplicationName("SSP-Interview").build();
    }

    private void updateSheets (String sheetId, String type, List<String> data) throws IOException
    {
        ArrayList<String> rows = new ArrayList<>();
        switch (type)
        {
            case "priorities": rows.add("2"); rows.add("11");
                break;
            case "fivepoint" : rows.add("14"); rows.add("30");
                break;
            case "yesno" : rows.add("2"); rows.add("37");
                break;
            case "question" : rows.add(40+Integer.parseInt(data.get(0))+""); rows.add(40+Integer.parseInt(data.get(0))+"");
                break;
        }

        String column = getLastColumn(sheetId);

        String range =  "'" + sheetId + "'!" + column + rows.get(0) + ":" + column + rows.get(1);
        ValueRange resp = new ValueRange();
        resp.setRange(range);
        resp.setMajorDimension("ROWS");
        List<List<Object>> values = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        for (String s:
             data) {
            value.add(0,s);
            values.add(0, value);
        }
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

    private String getLastColumn(String sheetId) throws IOException
    {
        String range = "'" + sheetId + "'!1:1";
        ValueRange resp = service.spreadsheets().values().get(spreadsheetId,range).execute();
        resp.setMajorDimension("ROWS");
        return resp.getValues().size()+"";
    }
}
