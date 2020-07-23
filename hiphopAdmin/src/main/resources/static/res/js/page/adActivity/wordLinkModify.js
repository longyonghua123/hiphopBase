$(function(){
	register_validation($('#iformModify'), $('#wordLinkModify .modal-body'));
	$('#datetimepickerFromModify').datetimepicker({
		language : 'zh-CN', // 汉化
		pickTime: false,
		inputMask: false
	});
	$('#enddatetimepickToModify').datetimepicker({
		language : 'zh-CN', // 汉化
		pickTime: false,
		inputMask: false
	});
	$('#startdatetimepickModify').datetimepicker({
		language : 'zh-CN', // 汉化
		pickDate: false,
		hourStep: 1,
        minuteStep: 15,
        secondStep: 30,
        inputMask: false
	});
	$('#enddatetimepickModify').datetimepicker({
		language : 'zh-CN', // 汉化
		pickDate: false,
		hourStep: 1,
        minuteStep: 15,
        secondStep: 30,
        inputMask: false
	});
    var relationTypeValue = $("#relationType").val();
	if(relationTypeValue == 2){
			$("#linkedclassidtr").hide();
			$("#linkedurltrZip").show();
			$("#radio2").attr("checked","checked");
	}
    
  //edit 2014/9/19 yangyong 展示字典时， 过滤字典表的channelId,如果channel为null时，显示为全选，为-1的时候显示为全不选，否则按照channelid的值来过滤。  
	if($("#channelCopy").val()!=null){
		if($("#channelCopy").val() != '-1'){
			var channelCopy = $("#promoChannelIdModify").val().split(",");
            $.each(channelCopy,function(i, n){
				$("#promoChannelIds").find("option[value='"+n+"']").prop("selected",true);
			});    
		} else{            	
        	$("#promoChannelIds option").each(function(index, val){ 
        		$(this).prop("selected",false);
        	}); 
        }
    }else{
    	$("#promoChannelIds option").each(function(index, val){ 
    		$(this).prop("selected",true);
    	});  
    }
	
	var dayof=$("#dayofWeekModify").val().split("");
	$("#dayOfWeek option").each(function(index,val){
		if(dayof[index]=="1"){
			$(this).prop("selected",true);
		}
		
	});
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
      checkZipRight = true;
	    	
	function selectType() {
		var type = $('input:radio[name="relationType"]:checked').val();
		if (type == 1) {
			$("#linkedclassidtr").show();
			$("#linkedurltrZip").hide();
		} else if (type == 2) {
			$("#linkedclassidtr").hide();
			$("#linkedurltrZip").show();
		}
	}
	function save() {
		var linkedUrl = $("#linkedUrl").val();
		var type = $('input:radio[name="relationType"]:checked').val();
		var linkedclassid = $("#linkedclassid").val();
		var zipName = $("#upZipFile").val();
		 if (type == 2 && !checkZipRight && (zipName!=null&& zipName!="")) {
			alert("上传的压缩文件必须为zip文件");
		 }else if($("#validFromId").val()>$("#validToId").val()){
         	$("#validFromId").focus();
			alert("有效期开始日期不能大于有效期结束日期!");
		 }else if($("#startTimeId").val()>$("#endTimeId").val()){
        	$("#startTimeId").focus();
			alert("有效开始时间不能大于有效结束时间!");
		 }else{
  			access=$('#iformModify').validate().form();
  			if(access==true){
  				var dayOfWeekVal="";
  				$("#dayOfWeek option").each(function() {
  					if($(this).attr("selected")){
  						dayOfWeekVal+=$(this).val();
  						dayOfWeekVal+=",";
  					}
  				});
  				var channelIdVal="";
				$("#promoChannelIds option").each(function() {
					if($(this).attr("selected")){
						channelIdVal+=$(this).val();
						channelIdVal+=",";
					}
				});

  				 $.post("checkWordLinkWithHotLink.action", {
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
  							$("#iformModify").submit();
  						} else {
  							alert("相同时间段内已存在热链!");
  							return;
  						}
  					}, 'json');
  			}
  		}   	
	}