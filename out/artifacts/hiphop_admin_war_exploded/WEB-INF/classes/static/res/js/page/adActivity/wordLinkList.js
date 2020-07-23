function del(id){
	    	if(confirm("确认要删除吗？")){
	    		$.post('linkDel.action?id='+id,
	    		    function(msg) {
					if (msg == "OK") {
						alert("删除成功!"); 
						$("#form").submit();
					} else {
						alert("删除失败");
					}
				}, 'json');
	    	}
     }
  //修改功能：
    function bannerModify(bid,vid){
    	$("#wordLinkModifyLabel").text("修改文字链");
    	$.ajax({
    		type: "post",
    		url: "linkModify.action?id="+bid+"&versionId="+vid,
    		dataType: "html",
    		success: function(data){
    				$("#wordLinkModify").html(data);
    				$("#wordLinkModify").modal();
    		},
    		error: function(){}
    	});
    }
  
  //修改功能：
    function imageUpload(bid,vid){
    	$("#imageUploadLabel").text("文字链图片上传");
    	$.ajax({
    		type: "post",
    		url: "imageUpload.action?id="+bid+"&versionId="+vid,
    		dataType: "html",
    		success: function(data){
    				$("#imageUpload").html(data);
    				$("#imageUpload").modal();
    		},
    		error: function(){}
    	});
    }
  
  
 // 查询分类和产品
	function queryIClassInfo(){
		var versionId=$("#versionId option:selected").val();
		var bannerType = $("#bannerType").val();
		var position = $("#position").val('');
		channelChange();
		if(versionId=="0"){
			alert("请先选择版本");	
		}
		else{
			$("#form").submit();
		}	
	}
	
	//检验是否选择版本
	function addWordList(){
			var versionId=$("#versionId option:selected").val();
			if(versionId=="0"){
				alert("请先选择版本");
			}else{
				$.ajax({
		    		type: "post",
		    		url: "checkWordVersion.action?versionId="+versionId,
		    		dataType: "json",
		    		success: function(msg){
		    			if(msg=="OK"){
		    				$("#wordLinkAddLabel").text("新建文字链");
		    		    	$.ajax({
		    		    		type: "post",
		    		    		url: "linkAdd.action?versionId="+versionId,
		    		    		dataType: "html",
		    		    		success: function(data){
		    		    				$("#wordLinkAdd").html(data);
		    		    				$("#wordLinkAdd").modal();
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
	
	 function channelChange(){
		 	var msg;
			msg=$("#channelSel").val();
			if(msg=="点击进行多选"){
				msg="";
			}
			//alert(msg);
			$('#channelIdCopy').val(msg);			
		 }
			
    
	$(function() {
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
		var promoChannelIdSearch=$("#promoChannelIdSearch").val().split(",");
        $.each(promoChannelIdSearch,function(i, n){
			$("#promoChannelId").find("option[value='"+n+"']").prop("selected",true);
		});
	});
	
	function turnUp(id,isFirst){
		if(isFirst){
			alert("已经是第一个，无法上移");
		}
		else {
			var versionid = $("#versionId").val();
			window.location.href="turnUpWordLink.action?id="+id+"&versionId="+versionid;
		}
	}

	function turnDown(id,isLast){
		if(isLast){
			alert("已经是最后一个，无法下移");
		}
		else {
			var versionid = $("#versionId").val();
			window.location.href="turnDownWordLink.action?id="+id+"&versionId="+versionid;
		}
	}
	
	// 删除图片
	function deleteImage(id,versionId){
		if(confirm("确认要删除图片吗？")){
			$.post("wordLinkDeleteImage.action",{
				id:id,
				versionId:versionId
				},function(data) {
					if (data == 'OK'){
						alert("删除图片成功！");
						$("#form").submit();
				   }else{
					   alert("删除图片失败，请刷新页面！");
				   }
				},'json');
		}
	}