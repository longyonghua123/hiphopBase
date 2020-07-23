var base = function(){

	/**
	 * 打开新增或修改界面
	 */
	function openEntity(width, height,condition, cid){
		var actionName = $("#actionName").val();
		var diag = new top.Dialog();
		if(cid==null || cid=='' || cid=='undefind'){
			diag.Title = "新增类别";
			cid = '';
		}else{
			diag.Title = "修改类别";
		}
		if(condition==null || condition=='' || condition=='undefind'){
			condition = "";
		}
		diag.Width = width;
		diag.Height = height;
		diag.URL = "./biz/"+actionName+"-input.action?id=" + cid + condition;
		diag.show();
	}
	
	/**
	 * 打开编辑界面
	 */
	function doModify(width, height, condition,id){
		if($("input:checked").length==1){
			openEntity(width, height, condition, $("input:checked").val());
		}else if(id!=null && id!='' && id!='undefind'){
			openEntity(width, height, condition, id);
		}else{
			alert("请选择要修改的数据！");
			return false;
		}
	}
	
	/**
	 * 删除勾选的记录
	 */
	function doRemove(){
		var actionName = $("#actionName").val();
		var ids = "";
		$("input:checked").each(function(){
			ids += ","+$(this).val(); 
		})
		if(ids==''){
			alert("请选择要删除的数据！");
			return false;
		}else{
			if(window.confirm("您确定要删除已选中的这些数据吗？")){
				$.get("./biz/"+actionName+"-remove.action?"+(new Date()).getTime(),{ids:ids},function(){
					alert("删除成功！");
					location.reload();
				})
			}
		}
	}
	/**
	 * 根据id删除
	 */
	function doRemoveById(id){
		if(window.confirm("您确定要删除该数据吗？")){
			var actionName = $("#actionName").val();
			$.get("./biz/"+actionName+"-remove.action?"+(new Date()).getTime(),{ids:id},function(){
				alert("删除成功！");
				location.reload();
			})
		}
	}
	
	/**
	 * 改变状态
	 */
	function myChangeState(state,str){
		var actionName = $("#actionName").val();
		var ids = "";
		$("input:checked").each(function(){
			ids += ","+$(this).val(); 
		})
		if(ids==''){
			alert("请选择要"+str+"的数据！");
			return false;
		}else{
			if(window.confirm("您确定要"+str+"已选中的这些数据吗？")){
				$.get("./biz/"+actionName+"-changeState.action?"+(new Date()).getTime(),{ids:ids,state:state},function(){
					alert("更新成功！");
					location.reload();
				})
			}
		}
	}
	
	/**
	 * 改变单条记录的状态
	 */
	function changeStateById(state,str,id,obj){
		var actionName = $("#actionName").val();
		if(window.confirm("您确定要"+str+"已选中的这些数据吗？")){
			$.get("./biz/"+actionName+"-changeState.action?"+(new Date()).getTime(),{ids:id,state:state},function(){
				if(state=='1'){
					$(obj).closest("td").html("显示<span onclick=\"base.changeStateById('0', '隐藏','${classid}',this)\" class=\"aspan\">点击隐藏</span>");
				}else{
					$(obj).closest("td").html("隐藏<span onclick=\"base.changeStateById('1', '显示','${classid}',this)\" class=\"aspan\">点击显示</span>");
				}
				alert("更新成功！");
			})
		}
	}
	
	/**
	 * 打开产品列表界面
	 */
	function openProductListPage(width,height,superClassid,classid,isp){
		var actionName = $("#actionName").val();
		var diag = new top.Dialog();
		diag.Title = "关联产品";
		diag.Width = width;
		diag.Height = height;
		diag.URL = "./biz/"+actionName+"-productList.action?classid="+classid+"&superclassid=" + superClassid+"&isp="+isp;
		diag.show();
	}
	
	/**
	 * 打开导入界面
	 */
	function doImport(){	
		var actionName = $("#actionName").val();
		var diag = new top.Dialog();
		diag.Title = "得到输入值";	
		diag.URL = "chapter3-2-content2.html";
		diag.OKEvent = function(){
			var inputValue = $(diag.innerFrame.contentWindow.document).find("#importFile").val();
			$.get("./biz/"+actionName+"-importFile.action?"+(new Date()).getTime(),{filepath: inputValue},function(){
				top.frmright.window.location.reload();
			});
		};
		diag.Width = 400;
		diag.Height = 60;
		diag.show();	
		var doc=diag.innerFrame.contentWindow.document;
		doc.open();
		doc.write('<html><body><div style="font-size:12px">上传文件：<input id="importFile" type="file"/></div></body></html>') ;
		doc.close();

	}
	
	/**
	 * 校验必填字段是否为空 页面字段后面标记了红色星号（必填标记），自动查找出并验证是否为空。
	 * 允许客户端不调用此功能，因为对于比较复杂的页面，此自动验证无法完成。如加星号，但是隐藏的等。
	 */
	function validateForm() {
		var succ = true;
		$("label:contains('*')").each(function(i){
			var column_name = $(this).closest("tr").find("td:first").html().replace("：", "");
			var $td = $(this).closest("td");
			if($td.find(":text").length>0){
				if($td.find(".textinput").val()==''){
					alert(column_name+'不能为空！');
					succ = false;
					return false;
				}
			}
		});
		return succ;
	}
	
	/**
	 * 页面跳转
	 */
	function gotoPage(url){
		location.href = url;
	}
	
	return {
		newPage: function(width, height, condition){openEntity(width, height, condition);},
		editPage: function(width, height, condition){doModify(width, height, condition);},
		editPageById: function(width, height, condition,id){doModify(width, height, condition, id);},
		remove: function(){doRemove();},
		removeById: function(id){doRemoveById(id);},
		changeState: function(state,str){myChangeState(state,str);},
		changeStateById: function(state,str,id,obj){changeStateById(state,str,id,obj);},
		productList: function(width, height, superClassid,classid,isp){openProductListPage(width, height, superClassid, classid,isp);},
		importFile: function(){doImport();},
		validate: function(){return validateForm();},
		gotoPage: function(url){gotoPage(url);}
	}
}();