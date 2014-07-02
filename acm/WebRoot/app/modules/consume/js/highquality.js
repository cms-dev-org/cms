Ext.onReady(function() {
	
	var brandGrid = new Ext.ux.BaseGrid({
		region		: 'center',
		width		: 320,
		paging		: true,
		selModel 	: new Ext.grid.RowSelectionModel(),
		height		: 200,
		noRn		: true,
		noSm		: true,
		list_url	: '../app/brandInfo.do?reqCode=query',
		viewConfig	: {
			forceFit	: false
		},
		tbar		: ['->', {
			id			: 'queryBrandParam',
			xtype		: 'textfield',
			style		: 'margin-left:5px;',
			listeners 	: {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						queryBrand();
					}
				}
			}
		}, {
			text		: '查询',
			style		: 'margin-left:5px;',
			iconCls		: 'previewIcon',
			handler		: function() {
				queryBrand();
			}
		}],
		listeners	: {
			rowdblclick:function(grid , rowIndex ,e){
				showMenu.hide();
				var rowOptions = grid.getSelectionModel().getSelections();
				for(var i=0; i< rowOptions.length; i++){
					var uid = rowOptions[i].get('id');
					var uname = rowOptions[i].get('specification_name');
					comb.setValue(uid);
					comb.setRawValue(uname);
					Ext.getCmp('brand_id_hidden').setValue(uid);
				}
			}
		},
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
			header 	  : '卷烟规格名称',
			name	  : 'specification_name',
			align	  : 'center',
			sortable  : true,
			width     : 160
		}]
	});
	
	var showMenu = new Ext.menu.Menu({
	     items : [brandGrid]
	});
	
	function queryBrand() {
		var paramValue = Ext.getCmp('queryBrandParam').getValue();
		brandGrid.getStore().baseParams = {
			queryParam	: paramValue
		};
		brandGrid.getStore().reload({
			params		: {
				start	: 0,
				limit	: brandGrid.getBottomToolbar().pageSize
			}
		});
	}
	
	var comb = new Ext.form.ComboBox({
		//name			: 'brand_id',
		//hiddenName		: 'brand_id',
		width			: 130,
		triggerAction	: 'all',
		typeAhead		: true,
		lazyRender		: true,
		fieldLabel		: '品牌',
		labelStyle 		: micolor,
		allowBlank		: false,
		editable		: false,
		emptyText		: '请选择...',
		valueField		: 'id',
		displayField	: 'specification_name',
		tpl				: '<div id="brand_grid"></div>',
		mode			: 'local',
		onSelect		: Ext.emptyFn,
		store			: new Ext.data.ArrayStore({ fields: ['id', 'specification_name'],  data:[[]] }),
		listeners		: {
			expand		: function(combo){
				//重要 
				if(this.menu == null) {
				   this.menu = showMenu;
				}
				brandGrid.getStore().load();
				this.menu.show(this.el);
			}
		}
	});	
	
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
					style			: 'margin-top:3px;margin-left:34px;color:blue;',
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
			}, comb, {
				name			: 'remark',
				fieldLabel		: '备注'
			}]
		}, {
			items			: [{
				fieldLabel		: 'ID',
				name			: 'highqulity_id',
				id				: 'highqulity_id',
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
				id				: 'brand_num',
				name			: 'num',
				labelStyle 		: micolor,
				fieldLabel		: '数量',
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: 0
			}, {
				name			: 'mode',
				id				: 'mode',
				hidden			: true
			}, {
				name			: 'brand_id',
				id				: 'brand_id_hidden',
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
					url			: '../app/highQuality.do?reqCode=' + method,
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
		list_url	: '../app/highQuality.do?reqCode=query',
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
				Ext.getCmp('brand_num').setValue(0);
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
				exportExcel('../app/consumeApproval.do?reqCode=exportExcel');
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
			name	  : 'highqulity_id',
			align	  : 'center',
			hidden	  : true
		}, {
			header    : '客户编号',
			name	  : 'customer_code',
			align	  : 'center',
			width 	  : 140,
			sortable  : true
		}, {
			header    : '客户名称',
			name	  : 'customer_name',
			align	  : 'center',
			width 	  : 130, 
			sortable  : true
		}, {
			header 	  : '客户姓名',
			name	  : 'director',
			align	  : 'center',
			sortable  : true,
			width     : 110
		}, {
			header	  : '经营业态',
			name	  : 'manage_type',
			width	  : 100
		}, {
			header 	  : '访销员',
			name      : 'sale_visitor',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '电访周期',
			name      : 'visit_cycle',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '品牌ID',
			name      : 'brand_id',
			align	  : 'center',
			sortable  : true,
			hidden	  : true,
			width 	  : 140
		}, {
			header 	  : '品牌',
			name      : 'specification_name',
			align	  : 'center',
			sortable  : true,
			width 	  : 140
		}, {
			header 	  : '数量',
			name      : 'num',
			align	  : 'center',
			sortable  : true,
			width 	  : 100
		}, {
			header 	  : '备注',
			name      : 'remark',
			align	  : 'center',
			sortable  : true,
			width 	  : 150
		}]
	});
	
	grid.getStore().reload({
		params 	: {
			start 		: 0,
			limit 		: grid.getBottomToolbar().pageSize
		}
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
	
	new Ext.Viewport({
		layout		: 'border',
		items		: [grid]
	});
	
	//查询基础数据
	function query() {
		grid.getStore().baseParams = {
			queryParam			: Ext.getCmp('queryParam').getValue()
		};
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
		};
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
		var ids = jsArray2JsString(rows, 'highqulity_id');
		Ext.Msg.confirm('请确认', '确认删除选中项？', function(btn, text) {
			if (btn == 'yes') {
				//showWaitMsg();
				Ext.Ajax.request({
					url 	: '../app/highQuality.do?reqCode=delete',
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
		Ext.getCmp('highqulity_id').setValue(record[0].get('highqulity_id'));
		addForm.getForm().loadRecord(record[0]);
		comb.setValue(record[0].get('brand_id'));
		comb.setRawValue(record[0].get('specification_name'));
		addWin.show();
		
	}
});

//修复办法，谷歌浏览器中,table的单元格实际宽度=指定宽度+padding，所以只要重写gridview里的一个方法，如下：
Ext.override(Ext.grid.GridView,{
	getColumnStyle : function(colIndex, isHeader) {
		var colModel  = this.cm,
			colConfig = colModel.config,
			style     = isHeader ? '' : colConfig[colIndex].css || '',
		align     = colConfig[colIndex].align;
	
	if(Ext.isChrome){
		style += String.format("width: {0};", parseInt(this.getColumnWidth(colIndex))-2+'px');
	}else{
		style += String.format("width: {0};", this.getColumnWidth(colIndex));
	}
	
	if (colModel.isHidden(colIndex)) {
		style += 'display: none; ';
	}
	
	if (align) {
		style += String.format("text-align: {0};", align);
	}
			
		return style;
	}
});