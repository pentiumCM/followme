/**
 * 方法请求路径:主机地址+项目名
 */
const METHOD_URL = 'http://127.0.0.1:8080';

/**
 * 方法请求后缀
 */
const METHOD_SUFFIX = '';

/**
 * 项目名
 */
const PROJECT_NAME = '/followme';

// vue/iview相关/layout相关
document.write("<script type='text/javascript' src='" + PROJECT_NAME + "/public/jquery/jQuery-2.1.4.min.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/vue/vue-2.6.10.min.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/vue/http-vue-loader.js'></script>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/public/iview-3.3.3/css/iview.css'>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/iview-3.3.3/iview.min.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/jitsoseVue.js'></script>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/public/other/css/vue.css'>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/layout/css/layout.css'>"
);

// 引入element-ui组件库
document.write("<link rel='stylesheet' href='" + PROJECT_NAME + "/public/element-ui-2.10.0/lib/theme-chalk/index.css'>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/element-ui-2.10.0/lib/index.js'></script>"
);

//document.write("<link rel='stylesheet' href='https://unpkg.com/element-ui/lib/theme-chalk/index.css'>"
//    + "<script async src='https://unpkg.com/element-ui/lib/index.js'></script>"
//);

// 自定义
document.write("<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/callBackAjax.js'>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/jitsoseUtil.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/tableTitleUtil.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/dataFormatUtil.js'></script>"
    + "<script type='text/javascript' src='" + PROJECT_NAME + "/public/other/js/verifyConstant.js'></script>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/public/other/css/layout.css'>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/public/other/css/body.css'>"
    + "<link rel='stylesheet' type='text/css' href='" + PROJECT_NAME + "/public/other/css/pop.css'>"
);

// 解决页面favicon.ico 404错误
document.write("<link rel='bookmark' href='" + PROJECT_NAME + "/favicon.ico' type='image/x-icon'>");

