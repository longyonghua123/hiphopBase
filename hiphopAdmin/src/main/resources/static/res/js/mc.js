//关闭菜单详情div
function clearDetailInfo_Div() {
	var divObj = document.getElementsByTagName("div");
	for ( var i = 0; i < divObj.length; i++) {
		if (divObj[i].getAttribute("ref") == "DetailInfo_Div") {
			divObj[i].style.display = "none";
		}
	}
}

function setFunctionPostion()
{
	var RightDivObj = document.getElementById("orderArea");
	var LeftDivObj = document.getElementById("china");
	var LeftDivObj2 = document.getElementById("english");
	var ScrollY = document.documentElement.scrollTop;//卷去内容高度
	var divheight =document.getElementById("orderArea").offsetHeight;//滚动层的高度
	var divheight2 =document.getElementById("china").offsetHeight;//滚动层的高度
	var bHeight= document.body.scrollHeight;
	var limitTop = bHeight-77;//foot块以上的页面高度	
		if(RightDivObj)
		{
			if(ScrollY > 67){
				if((ScrollY+divheight) < limitTop){
					RightDivObj.style.marginTop = ScrollY-62 +"px";
				}
			}
			else{
					RightDivObj.style.marginTop = 5+"px";	
			}
		}
		if(LeftDivObj)
		{
			if(ScrollY > 67){
				if((ScrollY+divheight2) < limitTop){
					LeftDivObj.style.marginTop = ScrollY-63 +"px";
				}
			}
			else{
					LeftDivObj.style.marginTop = 5+"px";
			}
		}
		if(LeftDivObj2)
		{
			if(ScrollY > 67){
				if((ScrollY+divheight3) < limitTop){
					LeftDivObj2.style.marginTop = ScrollY-63 +"px";
				}
			}
			else{
					LeftDivObj2.style.marginTop = 5+"px";
			}
		}
}
 
var BodyHeight;
window.onload = function()
{
	BodyHeight= document.body.scrollHeight;
}
window.onscroll = setFunctionPostion;