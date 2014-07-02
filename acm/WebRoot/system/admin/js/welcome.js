/**
 * 欢迎页面
 */
Ext.onReady(function() {
			new Ext.ux.TipWindow({
						title : '<span class=commoncss>提示</span>',
						html : '您有[0]条未读信息',
						iconCls : 'commentsIcon'
					}).show(Ext.getBody());
		});