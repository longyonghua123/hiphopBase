function deleteMark(cornerMarkId){
			if(confirm("真的要删除吗？")){
	  			$.post("cornerMarkDelete.action", {
	  				cornerMarkId: cornerMarkId
				}, function(data) {
					if (data == "OK") {
						window.location.href="cornerMarkList.action";
					}else if(data == "USED"){
						alert("当前角标已经被使用，不能删除！");	
					}else {
						alert("删除失败!");
					}
				}, 'json');	 
			}
		}
		function upOrDownCornerMark(upOrDown,cornerMarkId){
			$.post("cornerMarkUpOrDown.action",{
				cornerMarkId:cornerMarkId,
   				logic:upOrDown
   			},function(data) {
				if (data == 'OK'){
					window.location.href="cornerMarkList.action";
				}else if(data == 'NONEXT'){
					alert("已经是最后一个，不能下移!");
				}else if(data == 'NOUPPER'){
					alert("已经是第一个，不能上移!");
			    }else{
			    	alert("修改排序失败，请刷新页面!");
			    }
   			},'json');
		}
		
		function addCornerMark(){
			$("#cornMarkAddLabel").text("新增角标");
	    	$.ajax({
	    		type: "post",
	    		url: "cornerMarkPreAdd.action",
	    		dataType: "html",
	    		success: function(data){
	    				$("#cornMarkAdd").html(data);
	    				$("#cornMarkAdd").modal();
	    		},
	    		error: function(){}
	    	});
		}
		
		function modifyCornerMark(cornerMarkId){
			$("#cornMarkModifyLabel").text("修改角标");
	    	$.ajax({
	    		type: "post",
	    		url: "cornerMarkPreModify.action?cornerMarkId="+cornerMarkId,
	    		dataType: "html",
	    		success: function(data){
	    				$("#cornMarkModify").html(data);
	    				$("#cornMarkModify").modal();
	    		},
	    		error: function(){}
	    	});
		}
		
		/**   
		*  页面加载等待页面 
		*/    
				      
		 window.onload = function(){     
			 endMask();
		 }     
		    
		 function startMask(){ 
			 var height = window.screen.height-180;     
				var width = window.screen.width;     
				 var leftW = 300;     
				 if(width>1200){     
				    leftW = 500;     
				 }else if(width>1000){     
				    leftW = 350;     
				 }else {     
				    leftW = 100;     
				 }     
				 var _html = "<div id='loading' style=\"position:absolute;left:0;width:100%;height:" + height   
				 + "px;top:0;z-index:2000;background:#000;opacity:0.5;\"><div style=\"position:absolute;  cursor1:wait;left:"+leftW+"px;top:200px;width:auto;height:16px;padding:12px 5px 10px 30px;\">正在加载，请等待...</div></div>";     
				 $("#ss").html(_html);  
		 }  
		   
		 function endMask(){  
		    var _mask = document.getElementById('loading');  
		    if(_mask!=null){  
		        _mask.parentNode.removeChild(_mask);  
		    }     
		 }
		 
		 $(function(){
			 startMask();
		 })