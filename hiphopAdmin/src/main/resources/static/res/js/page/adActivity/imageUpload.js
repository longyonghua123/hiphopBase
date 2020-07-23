$(function(){
	   var text="";  
	   $("input[name=channels]").each(function() {  
	       if ($(this).attr("checked")) {  
	           text += ","+$(this).val();  
	       }  
	   });  
	   
	   var channels = text.split(","); 
	   for(var i=0; i<channels.length;i++){	   
		   $("#channelTrCn"+channels[i]).show();
		   $("#channelTrEn"+channels[i]).show();
	   }
});

function save(){
	$("#Imageiform").submit();
}