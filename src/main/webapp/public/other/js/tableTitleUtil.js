/**
 * 表头多选，使用unshift()头部添加
 * @return {{type: string, width: number, align: string}}
 */
function headSelection() {
    // 多选
    var selection = {
        type: 'selection',
        width: 60,
        align: 'center'
    };
    return selection;
}

/**
 * 加载表头
 * @param key 键-数据库表字段名称
 * @param title 值-表格显示名称
 * @returns {Array} 表头集合
 */
function showCol(key, title) {
    // 初始化表头数据
    var col = new Array();
    for (var i = 0; i < title.length; i++) {
        var json = {
            key: key[i],
            title: title[i],
            // align: 'center'
        };
        col.push(json);
    }
    return col;
}

/**
 * 加载slot-scope表头 需要手动添加template
 * @param key 键-数据库表字段名称
 * @param title 值-表格显示名称
 * @returns {Array} 表头集合
 */
function showColSlot(key, title) {
    // 初始化表头数据
    var col = new Array();
    for (var i = 0; i < title.length; i++) {
        var json = {
            slot: key[i],
            title: title[i],
            // align: 'center'
        };
        col.push(json);
    }
    return col;
}


/**
 * 自定义表头列模板，通过push() 结尾添加
 * @param is_dtl 是否显示详情按钮
 * @param dtlFunc 详情展示方法，若无写null
 * @param is_edit 是否显示编辑按钮
 * @param editFunc 编辑方法，若无写null
 * @param is_del 是否显示删除按钮
 * @param delFunc 删除方法，若无写null
 * @returns 自定义列模板
 */
function headAction(is_dtl, dtlFunc, is_edit, editFunc, is_del, delFunc) {
    // 自定义操作栏宽度
    var actionWidth = 200;
    // 初始化表头action
    var action = {
        title: '操作',
        key: 'action',
        width: actionWidth,
        align: 'center',
        render: (h, params) => {
            let btns = [];
            if (is_dtl) {
                btns.push(
                    h('Button', {
                        props: {
                            type: 'success',
                            size: 'small',
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                dtlFunc(params.index)
                            }
                        }
                    }, '详情')
                )
            }
            if (is_edit) {
                btns.push(
                    h('Button', {
                        props: {
                            type: 'primary',
                            size: 'small',
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                editFunc(params.index)
                            }
                        }
                    }, '修改')
                )
            }
            if (is_del) {
                btns.push(h('Poptip', {
                        props: {
                            confirm: true,
                            type: 'error',
                            size: 'large',
                            title: '您确定要删除吗?'
                        },
                        on: {
                            'on-ok': () => {
                                delFunc(params.index);
                            }
                        }
                    }, [h('Button', {
                        props: {
                            type: 'warning',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                    }, '删除')
                    ])
                )
            }
            return h("div", btns)
        }
    };
    return action;
}

/**
 * 加载slot-scope操作栏
 * @return 操作栏
 */
function headActionSlot() {
    var action = {
        title: '操作',
        slot: 'action',
        width: 200,
        align: 'center'
    };
    return action;
}


// 自定义action操作(用户具有查看修改权限的数据)
function updateAction(dtl, edt) {
    // 初始化表头action
    var action = {
        title: '操作',
        key: 'action',
        width: 200,
        align: 'center',
        render: (h, params) => {
            let btns = [];
            if (dtl) {
                btns.push(
                    h('Button', {
                        props: {
                            type: 'success',
                            size: 'small',
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                detail(params.index)
                            }
                        }
                    }, '详情')
                )
            }
            if (edt) {
                btns.push(
                    h('Button', {
                        props: {
                            type: 'primary',
                            size: 'small',
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                edit(params.index)
                            }
                        }
                    }, '修改')
                )
            }
            if (g_edt) {
                btns.push(h('Poptip', {
                        props: {
                            confirm: true,
                            type: 'error',
                            size: 'large',
                            title: '你确定要删除吗?'
                        },

                        on: {
                            'on-ok': () => {
                                remove(params.index);
                            }
                        }
                    }, [h('Button', {
                        props: {
                            type: 'warning',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                    }, '删除')
                    ])
                )
            }
            return h("div", btns)
        }
    };
    return action;
}
