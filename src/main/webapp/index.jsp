<%-- 
    Document   : index
    Created on : Dec 14, 2020, 1:34:51 PM
    Author     : jianqing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The Lucky Baud</title>
        <%@include file="/WEB-INF/jspf/headtags.jspf" %>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/askask11/weblib@master/css/lds-loader.css" />
    </head>
    <body>
        <br><!-- title -->
        <br>
        <div class="container" style="background-color: rgba(255,255,255,0.7); border-radius: 6px;max-width: 50%;">
            <br>
            <h1 class="text-center">
                Lucky Baud
            </h1>
            <br><!-- Space between main title and subtitles -->

            <div class="text-center" style="font-size: 27px; ">
                <h4>
                    Welcome to LuckyBaud
                </h4>

                <div>LuckyBaud understands how you feel when you see your test is "graded". It makes
                    a grade more than a number. <a href="javascript:vdemo()">Demo</a></div>

            </div>
            <!-- Get started -->
            <div class="text-center">
                <!-- About me trigger -->
               
                
                &nbsp;
                <button class="btn btn-primary" id="continueBtn">
                    Continue
                </button><!-- Button trigger modal -->

                <br>
                ${indexMessage} ${empty indexMessage ? "":"<br>"}
                <form id='mainform' method='POST' action='CheckGrade' style="display: none;">

                    <div id="loginTitle" style="display: none;">
                        <br>
                        Login with blackbaud:<br>
                    </div>

                    <div id="f1" class="animated-text-input-container text-center" style="margin:auto; display: none;">
                        <input type="text" required title="Username" name="username" />
                        <label class="label-name"><span class="content-name">Username</span></label>
                    </div>
                    <div id="f2" class="animated-text-input-container text-center" style="margin:auto; display: none;">
                        <input type="password" required title="Password" name="password" />
                        <label class="label-name"><span class="content-name">Password</span></label>
                    </div>
                    <div id="f3" class="animated-text-input-container text-center" style="margin:auto; display: none;">
                        <input type="url" required title="Assignment Link" name="assignmentlink" />
                        <label class="label-name"><span class="content-name">Blackbaud Assignment link</span></label>
                    </div><br>
                    <div id="f4" style='color: darkslategrey; display: none;'>
                        * We won't store your credentials on the server. To continue, you agree with the Terms and conditions of MySchoolApp(Blackbaud).
                    </div>
                    <br><!-- Sign in btn -->
                    <button id="signinBtn" type='submit' class='arrowbutton' style="display: none;">
                        <span>Sign In</span><!-- Sign in -->
                    </button> <button id="demo" type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModal" style="display: none;">
                        View A Demo
                    </button>
                    <br>
                    <button id="aboutme" type="button" class="transparent" style="border: 0px; color:grey; font-size: 16px; display: none;" data-toggle="modal" data-target="#aboutMeModal">
                    About Me
                </button><br>
                </form>



                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Get A Demo</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form id="demoform" method='POST' action='CheckGrade'>
                                <div class="modal-body">
                                    Just enter some grade information about any assignment, you can view a demo of LuckyBaud.
                                    <div class="animated-text-input-container text-center" style="margin:auto; ">
                                        <input type="number" required title="grade" name="grade" value="95"/>
                                        <label class="label-name"><span class="content-name">Point Earned</span></label>
                                    </div>
                                    <div class="animated-text-input-container text-center" style="margin:auto;">
                                        <input type="number" required title="maximum point" name="max" value="100"/>
                                        <label class="label-name"><span class="content-name">Maximum Point</span></label>
                                    </div>
                                    <div class="animated-text-input-container text-center" style="margin:auto;">
                                        <input type="text" required title="assignment name" name="name" value="My Fancy Test"/>
                                        <label class="label-name"><span class="content-name">Assignment Name</span></label>
                                    </div>
                                    <input name="demo" value="1" type="hidden">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">View Demo</button>
                                </div>
                            </form>   
                        </div>
                    </div>
                </div>

                

                <!-- About Me Modal -->
                <div class="modal fade" id="aboutMeModal" tabindex="-1" role="dialog" aria-labelledby="aboutMeModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="aboutMeModalLabel">About This App</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                This app is made & managed by Jianqing Gao.<br><!-- support -->
                                Contact:<a href="mailto:support@jianqinggao.com">support@jianqinggao.com</a><br><!-- IPN -->
                                None of your cridentials will be stored on server. You may view the source code on github.com!
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <!--<button type="button" class="btn btn-primary">Save changes</button>-->
                            </div>
                        </div>
                    </div>
                </div>
                

                <div class="text-center" id="wait" style="display: none;">
                    Please wait while we are gathering information from blackbaud.
                    <br>
                    <%@include file="/WEB-INF/jspf/lds-loader.jspf" %>
                </div>
                <br>
            </div>
                
                
            <script>

                (function init() {
                    $("#continueBtn").click(() => {
                        $("#continueBtn").attr("disabled", "true");

                        $("#mainform").fadeToggle("fast", () => {
                            $("#loginTitle").fadeToggle("fast", () => {
                                $("#f1").fadeToggle("fast", () => {
                                    $("#f2").fadeToggle("fast", () => {
                                        $("#f3").fadeToggle("fast", () => {
                                            $("#f4").fadeToggle("fast", () => {
                                                $("#signinBtn").fadeToggle("fast");
                                                $("#demo").fadeToggle("fast");
                                                $("#aboutme").fadeToggle("fast");
                                                $("#continueBtn").text($("#continueBtn").text() === "Continue" ? "Hide" : "Continue");
                                                $("#continueBtn").attr("disabled", false);
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                    document.getElementById("mainform").onsubmit = function () {
                        $("#continueBtn").fadeOut("fast");
                        $("#mainform").fadeOut("fast");
                        $("#wait").fadeIn("slow");
                    };
                }
                )();



                if (document.getElementById("serr") !== undefined)
                {
                    $("#serr").click(() => {
                        $("#ex").fadeToggle("fast");
                    });
                }

                function vdemo() {
                    document.getElementById("demo").click();
                }
            </script>
        </div>
    </body>
</html>
