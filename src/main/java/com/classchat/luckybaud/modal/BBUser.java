/*
 * Author: jianqing
 * Date: Dec 15, 2020
 * Description: This document is created for
 */
package com.classchat.luckybaud.modal;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.IOException;

/**
 *
 * @author jianqing
 */
public class BBUser
{

    public static final String SCHEMA_URL = "https://bostontrinity.myschoolapp.com";

    //attributes
    private boolean loggedIn;
    private String username;
    private String password;
    private String token;
    private int userId;
    private String errorMessage;

    //default constructor
    /**
     * Construct a default Blackbaud user not logged in with null username and
     * password.
     */
    public BBUser()
    {
        loggedIn = false;
        username = null;
        password = null;
        token = null;
        userId = 0;
    }

    //overloaded constructor
    /**
     * Construct a blackbaud user with username and password.
     *
     * @param username The username of the user. Not his real name, but the one
     * he use to log in.
     * @param password Password of the user.
     */
    public BBUser(String username, String password)
    {
        this();
        this.username = username;
        this.password = password;
    }

    //set and get methods.
    //<editor-fold>
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        if (loggedIn)
        {
            System.err.println("User is logged in so username cannot be changed.");
        } else
        {
            this.username = username;
        }
    }

    public String getPassword()
    {

        return password;
    }

    public void setPassword(String password)
    {
        if (loggedIn)
        {
            System.err.println("User logged in. Password cannot be changed.");
        } else
        {
            this.password = password;
        }
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    //</editor-fold>
    /**
     * Try to log this student into blackbaud. 
     * @return <code>true</code> if the student has successfully logged in. <code>false</code> otherwise.
     */
    public boolean login()
    {
        this.loggedIn = false;
        if (StrUtil.hasBlank(username, password))
        {
            this.errorMessage = "No cridential provided. Login Failed!";
        } else
        {
            //create a JSON object with user cridentials
            JSONObject jSONObject = JSONUtil.createObj()
                    .putOpt("username", username)
                    .putOpt("password", password);
            //create a GET request with payload of cridentials.
            HttpRequest request = HttpUtil.createGet(SCHEMA_URL + "/api/authentication/login").body(jSONObject.toString());
            HttpResponse response = request.execute();//send request
            JSONObject jSONResponse = JSONUtil.parseObj(response.body());
            this.token = jSONResponse.getStr("Token");
            this.errorMessage = jSONResponse.getStr("Error");

            //check if user has successfully logged in
            if (StrUtil.isBlank(token))
            {
                //user login failed, returned a blank token
                //check returned errors.
                this.errorMessage = this.errorMessage == null ? "Unknown Error" : this.errorMessage;
            } else
            {
                //user logged in, record user id and token returned.
                this.loggedIn = true;
                this.userId = jSONResponse.getInt("UserId");
            }

        }
        return this.loggedIn;
    }

    /**
     * Get the necessary assignment detail of a user.
     *
     * @param assignmentId the Assignment ID
     * @param assignmentIndexId index id of an assignment
     * @return the assignment of the user
     * @throws IOException The exception thrown when there is an error.
     */
    public BBAssignment getStudentAssignment(int assignmentId, int assignmentIndexId) throws IOException
    {
        //declear / call other methods to get needed info.
        BBAssignment assignment = new BBAssignment();
        JSONObject assignmentDetail = getAssignmentDetail(assignmentId);
        JSONArray indexAssignmentDetailArray = getStudentAssignmentDetail(assignmentIndexId);
        JSONObject indexAssignmentDetail = indexAssignmentDetailArray.toList(JSONObject.class).get(0);

        //pull information from json object
        assignment.setAssignmentId(assignmentId);
        assignment.setAssignmentIndexId(assignmentIndexId);
        assignment.setStudentId(userId);
        assignment.setGraded(assignmentDetail.getBool("HasGrades"));
        assignment.setAssignmentName(assignmentDetail.getStr("ShortDescription"));
        assignment.setMaxGrade(indexAssignmentDetail.getInt("maxPoints"));
        
        assignment.setGradeEarned(indexAssignmentDetail.get("pointsEarned")==null?0:indexAssignmentDetail.getInt("pointsEarned"));
        return assignment;
    }
    

    /**
     * Read the basic assignment detail of an assignment of this
     * user.<strong>Note: This method does not read grade of the assignment.If
     * you need grade of this assignment, please use the
     * <code>getStudentAssignmentDetail</code> method.</strong>
     *
     * @param assignmentId The ID of the assignment to read
     * @return JSONObject containing assignment information received from BB
     * API.
     * @throws java.io.IOException
     * @see getStudentAssignmentDetail()
     */
    public JSONObject getAssignmentDetail(int assignmentId) throws IOException
    {
        //send a GET request
        //visit this link to read assignment detail (exclude grade, read grade next step): 
        //https://bostontrinity.myschoolapp.com/api/assignment2/read/8153613/?format=json
        //create request
        return JSONUtil.parseObj(executeBBRequest("/api/assignment2/read/" + assignmentId + "/?format=json", token));
    }

    /**
     * Get the assignment detail of a student. with grades
     *
     * @param assignmentId
     * @param assignmentIndexId
     * @return
     * @throws IOException
     */
    public JSONArray getStudentAssignmentDetail(int assignmentIndexId) throws IOException
    {
        //https://bostontrinity.myschoolapp.com/api/datadirect/AssignmentStudentDetail?format=json&studentId=5028794&AssignmentIndexId=13133488
        return JSONUtil.parseArray(executeBBRequest("/api/datadirect/AssignmentStudentDetail?format=json&studentId=" + userId + "&AssignmentIndexId=" + assignmentIndexId));
    }

    /**
     * Send an request to the Blackbaud server with student token.
     *
     * @param url The relative URI exclude schema url.
     * @return The response from server.
     * @throws IOException When the status code is not 2xx.
     */
    public String executeBBRequest(String url) throws IOException
    {
        return executeBBRequest(url, token);
    }

    /**
     * Send an request to the Blackbaud server with given token.
     *
     * @param url The relative URI exclude schema url.
     * @param token The token of the stuent.
     * @return The response from server.
     * @throws IOException When the status code is not 2xx.
     */
    public static String executeBBRequest(String url, String token) throws IOException
    {
        HttpRequest request = HttpUtil.createGet(SCHEMA_URL + url);
        //set cookie for verification
        
        
        System.out.println("Token " + token);
        //HttpCookie personaCookie = new HttpCookie("persona", "student");
        
        request.header("Cookie","t="+token);
        request.contentType("application/json");
        request.charset("utf-8");
        //execute and get response
        HttpResponse response = request.execute();
        if (response.isOk())
        {
            return response.body();
        } else
        {
            throw new IOException("Get Assignment Detail returned an error, status code " + response.getStatus() + " return body, " + response.body() + ", token send : " + token);
        }
    }
}
