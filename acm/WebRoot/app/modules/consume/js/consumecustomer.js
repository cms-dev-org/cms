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
				name	  		: 'customer_code',
				fieldLabel		: '客户编号',
				allowBlank		: false,
				listeners		: {
					'blur'		: checkCustomerCode
				}
			}, {
				name			: 'address',
				fieldLabel		: '经营地址'
			}, {
				name			: 'company',
				fieldLabel		: '公司'
			}, {
				name			: 'director',
				fieldLabel		: '负责人'
			}, {
				name			: 'sale_visitor',
				fieldLabel		: '访销员'
			}, {
				name			: 'manage_type',
				fieldLabel		: '经营业态'
			}, {
				name			: 'relation',
				fieldLabel		: '与烟草公司关系'
			}, {
				name			: 'customer_type',
				fieldLabel		: '客户类型'
			}, {
				name			: 'visit_cycle',
				fieldLabel		: '电访周期'
			}, {
				name			: 'order_type',
				fieldLabel		: '订货方式'
			}, {
				name			: 'remark',
				fieldLabel		: '备注'
			}]
		}, {
			items			: [{
				name			: 'customer_name',
				fieldLabel		: '客户名称'
			}, {
				name			: 'phone_num',
				fieldLabel		: '联系电话'
			}, {
				name			: 'account',
				fieldLabel		: '账号'
			}, {
				name			: 'department',
				fieldLabel		: '部门'
			}, {
				name			: 'location_desc',
				fieldLabel		: '地理位置'
			}, {
				name			: 'sale_valume',
				fieldLabel		: '销售段'
			}, {
				name			: 'customer_level',
				fieldLabel		: '客户等级'
			}, {
				name			: 'send_num',
				fieldLabel		: '每周送货次数'
			}, {
				name			: 'order_area',
				fieldLabel		: '订货域'
			}, {
				name			: 'customer_status',
				fieldLabel		: '客户状态'
			}]
		}]
	});
	//新增窗口
	var addWin = new Ext.Window({
		title		: '新增客户信息',
		width		: 600,
		closeAction	: 'hide',
		autoScroll	: true,
		layout		: 'fit',
		buttonAlign	: 'right',
		height		: 360,
		items		: [addForm],
		buttons		: [{
			text		: '确定',
			handler		: function(btn) {
				if(!addForm.getForm().isValid()) {
					return;
				}
				addForm.getForm().submit({
					url			: '../app/consumeCustomer.do?reqCode=insert',
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
	
	//修改form
	var editForm = new Ext.form.FormPanel({
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
				xtype			: 'hidden',
				id				: 'customer_id',
				name			: 'customer_id',
				fieldLabel		: 'id'
			}, {
				name	  		: 'customer_code',
				fieldLabel		: '客户编号',
				allowBlank		: false
			}, {
				name			: 'address',
				fieldLabel		: '经营地址'
			}, {
				name			: 'company',
				fieldLabel		: '公司'
			}, {
				name			: 'director',
				fieldLabel		: '负责人'
			}, {
				name			: 'sale_visitor',
				fieldLabel		: '访销员'
			}, {
				name			: 'manage_type',
				fieldLabel		: '经营业态'
			}, {
				name			: 'relation',
				fieldLabel		: '与烟草公司关系'
			}, {
				name			: 'customer_type',
				fieldLabel		: '客户类型'
			}, {
				name			: 'visit_cycle',
				fieldLabel		: '电访周期'
			}, {
				name			: 'order_type',
				fieldLabel		: '订货方式'
			}, {
				name			: 'remark',
				fieldLabel		: '备注'
			}]
		}, {
			items			: [{
				name			: 'customer_name',
				fieldLabel		: '客户名称'
			}, {
				name			: 'phone_num',
				fieldLabel		: '联系电话'
			}, {
				name			: 'account',
				fieldLabel		: '账号'
			}, {
				name			: 'department',
				fieldLabel		: '部门'
			}, {
				name			: 'location_desc',
				fieldLabel		: '地理位置'
			}, {
				name			: 'sale_valume',
				fieldLabel		: '销售段'
			}, {
				name			: 'customer_level',
				fieldLabel		: '客户等级'
			}, {
				name			: 'send_num',
				fieldLabel		: '每周送货次数'
			}, {
				name			: 'order_area',
				fieldLabel		: '订货域'
			}, {
				name			: 'customer_status',
				fieldLabel		: '客户状态'
			}]
		}]
	});
	//修改窗口
	var editWin = new Ext.Window({
		title		: '修改客户信息',
		width		: 600,
		closeAction	: 'hide',
		autoScroll	: true,
		layout		: 'fit',
		buttonAlign	: 'right',
		height		: 360,
		items		: [editForm],
		buttons		: [{
			text		: '确定',
			handler		: function(btn) {
				if(!editForm.getForm().isValid()) {
					return;
				}
				//showWaitMsg();
				editForm.getForm().submit({
					url			: '../app/consumeCustomer.do?reqCode=update',
					method 		: 'POST',
					params		: {},
					success		: function(form, response) {
						grid.getStore().reload();
						editWin.hide();
						editForm.getForm().reset();
					},
					failuer		: function(form, response) {
						
					}
				});
			}
		}, {
			text		: '取消',
			handler		: function(btn) {
				editWin.hide();
				editForm.getForm().reset();
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
		list_url	: '../app/consumeCustomer.do?reqCode=query',
		viewConfig	: {
			forceFit	: false
		},
		tbar		: [{
			text : '新增',
			iconCls : 'page_addIcon',
			handler : function() {
				addWin.show();
			}
		}, '-', {
			text : '修改',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editInit();
			}
		}, '-', {
			text : '删除',
			iconCls : 'page_delIcon',
			handler : function() {
				delItem();
			}
		}, '->', {
			id			: 'queryParam',
			xtype		: 'textfield',
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						query();
					}
				}
			}
		}, {
			text		: '查询',
			iconCls		: 'previewIcon',
			handler		: function() {
				query();
			}
		}, '-', {
			text		: '刷新',
			iconCls		: 'arrow_refreshIcon',
			handler		: function() {
				grid.getStore().reload();
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
	grid.getStore().reload({
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
			queryParam	: Ext.getCmp('queryParam').getValue()
		}
		grid.getStore().load({
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
				showWaitMsg();
				Ext.Ajax.request({
					url 	: '../app/consumeCustomer.do?reqCode=delete',
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
		if(!record || record.length > 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');	
			return;
		}
		Ext.getCmp('customer_id').setValue(record[0].id);
		editForm.getForm().loadRecord(record[0]);
		editWin.show();
	}
	
	function checkCustomerCode(obj) {
		var customerCode = obj.getValue();
		if(customerCode == '') return;
		Ext.Ajax.request({
			url		: '../app/consumeCustomer.do?reqCode=checkCustomerCode',
			params	: {customerCode : customerCode},
			success	: function(response) {
				var msg = eval('(' + response.responseText + ')').msg;
				if(msg == 1) {
					Ext.Msg.alert('提示', '客户编号已存在，请重新录入！');
					obj.focus();
				}
			},
			failure	: function(data) {
				Ext.Msg.alert('错误', '查询工号发生错误，请联系管理员！');
			}
		});
	}
});







