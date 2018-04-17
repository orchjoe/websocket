$(function() {
	var userId = getParam("userId");
	if (userId && null != userId && "" != userId) {
		localStorage.setItem("X-AUTH-TOKEN", userId);
		console.log("手动设置userId:" + userId);
	} else {
		userId = 1;
	}
	// 初始化webSocket
	initWebSocket(userId)
})
var socket;
function initWebSocket(userId) {
	if (typeof (WebSocket) == "undefined") {
		console.log("您的浏览器不支持WebSocket");
	} else {
		if (window.location.protocol == 'http:') {
			url = 'ws://' + getWebSocketRootPath() + "studentWebSocket.do?id=" + userId;
		} else {
			url = 'wss://' + getWebSocketRootPath() + "studentWebSocket.do?id=" + userId;
		}
		socket = new WebSocket(url);
		socket.onopen = function() {
			console.log("Socket 已打开");
		};
		socket.onmessage = function(msg) {
			setword(msg.data)
		};
		socket.onclose = function() {
			console.log("Socket已关闭");
		};
		socket.onerror = function() {
			console.log("Socket发生了错误");
		};

	}
}

function funmp3(json) {
	var id = json.result;
	var obj = {};
	obj.msg = id;
	obj.type = 1
	var userId = getParam("userId");
	if (userId == "1") {
		obj.userId = "2"
	} else {
		obj.userId = "1"
	}
	socket.send(JSON.stringify(obj));
	// setword(id);
}

function setword(id) {
	console.log(id);
	try {
		if (id == null || id == "null") {
			if (fun != undefined) {
				setTimeout(function() {
					fun(0);
				}, 0)
			}
			return;
		}
		if ($("#player_audio").length == 0) {
			$("body").prepend($("<div style='display:none' id='readdiv'> <audio id='player_audio' src='' preload='auto'/></div>"))
		}
		$("#player_audio").attr("src", function() {
			var client = new OSS.Wrapper({
				region : 'oss-cn-beijing',
				accessKeyId : 'LTAIJuAAYm5fDqwm',
				accessKeySecret : '4TbWkvkDyx7F4ivsSNtuVHGmnvoj8G',
				bucket : 'xl-oss-pro1'
			});
			return client.signatureUrl(id);
		});
		document.getElementById("player_audio").play();
		var recordtime = new Date().getTime();
		// console.log("时间:" + recordtime);
		// document.getElementById("player_audio").addEventListener('ended',
		// function() {
		// $("#readdiv").remove();
		// if (fun != undefined)
		// fun(new Date().getTime() - recordtime);
		// }, true);
	} catch (e) {
	}
}