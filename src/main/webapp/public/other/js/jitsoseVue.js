/**
 * vue/iview相关js方法
 */

/**
 * 更改表格行样式
 * @param row
 * @param index 当前行索引
 * @returns  样式名称
 */
function rowClassName(row, index) {
    if (index % 2 === 1) {
        return 'myRowClassName';
    }
}


/**
 * Message 全局提示配置,使用提醒时先配置此方法
 * @param vue   vue实例
 * top   提示组件距离顶端的距离，单位像素,如：50，不可覆盖
 * duration  默认自动关闭的延时，单位秒，如：3，不关闭写0，可被覆盖
 */
function messageConfig(vue) {
    vue.$Message.config({
        top: 40,
        duration: 3,
    });
}

/**
 * Message 成功提示
 * @param vue   vue实例
 * @param content   提示内容
 */
function messageSuccess(vue, content) {
    vue.$Message.success({
        content: content,
    });
}

/**
 * Message 普通提示
 * @param vue   vue实例
 * @param content   提示内容<br>
 *  duration  默认自动关闭的延时，单位秒，如：3，不关闭写0
 */
function messageInfo(vue, content) {
    vue.$Message.info({
        content: content,
        duration: 6
    });
}

/**
 * Message 警告提示
 * @param vue   vue实例
 * @param content   提示内容
 * duration  默认自动关闭的延时，单位秒，如：3
 * closable  是否显示关闭按钮，默认false
 */
function messageWarning(vue, content) {
    vue.$Message.warning({
        content: content,
        duration: 10,
        closable: true
    });
}

/**
 * Message 错误提示
 * @param vue   vue实例
 * @param content   提示内容
 *  duration  默认自动关闭的延时，单位秒，如：3
 *  closable  是否显示关闭按钮，默认false
 */
function messageError(vue, content) {
    vue.$Message.error({
        content: content,
        duration: 10,
        closable: true
    });
}

/**
 * Message 加载提示
 * @param vue   vue实例
 * @param content   提示内容
 * @return 提示对象
 */
function messageLoading(vue) {
    const msg = vue.$Message.loading({
        content: '正在加载中...',
        duration: 0
    });
    return msg;
}

/**
 * Message 关闭提示
 * @param msg 提示对象
 */
function closeMessageLoading(msg) {
    setTimeout(msg, 0);
}


/**
 * Notice 全局通知提醒配置,使用提醒时先配置此方法
 * @param vue   vue实例
 *  top   提示组件距离顶端的距离，单位像素,如：50
 *  duration  默认自动关闭的延时，单位秒，如：3
 */
function noticeConfig(vue) {
    vue.$Notice.config({
        top: 50,
        duration: 5,
    });
}

/**
 * Notice 全局关闭某个通知
 * @param vue vue实例
 * @param name 当前通知的唯一标识
 */
function closeNotice(vue, name) {
    console.log(name);
    vue.$Notice.close(name);
}

/**
 * Notice 全局销毁
 * @param vue vue实例
 */
function destroyNotice(vue) {
    vue.$Notice.destroy()
}

/**
 * Notice 打开提醒
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 */
function noticeOpen(vue, title, desc) {
    vue.$Notice.open({
        title: title,
        desc: desc === (null || '') ? null : desc,
    });
}

/**
 * Notice 打开提醒,需手动关闭
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 * duration 默认自动关闭的延时，单位秒，如：3,为 0 则不自动关闭。
 */
function noticeOpenEver(vue, title, desc) {
    vue.$Notice.open({
        title: title,
        desc: desc === (null || '') ? null : desc,
        duration: 0
    });
    return vue.$Notice.name;
}

/**
 * Notice 打开消息提醒
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 */
function noticeInfo(vue, title, desc) {
    vue.$Notice.info({
        title: title,
        desc: desc === (null || '') ? null : desc,
    });
}

/**
 * Notice 打开成功提醒
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 */
function noticeSuccess(vue, title, desc) {
    vue.$Notice.success({
        title: title,
        desc: desc === (null || '') ? null : desc,
    });
}

/**
 * Notice 打开警告提醒
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 */
function noticeWarning(vue, title, desc) {
    vue.$Notice.warning({
        title: title,
        desc: desc === (null || '') ? null : desc,
    });
}

/**
 * Notice 打开错误提醒
 * @param vue vue实例
 * @param title 通知提醒的标题
 * @param desc 通知提醒的内容，为空或不填时，自动应用仅标题模式下的样式
 */
function noticeError(vue, title, desc) {
    vue.$Notice.error({
        title: title,
        desc: desc === (null || '') ? null : desc,
    });
}











