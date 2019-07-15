var app = new Vue({
	el: '#app',
	data: function () {
		return {
			columns: [
				{
					type: 'selection',
					width: 60,
					align: 'center'
				},
				{
					title: '俱乐部名称',
					align: 'center',
					key: 'clubName'
				},
				{
					title: '俱乐部描述',
					align: 'center',
					key: 'description'
				}
			],
			data: [
			]
		}
	},
	components: {
		'layout-header': httpVueLoader('./layout/layout-header.vue'),
		'layout-sider': httpVueLoader('./layout/layout-sider.vue'),
		'layout-breadcrumb': httpVueLoader('./layout/layout-breadcrumb.vue'),
		'layout-footer': httpVueLoader('./layout/layout-footer.vue')
	},
	methods: {

		headActionSlot() {
			var action = {
				title: '操作',
				slot: 'action',
				width: 200,
				align: 'center'
			};
			return action;

		},
	},
	mounted() {
		checkLogin();
		this.columns.push(headActionSlot());
		getActivities();
	}

});

function getActivities() {
	var url = PROJECT_NAME + '/admin/getAllClubs';
	callAjaxPost(url, "", renderData);
}

function renderData(data) {

	if (data.code == '501') {
		window.location.href = METHOD_URL + PROJECT_NAME + "/club/login.html"
	} else {
		console.log(data.obj);
		app.data = data.obj;
	}


}

function checkLogin(){
	console.log("adminName");
	console.log(sessionStorage.getItem("adminName"));
	if (sessionStorage.getItem("adminName") == null) {
		window.location.href = METHOD_URL + PROJECT_NAME + "/club/login.html"
	} else {
		return;
	}
}

function timestampToTime(timestamp) {
	var date = new Date(timestamp);
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
	var D = date.getDate() + ' ';
	var h = date.getHours() + ':';
	var m = date.getMinutes() + ':';
	var s = date.getSeconds();
	return Y + M + D + h + m + s;
}


