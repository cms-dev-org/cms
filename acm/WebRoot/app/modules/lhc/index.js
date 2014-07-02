Ext.onReady(function() {
	var rec_part = new Ext.data.Record.create([{
			name : 'buyer',
			type : 'string'
		}, {
			name : 'total',
			type : 'int'
		}, {
			name : 'name1',
			type : 'int'
		}, {
			name : 'name2',
			type : 'int'
		}, {
			name : 'name3',
			type : 'int'
		}, {
			name : 'name4',
			type : 'int'
		}, {
			name : 'name5',
			type : 'int'
		}, {
			name : 'name6',
			type : 'int'
		}, {
			name : 'name7',
			type : 'int'
		}, {
			name : 'name8',
			type : 'int'
		}, {
			name : 'name9',
			type : 'int'
		}, {
			name : 'name10',
			type : 'int'
		}, {
			name : 'name11',
			type : 'int'
		}, {
			name : 'name12',
			type : 'int'
		}, {
			name : 'name13',
			type : 'int'
		}, {
			name : 'name14',
			type : 'int'
		}, {
			name : 'name15',
			type : 'int'
		}, {
			name : 'name16',
			type : 'int'
		}, {
			name : 'name17',
			type : 'int'
		}, {
			name : 'name18',
			type : 'int'
		}, {
			name : 'name19',
			type : 'int'
		}, {
			name : 'name20',
			type : 'int'
		}, {
			name : 'name21',
			type : 'int'
		}, {
			name : 'name22',
			type : 'int'
		}, {
			name : 'name23',
			type : 'int'
		}, {
			name : 'name24',
			type : 'int'
		}, {
			name : 'name25',
			type : 'int'
		}, {
			name : 'name26',
			type : 'int'
		}, {
			name : 'name27',
			type : 'int'
		}, {
			name : 'name28',
			type : 'int'
		}, {
			name : 'name29',
			type : 'int'
		}, {
			name : 'name30',
			type : 'int'
		}, {
			name : 'name31',
			type : 'int'
		}, {
			name : 'name32',
			type : 'int'
		}, {
			name : 'name33',
			type : 'int'
		}, {
			name : 'name34',
			type : 'int'
		}, {
			name : 'name35',
			type : 'int'
		}, {
			name : 'name36',
			type : 'int'
		}, {
			name : 'name37',
			type : 'int'
		}, {
			name : 'name38',
			type : 'int'
		}, {
			name : 'name39',
			type : 'int'
		}, {
			name : 'name40',
			type : 'int'
		}, {
			name : 'name41',
			type : 'int'
		}, {
			name : 'name42',
			type : 'int'
		}, {
			name : 'name43',
			type : 'int'
		}, {
			name : 'name44',
			type : 'int'
		}, {
			name : 'name45',
			type : 'int'
		}, {
			name : 'name46',
			type : 'int'
		}, {
			name : 'name47',
			type : 'int'
		}, {
			name : 'name48',
			type : 'int'
		}, {
			name : 'name49',
			type : 'int'
		}]
	);
	
	var grid = new Ext.ux.BaseGrid({
		title		: '六合彩金额统计',
		width		: 1000,
		height		: 300,
		border		: true,
		noSm		: true,
		//noRn		: true,
		renderTo	: 'main_grid',
		clicksToEdit: 1,
		viewConfig	: {
			autoFill 	: false,
			scrollOffset: 220
		},
		tbar		: ['-', {
			text		: '新增',
			handler		: function() {
				addInit();
			}
		}, '-', {
			text		: '统计',
			handler		: function() {
				count();
			}
		}],
		structure	: [{
			header		: '删除',
			name		: 'delete',
			align		: 'center',
			width		: 100,
			sortable	: true,
			renderer	: function (value) {
				return "<a href='javascript:void(0);'><img src='../../../resource/image/ext/delete.png'/></a>";
			}
		}, {
			header		: '购买人',
			name		: 'buyer',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editor 		: new Ext.grid.GridEditor(new Ext.form.TextField({
				maxLength : 50
			}))
		}, {
			header		: '总计',
			name		: 'total',
			locked		: true,
			align		: 'center',
			width		: 100,
			sortable	: true,
			renderer: function(v, params, record){
				var name1 = record.data.name1;
				if(name1 == null) {name1 = 0};
				var name2 = record.data.name2;
				if(name2 == null) {name2 = 0};
                return name1 + name2;
            },
			/*editor 		: new Ext.grid.GridEditor(new Ext.form.NumberField({
				maxLength : 50
			}))*/
		}, {
			header		: '1',
			name		: 'name1',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '2',
			name		: 'name2',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '3',
			name		: 'name3',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '4',
			name		: 'name4',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '5',
			name		: 'name5',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '6',
			name		: 'name6',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '7',
			name		: 'name7',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '8',
			name		: 'name8',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '9',
			name		: 'name9',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '10',
			name		: 'name10',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '11',
			name		: 'name11',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '12',
			name		: 'name12',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '13',
			name		: 'name13',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '14',
			name		: 'name14',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '15',
			name		: 'name15',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '16',
			name		: 'name16',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '17',
			name		: 'name17',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '18',
			name		: 'name18',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '19',
			name		: 'name19',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '20',
			name		: 'name20',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '21',
			name		: 'name21',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '22',
			name		: 'name22',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '23',
			name		: 'name23',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '24',
			name		: 'name24',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '25',
			name		: 'name25',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '26',
			name		: 'name26',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '27',
			name		: 'name27',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '28',
			name		: 'name28',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '29',
			name		: 'name29',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '30',
			name		: 'name30',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '31',
			name		: 'name31',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '32',
			name		: 'name32',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '33',
			name		: 'name33',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '34',
			name		: 'name34',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '35',
			name		: 'name35',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '36',
			name		: 'name36',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '37',
			name		: 'name37',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '38',
			name		: 'name38',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '39',
			name		: 'name39',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '40',
			name		: 'name40',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '41',
			name		: 'name41',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '42',
			name		: 'name42',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '43',
			name		: 'name43',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '44',
			name		: 'name44',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '45',
			name		: 'name45',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '46',
			name		: 'name46',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '47',
			name		: 'name47',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '48',
			name		: 'name48',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '49',
			name		: 'name49',
			align		: 'center',
			width		: 100,
			sortable	: true,
			editable  	: true,
			editor	  	: new Ext.form.NumberField()
		}, {
			header		: '--',
			name		: 'name50',
			align		: 'center',
			width		: 100,
			sortable	: true
		}]
	});
	
	grid.on("cellclick", function(pGrid, rowIndex, columnIndex, e) {
		store = pGrid.getStore();
		var record = store.getAt(rowIndex);
		var fieldName = pGrid.getColumnModel()
				.getDataIndex(columnIndex);
		if (fieldName == 'delete' && columnIndex == 1) {
			var dirtytype = record.get('dirtytype');
			if (Ext.isEmpty(dirtytype)) {
				Ext.Msg.confirm('请确认', '你确认要删除当前对象吗?', function(btn,
								text) {
							if (btn == 'yes') {
								//delItem(record.get('partid'));
								store.remove(record);
							} else {
								return;
							}
						});
			} else {
				store.remove(record);

			}
		}
	});
	
	function addInit() {
		var rec = new rec_part({});
		grid.stopEditing();
		grid.getStore().insert(0, rec);
		grid.startEditing(0, 2);
		// store.getAt(0).dirty=true; //不起作用
		///store.getAt(0).set('cmpid', '不能为空,请输入');
		// 通过这种办法变相的将新增行标识为脏数据
	}
	
	function count() {
		
		var rec = new rec_part({});
		var store = grid.getStore();
		
		if(store.getAt(store.getTotalCount()) != null && store.getAt(store.getTotalCount()).get('buyer') == '总计') {
			return;
		}
		
		grid.stopEditing();
		store.insert(grid.getStore().getTotalCount(), rec);
		store.getAt(store.getTotalCount()).set('buyer', '总计');
		//grid.startEditing(0, 2);
	}
});