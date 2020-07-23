var  checkZipRight = false; 
$(function(){
		$('#datetimepickerFromAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false
		});
		$('#enddatetimepickToAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false
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
		register_validation($('#iformModify'), $('#hotLinkModify .modal-body'));
		var holi=$("#holi").val();
		if(holi=="Y"){
			$("#radio3").prop("selected",true);
		}
		if(holi=="N"){
			$("#radio4").prop("selected",true);
		}
		var dayof=$("#dayof").val().split("");
		$("#dayOfWeek option").each(function(index,val){
			if(dayof[index]=="1"){
				$(this).prop("selected",true);
			}
			
		});
		//初始化分类
		 var iClassIdList = $("#iClassIdList").val();
		  $("select[name='linkedClassid'] option").each(function(){
			   if(iClassIdList.indexOf($(this).val())>-1){
			    	$(this).prop("selected",true);
			   }
			  });
		  
		var channelCopy=$("#promoChannelId").val().split(",");
		$("#promoChannelId option").each(function(index,val){
			for(var i=0;i<channelCopy.length;i++){
				if($(this).val()==channelCopy[i]){
					$(this).prop("selected",true);
				}
			}
		});
		
	    var relationTypeValue = $("#relationType").val();
		if(relationTypeValue == 2){
				$("#linkedclassidtr").hide();
				$("#linkedurltrZip").show();
				$("#radio2").prop("checked",true);
		}
	  //edit 2014/9/19 yangyong 展示字典时， 过滤字典表的channelId,如果channel为null时，显示为全选，为-1的时候显示为全不选，否则按照channelid的值来过滤。        
	    if($("#channelCopy").val()!=null){
	    	if($("#channelCopy").val() != '-1'){
	    		var channelCopy = $("#promoChannelId").val().split(",");  
	    		console.log(channelCopy);
	        	$.each(channelCopy,function(i, n){
	    			$("#promoChannelId").find("option[value='"+n+"']").prop("selected",true);
	    		});    
	    	} else{            	
	    		$("#promoChannelId option").each(function(index, val){ 
	    			$(this).prop("selected",false);
	    		}); 
	    	}
	    }else{
	    	$("#promoChannelId option").each(function(index, val){ 
	    		$(this).prop("selected",true);
	   		});  
	    }
 });

 /**
  * 清除星期的选择项目
  * 
  */
 	function cleanOption(flag) {
 		if (flag == 1) {
 			$("#dayOfWeek option:selected").prop("selected", false);
 		} else if (flag == 2) {
 			$("#promoChannelIds option:selected").prop("selected", false);
 		}
 	}
  //zip文件的校验
  function checkZip(){
 		var type = $('input:radio[name="relationType"]:checked').val();
 		var zipName = $("#upZipFile").val();
 		var length = zipName.length;
 		if(zipName != null && zipName != "" && type == 2 ){
 			if(zipName.charAt(length-3)=="z" && zipName.charAt(length-2)=="i" && zipName.charAt(length-1)== "p"){	    				
 				checkZipRight = true;			
 			}
 			    else{
 			    	alert("上传文件必须为zip文件");
 			    	checkZipRight = false;
 			    }
 			}
 		}
 	    	
 	function selectType() {
 		var type = $('input:radio[name="relationType"]:checked').val();
 		if (type == 1) {
 			$("#isSingle").show();
 			$("#linkedclassidtr").show();
 			$("#linkedurltrZip").hide();
 		} else if (type == 2) {
 			$("#radio6").attr("checked","checked");
 			$("#isSingle").hide();
 			$("#linkedclassidtr").hide();
 			$("#linkedurltrZip").show();
 		}
 	}
 	function save() {
 		var zipName = $("#upZipFile").val();
 		var linkedUrl = $("#linkedUrl").val();
 		var type = $('input:radio[name="relationType"]:checked').val();
 		var linkedclassid = $("#linkedclassid").val();
		if(!checkZipRight && $('input:radio[name="relationType"]:checked').val() == 2 && (zipName!=null&& zipName!="")){
				alert("上传的压缩文件必须为zip文件");
        }else if($("#validFromId").val()>$("#validToId").val()){
        	$("#validFromId").focus();
			alert("有效期开始日期不能大于有效期结束日期!");
        }else if($("#startTime").val()>$("#endTime").val()){
        	$("#startTime").focus();
			alert("有效开始时间不能大于有效结束时间!");
        }else{
 	  	var	access=$('#iformModify').validate().form();
 	  		if(access==true){
 	  		var dayOfWeekVal="";
 	  		$("#dayOfWeek option").each(function() {
 	  			if($(this).prop("selected")){
 	  					dayOfWeekVal+=$(this).val();
 	  					dayOfWeekVal+=",";
 	  			     }
 	  			});
 	  				var channelIdVal="";
 					$("#promoChannelIds option").each(function() {
 						if($(this).prop("selected")){
 							channelIdVal+=$(this).val();
 							channelIdVal+=",";
 						}
 					});

 	  				 $.post("checkHotLinkWithWordLink.action", {
 	  					 validFrom :$("#validFromId").val(),
 	  					 validTo :$("#validToId").val(),
 	  					 versionId :$("#versionId").val(),
 	  					 dayOfWeek :dayOfWeekVal,
 	  					channelIdVal:channelIdVal
 	  					}, function(msg) {
 	  						if (msg == "OK") {
 	  							var validFrom=$("#validFromId").val();      
    		    				var validFromDate= new Date(Date.parse(validFrom.replace(/-/g,"/")));
    		    				var  validTo = $("#validToId").val();
    		    				var validToDate= new Date(Date.parse(validTo.replace(/-/g,"/")));
    		    				$("#validFromId").val(validFromDate);
    		    				$("#validToId").val(validToDate);
 	  							$("#iformModify").attr("action","hotLinkSave.action").submit();
 	  						} else {
 	  							alert("相同时间段内已存在文字链!");
 	  							return;
 	  						}
 	  					}, 'json');
 	  			}
 	  		}   	
}