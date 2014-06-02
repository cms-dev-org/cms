(function($) {
	/**
	 * 确认提示（用于删除等）
	 */
	$.fn.confirmOperator = function(opts) {
		var settings = $.extend({
			msg			: '该操作不可逆，确定执行该操作吗？',
			eventName	: 'click'
		}, opts || {});
		
		$(this).on(settings.eventName, function(event) {
			if(!confirm(settings.msg)) {
				event.preventDefault();
			}
		})
	}
})(jQuery)