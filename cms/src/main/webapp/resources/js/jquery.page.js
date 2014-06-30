 /*
 * Title: jquery.page.js. 
 * Description: 分页按钮插件
 * Aucthor:chengjia
 * Create Date:2011-01-15
 * Call Method:
 */
jQuery.fn.page = function(options) {
	var settings = {
		url :'',
		param:'',
		objId:'',
		callback:false
	};
	var opts = jQuery.extend(settings, options);
	if (opts.url == "")
		alert("你必须提供url参数。");
	$(this).find(".pageButton").click(function(){
		var pageflag = $(this).attr("pageFlag");
	    var pageNum = $("#pageNum").val();
		if(opts.url.indexOf("pager.pageNum") == -1){//防止点击上一页或者下一页过快传递重复参数
	    	opts.url += "&pager.pageNum="+pageNum;
	    }
	    if(opts.url.indexOf("pager.pageFlag") == -1){
	    	opts.url += "&pager.pageFlag="+pageflag;
	    }
	    if(opts.url.indexOf("pager.pageSizeSel") == -1){
	    	opts.url +="&pager.pageSizeSel="+$("#pageSizeSel").val();
	    }
        $.post( opts.url,
                opts.param,
                function (data) {
                    $("#" + opts.objId).html(data);
                    if (opts.callback) opts.callback();
                },
                "html"
        );
	});
	//输入分页数跳页
	$(this).find("#jumpPage").click(function(){
		opts.url +="&pager.pageNum="+$("#jump").val();
		opts.url +="&pager.pageFlag=G";
        $.post(opts.url,
                opts.param,
                function (data) {
                    $("#" + opts.objId).html(data);
                    if (opts.callback) opts.callback();
                },
                "html"  
        );
	});
	
	//输入框回车事件
	$("#jump").keydown(function (ev) {
		var event= ev||event;
		if (event.keyCode == "13") {//keyCode=13回车键
			opts.url +="&pager.pageNum="+$(this).val();
			opts.url +="&pager.pageFlag=G";
	        $.post(opts.url,
	                opts.param,
	                function (data) {
	                    $("#" + opts.objId).html(data);
	                    if (opts.callback) opts.callback();
	                },
	                "html"  
	        );
		}
		
	 });

	//点击分页数跳页
	$(this).find(".num").click(function(){
		var num = $(this).html();
		if(!regJump(num)){
			num = 1;
		}
		if(opts.url.indexOf("pager.pageNum") == -1){
			opts.url +="&pager.pageNum="+num;
	    }
		if(opts.url.indexOf("pager.pageFlag") == -1){
			opts.url +="&pager.pageFlag=G";
	    }
        $.post(opts.url,
        	    opts.param,
                function (data) {
                    $("#" + opts.objId).html(data);
                    if (opts.callback) opts.callback();
                },
                "html"
        );
	});
	//分页输入数校验
	function regJump(num){
		var patrn=/^[0-9]+$/; 
		if (!patrn.exec(num)){
		   return false;
		   }else{
		   return true;
		}
	}
}