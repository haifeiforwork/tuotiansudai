var require = {
    'baseUrl': staticServer,
    'paths': {
        'jquery': staticServer + '/api/js/libs/jquery-1.11.3.min',
        'csrf': staticServer + '/api/js/libs/csrf',
        'underscore': staticServer + '/api/js/libs/underscore-1.8.3.min',
        'jquery.ajax.extension': staticServer + '/api/js/dest/jquery_ajax_extension.min',
        'commonFun': staticServer + '/api/js/dest/common.min',
        'count_down': staticServer+'/api/js/dest/count_down.min'
    },
    'waitSeconds':0,
    'shim': {
        'jquery.validate': ['jquery'],
        'commonFun': ['jquery.validate']
    },

    config: {
        text: {
            useXhr: function (url, protocol, hostname, port) {
                return true;
            }
        }
    }
};

