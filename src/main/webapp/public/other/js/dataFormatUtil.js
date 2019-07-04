/**
 * 前端验证数据格式类型
 */

/**
 * 判断数据是否为空
 * @param value 需要验证的数据
 * @param msg 错误提示信息
 * @returns {boolean}
 */
function checkEmpty(value, msg) {
    if (value === null || value === "" || value.trim().length === 0) {
        messageWarning(G_vCall, msg);
        return true;
    } else {
        return false;
    }
}

/**
 * 判断数据长度是否超过自定义限值
 * @param value 需要验证的数据
 * @param length 自定义限值
 * @param msg 错误提示信息
 * @returns {boolean}
 */
function checkLength(value, length, msg) {
    if (value.length > length) {
        messageWarning(G_vCall, msg);
        return true;
    } else {
        return false;
    }
}
