/*
 * Author: jianqing
 * Date: Dec 15, 2020
 * Description: This document is created for
 */
package com.classchat.luckybaud.modal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jianqing
 */
public class BBAssignment
{
    private String assignmentName;
    private boolean graded;
    private int maxGrade;
    private int gradeEarned;
    
    private int studentId;
    private int assignmentId;
    private int assignmentIndexId;
    

    public BBAssignment()
    {
        assignmentName = null;
        graded = false;
        maxGrade=0;
        gradeEarned=0;
        studentId=0;
        assignmentId=0;
        assignmentIndexId=0;
    }

    public String getAssignmentName()
    {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName)
    {
        this.assignmentName = assignmentName;
    }

    public boolean isGraded()
    {
        return graded;
    }

    public void setGraded(boolean graded)
    {
        this.graded = graded;
    }

    public int getMaxGrade()
    {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade)
    {
        this.maxGrade = maxGrade;
    }

    public int getGradeEarned()
    {
        return gradeEarned;
    }

    public void setGradeEarned(int gradeEarned)
    {
        this.gradeEarned = gradeEarned;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public int getAssignmentId()
    {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId)
    {
        this.assignmentId = assignmentId;
    }

    public int getAssignmentIndexId()
    {
        return assignmentIndexId;
    }

    public void setAssignmentIndexId(int assignmentIndexId)
    {
        this.assignmentIndexId = assignmentIndexId;
    }

    /**
     * Pseudo method
     * @return 
     */
     public String getDateCommonFormat()
    {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(LocalDateTime.now().minusHours(1).atZone(ZoneId.of("UTC")));
    }
     
    @Override
    public String toString()
    {
        return "BBAssignment{" + "assignmentName=" + assignmentName + ", graded=" + graded + ", maxGrade=" + maxGrade + ", gradeEarned=" + gradeEarned + '}';
    }
    
    
    
   
   
}
