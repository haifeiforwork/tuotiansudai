var require = {
    'baseUrl': staticServer,
    'paths': {
        'text': staticServer + '/pointsystem/js/libs/text-2.0.14',
        'jquery': staticServer + '/pointsystem/js/libs/jquery-1.11.3.min',
        'csrf': staticServer + '/pointsystem/js/libs/csrf',
        'jqueryPage': staticServer + '/pointsystem/js/libs/jquery.page',
        'jquery.validate': staticServer + '/pointsystem/js/libs/jquery.validate-1.14.0.min',
        'jquery.form': staticServer + '/pointsystem/js/libs/jquery.form-3.51.0.min',
        'autoNumeric': staticServer + '/pointsystem/js/libs/autoNumeric',
        'mustache': staticServer + '/pointsystem/js/libs/mustache-2.1.3.min',
        'moment': staticServer + '/pointsystem/js/libs/moment-2.10.6.min',
        'underscore': staticServer + '/pointsystem/js/libs/underscore-1.8.3.min',
        'jquery.ajax.extension': staticServer + '/pointsystem/js/dest/jquery_ajax_extension.min',
        'daterangepicker': staticServer + '/pointsystem/js/libs/jquery.daterangepicker-0.0.7',
        'pagination': staticServer + '/pointsystem/js/dest/pagination.min',
        'lodash': staticServer + '/pointsystem/js/libs/lodash.min',
        'layer': staticServer + '/pointsystem/js/libs/layer/layer',
        'layer-extend':staticServer+'/pointsystem/js/libs/layer/extend/layer.ext',
        'echarts': staticServer + '/pointsystem/js/libs/echarts/dist/echarts.min',
        'jquery.validate.extension': staticServer + '/pointsystem/js/dest/jquery_validate_extension.min',
        'commonFun': staticServer + '/pointsystem/js/dest/common.min',
        'layerWrapper': staticServer + '/pointsystem/js/dest/wrapper-layer.min',
        'fullPage':staticServer+'/pointsystem/js/libs/jquery.fullPage.min',
        'swiper':staticServer+'/pointsystem/js/libs/swiper-3.2.7.jquery.min',
        'load-swiper':staticServer+'/pointsystem/js/dest/load_swiper.min',
        'coupon-alert': staticServer+'/pointsystem/js/dest/coupon_alert.min',
        'cnzz-statistics': staticServer+'/pointsystem/js/dest/cnzz_statistics.min',
        'red-envelope-float': staticServer+'/pointsystem/js/dest/red-envelope-float.min',
        'drag': staticServer+'/pointsystem/js/libs/drag',
        'rotate': staticServer+'/pointsystem/js/libs/jqueryrotate.min',
        'template':staticServer+'/pointsystem/js/libs/template.min',
        'fancybox':staticServer+'/pointsystem/js/libs/jquery.fancybox.min',
        'count_down': staticServer+'/pointsystem/js/dest/count_down.min',
        'placeholder': staticServer + '/pointsystem/js/libs/jquery.enplaceholder',
        'superslide': staticServer + '/pointsystem/js/libs/jquery.SuperSlide.2.1.1'
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
        'fancybox':['jquery'],
        'placeholder': ['jquery'],
        'superslide': ['jquery']
    },

    config: {
        text: {
            useXhr: function (url, protocol, hostname, port) {
                return true;
            }
        }
    }
};

