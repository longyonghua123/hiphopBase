function del(id){
	if(confirm("确认要删除吗？")){
		 $.post("hotDel.action", {
				id: id
			}, function(data) {
				if (data == "OK") {
						alert("删除成功!");
						$("#form").submit();
				}else{
					alert("删除失败");
				}
			}, 'json');	 
	}
}
//修改功能：
function modifyHot(bid,vid){
	$("#hotLinkModifyLabel").text("修改热链");
	$.ajax({
		type: "post",
		url: "hotModify.action?id="+bid+"&versionId="+vid,
		dataType: "html",
		success: function(data){
				$("#hotLinkModify").html(data);
				$("#hotLinkModify").modal();
		},
		error: function(){}
	});
}

function queryIClassInfo(){
	var check=$("#versionId option:selected").val();
	if(check=="0"){
		alert("请先选择版本");	
	}
	else{
		$("#form").submit();
	}
}

//检验是否选择版本
function addHotList(){
	var check=$("#versionId option:selected").val();
	if(check=="0"){
		alert("请先选择版本");
	}
	else{
		var versionId=$("#versionId option:selected").val();
		$("#hotLinkAddLabel").text("新增热链");
		$.ajax({
			type: "post",
			url: "hotLinkAdd.action?versionId="+versionId,
			dataType: "html",
			success: function(data){
					$("#hotLinkAdd").html(data);
					$("#hotLinkAdd").modal();
			},
			error: function(){}
		});
	}
}

$(function() {
	$('#datetimepickerFrom').datetimepicker({
		language : 'zh-CN', // 汉化
		pickTime: false
	});
	$('#enddatetimepickTo').datetimepicker({
		language : 'zh-CN', // 汉化
		pickTime: false
	});
	
});