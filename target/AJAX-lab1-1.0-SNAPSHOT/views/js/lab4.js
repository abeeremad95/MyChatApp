

let counter = 0;
let ws;
let sessionId ;
let listOfMesssagesOfThisClient = [];


function callback(messageObj){
        const msgBox = document.getElementById("msgbox");
        const container = document.createElement("div");
        const username = document.createElement("strong");
        const msg = document.createElement("span");
        username.textContent = messageObj.username;
        msg.textContent = messageObj.msg;
        container.style.width = "400px";
        container.style.background = "#ff3c2d";
        container.style.color = "white";
        container.style.border = "1px solid blue";
        container.style.marginTop = "20px";
        if(messageObj.sessionId === sessionId){
        }
        else{
            container.style.marginLeft = "400px"
        }
        username.style.padding = "20px";
        msg.style.padding = "20px";
        container.append(username, msg);
        msgBox.append(container);
}

document.body.onload = function () {
    createWebSocket();
};
function createWebSocket(){
    ws = new WebSocket("ws://localhost:8088/AJAX-lab1-1.0-SNAPSHOT/socketmessenger");
    ws.onopen = onOpen;
    ws.onmessage = onMessage;
}

function onOpen(){
    console.log("Connection Established");
}

function onMessage(e){
    const messageObj = JSON.parse(e.data);
    if(messageObj.hasOwnProperty("onlineusers")){
        document.getElementById("onlineusers").textContent = messageObj.onlineusers;
    }
    else if(messageObj.hasOwnProperty("msg")){
        console.log(messageObj);
        callback(messageObj);      //create html for msgs
        document.getElementById("message").value = "" //ht3ml eh ???
        listOfMesssagesOfThisClient.push({username: messageObj.username, message: messageObj.msg});
      //  document.write(listOfMesssagesOfThisClient);///to test chat on client
    }
    else{
        sessionId = messageObj.sessionId;    //3shan ymayez session bta3ti 3shan left wi right al msgs

    }

}

function sendMessage() {
   let Username= document.getElementById("username").value;
   let Message =document.getElementById("message").value;
    ws.send(JSON.stringify({
        username: Username ,
        msg: Message
    }));
}


function downloadChat(){


    $.post('download', {
            messages: JSON.stringify({
                messages: listOfMesssagesOfThisClient
            }),
            enctype: 'multipart/form-data'
        },
        function(returnedData){
            console.log(returnedData)
            alert(returnedData);
        });
}




