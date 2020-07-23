 $(function(){
		$('#datetimepickerFromAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false,
			inputMask: false
		});
		$('#enddatetimepickToAdd').datetimepicker({
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
		register_validation($('#iform'), $('#hotLinkAdd .modal-body'));
	//初始值
	  	$("#radio3").attr("checked","checked");
	  	$("#radio6").attr("checked","checked");
	  	$("#dayOfWeek option").each(function(index,val){
				$(this).attr("selected","selected");
		});
	  	$("#promoChannelId option").each(function(index,val){
			$(this).attr("selected","selected");
		});
		var dateSkin = "blue";
			try {
				dateSkin = themeColor;
			} catch (e) {
			}
			var succ = $("#succ").val();
			if (succ == 'OK') {
				top.Dialog.alert("保存成功!", function() {
					top.frmright.queryIClassInfo();
					top.Dialog.close();
				});
			}
 });
    	  	
	   var checkZipRight = false;
	  	  //提交表单
	  	  function save(){  
	  		    var type = $('input:radio[name="relationType"]:checked').val();
	  		    var linkedclassid = $("#linkedclassid").val();
	    		var linkedUrl = $("#linkedUrl").val();
	    		var zipName = $("#upZipFile").val();
	    		if(!checkZipRight && $('input:radio[name="relationType"]:checked').val() == 2 && (zipName!=null&& zipName!="")){
       					alert("上传的压缩文件必须为zip文件");
                }else if($("#validFromId").val()>$("#validToId").val()){
                	$("#validFromId").focus();
        			alert("有效期开始日期不能大于有效期结束日期!");
                }else if($("#startTime").val()>$("#endTime").val()){
                	$("#startTime").focus();
        			alert("有效开始时间不能大于有效结束时间!");
                }else{
	    			access=$('#iform').validate().form();
	    			if(access==true){
	    				var dayOfWeekVal="";
	    				$("#dayOfWeek option").each(function() {
	    					if($(this).prop("selected")){
	    						dayOfWeekVal+=$(this).val();
	    						dayOfWeekVal+=",";
	    					}
	    				});
	    				var channelIdVal="";
	    				$("#promoChannelId option").each(function() {
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
	    					 channelIdVal :channelIdVal 
	    					}, function(msg) {
	    						if (msg == "OK") {
	    		    				var validFrom=$("#validFromId").val();      
	    		    				var validFromDate= new Date(Date.parse(validFrom.replace(/-/g,"/")));
	    		    				var  validTo = $("#validToId").val();
	    		    				var validToDate= new Date(Date.parse(validTo.replace(/-/g,"/")));
	    		    				$("#validFromId").val(validFromDate);
	    		    				$("#validToId").val(validToDate);
	    							$("#iform").submit();
	    						} else {
	    							alert("相同时间段内已存在文字链!");
	    							return;
	    						}
	    					}, 'json');
	    			}
	    		}   	
	    	}
	  	  var zipName = $("#upZipFile").val();
	  	  //zip文件的校验
	  	  /**
	 * 清除星期的选择项目
	 * 
	 */
		function cleanOption(flag) {
			if (flag == 1) {
				$("#dayOfWeek option:selected").prop("selected", false);
			} else if (flag == 2) {
				$("#promoChannelId option:selected").prop("selected", false);
			}
		}
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

	//类别与路径的切换
	function selectType() {
		var type = $('input:radio[name="relationType"]:checked').val();
		if (type == 1) {
			$("#linkedclassidtr").show();
			$("#linkedurltr").hide();
			$("#linkedurltrZip").hide();
		} else if (type == 2) {
			$("#radio6").attr("checked","checked");
			$("#linkedclassidtr").hide();
			$("#linkedurltr").show();
			$("#linkedurltrZip").show();
			
		}
	}