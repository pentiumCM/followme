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
					title: '活动标题',
					align: 'center',
					key: 'actTitle'
				},
				{
					title: '开始时间',
					align: 'center',
					key: 'beginTime'
				},
				{
					title: '开始城市',
					align: 'center',
					key: 'beginCity'
				},
				{
					title: '活动介绍',
					align: 'center',
					key: 'introduction'
				},
				{
					title: '已报名人数',
					align: 'center',
					key: 'actCurPerson'
				}
			],
			data: [
			]
		}
	},
	components: {
		'layout-header': httpVueLoader('../layout/layout-header.vue'),
		'layout-sider': httpVueLoader('../layout/layout-sider.vue'),
		'layout-breadcrumb': httpVueLoader('../layout/layout-breadcrumb.vue'),
		'layout-footer': httpVueLoader('../layout/layout-footer.vue')
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
	var url = PROJECT_NAME + '/club/queryClubActByClubID'
	callAjaxPost(url, "", renderData);
}

function renderData(data) {

	if (data.code == '501') {
		window.location.href = METHOD_URL + PROJECT_NAME + "/club/login.html"
	} else {
		for (let index = 0; index < data.obj.length; index++) {
			data.obj[index].beginTime = timestampToTime(data.obj[index].beginTime);

		}
		app.data = data.obj;
	}


}

function checkLogin(){
	if (sessionStorage.getItem("clubName") == null) {
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


