var require = {
    'baseUrl': staticServer,
    'paths': {
        'text': staticServer + '/api/js/libs/text-2.0.14',
        'jquery': staticServer + '/api/js/libs/jquery-1.11.3.min',
        'csrf': staticServer + '/api/js/libs/csrf',
        'jqueryPage': staticServer + '/api/js/libs/jquery.page',
        'jquery.validate': staticServer + '/api/js/libs/jquery.validate-1.14.0.min',
        'jquery.form': staticServer + '/api/js/libs/jquery.form-3.51.0.min',
        'autoNumeric': staticServer + '/api/js/libs/autoNumeric',
        'mustache': staticServer + '/api/js/libs/mustache-2.1.3.min',
        'moment': staticServer + '/api/js/libs/moment-2.10.6.min',
        'underscore': staticServer + '/api/js/libs/underscore-1.8.3.min',
        'jquery.ajax.extension': staticServer + '/api/js/dest/jquery_ajax_extension.min',
        'daterangepicker': staticServer + '/api/js/libs/jquery.daterangepicker-0.0.7',
        'pagination': staticServer + '/api/js/dest/pagination.min',
        'lodash': staticServer + '/api/js/libs/lodash.min',
        'layer': staticServer + '/api/js/libs/layer/layer',
        'layer-extend':staticServer+'/api/js/libs/layer/extend/layer.ext',
        'echarts': staticServer + '/api/js/libs/echarts/dist/echarts.min',
        'jquery.validate.extension': staticServer + '/api/js/dest/jquery_validate_extension.min',
        'commonFun': staticServer + '/api/js/dest/common.min',
        'layerWrapper': staticServer + '/api/js/dest/wrapper-layer.min',
        'fullPage':staticServer+'/api/js/libs/jquery.fullPage.min',
        'swiper':staticServer+'/api/js/libs/swiper-3.2.7.jquery.min',
        'load-swiper':staticServer+'/api/js/dest/load_swiper.min',
        'coupon-alert': staticServer+'/api/js/dest/coupon_alert.min',
        'cnzz-statistics': staticServer+'/api/js/dest/cnzz_statistics.min',
        'red-envelope-float': staticServer+'/api/js/dest/red-envelope-float.min',
        'drag': staticServer+'/api/js/libs/drag',
        'rotate': staticServer+'/api/js/libs/jqueryrotate.min',
        'template':staticServer+'/api/js/libs/template.min',
        'fancybox':staticServer+'/api/js/libs/jquery.fancybox.min',
        'count_down': staticServer+'/api/js/dest/count_down.min',
    },
    'waitSeconds':0,
    'shim': {
        'jquery.validate': ['jquery'],
        'jquery.form': ['jquery'],
        'jqueryPage': ['jquery'],
        'autoNumeric': ['jquery'],
        'pagination': ['jquery'],
        'layer': ['jquery'],
        'layer-extend': ['jquery','layer'],
        'layerWrapper':['layer','layer-extend'],
        'commonFun': ['jquery.validate'],
        'jquery.validate.extension': ['jquery', 'jquery.validate'],
        'fullPage': ['jquery'],
        'swiper':['jquery'],
        'load-swiper':['swiper'],
        'drag':['jquery'],
        'rotate':['jquery'],
        'fancybox':['jquery']
    },

    config: {
        text: {
            useXhr: function (url, protocol, hostname, port) {
                return true;
            }
        }
    }
};

