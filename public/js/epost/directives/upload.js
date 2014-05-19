angular.module('uploadjs', []).directive('upload', function (uploadManager, $rootScope, $cookieStore) {
    return {
        restrict: 'A',
        link: function ($scope, element, attrs) {
            $(element).fileupload({
            	singleFileUploads : false,
            	replaceFileInput : false,
            	formData: function(e, data) {
            		return $('#fileupload').serializeArray();
            	},
                headers: {'x-epost-access-token':'' + $rootScope.apikey.access_token, 'PLATTFORM':$cookieStore.get('plattform').name},
                dataType: 'text',
                add: function (e, data) {
                    uploadManager.add(data);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    uploadManager.setProgress(progress);
                },
                done: function (e, data) {
                    uploadManager.setProgress(0);
                    uploadManager.done(data.result)
                },
                handleBlob : true
            });
        }
    };
}) .directive('preview', function () {
            return {
                controller:  function ($scope, $element, $attrs, $parse) {
                var fn = $parse($attrs.preview),
                    file = fn($scope);
                if (file.preview) {
                    $element.append(file.preview);
                }
            }
            };
        })

