
Ext.namespace('Ext.ux');
Ext.ux.BaseGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	//是否为主容器
	owner			: true,
	singleSelect 	: false,//是否单选
	loadMask 		: {  
        msg : '数据加载中，请稍候...'
    },
	structure 		: '',			//储存表格结构
	list_url		: '',			//获取数据url
	delete_url		: '',			//删除数据的url
	update_url		: '',			//保存数据的url
	displayInfo		: true,			//显示分页信息
	sm				: '',			//定义复选框
	noSm			: false,		//取消复选框
	rn				: '',			//定义序列
	noRn			: false,		//取消序列
	toolbar 		: '',			//定义工具条
	storeId 		: '',			//数据存储器ID
	paging 			: false,		//是否需要分页
	noPageButton 	: true,			//分不分页按钮
	pageLoadData 	: false,		//初始化加载数据
	isGroup			: false,		//是否分组
	groupField		: '',			//分组字段
	groupSort		: 'ASC',		
	initComponent: function() {
		var oCM = new Array(); // 列模式数组
		var oRecord = new Array(); // 容器对数组
		
		// 构成grid的两个必须组件: column和record，根据structure将这两个组件创建出来
		if(!this.noSm) {
			this.sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : this.singleSelect || false,
				handlerMouseDown: Ext.emptyFn/*,
				listeners:{'rowselect': function(s, n, r) {
					this.grid.getRowNumberer();
				}}*/
			});
			oCM[oCM.length] = this.sm;
		}
		
		if(!this.noRn) {
			this.rn = new Ext.grid.RowNumberer({
				header	:'序号',
				width	:40
			});
			oCM[oCM.length] = this.rn;
		}
		
		var len = this.structure.length;//得到结构的长度
		for(var i=0; i<len; i++) {
			var c = this.structure[i];
			
			//如果字段为hidden，则不生成filters和columnMode
			if(c.type == 'action') {
				oCM[oCM.length] = {
					xtype		:'actioncolumn',
					header		: c.header,
					handler		: c.handler,
					tooltip		: c.tooltip,
					icon		: c.icon,
					width		: c.width,
					scopt		: this,
					stopSelection: true,
					sortable	: false,
					dataIndex	: 'id',
					align		: 'center'
				};
			} else if(c.type == 'object') {
				for(var index=0; index<c.fields.length; index++) {
					var field = c.fields[index];
					oRecord[oRecord.length] = {
						name	: field.name,
						mapping	: c.mapping,
						mappingName: field.mappdingName,
						covert: function(v) {
							var m = this.mappingName;
							if(v && m.indexOf('.') > -1) {
								var array = m.split('.');
								var name = '';
								for(var i=0; i<array.length; i++) {
									name += '[\'' + array[i] + '\']';
								}
								var obj_val;
								try {
									obj_val = eval('v' + name);
								} catch(e) {
									obj_val = '';
								}
								return obj_val = '';
							} else if(v && m) {
								return v[m];
							} else {
								return v;
							}
						},
						renderer: field.renderer
					};
					oCM[oCM.length] = {
						header		: field.header,
						dataIndex	: field.name,
						hidden		: field.hidden || false,
						width		: field.width ? field.width: this.fieldwidth,
						renderer	: field.renderer
					};
				}
			} else {
				// 非Action, Object类型, 数据拼装
				oRecord[oRecord.length] = {
					name 		: c.name,
					mapping 	: c.mapping,
					convert 	: c.convert,
					defaultValue: c.defaultValue,
					type 		: c.type ? c.type : 'auto',
					dateFormat 	: c.dateFormat ? c.dateFormat : 'Y-m-d'
				};
				oCM[oCM.length] = {
					id 			: c.name,
					header 		: c.header,
					dataIndex 	: c.name,
					hidden 		: c.hidden || false,
					width 		: c.width ? c.width : this.fieldwidth,
					align		: c.align || 'center',
					fixed 		: c.fixed || false,
					renderer 	: c.renderer,
					sortable 	: c.sortable,
					editor 		: c.editor,
					menuDisabled: c.menuDisabled,
					summaryType : c.sumType,
					summaryRenderer : c.sumRenderer
				};
			}
		}
		this.colModel = oCM;
		//生成columnModel
		this.cm = new Ext.grid.ColumnModel(oCM);
		//默认可排序
		this.cm.defaultSortable = true;
		//生成表格数据容器
		var record = new Ext.data.Record.create(oRecord);
		
		var reader = new Ext.data.JsonReader({
			totalProperty	: 'TOTALCOUNT',
			root			: 'ROOT',
			idProperty		: this.keyField
		}, record);
		//创建store
		if(this.isGroup) {
			this.store = new Ext.data.GroupingStore({
				timeout		: 6000000,
				groupField	: this.groupField,
				storeId		: this.storeId,
				sortInfo	: {field: this.groupSortField, direction: this.groupSort},
				remoteSort	: this.remoteSort,
				proxy		: new Ext.data.HttpProxy({url: this.list_url}),
				reader		: reader
			})
		} else {
			this.store = new Ext.data.Store({
				timeout		: 6000000,
				storeId		: this.storeId,
				remoteSort	: this.remoteSort,
				proxy		: new Ext.data.HttpProxy({url: this.list_url}),
				reader		: reader
			})
		}
		
		//this.store.load();
		
		//顶部工具条
		if(this.toolbar && this.toolbar.length > 0) {
			var array = [];
			for(var i=0; i<this.toolbar.length; i++) {
				var id = this.toolbar[i].id;
				var items = this.toolbar[i].items;
				array[array.length] = new Ext.Toolbar({
					id: id,
					items: items
				});
			}
			var panel = new Ext.Panel({
				border: false,
				items: array
			});
			this.tbar = panel;
		}
		
		//分页工具条
		if(this.paging) {
			var pagesize_combo = new Ext.form.ComboBox({
				name 			: 'pagesize',
				hiddenName 		: 'pagesize',
				typeAhead 		: true,
				triggerAction 	: 'all',
				lazyRender 		: true,
				mode 			: 'local',
				valueField 		: 'value',
				displayField 	: 'text',
				value 			: '50',
				editable 		: false,
				width 			: 85,
				store 			: new Ext.data.ArrayStore({
					fields 	: ['value', 'text'],
					data 	: [
						[10, '10条/页'], 	[20, '20条/页'],
						[50, '50条/页'], 	[100, '100条/页'],
						[250, '250条/页'], 	[500, '500条/页']
					]
				})
			});
			var number = parseInt(pagesize_combo.getValue());
			
			var pagingToolbar = new Ext.PagingToolbar({//此组件需要单独声明，否则无法重新设置pagesize
				pageSize		: number,
				autoWidth		: true,
				store			: this.store,
				displayInfo		: this.displayInfo,
				displayMsg		: '显示第{0}条到{1}条记录，一共{2}条',
				emptyMsg		: '没有记录',
				beforePageText	: '第',
				afterPageText	: '页，总共{0}页',
				mode			: 'local',
				items 			: ['-', '&nbsp;&nbsp;', pagesize_combo]
			});
			
			pagesize_combo.on("select", function(comboBox) {
				pagingToolbar.pageSize = parseInt(comboBox.getValue());
				number = parseInt(comboBox.getValue());
				this.store.reload({
					params : {
						start : 0,
						limit : pagingToolbar.pageSize
					}
				});
			}, this);
			this.bbar = pagingToolbar;
			if(!this.noPageButton) {
				this.bbar.insert(11, '-',{
					text 		: '不分页',
	        		enableToggle: true,
	        		scope 		: this,
	        		handler 	: function(c) {
	        			var result = this.getStore();
						var totals = this.getStore().getTotalCount(); 
						if(c.pressed) {
							c.setText('分页');
						 	this.getBottomToolbar().pageSize = parseInt(totals);
						 	result.reload({params : {start : 0,limit : totals}});
						} else {
							c.setText('不分页');
							this.getBottomToolbar().pageSize = 20;
						 	result.reload({params : {start : 0,limit : this.pageSize}});
						}
	        		}					
				})
			}
			if(this.pageLoadData) {
				this.store.load({params:{start:0,limit:this.pageSize}})
			}
		}
		//过滤&排序
		/*this.store.on('beforeload', function(store, op) {
			store.baseParams.sort = this.sort ? this.sort:'dir';
			store.baseParams.dir = this.dir ? this.dir: 'desc';   
			if(this.joinPoint){
				store.baseParams.joinPoint = this.joinPoint;			
			}
			if (this.filter) {
				for (var p in this.filter) {
					store.setBaseParam(p, this.filter[p]);
				}
			}
		});*/
		Ext.applyIf(this, {
			animCollapse 	: false,
			stripeRows 		: true,
			resizeable 		: true,
			columnLines 	: true,
			forceLayout 	: true,
			loadMask 		: {
				msg : '加载中, 请稍后...'
			},
			viewConfig		: {
				autoFill		: true,
				scrollOffset	: 0,
				forceFit		: true,
				sortAscText		: '升序',
				sortDescText	: '降序',
				columnsText		: '显示的列'
			}
		});
		Ext.ux.BaseGrid.superclass.initComponent.call(this);
	},
	/**
	 * @功能：获得当前选中项的ID
	 */
	getSelectedId: function() {
		var selected = this.getSelectionModel().getSelections();
		if(selected) {
			if(selected.length == 1) {
				return selected[0].data.id;
			} else {
				var ids = [];
				for(var i=0; i<selected.length; i++) {
					ids.push(selected[i].data.id);
				}
				return ids;
			}
		}
		return '';
	},
	/**
	 * @功能: 获得当前选中项
	 */
	getSelected : function(){
		return this.getSelectionModel().getSelected();
	},
	
	/**
	 * @功能: 获得当前选中项 (复数)
	 */
	getSelections : function(){
		return this.getSelectionModel().getSelections();
	},
	/**
	 * @功能：创建窗口
	 */
	createWin: function(cfg) {
		var win;
		if(cfg.id) {
			win = Ext.getCmp(cfg.id);
			if(win) {
				return win;
			}
		}
		Ext.applyIf(cfg, {
			layout : 'fit',
			plain : false,
			border : true
		});
		win = new Ext.Window(cfg);
		var form = cfg.form;
		if(form) {
			form.module = this;
			form.win = win;
			win.add(form);
		}
		return win;
	},
	/**
	 * @功能: 从Store中移除选定项
	 */
	remove : function(){
		this.store.remove(this.getSelections());
	},
	/**
	 * @功能：批量删除
	 */
	del: function(id) {
		if(!this.delete_url) {
			alert('delete_url属性未定义！');
			return;
		}
		var ids = [];
		if(id) {
			ids.push(id);
		} else {
			var selected = this.getSelections();
			for(var i=0; i<selected.length; i++) {
				var item = selected[i].data;
				if(item.id) {
					ids.push(item.id);
				} else {
					this.store.remove(this.store.getAt(i));
				}
			}
		}
		if(ids.join('') == '') {
			Ext.MessageBox.alert('提示','请至少选择一条！');
			return;
		}
		Ext.Msg.confirm('提示', '确定要删除所选项吗？', function(btn) {
			if(btn == 'yes') {
				Ext.Ajax.request({
					url: this.delete_url,
					params:{ids:ids},
					scope: this,
					success: function(response) {
						//Ext.Msg.alert('提示','删除成功！');
						this.refresh();
					},
					failure: function() {}
				})
			}
		}, this);
	},
	//需传入{'args1':args}格式数据，args1为后台接受参数名字，args是后台接受的参数值
	research: function(args) {
		if(!args) {return false;}
		var baseParams = {
			params: {
				start: 0,
				limit:this.pageSize
			}
		}
		Ext.apply(baseParams.params,args);
		this.store.load(baseParams);
	},
	/**
	 * @功能: 刷新该Grid
	 */
	refresh : function() {
		var start = 0;
		var bbar = this.getBottomToolbar();
		if (bbar && bbar.inputItem && bbar.inputItem.value) {
			start = bbar.inputItem.value;
		}
		if (start) {
			start = start * this.pageSize - this.pageSize;
		}
		this.store.reload({
			params : {
				start : parseInt(start),
				limit : this.pageSize
			}
		});
	},
	stars : function(value) {
		var star = '';
		if(value != '--') {
			for(var i=0; i<value; i++) {
				star += '★';
			}
		} else {
			star = '--';
		}
		return star;
	}
})

Ext.reg('basegrid', Ext.ux.BaseGrid);

//月份选择
Ext.ux.MonthPicker=Ext.extend(
	Ext.form.Field,
	{
		getValue:function(){
			var arr=this.el.query("select");
			var year=arr[0].value;
			var month=arr[1].value;
			return year+"-"+month;
		},
		/**value 格式2000-07*/
		setValue:function(value){
			if(Ext.type(value)=="date")
			{
				var year=value.getFullYear();
				var rawMonth=value.getMonth()+1;
				console.log(rawMonth);
				var month=null;
				if(rawMonth<10)
					month="0"+rawMonth;
				else
					month=rawMonth;
				var arr=this.el.query("select");
				arr[0].value=year;
				arr[1].value=month;
			}
			else
				throw new Error();
		},
		defaultAutoCreate:function(){
			var result={tag:"div",children:[]};
			var year={tag:"select",children:[]};
			for(var i=2011;i<=2040;i++)
			{
				year.children.push({
					tag:"option",
					value:i,
					html:i
				})
			}
			var month={tag:"select",children:[]};
			for(var i=1;i<=12;i++)
			{
				if(i<10)
					i="0"+i;
				month.children.push({
					tag:"option",
					value:i,
					html:i
				});
			}
			result.children.push(year);
			result.children.push({tag:"span",html:"年"});
			result.children.push(month);
			result.children.push({tag:"span",html:"月"});
			return result; 
		}()
	}
)
Ext.reg("monthpicker",Ext.ux.MonthPicker);
/*
Ext.ux.PageSizePlugin = function() {
	Ext.ux.PageSizePlugin.superclass.constructor.call(this, {
		store : new Ext.data.SimpleStore({
			fields : ['text', 'value'],
			data : [['20', 20],['40',40],['60',60],['80',80],['100',100],['120',120]]
		}),
		model : 'local',
		displayField : 'text',
		valueField : 'value',
		editable : false,
		allowBlank : false,
		triggerAction : 'all',
		width : 45
	})
}

Ext.extend(Ext.ux.PageSizePlugin, Ext.form.ComboBox, {
	
	init : function(paging) {
		paging.on('render', this.onInitView, this);
	},
	
	onInitView : function(paging) {
		paging.add('-', this, '-');
		this.setValue(paging.pageSize);
		this.on('select', this.onPageSizeChanged, paging);
	},
	
	onPageSizeChanged : function(combo) {
		this.pageSize = parseInt(combo.getValue());
		this.doLoad(0);
	}
})*/














