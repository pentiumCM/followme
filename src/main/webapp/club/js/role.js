var vRole = new Vue({
    el: '#role',
    data: function () {
        return {
        	firstPath: '/account/role',// 请求一级路径
            nowData: [], column: [], loading: true, selection: [],// 表格参数
            totalNum: 0, pageNum: 1, pageSize:10,  // 分页参数
            loadingMsg: '',// 加载提示
            notice: '',// 提醒对象
            role: {
                id: '', key: '', name: '', description: '', codeName: '',enable:''
            },// 实体类
            sRole: {
            	key: '', name: ''		
            },// 搜索信息
            roleTemp: '',// 修改临时存放信息
            addRoleModal: false,// 新增角色信息模态框
            editRoleModal: false,// 编辑角色信息模态框
            removeRoleModal: false,// 删除角色信息模态框
        }
    },
    components: {
        'layout-header': httpVueLoader('../layout/layout-header.vue'),
        'layout-sider': httpVueLoader('../layout/layout-sider.vue'),
        'layout-breadcrumb': httpVueLoader('../layout/layout-breadcrumb.vue'),
        'layout-footer': httpVueLoader('../layout/layout-footer.vue')
    },
    mounted() {
        messageConfig(this);// 全局提示配置
        noticeConfig(this);// 全局通知提醒配置
        this.initPage();
        this.filter();
    },
    methods: {
        /**
         * 页面初始化加载项
         * 表格表头
         */
        initPage() {
            let data = {tableName: "role"};
            let url ='/tableTitle/listByTableName';
            callAjaxPost(url, data, this.getTableHeadSuc);
        },
        /**
         * 获取表头回调函数
         * @param data  请求返回参数
         */
        getTableHeadSuc(data) {
            // 生成表头
            this.column = showCol(data.obj.key, data.obj.title);
            //添加“备注”所在列信息
            this.column.push(description());
            // 添加自定义slot-scope
            this.column.push(headActionSlot());       
            // 添加多选
            this.column.unshift(headSelection());          
        },

        /**
         * 表格过滤查询
         */
        filter() {
        	if (checkLength(this.sRole.key, '64', '角色key不能超过64个字符') ||
                    checkLength(this.sRole.name, '64', '角色名称不能超过64个字符')) {
                    return;
                }
            //console.log('当前页：' + this.pageNum);
            //console.log('页面大小：' + this.pageSize);
            let data = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                key: this.sRole.key,
                name:this.sRole.name,
                enable:this.sRole.enable              
            };
            let url =this.firstPath + '/filter';
            callAjaxPost(url, data, this.filterSuc);
            // 显示加载
            this.loading = true;
        },
        /**
         * 表格过滤查询回调函数
         * @param data 请求返回参数
         */
        filterSuc(data) {
            //取消显示加载
        	 this.loading = false;
             this.nowData = data.obj.list;
             this.totalNum = data.obj.total;
             // 再次设置当前页码,若查询记录为空，设为第一页
             this.pageNum = data.obj.pageNum === 0 ? 1 : data.obj.pageNum;
         },

        /**
         * 清除搜索条件
         */
        clearSRole() {
            this.sRole.key = '';
            this.sRole.name = '';
        },

        /**
         * 改变页码
         * @param pageNum 改变后的页码
         */
        onPageChange(pageNum) {
            this.pageNum = pageNum;
            this.filter();
        },
        /**
         * 切换每页条数
         * @param pageSize 换后的每页条数
         */
        onPageSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.filter();
        },

        /**
         * 新增角色信息
         */
        addRole(){
        	 // 检查数据格式
            if (checkEmpty(this.role.key, '角色key不能为空') ||
                checkLength(this.role.key, '64', '角色key不能超过64个字符') ||
                checkEmpty(this.role.name, '角色名称不能为空') ||
                checkLength(this.role.name, '64', '角色名称不能超过64个字符')) {
                return;
            }
            // 发送请求
            let data = {
                key: this.role.key,
                name: this.role.name,
                description: this.role.description,
             
            };
            let url = this.firstPath + '/insert';
            callAjaxPost(url, data, this.addRoleSuc);
            // 打开加载提示
            this.loadingMsg = messageLoading(this);
        	
        }, 
               
        /**
         * 新增角色信息回调函数
         * @param data 请求返回参数
         */
        addRoleSuc(data) {
            // 关闭加载提示
            closeMessageLoading(this.loadingMsg);
            switch (data.code) {
            case 200:
                // 关闭模态框
                this.addRoleModal = false;
                messageSuccess(this, "新增角色信息成功");
                // 重新查询数据
                this.filter();
                // 清除角色信息
                this.clearRole();
                break;
            case 420:
                messageError(this, data.msg);
                break;
            default:
                break;
        }
    },
        /**
         * 取消新增角色
         */
        cancelAddRole() {
            // 关闭模态框
            this.addRoleModal = false;
            // 清除角色信息
            this.clearRole();
        },

        /**
         * 打开编辑角色信息模态框
         * @param index 当前数据索引
         */
        openEditRoleModal(index) {
            //console.log(this.nowData[index]);
            this.role.id = this.nowData[index].id;
            this.role.key = this.nowData[index].key;
            this.role.name = this.nowData[index].name;
            this.role.description = this.nowData[index].description;
//按钮状态           
//           this.role.enable=this.nowData[index].enable;
           this.role.enable=this.nowData[index].enableString;
           
           //console.log(this.role.enable);
            // 打开模态框
            this.editRoleModal = true;
        },
        /**
         * 修改角色信息
         */
        editRole() {

        	 if (checkEmpty(this.role.key, '角色key不能为空') ||
                     checkLength(this.role.key, '64', '角色key不能超过64个字符') ||
                     checkEmpty(this.role.name, '角色名称不能为空') ||
                     checkLength(this.role.name, '64', '角色名称不能超过64个字符')) {
                     return;
                 }
        	 //console.log(this.role);
        	 if(this.role.enable === '启用'){
        		 this.role.enable = true;
        	 }else {
        		 this.role.enable = false;
        	 }
        	let data ={
        			id:this.role.id,
        			 key: this.role.key,
                     name:this.role.name,
                     enable:this.role.enable,
                     description:this.role.description
                     
        	}
        	//console.log(data)
             let url = this.firstPath + '/update';
             callAjaxPost(url, data, this.editRoleSuc);
             // 打开加载提示
             this.loadingMsg = messageLoading(this);
        },
        editRoleSuc(data) {
            // 关闭加载提示
            closeMessageLoading(this.loadingMsg);
            switch (data.code) {
            case 200:
                // 关闭模态框
                this.editRoleModal = false;
                messageSuccess(this, "角色信息修改成功");
                // 重新查询数据
                this.filter();
                // 清除角色信息
                this.clearRole();
                break;
            case 420:
                messageError(this, data.msg);
                break;
            default:
                break;
        }
    },
        /**
         * 取消修改角色信息
         */
        cancelEditRole() {
            // 关闭模态框
            this.editRoleModal = false;
            // 清除角色信息
            this.clearRole();
        },
        /**
         * 清除角色信息
         */
        clearRole() {
        	this.role.id = '';
        	this.role.key = '';
        	this.role.name = '';
        	this.role.description = '';

        },

        /**
         * 在多选模式下有效，只要选中项发生变化时就会触发
         * @param selection 已选项数据
         */
        onSelectionChange(selection) {
            this.selection = selection;
            //console.log(this.selection);
        },

        /**
         * 打开禁用角色信息模态框
         */
        openRemoveRoleModal() {
            // 判断当前多选是否勾选
            if (this.selection.length === 0) {
                messageWarning(this, '请先勾选数据，再批量禁用');
                return;
            }
            // 打开模态框
            this.removeRoleModal = true;
        },

        /**
         * 批量禁用数据
         */
        removeRoleSelect() {
            // 关闭模态框
            this.removeRoleModal = false;
            var idList = [];
            for (var i = 0; i < this.selection.length; i++) {
                idList[i] = this.selection[i].id;
            }
            //console.log(idList);
            var data = idList;
            var url =  this.firstPath + '/deleteSelection';
            callAjaxPost(url, data, this.removeRoleSelectSuc);
            // 打开加载提示
            this.loadingMsg = messageLoading(this);
        },

        /**
         * 批量禁用数据成功回调函数
         */
        removeRoleSelectSuc(data) {
            // 关闭加载提示
            closeMessageLoading(this.loadingMsg);
            if (data.info === 'success') {
                messageSuccess(this, '批量禁用成功！');
            } else if (data.info === 'fail') {
                messageError(this, '批量禁用失败！');
            }
            // 清除多选列表
            this.selection = [];
            // 加载表格数据
            this.filter();
        },

        /**
         * 禁用角色信息
         * @param index
         */
        removeRole(index) {
            let data = this.nowData[index].id; 
            let url =this.firstPath + '/delete';
            callAjaxPost(url, data, this.removeRoleSuc);

            // 打开加载提示
            this.loadingMsg = messageLoading(this);
        },

        removeRoleSuc(data) {
            // 关闭加载提示
            closeMessageLoading(this.loadingMsg);
            if (data.info === 'success') {
                messageSuccess(this, '角色信息禁用成功！');
            } else if (data.info === 'fail') {
                messageError(this, '角色信息禁用失败！');
            }
            // 重新查询数据
            this.filter();
        },


    }
    
});
