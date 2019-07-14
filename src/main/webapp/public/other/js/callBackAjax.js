

/**
 * Ajax get请求函数
 *
 * @param url
 *            请求链接
 * @param funcSuc
 *            请求成功的回调函数
 * @constructor
 */
function callAjaxGetNoParam(url, funcSuc) {
    console.log('请求路径：' + METHOD_URL + url + METHOD_SUFFIX);
    $.ajax({
        url: METHOD_URL + url + METHOD_SUFFIX,
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        method: "get",
        dataType: "json",
        success: function (data) {
            sucClass(data, funcSuc);
        },
        error: function (data) {
            console.log('Ajax请求错误！');
            console.log(data);
            errorClass(data);
        }
    })
}

/**
 * Ajax 无参无回调函数get请求函数
 *
 * @param url
 *            请求链接
 * @constructor
 */
function callAjaxGetNoParamsAndFun(url) {
    console.log('请求路径：' + METHOD_URL + url + METHOD_SUFFIX);
    $.ajax({
        url: METHOD_URL + url + METHOD_SUFFIX,
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        method: "get",
        error: function (data) {
            console.log('Ajax请求错误！');
            console.log(data);
            errorClass(data);
        }
    })
}

/**
 * Ajax post请求函数
 *
 * @param url
 *            请求链接
 * @param params
 *            请求参数
 * @param funcSuc
 *            请求成功的回调函数
 * @constructor
 */
function callAjaxPost(url, params, funcSuc) {
    console.log('请求路径：' + METHOD_URL + url + METHOD_SUFFIX);
    $.ajax({
        url: METHOD_URL + url + METHOD_SUFFIX,
        type: "post",
        async: true,
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        data: JSON.stringify(params),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            sucClass(data, funcSuc);
        },
        error: function (data) {
            console.log('Ajax请求错误！');
            console.log(data);
            // noticeError(this, 'Ajax请求错误！', data);
            errorClass(data);
        }
    })
}

/**
 * 表单提交方式，暂不使用
 * @param url
 * @param params
 * @param funcSuc
 */
// function callAjaxPostForm(url, params, funcSuc) {
//     console.log('请求路径：' + METHOD_URL + url + METHOD_SUFFIX);
//     $.ajax({
//         async: true,
//         url: METHOD_URL + url + METHOD_SUFFIX,
//         type: 'POST',
//         contentType: 'application/x-www-form-urlencoded; charset=utf-8',
//         data: params,
//         dataType: 'json',
//         success: function (data) {
//             console.log(data);
//             if (data.info === 'error') {
//                 console.log('系统后台错误！');
//                 noticeError(G_vCall, '系统后台错误！', data.errorMsg);
//                 // errorClass(data);
//                 return;
//             }
//             //调用成功
//             funcSuc(data)
//         },
//         error: function (data) {
//             console.log('Ajax请求错误！');
//             console.log(data);
//             // noticeError(this, 'Ajax请求错误！', data);
//             errorClass(data);
//         }
//     });
// }


/**
 * ajax请求成功调用函数
 * @param data 返回参数
 * @param funcSuc 成功回调函数
 */
function sucClass(data, funcSuc) {
    console.log(data);
    if (data.code === 500) {
        console.log('系统后台错误！');
        noticeError(G_vCall, '系统后台错误！', data.msg);
        // errorClass(data.code);
        return;
    }
    //调用成功
    funcSuc(data)
}

function errorClass(code) {
    switch (code) {
        case 403:
            // parent.location.href = "error/403.html";
            break;
        case 404:
            // parent.location.href = "/error/404.html";
            break;
        case 500:
            // parent.location.href = "/error/500.html";
            break;
        default:
            break;
    }
}