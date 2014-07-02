Ext.onReady(function() {
	
	var addForm = new Ext.form.FormPanel({
		layout		: 'column',
		bodyStyle	: 'padding:5 5 5 5',
		border		: false,
		defaults	: {
			columnWidth		: .5,
			layout			: 'form',
			defaultType		: 'textfield',
			labelAlign		: 'right',
			labelWidth		: 90,
			border			: false,
			defaults		: {
				width		: 180
			}
		},
		items		: [{
			items		: [{
				name			: 'bar_code',
				fieldLabel		: '条形码',
				labelStyle 		: micolor,
				allowBlank		: false
			}, {
				name			: 'specification_name',
				fieldLabel		: '卷烟规格名称',
				labelStyle 		: micolor,
				allowBlank		: false
			}, {
				name			: 'package_type',
				fieldLabel		: '包装形式'
			}, {
				xtype			: 'numberfield',
				name			: 'wholesale_price',
				fieldLabel		: '统一批发价格'
			}, {
				name			: 'remark',
				fieldLabel		: '备注'
			}]
		}, {
			items		: [{
				name			: 'company',
				fieldLabel		: '企业名称',
				labelStyle 		: micolor,
				allowBlank		: false
			}, {
				name			: 'type',
				fieldLabel		: '烟型'
			}, {
				name			: 'price_level',
				fieldLabel		: '价类'
			}, {
				xtype			: 'numberfield',
				name			: 'retail_price',
				fieldLabel		: '零售指导价格'
			}, {
				id				: 'mode',
				xtype			: 'hidden'
			}, {
				id				: 'id',
				xtype			: 'hidden',
				fieldLabel		: 'ID'
			}]
		}]
	});
	
	var addWin = new Ext.Window({
		title		: '新增卷烟品牌',
		width		: 600,
		closeAction	: 'hide',
		resizable	: false,
		autoScroll	: true,
		layout		: 'fit',
		buttonAlign	: 'right',
		height		: 200,
		items		: [addForm],
		buttons		: [{
			text		: '确定',
			handler		: function(btn) {
				if(!addForm.getForm().isValid()) {
					return;
				}
				var mode = Ext.getCmp('mode').getValue();
				if(mode == 'add') {
					addForm.getForm().submit({
						url			: '../app/brandInfo.do?reqCode=insert',
						method 		: 'POST',
						params		: {},
						success		: function(form, response) {
							grid.getStore().reload();
							addWin.hide();
							addForm.getForm().reset();
						},
						failuer		: function(form, response) {
							
						}
					});
				} else {
					addForm.getForm().submit({
						url			: '../app/brandInfo.do?reqCode=update',
						method 		: 'POST',
						success		: function(form, response) {
							grid.getStore().reload();
							addWin.hide();
							addForm.getForm().reset();
						},
						failuer		: function(form, response) {
							
						}
					});
				}
			}
		}, {
			text		: '取消',
			handler		: function(btn) {
				addWin.hide();
				addForm.getForm().reset();
			}
		}]
	});
	
	var grid = new Ext.ux.BaseGrid({
		style		: 'margin:2 2 2 2',
		region		: 'center',
		paging		: true,
		selModel 	: new Ext.grid.RowSelectionModel(),
		height		: 200,
		noRn		: false,
		list_url	: '../app/brandInfo.do?reqCode=query',
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
		}/*, '-', {
			text	: '导出报表',
			iconCls : 'page_excelIcon',
			handler	: function(btn) {
				exportExcel('../app/brandInfo.do?reqCode=exportExcel');
			}
		}*/, '->', {
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
		}],
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true
		}, {
			header    : '条形码',
			name	  : 'bar_code',
			align	  : 'center',
			width 	  : 140,
			sortable  : true
		}, {
			header    : '企业名称',
			name	  : 'company',
			align	  : 'center',
			width 	  : 160, 
			sortable  : true
		}, {
			header 	  : '卷烟规格名称',
			name	  : 'specification_name',
			align	  : 'center',
			sortable  : true,
			width     : 160
		}, {
			header	  : '烟型',
			name	  : 'type',
			width	  : 100
		}, {
			header 	  : '包装形式',
			name      : 'package_type',
			align	  : 'center',
			sortable  : true,
			width 	  : 120
		}, {
			header 	  : '价类',
			name      : 'price_level',
			align	  : 'center',
			sortable  : true,
			width 	  : 120
		}, {
			header 	  : '统一批发价格',
			name      : 'wholesale_price',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '零售指导价格',
			name      : 'retail_price',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '备注',
			name      : 'remark',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}]
	});
	
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	
	grid.getStore().reload({
		params 	: {
			firstLoad	: '1',
			start 		: 0,
			limit 		: grid.getBottomToolbar().pageSize
		}
	});
	new Ext.Viewport({
		layout		: 'border',
		items		: [grid]
	});
	
	function query() {
		var paramValue = Ext.getCmp('queryParam').getValue();
		grid.getStore().baseParams = {
			queryParam			: paramValue
		};
		grid.getStore().reload({
			params		: {
				start	: 0,
				limit	: grid.getBottomToolbar().pageSize
			}
		});
	}
	
	function editInit() {
		var record = grid.getSelectionModel().getSelections();
		if(!record || record.length > 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');	
			return;
		}
		Ext.getCmp('id').setValue(record[0].id);
		Ext.getCmp('mode').setValue('update');
		addForm.getForm().loadRecord(record[0]);
		addWin.show();
	}
	
	//删除项目记录
	function delItem() {
		var rows = grid.getSelectionModel().getSelections();
		if(rows.length < 1) {Ext.Msg.alert('提示', '请至少选择一条数据');return;}; 
		var ids = jsArray2JsString(rows, 'id');
		Ext.Msg.confirm('请确认', '确认删除选中项？', function(btn, text) {
			if (btn == 'yes') {
				showWaitMsg();
				Ext.Ajax.request({
					url 	: '../app/brandInfo.do?reqCode=delete',
					params 	: {ids : ids},
					success : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						grid.getStore().reload();
						Ext.Msg.alert('提示', resultArray.msg);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('提示', resultArray.msg);
					}
				});
			}
		});
	}
});








