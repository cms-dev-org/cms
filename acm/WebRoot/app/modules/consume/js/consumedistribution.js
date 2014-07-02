Ext.onReady(function() {
	
	var distributionGrid = new Ext.ux.BaseGrid({
		height		: 220,
		border		: true,
		noSm		: true,
		list_url	: '../app/consumeDistribution.do?reqCode=queryDistribute',
		viewConfig	: {
			forceFit	: true
		},
		structure	: [{
			header		: 'ID',
			name		: 'id',
			align		: 'center',
			hidden		: true
		}, {
			header		: '分配日期',
			name		: 'distribution_date',
			align		: 'center',
			width		: 160,
			hidden		: true,
			sortable	: true
		}, {
			header		: '被分配人',
			name		: 'username',
			align		: 'center',
			width		: 160,
			sortable	: true
		}, {
			header		: '硬中华数量',
			name		: 'zh_hardbox',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '软中华数量',
			name		: 'zh_softbox',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '单位',
			name		: 'unit',
			align		: 'center',
			hidden		: true,
			width		: 150,
			sortable	: true
		}],
		columnWidth	: .6,
		bbar		: [{
			xtype	: 'label',
			html	: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中华（硬）总计：'
		}, {
			id		: 'zh_hardbox_total',
			xtype	: 'textfield'
		}, {
			xtype	: 'label',
			html	: '&nbsp;&nbsp;中华（软）总计：'
		}, {
			id		: 'zh_softbox_total',
			xtype	: 'textfield'
		}]
	});
	
	distributionGrid.getStore().on('load', function(store, record, opt) {
		Ext.Ajax.request({
			url 	: '../app/consumeDistribution.do?reqCode=countNum',
			success : function(response) {
				var data = response.responseText;
				var dataObj = eval('(' + data + ')');
				var hardBoxNum = dataObj.hardbox;
				var softBoxNum = dataObj.softbox;
				Ext.getCmp('zh_hardbox_total').setValue(hardBoxNum);
				Ext.getCmp('zh_softbox_total').setValue(softBoxNum);
			},
			failure : function(response) {
				
			}
		});
	});
	
	var distributeRateGrid = new Ext.ux.BaseGrid({
		height		: 220,
		style		: 'margin : 0 0 0 2',
		border		: true,
		columnWidth	: .4,
		labelWidth	: 140,
		labelAlign	: 'right',
		buttonAlign	: 'center',
		list_url	: '../app/consumeDistribution.do?reqCode=querySubUserDistributeAmount',
		autoScroll	: true,
		noSm		: true,
		noRn		: true,
		clicksToEdit: 2,
		viewConfig	: {
			forceFit	: true
		},
		listeners	: {
			'afteredit'		: function(e) {
				distributeRateGrid.getStore().commitChanges();
			}
		},
		structure	: [{
			header		: 'ID',
			name		: 'id',
			align		: 'center',
			hidden		: true,
			width		: 60,
			sortable	: true
		}, {
			header		: '被分配人ID',
			name		: 'userid',
			align		: 'center',
			hidden		: true,
			width		: 60,
			sortable	: true
		}, {
			header		: '被分配人',
			name		: 'username',
			align		: 'center',
			width		: 60,
			sortable	: true
		}, {
			header		: '硬中华',
			name		: 'zh_hardbox',
			align		: 'center',
			width		: 60,
			editable  	: true,
			editor	  	: new Ext.form.NumberField(),
			sortable	: true
		}, {
			header		: '软中华',
			name		: 'zh_softbox',
			align		: 'center',
			editable  	: true,
			editor	  	: new Ext.form.NumberField(),
			width		: 50,
			sortable	: true
		}],
		bbar		:[{
			xtype		: 'label',
			style		: 'margin : 0 0 0 20',
			text		: '分配方式：'
		}, {
			name		: 'distributeType',
			xtype		: 'radio',
			style		: 'margin : 4 0 0 10',
			inputValue	: 0,
			checked		: true,
			boxLabel	: '按比例',
			listeners	: {
				'check'	: function(field, checked) {
					changeAmountByType(field, checked);
				}
			}
		}, {
			name		: 'distributeType',
			style		: 'margin : 4 0 0 10',
			inputValue	: 1,
			xtype		: 'radio',
			boxLabel	: '按数量',
			listeners	: {
				'check'	: function(field, checked) {
					changeAmountByType(field, checked);
				}
			}
		}, /*{
			id			: 'distributeTypeRadio',
			xtype		: 'radiogroup',
			fieldLabel	: '分配方式',
			labelStyle 	: micolor,
			anchor		: '80%',
			items		: [
                {boxLabel: '按比例', name: 'num', inputValue: 0, checked: true},
                {boxLabel: '按数量', name: 'num', inputValue: 1}
            ],
            listeners	: {
            	'change'	: function(radioGroup, checkedRadio) {
            		queryDistributeData();
            	}
            }
		},*/ '-',  {
			xtype		: 'button',
			//style		: 'margin : 5 0 0 200',
			text		: '分配',
			iconCls		: 'groupIcon',
			//width		: 60,
			handler		: function(btn) {
				showWaitMsg();
				var selects = grid.getSelectionModel().getSelections();
				if(selects.length != 1) {
					Ext.Msg.alert('提示', '请选择一条分配记录！');
					return;
				}
				
				var type = 0;
				var typeRadios = distributeRateGrid.getBottomToolbar().findByType('radio');
				for(var i=0; i<typeRadios.length; i++) {
					var radio = typeRadios[i];
					if(radio.checked) {
						type = radio.inputValue;
					}
				}
				var select = selects[0];
				var numData = [];
				var records = distributeRateGrid.getStore().getRange();
				
				var date = select.get('distribute_date');
				var distribute_author = select.get('distribute_author');
				var zhHardBox = select.get('zh_hardbox');
				var zhSoftBox = select.get('zh_softbox');
				var unit = select.get('unit');
				
				for(var i=0; i<records.length; i++) {
					var temp = {};
					var item = records[i];
					temp.id = item.get('id');
					temp.author = item.get('userid');
					temp.zh_hardbox = item.get('zh_hardbox');
					temp.zh_softbox = item.get('zh_softbox');
					numData.push(temp);
				} 
				Ext.Ajax.request({
					url			: '../app/consumeDistribution.do?reqCode=distribute',
					params		: {date : date, type : type, unit : unit, pAuthor : distribute_author, distributeData : Ext.util.JSON.encode(numData),
						zhHardBox : zhHardBox, zhSoftBox : zhSoftBox},
					waitTitle	: '提示',
					waitMsg		: '正在加载数据，请稍后！',
					success		: function(response) {
						distributionGrid.getStore().reload({
							params	: {distributeAuthor	: distribute_author, distribute_date : date}
						});
						hideWaitMsg();
					},
					failure		: function(response) {
						
					}
				});
				/*
				var select = selects[0];
				var distributePanel = Ext.getCmp('distributePanel');
				var textFields = distributePanel.findByType('textfield');
				var numData = [];
				
				var date = select.get('distribute_date');
				var zhHardBox = select.get('zh_hardbox');
				var zhSoftBox = select.get('zh_softbox');
				var type = Ext.getCmp('distributeTypeRadio').getValue().getGroupValue();
				
				for(var i=0; i<textFields.length; i++) {
					var temp = {};
					var item = textFields[i];
					var author = item.author;
					var amount = item.getValue();
					temp.author = author;
					temp.amount = amount;
					numData.push(temp);
				}
				Ext.Ajax.request({
					url			: '../app/consumeDistribution.do?reqCode=distribute',
					params		: {date : date, type : type, distributeData : Ext.util.JSON.encode(numData),
						zhHardBox : zhHardBox, zhSoftBox : zhSoftBox},
					waitTitle	: '提示',
					waitMsg		: '正在加载数据，请稍后！',
					success		: function(response) {
						
					},
					failure		: function(response) {
						
					}
				});*/
			}
		}, '-']
	});
	
	var ratePanel = new Ext.Panel({
		height		: 250,
		frame		: true,
		autoScroll	: true,
		style		: 'margin : 0 0 0 2',
		border		: true,
		columnWidth	: .4,
		layout		: 'form',
		labelWidth	: 140,
		labelAlign	: 'right',
		buttonAlign	: 'center',
		items		: [ distributeRateGrid /*, {
			id			: 'distributePanel',
			layout		: 'column',
			xtype		: 'fieldset',
			anchor		: '100%',
			autoScroll	: true,
			title		: '分配数据',
			height		: 150,
			defaults	: {
				xtype		: 'panel',
				layout		: 'form',
				labelWidth	: 80
			},
			items		: [{
				columnWidth	: .5
			}, {
				columnWidth	: .5
			}]
		},*/ ]
	});
	
	var distributeAuthorComboStore = new Ext.data.Store({
		proxy		: new Ext.data.HttpProxy({
			method	:  'post',
			url		: '../app/consumeDistribution.do?reqCode=queryDistributeAuthor'
		}),
		autoLoad	: true,
		reader: new Ext.data.ArrayReader({}, [
			{name: 'userid'},
			{name: 'username'}
		])
	});
	var distributeAuthorCombo = new Ext.form.ComboBox({
		name		: 'distribute_author',
		hiddenName	: 'distribute_author',
		fieldLabel	: '被分配人',
		mode		: 'local',
		displayField: 'username',
		triggerAction: 'all',
		valueField	: 'userid',
		loadingText : '正在加载...',
		allowBlank	: false,
		editable	: false,
		store		: distributeAuthorComboStore
	});
	
	var addForm = new Ext.form.FormPanel({
		layout		: 'column',
		width		: 500,
		border		: false,
		bodyStyle	: 'padding: 5 2 2 2',
		defaults	: {
			columnWidth	: .5,
			xtype		: 'panel',
			defaultType	: 'textfield',
			layout		: 'form',
			border		: false,
			labelAlign	: 'right',
			labelWidth	: 70,
			defaults	: {
				anchor	: '99%'
			}
		},
		items		: [{
			items		: [{
				xtype		: 'hidden',
				id			: 'distribute_id',
				name		: 'distribute_id',
				fieldLabel	: 'ID'
			}, {
				name		: 'distribute_date',
				fieldLabel	: '分配日期',
				xtype		: 'datefield',
				format		: 'Y.m.d',
				value		: new Date(),
				allowBlank	: false
			}, {
				name		: 'zh_hardbox',
				fieldLabel	: '硬中华数量',
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: '0'
			}, {
				name		: 'unit',
				fieldLabel	: '单位'
			}]
		}, {
			
			items		: [distributeAuthorCombo, {
				name		: 'zh_softbox',
				fieldLabel	: '软中华数量',
				xtype			: 'numberfield',
				allowBlank		: false,
				decimalPrecision: 0,
				value			: '0'
			}, {
				name		: 'remark',
				fieldLabel	: '备注'
			}]
		}]
	});
	
	var addWin = new Ext.Window({
		title		: '录入消费额度',
		closeAction	: 'hide',
		width		: 500,
		resizable	: false,
		height		: 150,
		layout		: 'fit',
		items		: [addForm],
		buttons		: [{
			text	: '确定',
			handler	: function() {
				if(!addForm.getForm().isValid()) {
					return;
				}
				var distributeId = Ext.getCmp('distribute_id').getValue();
				if(distributeId) {
					addForm.getForm().submit({
						url			: '../app/consumeDistribution.do?reqCode=update',
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
						url			: '../app/consumeDistribution.do?reqCode=insert',
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
			}
		}, {
			text	: '取消',
			handler	: function() {
				addForm.getForm().reset();
				addWin.hide();
			}
		}]
	});
	
	var grid = new Ext.ux.BaseGrid({
		style		: 'margin : 2 2 2 2',
		region		: 'center',
		paging		: true,
		selModel	: new Ext.grid.RowSelectionModel(),
		noRn		: false,
		list_url	: '../app/consumeDistribution.do?reqCode=query',
		viewConfig	: {
			forceFit	: true
		},
		tbar		: [{
			id		: 'add_distribute_sum',
			text	: '新增',
			iconCls	: 'page_addIcon',
			handler	: function() {
				addWin.setTitle('录入消费额度');
				var distributeIdCmp = Ext.getCmp('distribute_id');
				var distributeId = distributeIdCmp.getValue();
				if(typeof(distributeId) != 'undefined') {
					addForm.getForm().getEl().dom.reset();
				} else {
					clearForm(addForm.getForm());
				}
				addForm.find('name', 'distribute_date')[0].setValue(new Date());
				addForm.find('name', 'zh_hardbox')[0].setValue(0);
				addForm.find('name', 'zh_softbox')[0].setValue(0);
				addWin.show();
			}
		}, {
			id		: 'up_distribute_sum',
			text	: '修改',
			iconCls	: 'page_edit_1Icon',
			handler	: function() {
				editInit();
			}
		},  {
			id		: 'del_distribute_sum',
			text	: '删除',
			iconCls	: 'page_delIcon',
			handler	: function() {
				delItem();
			}
		}, '->', {
			id			: 'distribute_date_start',
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
			id			: 'distribute_date_end',
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
			id		: 'queryParam',
			xtype	: 'textfield',
			style	: 'margin-left:5px',
			listeners	: {
				specialkey		: function(field, e) {
					if(e.getKey() == Ext.EventObject.ENTER) {
						query();
					}
				}
			}
		}, {
			text	: '查询',
			style	: 'margin-left:5px',
			iconCls	: 'previewIcon',
			handler	: function() {
				query();
			}
		},'-', {
			text	: '重置',
			iconCls	: 'arrow_refreshIcon',
			handler	: function() {
				Ext.getCmp('queryParam').setValue('');
				Ext.getCmp('distribute_date_start').setValue('');
				Ext.getCmp('distribute_date_end').setValue('');
			}
		}],
		structure	: [{
			header		: 'ID',
			name		: 'id',
			align		: 'center',
			hidden		: true
		}, {
			header		: '分配日期',
			name		: 'distribute_date',
			align		: 'center',
			width		: 160,
			sortable	: true
		}, {
			header		: '分配人',
			name		: 'username',
			align		: 'center',
			width		: 160,
			sortable	: true
		}, {
			header		: '硬中华数量',
			name		: 'zh_hardbox',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '软中华数量',
			name		: 'zh_softbox',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '单位',
			name		: 'unit',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '被分配人',
			name		: 'distribute_author_name',
			align		: 'center',
			width		: 150,
			sortable	: true
		}, {
			header		: '被分配人ID',
			name		: 'distribute_author',
			align		: 'center',
			hidden		: true,
			width		: 150
		}, {
			header		: '备注',
			name		: 'remark',
			align		: 'center',
			width		: 285,
			sortable	: true
		}]
	});
	grid.getStore().reload({
		params 	: {
			start : 0,
			limit : grid.getBottomToolbar().pageSize
		}
	});
	/*grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});*/
	
	grid.on('rowclick', function(grid, rowIndex, event) {
		queryDistributeData();
	});
	
	new Ext.Viewport({
		layout		: 'border',
		items		: [grid, {
			xtype			: 'panel',
			region			: 'south',
			title			: '分配',
			margins			: '5 0 4 2',
			titleCollapse	: true,
			height			: 250,
			collapsible		: true,
			collapsed   	: false,
			animCollapse	: true,
			bodyStyle		: 'padding:2 0 2 2',
			layout			: 'column',
			items			: [distributionGrid, distributeRateGrid]
		}]
	});
	
	function query() {
		grid.getStore().baseParams = {
			distribute_date_start	: Ext.getCmp('distribute_date_start').getValue(),
			distribute_date_end		: Ext.getCmp('distribute_date_end').getValue(),
			queryParam				: Ext.getCmp('queryParam').getValue()
		}
		grid.getStore().reload({
			params 	: {
				start : 0,
				limit : grid.getBottomToolbar().pageSize
			}
		});
	}
	
	function editInit() {
		var record = grid.getSelectionModel().getSelections();
		if(!record || record.length == 0) {
			Ext.Msg.alert('提示', '请选择一条记录！');	
			return;
		}
		Ext.getCmp('distribute_id').setValue(record[0].id);
		addForm.getForm().loadRecord(record[0]);
		addWin.setTitle('更新分配明细');
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
					url 	: '../app/consumeDistribution.do?reqCode=delete',
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
	//查询分配数据
	function queryDistributeData() {
		var selects = grid.getSelectionModel().getSelections();
		if(selects.length != 1) {
			Ext.Msg.alert('提示', '请选择一条分配记录！');
			return;
		}
		var select = selects[0];
		var distribute_author = select.get('distribute_author');
		var distribute_date = select.get('distribute_date');
		//var type = Ext.getCmp('distributeTypeRadio').getValue().getGroupValue();
		var radios = distributeRateGrid.getBottomToolbar().findByType('radio');
		var type = 0;
		for(var i=0; i<radios.length; i++) {
			var item = radios[i];
			if(item.checked===true){  
		    	type = item.inputValue;  
		    }  
		}
		distributeRateGrid.getStore().reload({
			params	: {distributeAuthor	: distribute_author, distributeType : type}
		});
		distributionGrid.getStore().reload({
			params	: {distributeAuthor	: distribute_author, distribute_date : distribute_date}
		});
		/*Ext.Ajax.request({
			url		: '../app/consumeDistribution.do?reqCode=querySubUserDistributeAmount',
			params	: {distributeAuthor	: distribute_author, distributeType : type},
			success	: function(response) {
				var data = response.responseText;
				var dataObj = eval('(' + data + ')');
				var distributePanel = Ext.getCmp('distributePanel');
				distributePanel.items.get(0).removeAll();
				distributePanel.items.get(1).removeAll();
				for(var i=0; i<dataObj.length; i++) {
					var sign = i % 2;
					var userId = dataObj[i].userid;
					var userName = dataObj[i].username;
					var amount = dataObj[i].amount;
					if(sign == 0) {
						distributePanel.items.get(0).add({			
							xtype		: 'textfield',
							fieldLabel	: userName,
							value		: amount,
							anchor		: '99%',
							author		: userId
						});
					} else {
						distributePanel.items.get(1).add({			
							xtype		: 'textfield',
							fieldLabel	: userName,
							value		: amount,
							anchor		: '99%',
							author		: userId
						});
					}
				}
				distributePanel.doLayout();
			},
			failure	: function(response) {
				Ext.Msg.alert('提示', '查询客户经理出错，请联系管理员！');
			}
		});*/
	}
	
	function changeAmountByType(field, checked) {
		if(checked) {
			var selects = grid.getSelectionModel().getSelections();
			if(selects.length != 1) {
				Ext.Msg.alert('提示', '请选择一条分配记录！');
				return;
			}
			var select = selects[0];
			var distribute_author = select.get('distribute_author');
			var type = field.inputValue;
			distributeRateGrid.getStore().reload({
				params	: {distributeAuthor	: distribute_author, distributeType : type}
			});
		}
	}
});








