<%@ page contentType="text/javascript; charset=UTF-8" %>

function editAction(listForm, radioName){
	if (!checkSelect(listForm,radioName)){
      alert("请选择一个条目");
      return false;      
    }else{    	
     eval("document"+"."+listForm).action="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?action=edit";
     return true;
    }
}

function checkSelect(listForm,radioName){
	 var isChecked = false;

	   if (eval("document"+"."+listForm+"."+radioName).checked){
	          isChecked = true;
	    }else{
	      for (i=0;i<eval("document"+"."+listForm+"."+radioName).length;i++){
	         if (eval("document"+"."+listForm+"."+radioName+ "["+i+"]").checked){
	           isChecked = true;
	           break;
	          }
	      }
	    }
	  return isChecked;
}

function delAction(listForm,radioName){
    if (!checkSelect(listForm,radioName)){
      alert("请选择一个条目");      
    }else{
       if (confirm( '删除该关注设置 ! \n\nAre you sure ? '))
        {
    	   eval("document"+"."+listForm).action="<%=request.getContextPath()%>/account/protected/sub/subSaveAction.shtml?action=delete";
           return true;
         }
    }
    return false;
}
