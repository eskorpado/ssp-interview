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
import com.google.api.services.sheets.v4.model.ValueRange;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by KrutovBS on 20.07.2017.
 */
@WebServlet("/test")
public class Test extends HttpServlet {
    public HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public JsonFactory JSON_FACTORY = new JacksonFactory();
    public AppIdentityCredential CREDENTIAL = new AppIdentityCredential(SheetsScopes.all());
    public Sheets service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        service = new Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY,CREDENTIAL).setApplicationName("SOME NAME").build();
        String range = "'Sh1'!A1:A1";
        ValueRange respo = new ValueRange();
        respo.setRange(range);
        respo.setMajorDimension("ROWS");
        List<List<Object>> values = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        value.add(0, "TEST");
        values.add(0, value);
        respo.setValues(values);

        BatchUpdateValuesRequest batchRequest = new BatchUpdateValuesRequest();
        batchRequest.setValueInputOption("RAW");

        List<ValueRange> updateValueRangeList = new ArrayList<>();
        updateValueRangeList.add(respo);

        batchRequest.setData(updateValueRangeList);

        BatchUpdateValuesResponse updateResponse = service.spreadsheets().
                values().batchUpdate("1XUUdErflFkHpzOSkH3QZOTsL45nXBRFQTIn5aOVCH1A", batchRequest).
                execute();

    }
}