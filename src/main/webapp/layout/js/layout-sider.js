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
        this.selectMenuList();
        // 获取主机地址之后的目录，设置为激活的菜单
        this.activeName = window.document.location.pathname;
        // 以'/'分隔从后截取，设置为展开的父菜单
        this.openMenu.push(this.activeName.substring(0, this.activeName.lastIndexOf('/')));
    },
    methods: {
        /**
         * 查询菜单列表
         */
        selectMenuList() {
            let url = this.firstPath + '/selectMenuList';
            callAjaxGetNoParam(url, this.selectMenuListSuc);
        },

        /**
         * 查询菜单列表回调函数
         */
        selectMenuListSuc(data) {
            this.menuList = data.obj;
            // 动态设置menu组件，设置active-name和open-names时，需要手动更新，并给menu组件绑定ref
            this.$nextTick(() => {
                // 手动更新展开的子目录，注意要在 $nextTick 里调用
                this.$refs.sider_menu.updateOpened();
                // 手动更新当前选择项，注意要在 $nextTick 里调用
                this.$refs.sider_menu.updateActiveName();
            });
            console.log("默认打开父菜单:" + this.openMenu);
            console.log("激活的菜单:" + this.activeName);
        },

        /**
         * 选择菜单（MenuItem）时触发
         * @param name MenuItem的name属性值
         */
        onSelectMenu(name) {
            console.log("页面跳转:" + name);
            window.location.href = name;
        },
    },
};