// 访问路径第一层
var firstPath = '/followme';
var vRegister = new Vue({
	el : '#register',
	data : function() {
		return {
			clubLogin : '',
			clubName :'',
			description :'',
			password : '',
			password1 : '',
			picCode : '',// 绑定属性
			imgSrc : METHOD_URL + firstPath + "/verify/getVerifyCode"
					+ METHOD_SUFFIX, // 图形验证码
			loginNameFindErr : '',
			passwordTip : '6-16个字母、数字或者英文符号，区分大小写',
			passwordErr : '',
			passwordErr1 : '',
			picCodeErr : '',
			message : '', // 错图提示信息
			checkLoginNameFlag : true,
			checkPasswordFlag1 : true,
			checkPasswordFlag2 : true,
			checkCompareFlag : true,
			picCodeFlag : false,// 检查注册时，true可以注册,false不可以注册
			loginURL:METHOD_URL + firstPath + "/club/login.html"
		}
	},
});


/**
 * 检查密码格式
 */
function checkPassword() {
	if (vRegister.password === null || vRegister.password === '') {
		vRegister.passwordErr = '请输入密码！';
		return;
	}
	if (vRegister.password.length < 6 || vRegister.password.length > 16) {
		vRegister.passwordErr = '密码长度在6~16之间';
		vRegister.checkPasswordFlag1 = false;
	} else {
		vRegister.passwordErr = '';
		vRegister.checkPasswordFlag1 = true;
	}
	vRegister.checkPasswordFlag2 = true;
}

/**
 * 检查两次密码输入是否相同
 */
function compare() {
	// 密码验证不能为空
	if (vRegister.password1 == null || vRegister.password1 == '') {
		vRegister.passwordErr1 = '请再次输入密码！';
		return;
	}
	if (vRegister.password !== vRegister.password1) {
		vRegister.passwordErr1 = '密码不一致，请重新输入！';
		vRegister.checkCompareFlag = false;
	} else {
		vRegister.passwordErr1 = '';
		vRegister.checkCompareFlag = true;
	}
}





/**
 * 1,前端验证注册信息
 */
function checkRegister() {
	checkPassword();
	compare();
	// 1.2，验证格式Flag,全为true才能进行下一步操作
	if (vRegister.checkPasswordFlag1 == false
			|| vRegister.checkPasswordFlag2 == false
			|| vRegister.checkCompareFlag == false
			) {
		console.log("1"+vRegister.checkPasswordFlag1);
		console.log("2"+vRegister.checkPasswordFlag2);
		console.log("3"+vRegister.checkCompareFlag);
		return;     
	}else{
		console.log("---------")
		register();
	}
}




/**
 * 3.注册
 */
function register() {
	// 设置参数
	let data = {
		clubLogin  : vRegister.clubLogin,
		clubName  : vRegister.clubName,
		password : vRegister.password,
		picCode : vRegister.picCode,
		description : vRegister.description,
	};
	// 调动业务端方法
	let url = firstPath + '/club/registerClub';
	callAjaxPost(url, data, registerSuc);
}

function registerSuc(data) {
	console.log(data);
	if (data.code === "504") {
		vRegister.loginNameFindErr = '验证码错误！';
		return;
	}
	if (data.code === "506") {
		vRegister.loginNameFindErr = '用户名已存在！';
		return;
	}
	
	if (data.code === "200") {
		// 打开Vue对话框
		vRegister.$Modal.success({
			content : '<p>注册成功</p>',
			okText : '跳转登录',
			onOk : function() {
				// 重置登陆信息
				resetRegisterParam();
/*				// 清除后台session中的数据
				callAjaxGetNoParamsAndFun(url);
				window.location.href = "/";
*/			}
		});
	}
	
	
}

/**
 * 重置注册时填写的参数
 */
function resetRegisterParam() {
	vRegister.clubLogin = '';
	vRegister.clubName = '';
	vRegister.description = '';
	vRegister.picCode = '';
	vRegister.password = '';
	vRegister.password1 = '';
}


/**
 * 刷新注册验证码
 */
function refreshRegister() {
	let url = METHOD_URL + firstPath + "/verify/getVerifyCode" + METHOD_SUFFIX
			+ "?number=" + Math.random();
	// attr() 方法设置或返回被选元素的属性和值。
	$("#registerImg").attr("src", url);
}

/**
 * 键盘输入事件绑定到特定按钮，事件 onkeydown 这个事件在用户按下任何键盘键（包括系统按钮，如箭头键和功能键）时发生 onkeyup
 * 这个事件在用户放开任何先前按下的键盘键时发生。 onkeypress
 * 这个事件在用户按下并放开任何字母数字键时发生。系统按钮（例如，箭头键和功能键）无法得到识别
 */
document.onkeydown = function(event) {
	/*
	 * IE用event.keCode方法获取当前被按下的键盘按键值 而NetScape/FireFox/Opera用的则是event.which
	 */
	let e = event || window.event;
	let keyCode = e.keyCode || e.which;
	switch (keyCode) {
	// 回车
	case 13:
		checkRegister();
		break;
	default:
		break;
	}
};