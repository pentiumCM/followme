module.exports = {
    data: function () {
        return {
            firstPath: '/account/menu',
            menu: {
                id: '', url: '', path: '', name: '',
                parentId: '', sort: '', isEnable: ''
            },// 菜单类
            menuList: [],// 菜单列表
            activeName: '',// 激活菜单的 name 值
            openMenu: [],// 展开的 Submenu 的 name 集合
            realTime: '',
            html: '',

        }
    },
    mounted() {
        // console.log("I am the sider");
        // 查询菜单
		// 获取主机地址之后的目录，设置为激活的菜单
		console.log(window.document.location.pathname);
		this.activeName = window.document.location.pathname;
		
        // 以'/'分隔从后截取，设置为展开的父菜单
        this.openMenu.push(this.activeName.substring(0, this.activeName.lastIndexOf('/')));
    },
    methods: {


        /**
         * 选择菜单（MenuItem）时触发
         * @param name MenuItem的name属性值
         */
        onSelectMenu(name) {
			console.log("页面跳转:" + METHOD_URL+name);
            window.location.href = METHOD_URL+name;
        },
    },
};