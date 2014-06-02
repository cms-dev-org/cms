(function($) {
	$.fn.mymenutree = function(opt) {
		var settings = $.extend({
			leafNodeTagName		: 'li',
			selectedCls			: 'on',
			frameMainEl			: 'rightMain'
		}, opt || {});
		
		$(this).find(settings.leafNodeTagName).css('cursor', 'pointer');
		
		$(this).find(settings.leafNodeTagName).click(function(e) {
			$(this).parent().find(settings.leafNodeTagName).removeClass(settings.selectedCls);
			$(this).addClass(settings.selectedCls);
			
			$('#rightMain').attr('src', $(this).attr('src'));
		});
		
	}
})(jQuery)