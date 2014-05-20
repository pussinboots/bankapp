angular.module('DatePicker', []).directive('datepicker', function() {
	return function(scope, element, attrs) {
		element.datepicker({
			changeYear : true,
			changeMonth : true,
			appendText : '(yyyy-mm-dd)',
			dateFormat : 'yy-mm-dd',
			onSelect : function(dateText) {
				var mdlAttr = $(this).attr('ng-model');
				scope[mdlAttr] = dateText;
				var chAttr = $(this).attr('ng-change');
				scope.update();
				scope.$apply();
			}
		});
	}
});