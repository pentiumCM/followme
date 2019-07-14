// let homePage =  "/test/people.html";
var homePage = "/manage/course_info";
//访问路径第一层
var firstPath = '/followme';
var vLogin = new Vue({
	//对应div的id
	el: '#login',
	data: function () {
		return {
			loginType: 'club',
			loginError: '',
			phoneError: '', //错误提示信息
			imgSrc: METHOD_URL + firstPath + "/verify/getVerifyCode" + METHOD_SUFFIX, //图形验证码
			clubForm: {
				userName: '', password: '', code: ''
			}, //账户表单属性
			clubRules: {
				userName: [{ required: true, message: '账号不能为空', trigger: 'blur' }],
				password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
				code: [{ required: true, message: '验证码不能为空', trigger: 'blur' }]
			}, //账户表单验证规则
			registerURL: METHOD_URL + firstPath + "/club/register.html"
		}
	},
	mounted() {
		console.log(this.imgSrc);
		console.log(this.loginType);
	}
});

/**
 * club登录
 */
function loginByClub() {
	vLogin.loginType = 'club';
}

/**
 * admin登录
 */
function loginByAdmin() {
	vLogin.loginType = 'admin';
}

/**
 * 表单提交
 */
function loginSubmit() {
	console.log(vLogin.loginType);
	let data = {
		username: vLogin.clubForm.userName,
		password: vLogin.clubForm.password,
		verify: vLogin.clubForm.code
	};
	console.log(data);
	let url = null;
	if (vLogin.loginType == 'club') {
		url = firstPath + "/club/loginClub";
	}
	if (vLogin.loginType == 'admin') {
		url = firstPath + "/admin/loginAdmin";
	}

	callAjaxPost(url, data, LoginSuc);


}

function LoginSuc(data) {
	console.log(data);
	//1，用户名密码错误 或后 台异常直接返回
	if (data.code === "505") {
		//验证码错误,刷新页面，选择性重置相关值
		vLogin.loginError = '用户名或密码错误！';
		resetParams();
		return;
	}
	if (data.code === "504") {
		//验证码错误,刷新页面，选择性重置相关值
		vLogin.loginError = '验证码错误！';
		resetParams();
		refreshAccountImg();
		return;
	}
	if (data.code === "200") {
		// window.location.href = homePage;
		console.log(vLogin.loginType);
		if (vLogin.loginType == 'club') {
			window.sessionStorage.setItem("clubName", data.obj.clubName);
			window.location.href = METHOD_URL + PROJECT_NAME + "/club/activityList.html"
		}
		console.log("success");
	}
}




/**
 * 登录失败，重置相关参数并刷新验证码
 */
function resetParams() {
	vLogin.clubForm.password = '';
	vLogin.clubForm.code = '';
	refreshAccountImg();
}


/**
 * 刷新账号验证码
 */
function refreshAccountImg() {
	let url = firstPath + "/verify/getVerifyCode?number=" + Math.random();
	$("#accountImg").attr("src", url);
}


