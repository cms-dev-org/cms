Ext.onReady(function() {
	
	var continentGroupRow = [{header: '客户信息', colspan: 10, align: 'center', cls:'no-checkbox-header'}];
	if(powerBrandJson.TOTALCOUNT > 0) {
		continentGroupRow.push({
			header		: '动力品牌', 
			colspan		: powerBrandJson.TOTALCOUNT, 
			align		: 'center'
		});
	}
	if( policyBrandJson.TOTALCOUNT > 0) {
		continentGroupRow.push({
			header		: '政策品牌', 
			colspan		: policyBrandJson.TOTALCOUNT, 
			align		: 'center'
		});
	}
	
	var staticStructure = [{
		header    : 'ID',
		name	  : 'id',
		align	  : 'center',
		hidden	  : true
	}, {
		header    : '客户编号',
		name	  : 'customer_code',
		align	  : 'center',
		width 	  : 100,
		sortable  : true
	}, {
		header    : '客户名称',
		name	  : 'customer_name',
		align	  : 'center',
		width 	  : 180,
		sortable  : true
	}, {
		header 	  : '客户姓名',
		name	  : 'director',
		align	  : 'center',
		sortable  : true,
		hidden	  : true,
		width     : 90
	}, {
		header	  : '经营业态',
		name	  : 'manage_type',
		hidden	  : true,
		width	  : 90
	}, {
		header 	  : '访销员',
		name      : 'sale_visitor',
		align	  : 'center',
		hidden	  : true,
		sortable  : true,
		width 	  : 80
	}, {
		header 	  : '电访周期',
		name      : 'visit_cycle',
		align	  : 'center',
		hidden	  : true,
		sortable  : true,
		width 	  : 100
	}, {
		header 	  : '备注',
		name      : 'remark',
		align	  : 'center',
		hidden	  : true,
		sortable  : true,
		width 	  : 100
	}];
	
	for(var i=0; i<powerBrandJson.TOTALCOUNT; i++) {
		var brandColumnBrand = {};
		var powerBrandArr = powerBrandJson.ROOT;
		brandColumnBrand.header = powerBrandArr[i].specification_name;
		brandColumnBrand.name = 'name' + powerBrandArr[i].brand_id;
		brandColumnBrand.align = 'center';
		brandColumnBrand.width = 180;
		brandColumnBrand.sortable = true;
		staticStructure.push(brandColumnBrand);
	}
	
	for(var i=0; i<policyBrandJson.TOTALCOUNT; i++) {
		var brandColumnBrand = {};
		var policyBrandArr = policyBrandJson.ROOT;
		brandColumnBrand.header = policyBrandArr[i].specification_name;
		brandColumnBrand.name = 'name' + policyBrandArr[i].brand_id;
		brandColumnBrand.align = 'center';
		brandColumnBrand.width = 180;
		brandColumnBrand.sortable = true;
		staticStructure.push(brandColumnBrand);
	}
	
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
	//选择客户
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
	
	var leftField = [{
		xtype			: 'panel',
		layout			: 'column',
		width			: 273,
		style			: 'margin:2 2 4 2;',
		border			: false,
		items			: [{
			columnWidth		: .342,
			xtype			: 'label',
			style			: 'margin-top:3px;margin-left:35px;font-style:normal;',
			text			: '客户编号:'
		},{
			columnWidth		: .458,
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
	}];
	
	var rightField = [{
		fieldLabel		: 'ID',
		name			: 'hdm_soft_id',
		id				: 'hdm_soft_id',
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
	}];
	
	var allBrandInfo = powerBrandJson.ROOT.concat(policyBrandJson.ROOT);
	
	for(var i=0; i<allBrandInfo.length; i++) {
		if(i%2 == 0) {
			leftField.push({
				name			: 'name' + allBrandInfo[i].brand_id,
				fieldLabel		: allBrandInfo[i].specification_name,
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: '0'
			});
		} else {
			rightField.push({
				name			: 'name' + allBrandInfo[i].brand_id,
				fieldLabel		: allBrandInfo[i].specification_name,
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: '0'
			});
		}
	}
	if(allBrandInfo.length % 2 == 0) {
		leftField.push({
			name			: 'remark',
			fieldLabel		: '备注'
		}, {
			name			: 'mode',
			id				: 'mode',
			hidden			: true
		}, {
			name			: 'id',
			hidden			: true,
			id				: 'id'
		});
	} else {
		rightField.push({
			name			: 'remark',
			fieldLabel		: '备注'
		}, {
			name			: 'mode',
			id				: 'mode',
			hidden			: true
		}, {
			name			: 'id',
			hidden			: true,
			id				: 'id'
		});
	}
	//新增form
	var addForm = new Ext.form.FormPanel({
		layout		: 'column',
		bodyStyle	: 'padding:5 0 5 0',
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
			items			: leftField
		}, {
			items			: rightField
		}]
	});
	//新增窗口
	var addWin = new Ext.Window({
		title		: '添加信息',
		width		: 600,
		closeAction	: 'hide',
		autoScroll	: true,
		layout		: 'form',
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
				var method = 'addItem';
				if(mode == 'edit') {
					method = 'updateItem';
				}
				addForm.getForm().submit({
					url			: '../app/highQualityBrand.do?reqCode=' + method,
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
	
	var group = new Ext.ux.grid.ColumnHeaderGroup({
        rows		: [continentGroupRow]
    });
	
	var grid = new Ext.ux.BaseGrid({
		style		: 'margin:2 2 2 2',
		region		: 'center',
		paging		: true,
		selModel 	: new Ext.grid.RowSelectionModel(),
		height		: 200,
		noRn		: false,
		noSm		: false,
		list_url	: '../app/highQualityBrand.do?reqCode=query',
		plugins		: group,
		viewConfig	: {
			forceFit	: false
		},
		tbar		: [{
			text 		: '新增',
			iconCls 	: 'page_addIcon',
			handler 	: function() {
				addWin.show();
			}
		}, '-', {
			text 		: '修改',
			iconCls 	: 'page_edit_1Icon',
			handler 	: function() {
				editInit();
			}
		}, '-', {
			text 		: '删除',
			iconCls 	: 'page_delIcon',
			handler 	: function() {
				delItem();
			}
		}, '-', {
			text		: '设置动力品牌',
			iconCls		: '',
			handler		: function() {
				setPowerBrand();
			}
		}, '-', {
			text		: '设置政策品牌',
			iconCls		: '',
			handler		: function() {
				setPolicyBrand();
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
		structure	: staticStructure
	});
	var baseParams = {};
	for(var i=0; i<allBrandInfo.length; i++) {
		var index = 'name' + allBrandInfo[i].brand_id;
		baseParams[index] = allBrandInfo[i].brand_id;
	}
	grid.getStore().baseParams = baseParams;
	grid.getStore().reload({
		params 	: {
			start	: 0,
			limit	: grid.getBottomToolbar().pageSize
		}
	});
	
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	
	grid.on('afterrender', function() {
		//分组表头 移出包含sm列的分组表头上出现的checkbox
		var firstHeadDiv = Ext.query(".no-checkbox-header div")[0];
		Ext.fly(firstHeadDiv).removeClass('x-grid3-hd-checker');
	});

	var dataBrandGrid = new Ext.ux.BaseGrid({
		ddGroup     : 'selectedGridDDGroup',
		noRn		: true,
		noSm		: true,
		sm			: new Ext.grid.RowSelectionModel(),
		enableDragDrop   : true,
        stripeRows       : true,
        list_url	: '../app/highQualityBrand.do?reqCode=queryBrand',
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true,
			width	  : 150
		}, {
			header    : '条形码',
			name	  : 'bar_code',
			align	  : 'center',
			width	  : 150
		}, {
			header    : '品牌名称',
			name	  : 'specification_name',
			align	  : 'center',
			width 	  : 100,
			sortable  : true
		}]
	});
	
	var selectedBrandGrid = new Ext.ux.BaseGrid({
		ddGroup    	: 'dataBrandGridDDGroup',
		noRn		: true,
		noSm		: true,
		sm			: new Ext.grid.RowSelectionModel(),
		list_url	: '../app/highQualityBrand.do?reqCode=querySelectedBrand',
		enableDragDrop   : true,
        stripeRows       : true,
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true,
			width	  : 150
		}, {
			header    : '条形码',
			name	  : 'bar_code',
			align	  : 'center',
			width	  : 150
		}, {
			header    : '品牌名称',
			name	  : 'specification_name',
			align	  : 'center',
			width 	  : 100,
			sortable  : true
		}]
	});
	
	var setBrandWin = new Ext.Window({
		title		: '设置动力品牌',
		width		: 650,
		height		: 330,
		resizable	: false,
		closeAction	: 'hide',
		modal		: true,
		layout		: {
			type		: 'vbox',
			align		: 'stretch'
		},
		items		: [{
			border 		: false,
			flex		: 1,
			layout		: 'column',
			items		: [{
				style	: 'margin-top:3px;margin-left:2px;',
				width	: 232,
				xtype	: 'textfield',
				id		: 'queryBrandParam',
				emptyText : '请输入品牌名称\\条形码'
			}, {
				style	: 'margin-top:3px;margin-left:2px;',
				xtype	: 'button',
				width	: 80,
				text	: '查    询',
				handler	: function() {
					queryBrand(dataBrandGrid, 0);
				}
			}]
		}, {
			flex		: 9,
			border		: false,
			layout		: {
				type		: 'hbox',
				align		: 'stretch',
				defaultMargins	: {top:2,right:2,bottom:2,left:2}
			},
			defaults	: {flex : 1},
			items		: [dataBrandGrid, selectedBrandGrid]
		}],
		buttons		: [{
			text	: '确定',
			handler	: function() {
				var paramObj = {};
				var powerBrandData = selectedBrandGrid.getStore().getRange();
				
				for(var i=0; i<powerBrandData.length; i++) {
					paramObj[i] = Ext.encode(powerBrandData[i].data);
				}
				Ext.Ajax.request({
					url		: '../app/highQualityBrand.do?reqCode=addSelectedBrand',
					params	: {selectedBrandData : Ext.encode(paramObj), type : 0},
					success	: function(response) {
						window.location.reload(); 
					},
					failure	: function(response) {
						
					}
				});
			}
		}, {
			text	: '取消',
			handler	: function() {
				setBrandWin.hide();
			}
		}]
		
	});
	
	var dataPolicyBrandGrid = new Ext.ux.BaseGrid({
		ddGroup     : 'selectedPolicyGridDDGroup',
		noRn		: true,
		noSm		: true,
		sm			: new Ext.grid.RowSelectionModel(),
		enableDragDrop   : true,
        stripeRows       : true,
        list_url	: '../app/highQualityBrand.do?reqCode=queryBrand',
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true,
			width	  : 150
		}, {
			header    : '条形码',
			name	  : 'bar_code',
			align	  : 'center',
			width	  : 150
		}, {
			header    : '品牌名称',
			name	  : 'specification_name',
			align	  : 'center',
			width 	  : 100,
			sortable  : true
		}]
	});
	
	var selectedPolicyBrandGrid = new Ext.ux.BaseGrid({
		ddGroup    	: 'dataPolicyBrandGridDDGroup',
		noRn		: true,
		noSm		: true,
		sm			: new Ext.grid.RowSelectionModel(),
		list_url	: '../app/highQualityBrand.do?reqCode=querySelectedBrand',
		enableDragDrop   : true,
        stripeRows       : true,
		structure	: [{
			header    : 'ID',
			name	  : 'id',
			align	  : 'center',
			hidden	  : true,
			width	  : 150
		}, {
			header    : '条形码',
			name	  : 'bar_code',
			align	  : 'center',
			width	  : 150
		}, {
			header    : '品牌名称',
			name	  : 'specification_name',
			align	  : 'center',
			width 	  : 100,
			sortable  : true
		}]
	});
	
	var setPolicyBrandWin = new Ext.Window({
		title		: '设置政策品牌',
		width		: 650,
		height		: 330,
		resizable	: false,
		closeAction	: 'hide',
		modal		: true,
		layout		: {
			type		: 'vbox',
			align		: 'stretch'
		},
		items		: [{
			border 		: false,
			flex		: 1,
			layout		: 'column',
			items		: [{
				style	: 'margin-top:3px;margin-left:2px;',
				width	: 232,
				xtype	: 'textfield',
				id		: 'queryBrandParam',
				emptyText : '请输入品牌名称\\条形码'
			}, {
				style	: 'margin-top:3px;margin-left:2px;',
				xtype	: 'button',
				width	: 80,
				text	: '查    询',
				handler	: function() {
					queryBrand(dataPolicyBrandGrid, 1);
				}
			}]
		}, {
			flex		: 9,
			border		: false,
			layout		: {
				type		: 'hbox',
				align		: 'stretch',
				defaultMargins	: {top:2,right:2,bottom:2,left:2}
			},
			defaults	: {flex : 1},
			items		: [dataPolicyBrandGrid, selectedPolicyBrandGrid]
		}],
		buttons		: [{
			text	: '确定',
			handler	: function() {
				var paramObj = {};
				var policyBrandData = selectedPolicyBrandGrid.getStore().getRange();
				
				for(var i=0; i<policyBrandData.length; i++) {
					paramObj[i] = Ext.encode(policyBrandData[i].data);
				}
				Ext.Ajax.request({
					url		: '../app/highQualityBrand.do?reqCode=addSelectedBrand',
					params	: {selectedBrandData : Ext.encode(paramObj), type : 1},
					success	: function(response) {
						window.location.reload(); 
					},
					failure	: function(response) {
						
					}
				});
			}
		}, {
			text	: '取消',
			handler	: function() {
				setPolicyBrandWin.hide();
			}
		}]
		
	});
	
	new Ext.Viewport({
		layout		: 'border',
		items		: [grid]
	});
	
	setBrandWin.on('show', function(win) {//动力品牌设置 需要在组件初始化完成之后 再初始化拖拽组件 否则 报错
		//查询已经配置好的动力品牌
		selectedBrandGrid.getStore().load({
			params : {type : 0}
		});
		
		var firstGridDropTargetEl =  dataBrandGrid.getView().scroller.dom;
	    new Ext.dd.DropTarget(firstGridDropTargetEl, {
	        ddGroup    : 'dataBrandGridDDGroup',
	        notifyDrop : function(ddSource, e, data){
	            var records = ddSource.dragData.selections;
	            Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	            dataBrandGrid.store.add(records);
	            return true;
	        }
	    });
		
		var secondGridDropTargetEl = selectedBrandGrid.getView().scroller.dom;
		new Ext.dd.DropTarget(secondGridDropTargetEl, {
		    ddGroup    : 'selectedGridDDGroup',
		    notifyDrop : function(ddSource, e, data){
	            var records =  ddSource.dragData.selections;
	            Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	            selectedBrandGrid.store.add(records);
	            return true;
		     }
		});
	});
	
	setPolicyBrandWin.on('show', function(win) {//动力品牌设置 需要在组件初始化完成之后 再初始化拖拽组件 否则 报错
		//查询已经配置好的动力品牌
		selectedPolicyBrandGrid.getStore().load({
			params : {type : 1}
		});
		
		var firstGridDropTargetEl =  dataPolicyBrandGrid.getView().scroller.dom;
	    new Ext.dd.DropTarget(firstGridDropTargetEl, {
	        ddGroup    : 'dataPolicyBrandGridDDGroup',
	        notifyDrop : function(ddSource, e, data){
	            var records = ddSource.dragData.selections;
	            Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	            dataPolicyBrandGrid.store.add(records);
	            return true;
	        }
	    });
		
		var secondPolicyGridDropTargetEl = selectedPolicyBrandGrid.getView().scroller.dom;
		new Ext.dd.DropTarget(secondPolicyGridDropTargetEl, {
		    ddGroup    : 'selectedPolicyGridDDGroup',
		    notifyDrop : function(ddSource, e, data){
	            var records =  ddSource.dragData.selections;
	            Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	            selectedPolicyBrandGrid.store.add(records);
	            return true;
		     }
		});
	});
	
	/**
	 * 设置动力品牌
	 */
	function setPowerBrand() {
		setBrandWin.show();
	}
	/**
	 * 设置政策品牌
	 */
	function setPolicyBrand() {
		setPolicyBrandWin.show();
	}
	
	function queryBrand(grid, type) {
		var paramValue = Ext.getCmp('queryBrandParam').getValue();
		grid.getStore().reload({
			params	: {
				queryParam	: paramValue,
				type		: type
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
	//修改
	function editInit() {
		var record = grid.getSelectionModel().getSelections();
		Ext.getCmp('mode').setValue('edit');
		if(!record || record.length != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');	
			return;
		}
		//Ext.getCmp('approval_id').setValue(record[0].id);
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
				//showWaitMsg();
				Ext.Ajax.request({
					url 	: '../app/highQualityBrand.do?reqCode=deleteItems',
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
	
	//查询基础数据
	function query() {
		baseParams['queryParam'] = Ext.getCmp('queryParam').getValue();
		grid.getStore().baseParams = baseParams;
		grid.getStore().load({
			params		: {
				start	: 0,
				limit	: grid.getBottomToolbar().pageSize
			}
		});
	}
	
});
//修改chrome浏览器下 合并表头 ，列有错位情况
Ext.grid.ColumnModel.override({  
	getTotalWidth: function(includeHidden) {  
	    var off = 0;  
	//      if(!Ext.isDefined(Ext.isChrome19)){  
	//          Ext.isChrome19 = /\bchrome\/19\b/.test(navigator.userAgent.toLowerCase());  
	//          };  
	    if (Ext.isChrome){  
	        off = 8;  
	    };  
	    if (!this.totalWidth) {  
	        this.totalWidth = 0;  
	        for (var i = 0, len = this.config.length; i < len; i++) {  
	            if (includeHidden || !this.isHidden(i)) {  
	                this.totalWidth += this.getColumnWidth(i)+off;  
	            };  
	        };  
	    };  
	    return this.totalWidth;  
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





