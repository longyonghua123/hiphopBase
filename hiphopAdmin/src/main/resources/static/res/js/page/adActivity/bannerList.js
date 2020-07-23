function toPage(pageNo){
	 $("#pageNo").val(pageNo);
	 $("#bannerQueryFrom").submit();
}

 //修改功能：
 function bannerModifyModel(bid,vid){
		$("#bannerEditLabel").text("修改Banner");
    	$.ajax({
    		type: "post",
    		url: "bannerUpdate.action?bannerid="+bid+"&versionId="+vid,
    		dataType: "html",
    		success: function(data){
    				$("#bannerEdit").html(data);
    				$("#bannerEdit").modal();
    		},
    		error: function(){}
    	});
 }
 //删除功能，by id
 function bannerDelete(bid){
	if(confirm("确定要删除？")){
    $.post("bannerDelete.action", {
				bannerid : bid
			}, function(msg) {
				if (msg == "OK") {
					alert("删除成功!");
					$("#bannerQueryFrom").submit();
				} else {
					alert("删除失败,该图片没有过期!");
				}
			}, 'json');
		}
 }
//查询分类和产品
	function queryIClassInfo(){
		var versionId=$("#versionId option:selected").val();
		if(versionId=="0"){
			alert("请先选择版本");	
		}
		else{
			$("#bannerQueryFrom").submit();
		}
		
	}
	
	//检验是否选择版本
	function addBanner(){
		var check=$("#versionId option:selected").val();
		if(check=="0"){
			alert("请先选择版本");
		}
		else{
			var versionId=$("#versionId option:selected").val();
			$.ajax({
	    		type: "post",
	    		url: "checkBannerVersion.action?versionId="+versionId,
	    		dataType: "json",
	    		success: function(msg){
	    			if(msg=="OK"){
	    				$("#bannerAddLabel").text("新增Banner");
	    		    	$.ajax({
	    		    		type: "post",
	    		    		url: "bannerAdd.action?versionId="+versionId,
	    		    		dataType: "html",
	    		    		success: function(data){
	    		    				$("#bannerAdd").html(data);
	    		    				$("#bannerAdd").modal();
	    		    		},
	    		    		error: function(){}
	    		    	});
					}
					else {
						alert("该版本已提交UAT，不可新增");
					}
	    		},
	    		error: function(){}
	    	});
		}
	}

	function changeType() {
		var type = $("#bannerType").val();
		if (type == 2) {
			$("#picAddressC").show();
			$("#picAddressN").show();
		} else {
			$("#picAddressC").hide();
			$("#picAddressN").hide();
			$("#position").val('');
		}
	}
	
	 function channelChange(){
		 	var msg;
			msg=$("#channelSel").val();
			if(msg=="点击进行多选"){
				msg="";
			}
			$('#channelIdCopy').val(msg);			
		 }
	
	$(function() {
		var errerMessage = $("#errerMessage").val();
		if(errerMessage!=null && errerMessage!="" && errerMessage.indexOf("Banner")>=0){
			alert("上传Banner图片尺寸不能大于"+errerMessage.substring(6)+"!请重新录入")
		}else if(errerMessage!=null && errerMessage!="" && errerMessage.indexOf("SmallPic")>=0){
			alert("上传弹出小图尺寸不能大于"+errerMessage.substring(8)+"!请重新录入")
		}
		var bannerTypeValue = $("#bannerTypeValue").val();
		$("#bannerType").find("option[value='"+bannerTypeValue+"']").prop("selected",true);
		var positionValue = $("#positionValue").val();
		$("#position").find("option[value='"+positionValue+"']").prop("selected",true);
		changeType();	
		channelChange();
			$('#datetimepickerFrom').datetimepicker({
				language : 'zh-CN', // 汉化
				pickTime: false,
				inputMask: false
			});
			$('#enddatetimepickTo').datetimepicker({
				language : 'zh-CN', // 汉化
				pickTime: false,
				inputMask: false
			});
			$('#startdatetimepick').datetimepicker({
				language : 'zh-CN', // 汉化
				pickDate: false,
				hourStep: 1,
		        minuteStep: 15,
		        secondStep: 30,
		        inputMask: false
			});
			$('#enddatetimepick').datetimepicker({
				language : 'zh-CN', // 汉化
				pickDate: false,
				hourStep: 1,
		        minuteStep: 15,
		        secondStep: 30,
		        inputMask: false
			});
		var channelCopy = $("#promoChannelId").val().split(",");                
			$.each(channelCopy,function(i, n){			 
		    	$("#channelSel").find("option[value='"+n+"']").prop("selected",true);
		 });
	});