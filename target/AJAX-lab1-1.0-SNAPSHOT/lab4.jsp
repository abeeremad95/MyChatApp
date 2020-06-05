<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
<head>
    <meta charset="UTF-8">
    <title>WebSocket Lab</title>


    <style>
        .center-block{
            width: 800px;
        }
    </style>
</head>
<body>
    <h2 class="center text-primary">Online Users: <span id="onlineusers">0</span></h2>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-10">
                <input type="text" class="form-control"  id="username"  value= "<%=session.getAttribute("uname")%>" >



            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Message</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" placeholder="Enter message" id="message">
            </div>
            <div class="col">
                <button type="button" class="btn btn-light mt-3" onclick="sendMessage()">Send</button>
            </div>
        </div>
    <div class="form-group row">
        <button onclick="downloadChat()">download Chat file</button>
    </div>
    <div id="msgbox" class="center-block">

    </div>

    <script src="https://code.jquery.com/jquery-git.min.js"></script>
    <script src="./views/js/lab4.js"></script>
</body>
</html>