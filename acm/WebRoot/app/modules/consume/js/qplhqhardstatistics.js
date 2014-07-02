Ext.onReady(function() {
	
	//新增form
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
			items			: [{
				xtype			: 'panel',
				layout			: 'column',
				width			: 273,
				style			: 'margin:2 2 4 2;',
				border			: false,
				items			: [{
					columnWidth		: .34,
					xtype			: 'label',
					style			: 'margin-top:3px;margin-left:34px;',
					text			: '客户编号:'
				},{
					columnWidth		: .46,
					xtype			: 'textfield',
					width			:  140,
					name	  		: 'customer_code',
					readOnly		: true,
					allowBlank		: false
				}, {
					columnWidth		: .2,
					xtype			: 'button',
					text			: '选择',
					handler			: function(btn) {
						selectCustomerWin.show();
					}
				}]
			}, {
				name			: 'director',
				fieldLabel		: '客戶姓名',
				readOnly		: true
			}, {
				name			: 'sale_visitor',
				fieldLabel		: '访销员',
				readOnly		: true
			}, {
				id				: 'qplhq_hard',
				name			: 'qplhq_hard',
				fieldLabel		: '七匹狼豪情',
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: '0'
			}, {
				id				: 'add_report_date',
				name			: 'report_date',
				fieldLabel		: '上报时间',
				xtype			: 'datefield',
				format			: 'Y.m.d',
				allowBlank		: false,
				value			: new Date(),
				editable		: false
			}, {
				name			: 'remark',
				fieldLabel		: '备注'
			}]
		}, {
			items			: [{
				fieldLabel		: 'ID',
				name			: 'qplhq_hard_id',
				id				: 'qplhq_hard_id',
				xtype			: 'hidden'
			}, {
				name			: 'customer_name',
				fieldLabel		: '客户名称',
				readOnly		: true
			}, {
				name			: 'manage_type',
				fieldLabel		: '经营业态',
				readOnly		: true
			}, {
				name			: 'visit_cycle',
				fieldLabel		: '电访周期',
				readOnly		: true
			}, {
				name			: 'unit',
				fieldLabel		: '单位'
			}, {
				name			: 'mode',
				id				: 'mode',
				hidden			: true
			}]
		}]
	});
	//新增窗口
	var addWin = new Ext.Window({
		title		: '添加信息',
		width		: 600,
		closeAction	: 'hide',
		autoScroll	: true,
		layout		: 'fit',
		buttonAlign	: 'right',
		height		: 230,
		items		: [addForm],
		buttons		: [{
			text		: '确定',
			handler		: function(btn) {
				if(!addForm.getForm().isValid()) {
					return;
				}
				var mode = Ext.getCmp('mode').getValue();
				var method = 'insert';
				if(mode == 'edit') {
					method = 'update';
				}
				addForm.getForm().submit({
					url			: '../app/qplhqHardStatistics.do?reqCode=' + method,
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
			}
		}, {
			text		: '取消',
			handler		: function(btn) {
				addWin.hide();
				addForm.getForm().reset();
			}
		}]
	});
	
	
	var selectCustomerGrid = new Ext.ux.BaseGrid({
		region		: 'center',
		paging		: true,
		border		: false,
		selModel 	: new Ext.grid.RowSelectionModel(),
		height		: 200,
		noRn		: false,
		list_url	: '../app/consumeCustomer.do?reqCode=query',
		viewConfig	: {
			forceFit	: false
		},
		tbar		: ['->', {
			id			: 'queryParam',
			xtype		: 'textfield',
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						queryCustomer();
					}
				}
			}
		}, {
			text		: '查询',
			iconCls		: 'previewIcon',
			handler		: function() {
				queryCustomer();
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
			width 	  : 130,
			sortable  : true
		}, {
			header    : '客户名称',
			name	  : 'customer_name',
			align	  : 'center',
			width 	  : 180,
			sortable  : true
		}, {
			header 	  : '经营地址',
			name	  : 'address',
			align	  : 'center',
			sortable  : true,
			width     : 130
		}, {
			header	  : '联系电话',
			name	  : 'phone_num',
			width	  : 130
		}, {
			header 	  : '公司',
			name      : 'company',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '账号',
			name      : 'account',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '负责人',
			name      : 'director',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '部门',
			name      : 'department',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '访销员',
			name      : 'sale_visitor',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '地理位置',
			name      : 'location_desc',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '经营业态',
			name      : 'manage_type',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '销售段',
			name      : 'sale_valume',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '与烟草公司关系',
			name      : 'relation',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '客户等级',
			name      : 'customer_level',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '客户类型',
			name      : 'customer_type',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '每周送货次数',
			name      : 'send_num',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '拨打周期/电访周期/订货日期',
			name      : 'visit_cycle',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '订货域',
			name      : 'order_area',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '订货方式',
			name      : 'order_type',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '客户状态',
			name      : 'customer_status',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '备注',
			name      : 'remark',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}, {
			header 	  : '更新时间',
			name      : 'update_time',
			align	  : 'center',
			sortable  : true,
			width 	  : 190
		}]
	});
	var selectCustomerWin = new Ext.Window({
		title		: '查找客户信息',
		width		: 700,
		closeAction	: 'hide',
		modal		: true,
		autoScroll	: false,
		layout		: 'fit',
		buttonAlign	: 'right',
		height		: 400,
		items		: [selectCustomerGrid],
		buttons		: [{
			text		: '确定',
			handler		: function(btn) {
				var rows = selectCustomerGrid.getSelectionModel().getSelections();
				if(rows.length != 1) {Ext.Msg.alert('提示', '请选择一条客户信息！');return;}; 
				var record = rows[0];
				addForm.getForm().loadRecord(record);
				selectCustomerWin.hide();
			}
		}, {
			text		: '取消',
			handler		: function(btn) {
				selectCustomerWin.hide();
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
		list_url	: '../app/qplhqHardStatistics.do?reqCode=query',
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
				Ext.getCmp('qplhq_hard').setValue(0);
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
				exportExcel('../app/qplhqHardStatistics.do?reqCode=exportExcel');
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
			header 	  : '七匹狼豪情',
			name      : 'qplhq_hard',
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
	
	grid.getBottomToolbar().add({
		xtype	: 'label',
		html	: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;软哈总计：'
	}, {
		id		: 'qplhq_hard_total',
		xtype	: 'textfield'
	});
	
	grid.getStore().reload({
		params 	: {
			firstLoad	: '1',
			start 		: 0,
			limit 		: grid.getBottomToolbar().pageSize
		}
	});
	
	grid.getStore().on('load', function(store, record, opt) {
		Ext.Ajax.request({
			url 	: '../app/qplhqHardStatistics.do?reqCode=countNum',
			success : function(response) {
				var data = response.responseText;
				var dataObj = eval('(' + data + ')');
				var qplhqHardCount = dataObj.qplhqHardCount;
				Ext.getCmp('qplhq_hard_total').setValue(qplhqHardCount);
			},
			failure : function(response) {
				
			}
		});
	});
	
	selectCustomerGrid.getStore().reload({
		params 	: {
			start : 0,
			limit : grid.getBottomToolbar().pageSize
		}
	});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	
	var viewPort = new Ext.Viewport({
		layout		: 'border',
		items		: [grid]
	});
	
	//查询基础数据
	function query() {
		grid.getStore().baseParams = {
			report_date_start	: Ext.getCmp('report_date_start').getValue(),
			report_date_end		: Ext.getCmp('report_date_end').getValue(),
			queryParam			: Ext.getCmp('queryParam').getValue()
		}
		grid.getStore().load({
			params		: {
				start	: 0,
				limit	: grid.getBottomToolbar().pageSize
			}
		});
	}
	
	//查询客户数据
	function queryCustomer() {
		selectCustomerGrid.getStore().baseParams = {
			queryParam	: Ext.getCmp('queryParam').getValue()
		}
		selectCustomerGrid.getStore().load({
			params		: {
				start	: 0,
				limit	: grid.getBottomToolbar().pageSize
			}
		});
	}
	
	//删除项目记录
	function delItem() {
		var rows = grid.getSelectionModel().getSelections();
		if(rows.length < 1) {Ext.Msg.alert('提示', '请至少选择一条数据');return;}; 
		var ids = jsArray2JsString(rows, 'id');
		Ext.Msg.confirm('请确认', '确认删除选中项？', function(btn, text) {
			if (btn == 'yes') {
				//showWaitMsg();
				Ext.Ajax.request({
					url 	: '../app/qplhqHardStatistics.do?reqCode=delete',
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
	
	//修改
	function editInit() {
		var record = grid.getSelectionModel().getSelections();
		Ext.getCmp('mode').setValue('edit');
		if(!record || record.length != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');	
			return;
		}
		Ext.getCmp('qplhq_hard_id').setValue(record[0].id);
		addForm.getForm().loadRecord(record[0]);
		addWin.show();
	}
});