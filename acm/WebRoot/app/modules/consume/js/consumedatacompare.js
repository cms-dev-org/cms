Ext.onReady(function() {
	
	var grid = new Ext.ux.BaseGrid({
		style		: 'margin:2 2 2 2',
		region		: 'center',
		paging		: true,
		selModel 	: new Ext.grid.RowSelectionModel(),
		height		: 200,
		noRn		: false,
		list_url	: '../app/consumeApproval.do?reqCode=query',
		viewConfig	: {
			forceFit	: false
		},
		tbar		: [{
			text : '新增',
			iconCls : 'page_addIcon',
			handler : function() {
				var flag = Ext.getCmp('mode').getValue();
				if (typeof(flag) != 'undefined') {
					addForm.form.getEl().dom.reset();
				} else {
					clearForm(addForm.getForm());
				}
				Ext.getCmp('mode').setValue('add');
				Ext.getCmp('zh_hardbox').setValue(0);
				Ext.getCmp('zh_softbox').setValue(0);
				Ext.getCmp('add_report_date').setValue(new Date());
				addWin.show();
			}
		}, '-', {
			text : '修改',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editInit();
			}
		}, '-', {
			text	: '删除',
			iconCls : 'page_delIcon',
			handler : function() {
				delItem();
			}
		}, '-', {
			text	: '导出报表',
			iconCls : 'page_excelIcon',
			handler	: function(btn) {
				exportExcel('../app/consumeApproval.do?reqCode=exportExcel');
			}
		}, '->', {
			id			: 'report_date_start',
			xtype		: 'datefield',
			emptyText	: '起始时间',
			editable	: false,
			format		: 'Y.m.d',
			width		: 130,
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						query();
					}
				}
			}
		}, {
			id			: 'report_date_end',
			xtype		: 'datefield',
			emptyText	: '截止时间',
			editable	: false,
			format		: 'Y.m.d',
			width		: 130,
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						query();
					}
				}
			}
		}, {
			id			: 'queryParam',
			xtype		: 'textfield',
			style		: 'margin-left:5px;',
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						query();
					}
				}
			}
		}, {
			text		: '查询',
			style		: 'margin-left:5px;',
			iconCls		: 'previewIcon',
			handler		: function() {
				query();
			}
		}, '-', {
			text		: '重置',
			iconCls		: 'arrow_refreshIcon',
			handler		: function() {
				//grid.getStore().reload();
				Ext.getCmp('queryParam').setValue('');
				Ext.getCmp('report_date_start').setValue('');
				Ext.getCmp('report_date_end').setValue('');
			}
		}],
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true
		}, {
			header    : '客户编号',
			name	  : 'customer_code',
			align	  : 'center',
			width 	  : 120,
			sortable  : true
		}, {
			header    : '客户名称',
			name	  : 'customer_name',
			align	  : 'center',
			width 	  : 125, 
			sortable  : true
		}, {
			header 	  : '客户姓名',
			name	  : 'director',
			align	  : 'center',
			sortable  : true,
			width     : 90
		}, {
			header	  : '经营业态',
			name	  : 'manage_type',
			width	  : 90
		}, {
			header 	  : '访销员',
			name      : 'sale_visitor',
			align	  : 'center',
			sortable  : true,
			width 	  : 80
		}, {
			header 	  : '电访周期',
			name      : 'visit_cycle',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '中华（硬）',
			name      : 'zh_hardbox',
			align	  : 'center',
			sortable  : true,
			width 	  : 90
		}, {
			header 	  : '中华（软）',
			name      : 'zh_softbox',
			align	  : 'center',
			sortable  : true,
			width 	  : 90
		}, {
			header 	  : '上报时间',
			name      : 'report_date',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '备注',
			name      : 'remark',
			align	  : 'center',
			sortable  : true,
			width 	  : 150
		}, {
			header 	  : '单位',
			name      : 'unit',
			align	  : 'center',
			hidden	  : true,
			width 	  : 190
		}]
	});
	new Ext.Viewport({
		layout		: 'border',
		items		: [grid]
	});
	
});








